package com.example.ecowheelztest1.Repository;

public class User {

    private static String userName, email, fullName, phoneNumber;

    public User(String userName, String email, String fullName, String phoneNumber) {
        this.userName = userName;
        this.email = email;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        User.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        User.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        User.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        User.phoneNumber = phoneNumber;
    }
}
