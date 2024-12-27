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
import com.example.chitchat2.model.ChatRoomModel;
import com.example.chitchat2.model.UserModel;
import com.example.chitchat2.utils.AndriodUtils;
import com.example.chitchat2.utils.FirebaseUtils;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;

public class RecentChatRecyclerAdapter extends FirestoreRecyclerAdapter<ChatRoomModel, RecentChatRecyclerAdapter.ChatRoomModelViewAdapter> {

    Context context;
    public RecentChatRecyclerAdapter(@NonNull FirestoreRecyclerOptions<ChatRoomModel> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull ChatRoomModelViewAdapter holder, int position, @NonNull ChatRoomModel model) {
        FirebaseUtils.getOtherUserFromChatRoom(model.getUserIDs()).get().addOnCompleteListener(task ->  {
            boolean lastMessegeSentByMe = model.getLastMessegeSenderID().equals(FirebaseUtils.currentUserId());

            UserModel otherUserModel = task.getResult().toObject(UserModel.class);
            holder.name.setText(otherUserModel.getUsername());

            if (lastMessegeSentByMe) {
                holder.lastMessageText.setText("(You) " + model.getLastMessage());
            }
            else {
                holder.lastMessageText.setText(model.getLastMessage());
            }

            holder.lastMessageTime.setText(FirebaseUtils.timeToString(model.getLastMessegeTime()));

            holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(context, Chat.class);

                AndriodUtils.passUserModelAsIntent(intent, otherUserModel);

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            });
        });
    }

    @NonNull
    @Override
    public ChatRoomModelViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recent_chat_recycler_row, parent, false);
        return new ChatRoomModelViewAdapter(view);
    }

    class ChatRoomModelViewAdapter extends RecyclerView.ViewHolder {

        TextView name, lastMessageText, lastMessageTime;
        ImageView profilePic;

        public ChatRoomModelViewAdapter(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name_text);
            lastMessageText = itemView.findViewById(R.id.lastMessage_text);
            lastMessageTime = itemView.findViewById(R.id.lastMessage_time_text);
            profilePic = itemView.findViewById(R.id.profilePic_image);
        }
    }

}
