package com.example.ourkos;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ChatRoom extends AppCompatActivity {

    private Button btnSend;
    private TextView chatView;
    private EditText inputMessage;
    private String username;
    private DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
    }

    private void initView(){
        btnSend = findViewById(R.id.button);
        chatView = findViewById(R.id.textView);
        inputMessage = findViewById(R.id.editText);

        ref = FirebaseDatabase.getInstance().getReference();
    }
}
