package com.example.chitchat2.utils;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.chitchat2.model.UserModel;

public class AndriodUtils {
    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void passUserModelAsIntent(Intent intent, UserModel model) {
        intent.putExtra("userID", model.getUserID());
        intent.putExtra("username", model.getUsername());
        intent.putExtra("phoneNo", model.getPhoneNo());
    }

    public static UserModel getUserModelFromIntent(Intent intent) {
        UserModel userModel = new UserModel();
        userModel.setUserID(intent.getStringExtra("userID"));
        userModel.setUsername(intent.getStringExtra("username"));
        userModel.setPhoneNo(intent.getStringExtra("phoneNo"));
        return userModel;
    }
}
