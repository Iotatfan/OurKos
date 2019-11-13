package com.example.ourkos;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private EditText editTextUsername;
    private AppCompatTextView appCompatTextViewJK, appCompatTextViewEmail, appCompatTextViewPhone;
    private FloatingActionButton yes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().hide();

        initView();
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

        progressBar.setVisibility(View.VISIBLE);

        appCompatTextViewEmail.setText(user.getEmail());

        mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid());

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String username = dataSnapshot.child("username").getValue().toString();
                String gender = dataSnapshot.child("gender").getValue().toString();
                String phone = dataSnapshot.child("phone").getValue().toString();

                editTextUsername.setText(username, EditText.BufferType.EDITABLE);
                appCompatTextViewJK.setText(gender);
                appCompatTextViewPhone.setText(phone);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), databaseError.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
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
