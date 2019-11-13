package com.example.ourkos;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

public class FirebaseDBCreateKostActivity extends AppCompatActivity {
    private EditText edtNama, edtAlamat;
    private MaterialBetterSpinner spinnerJenis, spinnerRegion;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private DatabaseReference database;
    private Button lanjutbutton;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dbcreatekost);
        initViews();
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
                else{
                    writeToDB(nama,alamat,jenis,region);
                }
            }
        });
    }
    private void writeToDB(String nama, String alamat, String jenis,String region){
        Kost kost = new Kost(0,nama,alamat,0,0,region,jenis);
        database.child("kost").child(user.getUid()).setValue(kost).addOnSuccessListener(this, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                edtNama.setText("");
                edtAlamat.setText("");
                spinnerJenis.setText("");
                spinnerRegion.setText("");
            }
        });
    }
}
