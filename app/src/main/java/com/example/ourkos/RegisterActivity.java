package com.example.ourkos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

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

    private EditText editTextEmail, etEditTextUsername, etEditTextPassword, editTextPhones, editTextConfirm;
    private TextInputLayout tilEmail, tilUsername, tilPassword, tilConfirm;
    private Button registerButton;
    private MaterialBetterSpinner spinnerGender, spinnerAccess;
    private FirebaseAuth auth;
    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().hide();

        initViews();
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
        editTextPhones = findViewById(R.id.editTextPhone);
        etEditTextPassword = findViewById(R.id.editTextPassword);
        editTextConfirm = findViewById(R.id.editTextConfirm);
        tilEmail = findViewById(R.id.textInputLayoutEmail);
        tilUsername = findViewById(R.id.textInputLayoutUsername);
        tilPassword = findViewById(R.id.textInputLayoutPassword);
        tilConfirm = findViewById(R.id.textInputLayoutConfirm);
        registerButton = findViewById(R.id.registerButton);
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference();

        spinnerAccess.setAdapter(adapterAccess);
        spinnerGender.setAdapter(adapterGender);
    }

    private void registerUser(){

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString();
                final String username = etEditTextUsername.getText().toString();
                String password = etEditTextPassword.getText().toString();
                String confirm = editTextConfirm.getText().toString();
                final String gender = spinnerGender.getText().toString();
                final String type = spinnerAccess.getText().toString();
                final String phone = editTextPhones.getText().toString();

                if(email.isEmpty()) {
                    tilEmail.setError("Email Ga Bole Kosong");
                }
                else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    tilEmail.setError("Email Ga Valid");
                }
                else if(password.isEmpty()) {
                    tilPassword.setError("Password Ga Bole Kosong");
                }
                else if(password.length() < 8) {
                    tilPassword.setError("Password Kependekan");
                }
                else if(username.isEmpty()){
                    tilUsername.setError("Username Ga Bole Kosong");
                }
                else if(!password.equals(confirm)) {
                    tilPassword.setError("Cek Lagi Password Na");
                    tilConfirm.setError("Cek Lagi Password Na");
                }
                else {
                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(RegisterActivity.this, new
                        OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Snackbar.make(registerButton,
                                        "Failed to Register " + task.getException().getMessage(),
                                        Snackbar.LENGTH_LONG).show();
                            }
                            else {
                                writeToDB(task.getResult().getUser(),username, gender, phone, type);
                                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                            }
                        }
                    });
                }
            }
        });
    }

    private void writeToDB(FirebaseUser users, String username, String gender, String phone,String type){
        User user = new User(users.getEmail(),username, gender, phone,type);
        database.child("users").child(users.getUid()).setValue(user);
    }
}
