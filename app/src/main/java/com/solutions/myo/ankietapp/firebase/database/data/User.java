package com.solutions.myo.ankietapp.firebase.database.data;

/**
 * Created by Jacek on 2017-01-12.
 */

public class User {

    public String username;
    public String email;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

}
