package com.example.ourkos;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OwnerActivity extends AppCompatActivity {

    private FloatingActionButton fabAddKost;
    private RecyclerView listKos;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Kost> data;
    private ArrayList<String> image;
    private DatabaseReference database;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private TextView empty;
    private View vLoading;
    private ProgressBar loadingPrgress;
    Kost kost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner);
        initView();
        listKos.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        listKos.setLayoutManager(layoutManager);
        getUserData();
        addKos();

    }

    private void addKos() {

        fabAddKost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OwnerActivity.this, FirebaseDBCreateKostActivity.class));
            }
        });

    }

    private void getUserData() {

        database.child("kost").child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DataSnapshot noteDataSnapshot = dataSnapshot.child("detail");
                DataSnapshot noteDataSnapshots= dataSnapshot.child("image/cover");
                if (noteDataSnapshot.exists() && noteDataSnapshots.exists()) {
                    for (DataSnapshot snap : noteDataSnapshot.getChildren()) {
                        kost = snap.getValue(Kost.class);
                        data.add(kost);
                    }
                    for (DataSnapshot snaps : noteDataSnapshots.getChildren()) {
                        for (DataSnapshot snapshot : snaps.getChildren()) {
                            String name = snapshot.child("imglink").getValue().toString();
                            image.add(name);
                        }
                    }
                }
                adapter = new OwnerAdapter(getApplicationContext(), data, image);
                if (adapter.getItemCount() <= 0) {
                    empty.setVisibility(View.VISIBLE);
                    listKos.setVisibility(View.GONE);
                    vLoading.setVisibility(View.GONE);
                    loadingPrgress.setVisibility(View.GONE);
                }
                else{
                    empty.setVisibility(View.GONE);
                    listKos.setVisibility(View.VISIBLE);
                    vLoading.setVisibility(View.GONE);
                    loadingPrgress.setVisibility(View.GONE);
                }
                listKos.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), databaseError.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initView() {

        fabAddKost = findViewById(R.id.fab_createKost);
        listKos = findViewById(R.id.list_kos);
        database = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        data = new ArrayList<>();
        image = new ArrayList<>();
        empty = findViewById(R.id.empty_view);
        loadingPrgress = findViewById(R.id.loading);
        vLoading=findViewById(R.id.white_view);
    }

}