package com.developerrr.fitnesstracker.utils;

public class Qoute {

    String category;
    String mode;
    String message;
    String auther;

    public String getAuther() {
        return auther;
    }

    public void setAuther(String auther) {
        this.auther = auther;
    }

    public Qoute() {
    }

    public Qoute(String category, String mode, String message, String auther) {
        this.category = category;
        this.mode = mode;
        this.message = message;
        this.auther = auther;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
