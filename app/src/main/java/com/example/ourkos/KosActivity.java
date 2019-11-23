package com.example.ourkos;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class KosActivity extends AppCompatActivity {

    private TextView hargaHarian, hargaBulanan, hargaTahunan, luasKamar;
    private AppCompatTextView textViewNama;
    private ImageView yesListrik, yesAir, yesAC, yesWifi, yesKasur, yesDalam;
    private ImageView noListrik, noAir, noAc, noWifi, noKasur, noDalam;
    private FloatingActionButton fabSewa;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kos);

        initView();
        getKosData();
    }

    private void initView(){

        textViewNama = findViewById(R.id.namaKos);
        hargaHarian = findViewById(R.id.harga_harian);
        hargaBulanan = findViewById(R.id.harga_bulanan);
        hargaTahunan = findViewById(R.id.harga_tahunan);

        yesAC = findViewById(R.id.okAC);
        yesAir = findViewById(R.id.okAir);
        yesKasur = findViewById(R.id.okKasur);
        yesListrik = findViewById(R.id.okListrik);
        yesDalam = findViewById(R.id.okToilet);
        yesWifi = findViewById(R.id.okWifi);

        noAc = findViewById(R.id.noAC);
        noAir = findViewById(R.id.noAir);
        noKasur = findViewById(R.id.noKasur);
        noListrik = findViewById(R.id.noListrik);
        noDalam = findViewById(R.id.noToilet);
        noWifi = findViewById(R.id.noWifi);

        fabSewa = findViewById(R.id.fabSewa);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("kos");
    }

    private void getKosData(){

        Intent kos = getIntent();
        String namaKos = kos.getStringExtra("name");

        textViewNama.setText(namaKos);
    }


}
