package com.developerrr.fitnesstracker.models;

import java.util.ArrayList;

public class Profile {

    private String firstName;
    private String lastName;
    private String country;
    private String username;
    private String password;
    private long dbId;

    public Profile (String firstName, String lastName, String country, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.country = country;
        this.username = username;
        this.password = password;
    }

    public Profile (String firstName, String lastName, String country, String username, String password, long dbId) {
        this(firstName, lastName, country, username, password);
        this.dbId = dbId;
    }

    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public String getCountry() {
        return country;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }

    public long getDbId() { return dbId; }
    public void setDbId(long dbId) { this.dbId = dbId; }



}
