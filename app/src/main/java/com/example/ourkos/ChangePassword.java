package com.example.ourkos;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePassword extends AppCompatActivity {

    private TextInputEditText editTextPwBaru, editTextConfirm;
    private TextInputLayout tilBaru, tilConfrim;
    private Button btn;
    private FirebaseAuth auth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        getSupportActionBar().hide();

        initView();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePassword();
            }
        });
    }

    private void initView() {

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        btn = findViewById(R.id.gantiBtn);
        editTextPwBaru = findViewById(R.id.editTextPwBaru);
        editTextConfirm = findViewById(R.id.editTextConfirm);
        tilBaru  = findViewById(R.id.tilBaru);
        tilConfrim = findViewById(R.id.tilConfirm);
    }

    private void changePassword(){
        final String baru, confirm;

        baru = editTextPwBaru.getText().toString();
        confirm = editTextConfirm.getText().toString();


        if(validate(baru, confirm)){

            user.updatePassword(baru).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()) {
                        Toast.makeText(ChangePassword.this,"Password Berhasil Diubah", Toast.LENGTH_LONG ).show();
                    }
                }
            });
        }

    }

    private boolean validate(String baru, String confirm){

            if(!baru.isEmpty() && baru.length() >= 8) {
                if(!confirm.isEmpty() && confirm.length() >= 8){
                    if(baru.equals(confirm)) {
                        return true;
                    }
                    else {
                        tilConfrim.setError("Tidak Sama Dengan Atas");
                        tilBaru.setError("Tidak Sama Dengan Bawah");
                        return false;
                    }
                }
                else{
                    tilConfrim.setError("Ini Ga Valid");
                    return false;
                }
            }
            else{
                tilBaru.setError("Ini Ga Valid");
                return false;
            }
    }
}
