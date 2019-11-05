package com.example.ourkos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

public class RegisterActivity extends AppCompatActivity {

    private EditText editTextEmail, etEditTextUsername, etEditTextPassword;
    private TextInputLayout tilEmail, tilUsername, tilPassword;
    private Button registerButton;
    private TextView backToLogin;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().hide();

        initViews();
        initTextViewLogin();
        registerUser();
    }

    private void initViews() {

        editTextEmail = findViewById(R.id.editTextEmail);
        etEditTextUsername = findViewById(R.id.editTextUsername);
        etEditTextPassword = findViewById(R.id.editTextPassword);
        tilEmail = findViewById(R.id.textInputLayoutEmail);
        tilUsername = findViewById(R.id.textInputLayoutUsername);
        tilPassword = findViewById(R.id.textInputLayoutPassword);
        registerButton = findViewById(R.id.registerButton);
        auth = FirebaseAuth.getInstance();

    }

    private void initTextViewLogin() {

        backToLogin = findViewById(R.id.textViewBackLogin);

        backToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void registerUser(){

            registerButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String email = editTextEmail.getText().toString();
                    String username = etEditTextUsername.getText().toString();
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
                                                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                                }
                                            }
                                        });
                    }

                }
            });
    }
}
