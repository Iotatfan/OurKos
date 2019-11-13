package com.example.ourkos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextEmail, editTextPassword;
    private TextInputLayout textInputLayoutEmail, textInputLayoutPassword;
    private Button  buttonLogin;
    private FirebaseAuth auth;
    private ProgressBar mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();
        initCreateAccountTextView();
        initViews();
        login();
    }

    @Override
    public void onBackPressed()
    {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }

    private void initViews() {
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        textInputLayoutEmail = findViewById(R.id.textInputLayoutEmail);
        textInputLayoutPassword = findViewById(R.id.textInputLayoutPassword);
        buttonLogin = findViewById(R.id.loginButton);
        auth = FirebaseAuth.getInstance();

        mProgress = findViewById(R.id.progressBar1);
    }

    private void initCreateAccountTextView() {
        TextView textViewCreateAccount = findViewById(R.id.textViewCreateAccount);
        textViewCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void login() {

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate()) {
                    mProgress.setVisibility(View.VISIBLE);

                    final String email = editTextEmail.getText().toString();
                    final String password = editTextPassword.getText().toString();

                    auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(LoginActivity.this,
                            new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(!task.isSuccessful()) {
                                        mProgress.setVisibility(View.INVISIBLE);
                                        Toast.makeText(LoginActivity.this, "Login Failed \n " +
                                                task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                    else {
                                        Bundle bundle = new Bundle();
                                        bundle.putString("email", email);
                                        bundle.putString("password", password);
                                        startActivity(new Intent(LoginActivity.this, MainActivity.class).putExtra("emailpassword", bundle));
                                        mProgress.setVisibility(View.INVISIBLE);
                                        finish();
                                    }
                                }
                            });
                }
                else {
                    mProgress.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(), "Email Atau Password Salah ", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private boolean validate() {

        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            textInputLayoutEmail.setError("Email Ga Valid");
            return false;
        }
        else {
            textInputLayoutEmail.setError(null);
            if (password.isEmpty()) {
                textInputLayoutPassword.setError("Password Ga Kosong!");
                return false;
            }
            else {
                if (password.length() > 5) {
                    textInputLayoutPassword.setError(null);
                    return true;
                }
                else {
                    textInputLayoutPassword.setError("Password Minimal 8 Karakter!");
                    return false;
                }
            }
        }

    }
}