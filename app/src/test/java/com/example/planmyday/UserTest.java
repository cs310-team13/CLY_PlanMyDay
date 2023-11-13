package com.example.planmyday;

import static org.junit.Assert.*;
import org.junit.Test;

public class UserTest {

    @Test
    public void testUserConstructorAndGetters() {
        String nickname = "TestUser";
        String email = "testuser@example.com";
        String dateOfBirth = "01/01/1990";

        User user = new User(nickname, email, dateOfBirth);

        assertEquals("Nickname should match", nickname, user.getNickname());
        assertEquals("Email should match", email, user.getEmail());
        assertEquals("Date of birth should match", dateOfBirth, user.getDateOfBirth());
    }
}