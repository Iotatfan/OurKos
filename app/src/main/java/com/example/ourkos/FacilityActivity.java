package com.example.ourkos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FacilityActivity extends AppCompatActivity {

    private Button btnNext;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private DatabaseReference mDatabase;
    int ac, wifi, listrik, air, kamarMandiDalam, kasur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facility);

        initView();
        next();
    }

    private void initView() {

        btnNext = findViewById(R.id.btn_lanjut);
//        ac = findViewById(R.id.checkboxAC);
//        wifi = findViewById(R.id.checkboxWifi);
//        listrik = findViewById(R.id.checkboxListrik);
//        air = findViewById(R.id.checkboxAir);
//        kamarMandiDalam = findViewById(R.id.checkboxKamarMandiDalam);
//        kasur = findViewById(R.id.checkboxKasur);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();

    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.checkboxAC:
                if (checked){
                    ac = 1;
                }
                else
                    ac = 0;

            case R.id.checkboxWifi:
                if (checked){
                    wifi = 1;
                }
                else
                    wifi = 0;

            case R.id.checkboxListrik:
                if (checked) {
                    listrik = 1;
                }
                else
                    listrik = 0;

            case R.id.checkboxAir:
                if (checked) {
                    air = 1;
                }
                else
                    air = 0;

            case R.id.checkboxKamarMandiDalam:
                if (checked) {
                    kamarMandiDalam = 1;
                }
                else
                    kamarMandiDalam = 0;

            case R.id.checkboxKasur:
                if (checked) {
                    kasur = 1;
                }
                else
                    kasur = 0;
        }
    }

    private void next() {

        Bundle bundle = getIntent().getExtras();
        final String nama = bundle.getString("namaKos");
        final String alamat = bundle.getString("alamatKos");
        final String jenis = bundle.getString("jenisKos");
        final String region = bundle.getString("region");
        final int hari = bundle.getInt("hargaHarian");
        final int mingguan = bundle.getInt("hargaMingguan");
        final int bulanan = bundle.getInt("hargaBulanan");
        final int tahunan = bundle.getInt("hargaTahunan");
        final int stock = bundle.getInt("kamarKosong");

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeToDB(nama, alamat, jenis, region, hari, mingguan, bulanan, tahunan, stock);
                Intent intent = new Intent(FacilityActivity.this, Upload.class);
                startActivity(intent);
                finish();
            }
        });

    }


    private void writeToDB(String nama, String alamat, String jenis, String region, int hari,
                           int minggu, int bulan, int tahun, int stock){

        Kost kost = new Kost(nama,alamat,stock,bulan,hari,minggu, tahun, region, jenis, ac, wifi,
                listrik, air, kamarMandiDalam, kasur);
        String key = mDatabase.child("kost").child(user.getUid()).child("detail").push().getKey();
        kost.setKey(key);
        mDatabase.child("kost").child(user.getUid()).child("detail").push().setValue(kost);
    }

}
