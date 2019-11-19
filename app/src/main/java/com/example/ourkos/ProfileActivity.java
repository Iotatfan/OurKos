package com.example.ourkos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

public class ProfileActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private EditText editTextUsername;
    private AppCompatTextView appCompatTextViewJK, appCompatTextViewEmail, appCompatTextViewPhone;
    private FloatingActionButton yes;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().hide();

        initView();
        backButton();
        progressBar.setVisibility(View.VISIBLE);
        getUserData();
        progressBar.setVisibility(View.INVISIBLE);
        updateButton();
    }

    private void initView(){
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        editTextUsername = findViewById(R.id.editTextUsername);
        appCompatTextViewEmail = findViewById(R.id.editTextEmail);
        appCompatTextViewJK = findViewById(R.id.editTextGender);
        appCompatTextViewPhone = findViewById(R.id.editTextPhone);
        progressBar = findViewById(R.id.progressBar1);
        yes = findViewById(R.id.btnYes);
        toolbar = findViewById(R.id.toolbar);
    }

    private void backButton(){
        toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_white_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getUserData(){
        appCompatTextViewEmail.setText(user.getEmail());

        Intent profile = getIntent();
        String username = profile.getStringExtra("username");
        String gender = profile.getStringExtra("gender");
        String phone = profile.getStringExtra("phone");

        editTextUsername.setText(username, EditText.BufferType.EDITABLE);
        appCompatTextViewJK.setText(gender);
        appCompatTextViewPhone.setText(phone);
    }

    private void updateButton() {

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editTextUsername.getText().toString();

                if(username.isEmpty()) {
                    editTextUsername.setError("Nama ga bole kosong");
                }

                mDatabase.child("username").setValue(username, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                        if(databaseError!=null) {
                            Toast.makeText(ProfileActivity.this, "Data Nama Gagal di Update", Toast.LENGTH_LONG).show();
                        }
                        else {
                            Toast.makeText(ProfileActivity.this, "Data Nama Berhasil di Update", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }



}
