package com.example.ourkos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class OwnerActivity extends AppCompatActivity {

    private FloatingActionButton fabAddKost;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        initView();
//        addKos();

    }

    private void addKos() {

        fabAddKost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OwnerActivity.this, FirebaseDBCreateKostActivity.class));
            }
        });

    }

    private void initView() {

        fabAddKost = findViewById(R.id.fab_createKost);

    }


}
