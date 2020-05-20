package com.foodplanner.rest_service.controller;

import com.foodplanner.rest_service.databasemodel.Role;
import com.foodplanner.rest_service.databasemodel.Scoreboard;
import com.foodplanner.rest_service.databasemodel.User;
import com.foodplanner.rest_service.dtos.LoginDTO;
import com.foodplanner.rest_service.dtos.LoginReturnDTO;
import com.foodplanner.rest_service.endpoints.AuthEndpoint;
import com.foodplanner.rest_service.exceptions.FileNotWritable;
import com.foodplanner.rest_service.ldap.Person;
import com.foodplanner.rest_service.ldap.PersonRepository;
import com.foodplanner.rest_service.logic.jwt.JwtTokenProvider;
import com.foodplanner.rest_service.repositories.RoleRepository;
import com.foodplanner.rest_service.repositories.UserRepository;
import com.foodplanner.rest_service.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RequestMapping("/auth")
@RestController
@CrossOrigin
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private PersonRepository ldapRepository;

    @Autowired
    private RoleRepository roleRepository;

    User user = new User();

    @PostMapping(value = AuthEndpoint.LOGIN)
    public ResponseEntity<?> loginUser(@RequestBody LoginDTO dto) {
        LoginReturnDTO returnDTO = new LoginReturnDTO();
        if(ldapRepository.authenticateByEmail(dto.getEmail(), dto.getPassword())) {
            List<Person> ps = ldapRepository.findByEmail(dto.getEmail());
            Person p = ps.get(0);
            User dbUser = userRepository.findByEmail(dto.getEmail());
            List<Link> links = getLoginLinks(dbUser.getRole());
                if (dbUser != null) {
                    returnDTO.setToken(jwtTokenProvider.createToken(dbUser.getId(), dbUser.getName(), dbUser.getRole().getName(), dto.getEmail()));
                    return new ResponseEntity<>(returnDTO.add(links), HttpStatus.OK);
                } else {
                    setNewUser(dto.getEmail(), p.getFullName(), "ROLE_EMPLOYEE");
                    userRepository.save(user);
                    returnDTO.setToken(jwtTokenProvider.createToken(user.getId(), user.getName(), user.getRole().getName(), dto.getEmail()));
                    return new ResponseEntity<>(returnDTO.add(links), HttpStatus.OK); // return with token
                }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    public void setNewUser(String email, String name, String role){
        user.setEmail(email);
        user.setName(name);
        user.setRole(roleRepository.findByName(role));
        user.setScoreboard(new Scoreboard(0L, 0L, user));
    }


    /**
     * @param role Role class for getting the user role
     * @return a list of the correct links based on the user his/her role
     */
    private List<Link> getLoginLinks(Role role){
        List<Link> links = new ArrayList<>();

        //region default links init
        links.add(linkTo(methodOn(UserController.class).loginUser(null)).withSelfRel());
        links.add(linkTo(methodOn(FoodOrderController.class).getFoodOrdersByUserID(null)).withRel("by_user_id").withProfile("food_order"));
        links.add(linkTo(methodOn(FoodOrderController.class).getFoodOrdersPerWeek(null, null)).withRel("per_week").withProfile("food_order"));
        links.add(linkTo(methodOn(FoodOrderController.class).getCurrentPrice()).withRel("get_price").withProfile("food_order"));
        links.add(linkTo(methodOn(ScoreBoardController.class).getScoreboardInTime()).withRel("get_scoreboard_in_time").withProfile("scoreboard"));
        links.add(linkTo(methodOn(ScoreBoardController.class).getScoreboardMostEaten()).withRel("get_scoreboard_most_eaten").withProfile("scoreboard"));
        links.add(linkTo(methodOn(ScoreBoardController.class).getScoreboardTooLate()).withRel("get_scoreboard_too_late").withProfile("scoreboard"));
        //endregion

        //Links for secretary and admin roles
        if(role.getName().equals("ROLE_SECRETARY") || role.getName().equals("ROLE_ADMIN")){
            links.add(linkTo(methodOn(SecretaryController.class).getUsersBetweenDates(null, null)).withRel("get_users_between_dates").withProfile("secretary"));
            links.add(linkTo(methodOn(SecretaryController.class).getUsersByDate(null)).withRel("get_users_by_date").withProfile("secretary"));
        }
        //Links for admin roles
        if(role.getName().equals("ROLE_ADMIN")){
            links.add(linkTo(methodOn(AdministrationController.class).getAllUsers()).withRel("get_all_users").withProfile("admin"));
            try {
                links.add(linkTo(methodOn(AdministrationController.class).updatePrice(null)).withRel("update_price").withProfile("admin"));
            } catch (FileNotWritable fileNotWritable) {
                fileNotWritable.printStackTrace();
            }
        }

        return links;
    }

}