package com.example.chitchat2.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chitchat2.Chat;
import com.example.chitchat2.R;
import com.example.chitchat2.model.UserModel;
import com.example.chitchat2.utils.AndriodUtils;
import com.example.chitchat2.utils.FirebaseUtils;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class SearchRecyclerAdapter extends FirestoreRecyclerAdapter<UserModel, SearchRecyclerAdapter.UserModelAdapter> {

    Context context;
    public SearchRecyclerAdapter(@NonNull FirestoreRecyclerOptions<UserModel> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull UserModelAdapter holder, int position, @NonNull UserModel model) {
        holder.name.setText(model.getUsername());
        holder.phone.setText(model.getPhoneNo());

        if (model.getUserID().equals(FirebaseUtils.currentUserId())) {
            holder.name.setText(model.getUsername() + "(ME)");
        }

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, Chat.class);

            AndriodUtils.passUserModelAsIntent(intent, model);

            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });
    }

    @NonNull
    @Override
    public UserModelAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.search_recycler_row, parent, false);
        return new UserModelAdapter(view);
    }

    class UserModelAdapter extends RecyclerView.ViewHolder {

        TextView name, phone;
        ImageView profilePic;

        public UserModelAdapter(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name_text);
            phone = itemView.findViewById(R.id.phone_text);
            profilePic = itemView.findViewById(R.id.profilePic_image);
        }
    }

}
