package com.example.chitchat2.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chitchat2.Chat;
import com.example.chitchat2.R;
import com.example.chitchat2.model.ChatMessageModel;
import com.example.chitchat2.model.ChatRoomModel;
import com.example.chitchat2.utils.AndriodUtils;
import com.example.chitchat2.utils.FirebaseUtils;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class ChatRecyclerAdapter extends FirestoreRecyclerAdapter<ChatMessageModel, ChatRecyclerAdapter.ChatMessageModelAdapter> {

    Context context;
    public ChatRecyclerAdapter(@NonNull FirestoreRecyclerOptions<ChatMessageModel> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull ChatMessageModelAdapter holder, int position, @NonNull ChatMessageModel model) {
        if (model.getSenderID().equals(FirebaseUtils.currentUserId())) {
            holder.leftLayout.setVisibility(View.GONE);
            holder.rightLayout.setVisibility(View.VISIBLE);
            holder.rightChat.setText(model.getMessage());
        }
        else {
            holder.rightLayout.setVisibility(View.GONE);
            holder.leftLayout.setVisibility(View.VISIBLE);
            holder.leftChat.setText(model.getMessage());
        }
    }

    @NonNull
    @Override
    public ChatMessageModelAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.chat_message_recycler_row, parent, false);
        return new ChatMessageModelAdapter(view);
    }

    class ChatMessageModelAdapter extends RecyclerView.ViewHolder {

        LinearLayout leftLayout, rightLayout;
        TextView leftChat, rightChat;

        public ChatMessageModelAdapter(@NonNull View itemView) {
            super(itemView);

            leftLayout = itemView.findViewById(R.id.left_chat_layout);
            rightLayout = itemView.findViewById(R.id.right_chat_layout);
            leftChat = itemView.findViewById(R.id.left_chat_textview);
            rightChat = itemView.findViewById(R.id.right_chat_textview);
        }
    }

}
