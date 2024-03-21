package com.example.ecowheelz.Repository;

public class User {

    private String userName, email, fullName, phoneNumber, row_id, homeLocation, workLocation;


    public User(String userName, String email, String fullName, String phoneNumber, String rowid, String homeloc, String workloc) {
        this.userName = userName;
        this.email = email;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.row_id = rowid;
        this.homeLocation = homeloc;
        this.workLocation = workloc;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getRow_id() {
        return row_id;
    }

    public void setRow_id(String row_id) {
        this.row_id = row_id;
    }

    public String getHomeLocation() {
        return homeLocation;
    }

    public void setHomeLocation(String homeLocation) {
        this.homeLocation = homeLocation;
    }

    public String getWorkLocation() {
        return workLocation;
    }

    public void setWorkLocation(String workLocation) {
        this.workLocation = workLocation;
    }
}
