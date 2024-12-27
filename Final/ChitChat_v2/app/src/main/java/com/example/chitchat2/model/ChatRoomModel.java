package com.example.chitchat2.model;

import com.google.firebase.Timestamp;
import java.util.List;

public class ChatRoomModel {
    String chatRoomID, lastMessageSenderID;
    List<String> userIDs;
    Timestamp lastMessageTime;
    String lastMessage;

    public ChatRoomModel() {
    }

    public ChatRoomModel(String chatRoomID, String lastMessegeSenderID, Timestamp lastMessegeTime, List<String> userIDs) {
        this.chatRoomID = chatRoomID;
        this.lastMessageSenderID = lastMessegeSenderID;
        this.lastMessageTime = lastMessegeTime;
        this.userIDs = userIDs;
    }

    public String getChatRoomID() {
        return chatRoomID;
    }

    public void setChatRoomID(String chatRoomID) {
        this.chatRoomID = chatRoomID;
    }

    public String getLastMessegeSenderID() {
        return lastMessageSenderID;
    }

    public void setLastMessegeSenderID(String lastMessegeSenderID) {
        this.lastMessageSenderID = lastMessegeSenderID;
    }

    public Timestamp getLastMessegeTime() {
        return lastMessageTime;
    }

    public void setLastMessegeTime(Timestamp lastMessegeTime) {
        this.lastMessageTime = lastMessegeTime;
    }

    public List<String> getUserIDs() {
        return userIDs;
    }

    public void setUserIDs(List<String> userIDs) {
        this.userIDs = userIDs;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getLastMessageSenderID() {
        return lastMessageSenderID;
    }

    public void setLastMessageSenderID(String lastMessageSenderID) {
        this.lastMessageSenderID = lastMessageSenderID;
    }

    public Timestamp getLastMessageTime() {
        return lastMessageTime;
    }

    public void setLastMessageTime(Timestamp lastMessageTime) {
        this.lastMessageTime = lastMessageTime;
    }
}
