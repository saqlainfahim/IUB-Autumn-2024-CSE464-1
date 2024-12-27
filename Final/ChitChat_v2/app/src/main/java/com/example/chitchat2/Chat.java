package com.example.chitchat2;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chitchat2.adapter.ChatRecyclerAdapter;
import com.example.chitchat2.adapter.SearchRecyclerAdapter;
import com.example.chitchat2.model.ChatMessageModel;
import com.example.chitchat2.model.ChatRoomModel;
import com.example.chitchat2.model.UserModel;
import com.example.chitchat2.utils.AndriodUtils;
import com.example.chitchat2.utils.FirebaseUtils;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.Query;

import java.lang.reflect.Array;
import java.util.Arrays;

public class Chat extends AppCompatActivity {

    UserModel otherUser;

    ImageButton backButton, sendbutton;
    TextView otherUserName;
    EditText sendChatBox;
    RecyclerView recyclerView;

    String chatRoomID;
    ChatRoomModel chatRoomModel;
    ChatRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chat);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        otherUser = AndriodUtils.getUserModelFromIntent(getIntent());
        chatRoomID = FirebaseUtils.getchatRoomID(FirebaseUtils.currentUserId(), otherUser.getUserID());

        backButton = findViewById(R.id.back_button);
        sendbutton = findViewById(R.id.sendChatBtn);
        otherUserName = findViewById(R.id.otherUser_name);
        sendChatBox = findViewById(R.id.chat_input_box);
        recyclerView = findViewById(R.id.chat_recyclreView);

        backButton.setOnClickListener(v -> {
            onBackPressed();
        });

        otherUserName.setText(otherUser.getUsername());

        sendbutton.setOnClickListener(v -> {
            String message = sendChatBox.getText().toString().trim();
            if (message.isEmpty()) {
                return;
            }
            else {
                sendMessageToUser(message);
            }
        });

        getOrCreateChatRoomModel();
        setupChatRecyclerView();
    }

    void getOrCreateChatRoomModel() {
        FirebaseUtils.getChatRoomReference(chatRoomID).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                chatRoomModel = task.getResult().toObject(ChatRoomModel.class);

                if (chatRoomModel == null) {
                    chatRoomModel = new ChatRoomModel(chatRoomID,
                            "",
                            Timestamp.now(),
                            Arrays.asList(FirebaseUtils.currentUserId(), otherUser.getUserID()));

                    FirebaseUtils.getChatRoomReference(chatRoomID).set(chatRoomModel);
                }
            }
        });

    }

    void sendMessageToUser(String message) {
        chatRoomModel.setLastMessegeSenderID(FirebaseUtils.currentUserId());
        chatRoomModel.setLastMessegeTime(Timestamp.now());
        chatRoomModel.setLastMessage(message);

        FirebaseUtils.getChatRoomReference(chatRoomID).set(chatRoomModel);

        ChatMessageModel chatMessageModel = new ChatMessageModel(message, FirebaseUtils.currentUserId(), Timestamp.now());

        // Use a collection reference to add the message
        FirebaseUtils.getChatRoomReference(chatRoomID)
                .collection("chats")
                .add(chatMessageModel)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        sendChatBox.setText("");
                    }
                    else {
                        AndriodUtils.showToast(this, "Failed to send message");
                    }
                });
    }

    void setupChatRecyclerView() {
        Query query = FirebaseUtils.getChatRoomMessageReference(chatRoomID)
                .orderBy("timestamp", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<ChatMessageModel> options = new FirestoreRecyclerOptions.Builder<ChatMessageModel>()
                .setQuery(query, ChatMessageModel.class).build();

        adapter = new ChatRecyclerAdapter(options, getApplicationContext());

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setReverseLayout(true);
        recyclerView.setLayoutManager(manager);

        recyclerView.setAdapter(adapter);
        adapter.startListening();
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                recyclerView.smoothScrollToPosition(0);
            }
        });
    }
}