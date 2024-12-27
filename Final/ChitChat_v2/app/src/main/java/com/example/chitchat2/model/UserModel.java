package com.example.chitchat2.model;

import com.google.firebase.Timestamp;

public class UserModel {
    private String phoneNo;
    private String username;
    private Timestamp createdTimestamp;
    private String userID;

    public UserModel() {
    }

    public UserModel(Timestamp createdTimestamp, String phoneNo, String username, String userID) {
        this.createdTimestamp = createdTimestamp;
        this.phoneNo = phoneNo;
        this.username = username;
        this.userID = userID;
    }

    public Timestamp getCreatedTimestamp() {
        return createdTimestamp;
    }

    public void setCreatedTimestamp(Timestamp createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
