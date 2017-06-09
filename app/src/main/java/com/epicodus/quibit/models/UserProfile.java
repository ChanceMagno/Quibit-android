package com.epicodus.quibit.models;



public class UserProfile {
        public String getDisplayName() {
        return displayName;
    }

    public UserProfile(String displayName, String email) {
        this.displayName = displayName;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    String displayName;
    String email;

}
