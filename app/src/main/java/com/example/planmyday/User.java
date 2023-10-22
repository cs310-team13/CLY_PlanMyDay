package com.example.planmyday;

public class User {
    private String nickname;
    private String email;

    // Default constructor required for calls to DataSnapshot.getValue(User.class)
    public User() {}

    public User(String nickname, String email) {
        this.nickname = nickname;
        this.email = email;
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
}
