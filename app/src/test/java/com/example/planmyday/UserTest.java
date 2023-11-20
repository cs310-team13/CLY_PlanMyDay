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

    @Test
    public void testSetters() {
        User user = new User();
        user.setNickname("NewNickname");
        user.setEmail("newemail@example.com");
        user.setDateOfBirth("02/02/1990");

        assertEquals("Nickname should be updated", "NewNickname", user.getNickname());
        assertEquals("Email should be updated", "newemail@example.com", user.getEmail());
        assertEquals("Date of birth should be updated", "02/02/1990", user.getDateOfBirth());
    }


}