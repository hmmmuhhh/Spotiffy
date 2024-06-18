package com.example.spotiffy;

import java.util.HashMap;
import java.util.Map;

public class UserStore {
    private static final Map<String, String> users = new HashMap<>();
    private static final Map<String, User> userDetails = new HashMap<>();

    public static boolean signup(String firstName, String lastName, String email, String username, String password) {
        if (users.containsKey(username) || !isValidEmail(email)) {
            return false;
        }
       users.put(username, password);
        userDetails.put(username, new User(firstName, lastName, email, username));
        return true;
    }

    public static boolean login(String username, String password) {
        return users.containsKey(username) && users.get(username).equals(password);
    }

    private static boolean isValidEmail(String email) {
        return email.contains("@") && email.contains(".");
    }

    private static class User {
        String firstName;
        String lastName;
        String email;
        String username;

        User(String firstName, String lastName, String email, String username) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.email = email;
            this.username = username;
        }
    }
}
