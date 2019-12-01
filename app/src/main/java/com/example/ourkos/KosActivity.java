package com.example.ourkos;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class KosActivity extends AppCompatActivity {

    private TextView textViewhargaHarian,textViewhargaMingguan, textViewhargaBulanan,
            textViewhargaTahunan, textViewStokKamar;
    private AppCompatTextView textViewNama;
    private ImageView yesListrik, yesAir, yesAC, yesWifi, yesKasur, yesDalam,
            noListrik, noAir, noAc, noWifi, noKasur, noDalam,
            kosImages;
    private FloatingActionButton fabSewa;
    private DatabaseReference mDatabase;
    private RecyclerView listView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<String> imageKost;
    private String kun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kos);

        initView();
        getKosData();
    }

    private void initView(){

        listView = (RecyclerView) findViewById(R.id.listV);
        listView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        listView.setLayoutManager(layoutManager);

        imageKost = new ArrayList<>();

        textViewNama = findViewById(R.id.namaKos);
        textViewhargaHarian = findViewById(R.id.harga_harian);
        textViewhargaMingguan = findViewById(R.id.harga_mingguan);
        textViewhargaBulanan = findViewById(R.id.harga_bulanan);
        textViewhargaTahunan = findViewById(R.id.harga_tahunan);
        textViewStokKamar = findViewById(R.id.kamarKosong);

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

//        kosImages = findViewById(R.id.images);

        fabSewa = findViewById(R.id.fabSewa);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("kost");
    }

    private void getKosData(){

        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

        Intent kos = getIntent();
        final String namaKos = kos.getStringExtra("name");
        String imageURL = kos.getStringExtra("image_url");
        int hargaHarian = kos.getIntExtra("hargaHarian", 0);
        int hargaMingguan = kos.getIntExtra("hargaMingguan", 0);
        int hargaBulanan = kos.getIntExtra("hargaBulanan", 0);
        int hargaTahunan = kos.getIntExtra("hargaTahunan", 0);
        int stokKamar = kos.getIntExtra("stokKamar", 0);
        int ac = kos.getIntExtra("ac", 0);
        int wifi = kos.getIntExtra("wifi", 0);
        int listrik = kos.getIntExtra("listrik", 0);
        int air = kos.getIntExtra("air", 0);
        int dalam = kos.getIntExtra("kamarMandiDalam", 0);
        int kasur = kos.getIntExtra("kasur", 0);
        kun=kos.getStringExtra("key");

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot){
                for(DataSnapshot uid:dataSnapshot.getChildren()){
                    DataSnapshot bangunan=uid.child("image");
                    if(bangunan.exists()){
                        for(DataSnapshot jenis:bangunan.getChildren()){
                            DataSnapshot kid=jenis.child(kun);
                                for(DataSnapshot iid: kid.getChildren()) {
                                    String imagekost = iid.child("imglink").getValue().toString();
                                    imageKost.add(imagekost);
                                }
                        }
                    }
                }
                adapter = new ImageViewHorizontalAdapter(getApplicationContext(),imageKost);
                adapter.notifyDataSetChanged();
                listView.setAdapter(adapter);
                listView.scrollToPosition(0);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        textViewNama.setText(namaKos);
        textViewhargaHarian.setText(formatRupiah.format( hargaHarian ));
        textViewhargaMingguan.setText(formatRupiah.format( hargaMingguan ));
        textViewhargaBulanan.setText(formatRupiah.format( hargaBulanan ));
        textViewhargaTahunan.setText(formatRupiah.format( hargaTahunan ));
        textViewStokKamar.setText( Integer.toString(stokKamar) );
//        Glide.with(this).load(imageURL).into(kosImages);

        System.out.println("kasur"+kasur);

        if( ac == 1 ) {
            yesAC.setVisibility(View.VISIBLE);
            noAc.setVisibility(View.GONE);
        }
        if ( wifi == 1 ) {
            yesWifi.setVisibility(View.VISIBLE);
            noWifi.setVisibility(View.GONE);
        }
        if( listrik == 1 ) {
            yesListrik.setVisibility(View.VISIBLE);
            noListrik.setVisibility(View.GONE);
        }
        if ( air == 1 ) {
            yesAir.setVisibility(View.VISIBLE);
            noAir.setVisibility(View.GONE);
        }
        if( dalam == 1 ) {
            yesDalam.setVisibility(View.VISIBLE);
            noDalam.setVisibility(View.GONE);
        }
        if ( kasur == 1 ) {
            yesKasur.setVisibility(View.VISIBLE);
            noKasur.setVisibility(View.GONE);
        }

    }

}
