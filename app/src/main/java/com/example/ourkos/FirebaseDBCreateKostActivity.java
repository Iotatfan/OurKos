package com.example.ourkos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import me.abhinay.input.CurrencyEditText;

public class FirebaseDBCreateKostActivity extends AppCompatActivity {

    private EditText edtNama, edtAlamat,edtStock;
    private CurrencyEditText edtHargaH, edtHargaM,edtHargaB,edtHargaT;
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
        user = auth.getCurrentUser();
        lanjutbutton = findViewById(R.id.btn_lanjut);
        edtStock = findViewById(R.id.editTextStock);
        edtNama = findViewById(R.id.editTextNama);
        edtAlamat = findViewById(R.id.editTextAlamat);
        edtHargaH = findViewById(R.id.editTextHarian);
//        edtHargaH.setSpacing(false);
//        edtHargaH.setDelimiter(false);
        edtHargaH.setDecimals(false);
        edtHargaH.setSeparator(".");
        edtHargaM = findViewById(R.id.editTextMingguan);
        edtHargaM.setDecimals(false);
        edtHargaM.setSeparator(".");
        edtHargaB = findViewById(R.id.editTextBulanan);
        edtHargaB.setSeparator(".");
        edtHargaB.setDecimals(false);
        edtHargaT = findViewById(R.id.editTextTahunan);
        edtHargaT.setSeparator(".");
        edtHargaT.setDecimals(false);
        progressBar = findViewById(R.id.progressBar1);
        database = FirebaseDatabase.getInstance().getReference();
        spinnerJenis.setAdapter(adapterJenis);
        spinnerRegion.setAdapter(adapterRegion);
    }
    private void registerKost(){
        lanjutbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int hari, minggu, bulan, tahun, stock;

                String nama = edtNama.getText().toString();
                String alamat = edtAlamat.getText().toString();
                String jenis = spinnerJenis.getText().toString();
                String region = spinnerRegion.getText().toString();
                String haris = edtHargaH.getText().toString().replaceAll("[^0-9]", "");
                String minggus = (edtHargaM.getText().toString()).replaceAll("[^0-9]", "");
                String bulans = (edtHargaB.getText().toString()).replaceAll("[^0-9]", "");
                String tahuns = (edtHargaT.getText().toString()).replaceAll("[^0-9]", "");
                System.out.println(haris);
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
                    hari = Integer.parseInt(haris);
                    minggu = Integer.parseInt(minggus);
                    bulan = Integer.parseInt(bulans);
                    tahun = Integer.parseInt(tahuns);
                    stock = Integer.parseInt(stocks);
                    progressBar.setVisibility(View.VISIBLE);
                    writeToDB(nama,alamat,jenis,region,hari,minggu,bulan,tahun,stock);
                    Intent intent = new Intent(FirebaseDBCreateKostActivity.this,Upload.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private void writeToDB(String nama, String alamat, String jenis,String region,int hari,int minggu, int bulan,int tahun,int stock){
        Kost kost = new Kost(nama,alamat,stock,bulan,hari,minggu,tahun,region,jenis);
        database.child("kost").child(user.getUid()).child("detail").push().setValue(kost);
    }
}
