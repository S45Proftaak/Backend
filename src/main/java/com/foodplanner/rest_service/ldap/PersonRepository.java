package com.foodplanner.rest_service.ldap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.DistinguishedName;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.AbstractContextMapper;
import org.springframework.ldap.core.support.BaseLdapNameAware;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.ldap.query.LdapQuery;
import org.springframework.ldap.support.LdapNameBuilder;
import org.springframework.ldap.support.LdapUtils;
import org.springframework.stereotype.Service;

import javax.naming.Name;
import javax.naming.directory.*;
import javax.naming.ldap.LdapName;
import java.util.List;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

@Service
public class PersonRepository implements BaseLdapNameAware {

    @Autowired
    private LdapTemplate ldapTemplate;
    private LdapName baseLdapPath;

    public void setBaseLdapPath(LdapName baseLdapPath) {
        this.baseLdapPath = baseLdapPath;
    }


    public void create(Person p) {
        Name dn = buildDn(p);
        ldapTemplate.bind(dn, null, buildAttributes(p));
    }

    public List<Person> findAll() {
        EqualsFilter filter = new EqualsFilter("objectclass", "person");
        return ldapTemplate.search(LdapUtils.emptyLdapName(), filter.encode(), new PersonContextMapper());
    }

    public Person findOne(String uid) {
        Name dn = LdapNameBuilder.newInstance(baseLdapPath)
                .add("ou", "people")
                .add("uid", uid)
                .build();
        return ldapTemplate.lookup(dn, new PersonContextMapper());
    }

    public List<Person> findByName(String name) {
        LdapQuery q = query()
                .where("objectclass").is("person")
                .and("cn").whitespaceWildcardsLike(name);
        return ldapTemplate.search(q, new PersonContextMapper());
    }

    public List<Person> findByEmail(String email)
    {
        LdapQuery q = query()
                .where("objectclass").is("person")
                .and("email").whitespaceWildcardsLike(email);
        return ldapTemplate.search(q, new PersonContextMapper());
    }

    public Boolean authenticateByEmail(String email, String password)
    {
        AndFilter filter = new AndFilter();
        filter.and(new EqualsFilter("objectclass", "person")).and(new EqualsFilter("email", email));
        return ldapTemplate.authenticate(DistinguishedName.EMPTY_PATH, filter.toString(), password);
    }

    public void update(Person p) {
        ldapTemplate.rebind(buildDn(p), null, buildAttributes(p));
    }

    public void updateLastName(Person p) {
        Attribute attr = new BasicAttribute("sn", p.getLastName());
        ModificationItem item = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, attr);
        ldapTemplate.modifyAttributes(buildDn(p), new ModificationItem[] {item});
    }

    public void delete(Person p) {
        ldapTemplate.unbind(buildDn(p));
    }

    private Name buildDn(Person p) {
        return LdapNameBuilder.newInstance(baseLdapPath)
                .add("ou", "people")
                .add("uid", p.getUid())
                .build();
    }

    private Attributes buildAttributes(Person p) {
        Attributes attrs = new BasicAttributes();
        BasicAttribute ocAttr = new BasicAttribute("objectclass");
        ocAttr.add("top");
        ocAttr.add("person");
        attrs.put(ocAttr);
        attrs.put("ou", "people");
        attrs.put("uid", p.getUid());
        attrs.put("cn", p.getFullName());
        attrs.put("sn", p.getLastName());
        attrs.put("password", p.getPassword());
        return attrs;
    }


    private static class PersonContextMapper extends AbstractContextMapper<Person> {
        public Person doMapFromContext(DirContextOperations context) {
            Person person = new Person();
            person.setFullName(context.getStringAttribute("cn"));
            person.setLastName(context.getStringAttribute("sn"));
            person.setUid(context.getStringAttribute("uid"));
            person.setPassword(context.getStringAttribute("password"));
            return person;
        }
    }
}
