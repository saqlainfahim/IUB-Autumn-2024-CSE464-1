package com.example.chitchat2;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.chitchat2.model.UserModel;
import com.example.chitchat2.utils.AndriodUtils;
import com.example.chitchat2.utils.FirebaseUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;

public class ProfileFragment extends Fragment {

    ImageView profilePic;
    EditText username, phoneNo;
    Button updateButton, logoutButton;

    UserModel currentUserModel;

    public ProfileFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        profilePic = view.findViewById(R.id.profile_image);
        username = view.findViewById(R.id.profile_username);
        phoneNo = view.findViewById(R.id.profile_PhoneNo);
        updateButton = view.findViewById(R.id.update_button);
        logoutButton = view.findViewById(R.id.logout_button);

        getCurrentUserData();

        updateButton.setOnClickListener(v -> {
            updateButtonClick();
        });

        logoutButton.setOnClickListener(v -> {
            FirebaseUtils.logOut();
            Intent intent = new Intent(getContext(), Splash.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

        return view;
    }

    void getCurrentUserData() {
        setInProgress(true);
        FirebaseUtils.currentUserDetails().get().addOnCompleteListener(task ->  {
            setInProgress(false);
            currentUserModel = task.getResult().toObject(UserModel.class);

            username.setText(currentUserModel.getUsername());
            phoneNo.setText(currentUserModel.getPhoneNo());
        });
    }

    void setInProgress(boolean inProgress) {
        if (inProgress) {
            updateButton.setVisibility(View.GONE);
        }
        else {
            updateButton.setVisibility(View.VISIBLE);
        }
    }

    void updateButtonClick() {
        String newUsername = username.getText().toString();

        if (newUsername.isEmpty() || newUsername.length() < 2) {
            username.setError("Username length must be >2");
            return;
        }
        currentUserModel.setUsername(newUsername);

        setInProgress(true);
        updateFirestore();
    }

    void updateFirestore() {
        FirebaseUtils.currentUserDetails().set(currentUserModel).addOnCompleteListener(task -> {
            setInProgress(false);
            if (task.isSuccessful()) {
                AndriodUtils.showToast(getContext(), "Update Successful");
            }
            else {
                AndriodUtils.showToast(getContext(), "Update Failed");
            }
        });
    }
}