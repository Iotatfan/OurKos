package com.example.ourkos;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private EditText editTextUsername;
    private AppCompatTextView appCompatTextViewJK;
    private AppCompatTextView appCompatTextViewEmail;
    private AppCompatTextView appCompatTextViewPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().hide();

        iniView();

    }

    private void iniView(){

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        editTextUsername = findViewById(R.id.editTextUsername);
        appCompatTextViewEmail = findViewById(R.id.editTextEmail);
        appCompatTextViewJK = findViewById(R.id.editTextGender);
        appCompatTextViewPhone = findViewById(R.id.editTextPhone);

        appCompatTextViewEmail.setText(user.getEmail());
//        editTextUsername.setText(user.getDisplayName());

        mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid());

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String username = dataSnapshot.child("username").getValue().toString();
                editTextUsername.setText(username, EditText.BufferType.EDITABLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), databaseError.getMessage(), Toast.LENGTH_LONG).show();
            }
        });



    }

}
