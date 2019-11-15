package com.example.ourkos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;

public class FirebaseDBCreateKostActivity extends AppCompatActivity {
    private EditText edtNama, edtAlamat,edtHargaH,edtHargaM,edtHargaB,edtHargaT,edtStock;
    private MaterialBetterSpinner spinnerJenis, spinnerRegion;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private DatabaseReference database;
    private Button lanjutbutton;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dbcreatekost);
        getSupportActionBar().hide();
        initViews();
        progressBar.setVisibility(View.INVISIBLE);
        registerKost();
    }
    private void initViews(){
        String[] SPINNER_JENIS = {"Putra","Putri","Campur"};
        String[] SPINNER_REGION= {"Bandung","Jakarta","Malang","Semarang","Solo","Surabaya","Yogyakarta"};
        spinnerJenis=findViewById(R.id.material_spinner3);
        spinnerRegion=findViewById(R.id.material_spinner4);
        ArrayAdapter<String> adapterJenis = new ArrayAdapter<>(FirebaseDBCreateKostActivity.this,R.layout.support_simple_spinner_dropdown_item,SPINNER_JENIS);
        ArrayAdapter<String> adapterRegion = new ArrayAdapter<>(FirebaseDBCreateKostActivity.this,R.layout.support_simple_spinner_dropdown_item,SPINNER_REGION);
        auth = FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        lanjutbutton=findViewById(R.id.btn_lanjut);
        edtStock=findViewById(R.id.etstock);
        edtNama=findViewById(R.id.etnama);
        edtAlamat=findViewById(R.id.etalamat);
        edtHargaM=findViewById(R.id.etminggu);
        edtHargaH=findViewById(R.id.ethari);
        edtHargaB=findViewById(R.id.etbulan);
        edtHargaT=findViewById(R.id.ettahun);
        progressBar = findViewById(R.id.progressBar1);
        database= FirebaseDatabase.getInstance().getReference();
        spinnerJenis.setAdapter(adapterJenis);
        spinnerRegion.setAdapter(adapterRegion);
    }
    private void registerKost(){
        lanjutbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nama = edtNama.getText().toString();
                String alamat = edtAlamat.getText().toString();
                String jenis = spinnerJenis.getText().toString();
                String region = spinnerRegion.getText().toString();
                int hari;
                String haris=edtHargaH.getText().toString();
                int minggu;
                String minggus = (edtHargaM.getText().toString());
                int bulan;
                String bulans = (edtHargaB.getText().toString());
                int tahun;
                String tahuns = (edtHargaT.getText().toString());
                int stock;
                String stocks = (edtStock.getText().toString());
                if(nama.isEmpty()){
                    edtNama.setError("Ga Boleh Kosong Nama Kosnya");
                }
                else if(alamat.isEmpty()){
                    edtAlamat.setError("Ga Boleh Kosong Alamatnya");
                }
                else if(jenis.isEmpty()){
                    spinnerJenis.setError("Harus Pilih Salah Satu");
                }
                else if(region.isEmpty()){
                    spinnerRegion.setError("Harus Pilih Salah Satu");
                }
                else if(haris.isEmpty()){
                    edtHargaH.setText("0");
                }
                else if(minggus.isEmpty()){
                    edtHargaM.setText("0");
                }
                else if(bulans.isEmpty()){
                    edtHargaB.setText("0");
                }
                else if(tahuns.isEmpty()){
                    edtHargaT.setText("0");
                }
                else if(stocks.isEmpty()){
                    edtStock.setText("0");
                }
                else{
                    hari=Integer.parseInt(haris);
                    minggu=Integer.parseInt(minggus);
                    bulan=Integer.parseInt(bulans);
                    tahun=Integer.parseInt(tahuns);
                    stock=Integer.parseInt(stocks);
                    progressBar.setVisibility(View.VISIBLE);
                    writeToDB(nama,alamat,jenis,region,hari,minggu,bulan,tahun,stock);
                    startActivity(new Intent(FirebaseDBCreateKostActivity.this,Upload.class));
                }
            }
        });
    }
    private void writeToDB(String nama, String alamat, String jenis,String region,int hari,int minggu, int bulan,int tahun,int stock){
        ArrayList <String> img=new ArrayList<String>();
        Kost kost = new Kost(0,img,img,img,nama,alamat,stock,bulan,hari,minggu,tahun,region,jenis);
        database.child("kost").child(user.getUid()).child(nama).setValue(kost);
    }
}
