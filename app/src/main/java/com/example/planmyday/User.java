package com.example.planmyday;

public class User {
    private String nickname;
    private String email;
    private String dateOfBirth;

    // Default constructor required for calls to DataSnapshot.getValue(User.class)
    public User() {}

    public User(String nickname, String email, String dateOfBirth) {
        this.nickname = nickname;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
    }

    // Getter and setter methods
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getDateOfBirth() { return dateOfBirth; }

    public void setDateOfBirth(String dateOfBirth) { this.dateOfBirth = dateOfBirth; }
}
