package main.domain;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserDomainUnitTest {

    @Test
    public void createUserTest() {
        User user = new User(1L, "test", "test@test.com", "test", "token");
        assertEquals(1L, user.getId());
        assertEquals("test", user.getUsername());
        assertEquals("test@test.com", user.getEmail());
        assertEquals("test", user.getPassword());
        assertEquals("token", user.getToken());
    }

    @Test
    public void createAndChangeUserTest() {
        User user = new User(1L, "test", "test@test.com", "test", "token");

        user.setId(2L);
        assertEquals(2L, user.getId());

        user.setEmail("modificat@test.com");
        assertEquals("modificat@test.com", user.getEmail());

        user.setUsername("username");
        assertEquals("username", user.getUsername());

        user.setPassword("password");
        assertEquals("password", user.getPassword());

        user.setToken("updatedToken");
        assertEquals("updatedToken", user.getToken());
    }
}
