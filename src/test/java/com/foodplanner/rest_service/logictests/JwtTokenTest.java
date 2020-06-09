package com.foodplanner.rest_service.logictests;
import com.foodplanner.rest_service.logic.jwt.JwtTokenProvider;
import io.jsonwebtoken.JwtException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class JwtTokenTest {

    JwtTokenProvider provider;

    @BeforeEach
    public void init(){
        provider = new JwtTokenProvider();
    }

    @Test
    public void validTokenTest(){
        String token = provider.createToken(1, "test", "role", "rico@test.nl");

        assertThat(provider.validateToken(token)).isEqualTo(true);
    }

    @Test
    public void notValidTokenTest(){
        String token = "testToken";
        assertThrows(JwtException.class, () -> provider.validateToken(token));
    }

    @Test
    public void getEmailFromToken_Happy(){
        String token = provider.createToken(1, "test", "role", "rico@test.nl");
        assertThat(provider.getEmailFromToken(token)).isEqualTo("rico@test.nl");
    }

    @Test
    public void getEmailFromToken_Sad(){
        String token = provider.createToken(1, "test", "role", "rico@test.nl");
        assertThat(provider.getEmailFromToken(token)).isNotEqualTo("test@test.nl");
    }

    @Test
    public void getUserIdFromToken_Happy(){
        String token = provider.createToken(1, "test", "role", "rico@test.nl");
        assertThat(provider.getUserIdFromToken(token)).isEqualTo(1);
    }

    @Test
    public void getUserIdFromToken_Sad(){
        String token = provider.createToken(1, "test", "role", "rico@test.nl");
        assertThat(provider.getUserIdFromToken(token)).isNotEqualTo(2);
    }

    @Test
    public void getRoleFromToken_Happy(){
        String token = provider.createToken(1, "test", "role", "rico@test.nl");
        assertThat(provider.getRoleFromToken(token)).isEqualTo("role");
    }

    @Test
    public void getRoleFromToken_Sad(){
        String token = provider.createToken(1, "test", "admin", "rico@test.nl");
        assertThat(provider.getRoleFromToken(token)).isNotEqualTo("employee");
    }

    @Test
    public void getUsernameFromToken_Happy(){
        String token = provider.createToken(1, "test", "admin", "rico@test.nl");
        assertThat(provider.getUsernameFromToken(token)).isEqualTo("test");
    }

    @Test
    public void getUsernameFromToken_Sad(){
        String token = provider.createToken(1, "rico", "admin", "rico@test.nl");
        assertThat(provider.getUsernameFromToken(token)).isNotEqualTo("test");
    }
}
