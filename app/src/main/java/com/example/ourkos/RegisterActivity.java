package com.example.ourkos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

public class RegisterActivity extends AppCompatActivity {

    private EditText editTextEmail, etEditTextUsername, etEditTextPassword;
    private TextInputLayout tilEmail, tilUsername, tilPassword;
    private Button registerButton;
    private MaterialBetterSpinner spinnerGender, spinnerAccess;
    private TextView backToLogin;
    private FirebaseAuth auth;
    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().hide();

        initViews();
//        initTextViewLogin();
        registerUser();
    }

    private void initViews() {

        String[] SPINNER_GENDER = {"Pria", "Wanita"};
        String[] SPINNER_ACCESS = {"Pencari Kos", "Pemilik Kos"};

        spinnerGender = findViewById(R.id.material_spinner1);
        spinnerAccess = findViewById(R.id.material_spinner2);

        ArrayAdapter<String> adapterGender = new ArrayAdapter<>(RegisterActivity.this, R.layout.support_simple_spinner_dropdown_item, SPINNER_GENDER);
        ArrayAdapter<String> adapterAccess = new ArrayAdapter<>(RegisterActivity.this, R.layout.support_simple_spinner_dropdown_item, SPINNER_ACCESS);

        editTextEmail = findViewById(R.id.editTextEmail);
        etEditTextUsername = findViewById(R.id.editTextUsername);
        etEditTextPassword = findViewById(R.id.editTextPassword);
        tilEmail = findViewById(R.id.textInputLayoutEmail);
        tilUsername = findViewById(R.id.textInputLayoutUsername);
        tilPassword = findViewById(R.id.textInputLayoutPassword);
        registerButton = findViewById(R.id.registerButton);
        auth = FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance().getReference();

        spinnerAccess.setAdapter(adapterAccess);
        spinnerGender.setAdapter(adapterGender);
    }

//    private void initTextViewLogin() {
//
//        backToLogin = findViewById(R.id.textViewBackLogin);
//
//        backToLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        });
//    }

    private void registerUser(){

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = editTextEmail.getText().toString();
                final String username = etEditTextUsername.getText().toString();
                String password = etEditTextPassword.getText().toString();

                if(email.isEmpty()) {
                    editTextEmail.setError("Email Ga Bole Kosong");
                }
                else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    editTextEmail.setError("Invalid Email");
                }
                else if(password.isEmpty()) {
                    etEditTextPassword.setError("Password Ga Bole Kosong");
                }
                else if(password.length() < 8) {
                    etEditTextPassword.setError("Password too short");
                }
                else if(username.isEmpty()){
                    etEditTextUsername.setError("Username Ga Bole Kosong");
                }
                else {
                    auth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(RegisterActivity.this, new
                                    OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if(!task.isSuccessful()){
                                                Snackbar.make(registerButton,
                                                        "Failed to Register" + task.getException().getMessage(),
                                                        Snackbar.LENGTH_LONG).show();
                                            }
                                            else {
                                                writeToDB(task.getResult().getUser(),username);
                                                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                            }
                                        }
                                    });
                }

            }
        });
    }
    private void writeToDB(FirebaseUser usera, String username){
        User user = new User(usera.getEmail(),username);
        database.child("users").child(usera.getUid()).setValue(user);
    }
}