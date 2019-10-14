package com.example.ourkos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

public class RegisterActivity extends AppCompatActivity {

    EditText editTextEmail, etEditTextUsername, etEditTextPassword;
    TextInputLayout tilEmail, tilUsername, tilPassword;
    Button registerButton;
    TextView backToLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().hide();

        initViews();
        initTextViewLogin();
    }

    private void initViews() {

        editTextEmail = findViewById(R.id.editTextEmail);
        etEditTextUsername = findViewById(R.id.editTextUsername);
        etEditTextPassword = findViewById(R.id.editTextPassword);
        tilEmail = findViewById(R.id.textInputLayoutEmail);
        tilUsername = findViewById(R.id.textInputLayoutUsername);
        tilPassword = findViewById(R.id.textInputLayoutPassword);
        registerButton = findViewById(R.id.registerButton);

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
}
