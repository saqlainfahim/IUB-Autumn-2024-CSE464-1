package com.example.chitchat2.utils;

import androidx.annotation.NonNull;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.List;

public class FirebaseUtils {
    public static String currentUserId() {
        return FirebaseAuth.getInstance().getUid();
    }

    public static DocumentReference currentUserDetails() {
        return FirebaseFirestore.getInstance().collection("users").document(currentUserId());
    }

    public static boolean isLoggedIN() {
        if (currentUserId() != null) {
            return true;
        }
        else {
            return false;
        }
    }

    public static CollectionReference collectionReference() {
        return FirebaseFirestore.getInstance().collection("users");
    }

    public static DocumentReference getChatRoomReference(String chatRoomID) {
        return FirebaseFirestore.getInstance().collection("chatRooms").document(chatRoomID);
    }

    public static String getchatRoomID(String userID1, String userID2) {
        if (userID1.hashCode() < userID2.hashCode()) {
            return userID1 + "_" + userID2;
        }
        else {
            return userID2 + "_" + userID1;
        }
    }

    public static CollectionReference getChatRoomMessageReference(String chatRoomID) {
        return getChatRoomReference(chatRoomID).collection("chats");
    }

    public static CollectionReference allChatRoomCollectionReference() {
        return FirebaseFirestore.getInstance().collection("chatRooms");
    }

    public static DocumentReference getOtherUserFromChatRoom(List<String> userIDs) {
        if (userIDs.get(0).equals(FirebaseUtils.currentUserId())) {
            return collectionReference().document(userIDs.get(1));
        }
        else {
            return collectionReference().document(userIDs.get(0));
        }
    }

    public static String timeToString(Timestamp timestamp) {
        return new SimpleDateFormat("HH:MM").format(timestamp.toDate());
    }

    public static void logOut() {
        FirebaseAuth.getInstance().signOut();
    }
}
