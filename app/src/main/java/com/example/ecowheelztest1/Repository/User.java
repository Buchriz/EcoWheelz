package com.example.ecowheelztest1.Repository;

public class User {

    private static String userName, email, fullName;
    private static int phoneNumber;

    public User(String userName, String email, String fullName, int phoneNumber) {
        this.userName = userName;
        this.email = email;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
    }

    public static String getUserName() {
        return userName;
    }

    public static void setUserName(String userName) {
        User.userName = userName;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        User.email = email;
    }

    public static String getFullName() {
        return fullName;
    }

    public static void setFullName(String fullName) {
        User.fullName = fullName;
    }

    public static int getPhoneNumber() {
        return phoneNumber;
    }

    public static void setPhoneNumber(int phoneNumber) {
        User.phoneNumber = phoneNumber;
    }
}
