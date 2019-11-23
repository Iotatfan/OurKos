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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchListActivity extends AppCompatActivity {

    private String name;
    private List<Kost> listKost;
    private List<String> imageKost,region;
    private RecyclerView listView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    Kost kost;
    private DatabaseReference database;
    private TextView empty;
    private View vLoading;
    private ProgressBar loadingPrgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchlist);
        Intent intent = getIntent();
        name = intent.getStringExtra("place");
        getSupportActionBar().setTitle(name);
        initview();
        listView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        listView.setLayoutManager(layoutManager);
        getData();
    }
    private void initview(){
        listView=(RecyclerView) findViewById(R.id.listV);
        listKost=new ArrayList<Kost>();
        imageKost=new ArrayList<>();
        region=new ArrayList<>();
        kost = new Kost();
        database = FirebaseDatabase.getInstance().getReference();
        empty = findViewById(R.id.empty_view);
        loadingPrgress = findViewById(R.id.loading);
        vLoading=findViewById(R.id.white_view);
        vLoading.setVisibility(View.VISIBLE);
        loadingPrgress.setVisibility(View.VISIBLE);
    }
    private void getData(){
        database.child("kost").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listKost.clear();
                for(DataSnapshot uid : dataSnapshot.getChildren()){
                    DataSnapshot detail=uid.child("detail");
                    DataSnapshot image=uid.child("image/cover");
                    if(detail.exists()&&image.exists()){
                        for(DataSnapshot kid: detail.getChildren()){
                            kost = kid.getValue(Kost.class);
                            listKost.add(kost);
                        }
                        for(DataSnapshot kid:image.getChildren()){
                            for(DataSnapshot iid:kid.getChildren()){
                                String imagekost=iid.child("imglink").getValue().toString();
                                imageKost.add(imagekost);
                            }
                        }
                    }
                }
                int size =listKost.size();
                List<Kost> temp=listKost;
                int j =0;
                for(int i=0;i<size;i++){
                    String k=listKost.get(j).getRegion();
                    Log.d("Jumlah",String.valueOf(j));
                    Log.d("KOST",listKost.toString());
                    region.add(k);
                    Log.d("REGION",region.toString());
                    Log.d("NAME",name);
                    if(!region.get(j).equals(name)){
                        listKost.remove(j);
                        imageKost.remove(j);
                        region.remove(j);
                    }
                    else if (region.get(j).equals(name)){
                        j++;
                    }
                }
                adapter = new KostAdapter(getApplicationContext(), listKost, imageKost);
                adapter.notifyDataSetChanged();
                if(listKost.size()<=0){
                    empty.setVisibility(View.VISIBLE);
                    listView.setVisibility(View.GONE);
                    vLoading.setVisibility(View.GONE);
                    loadingPrgress.setVisibility(View.GONE);
                }
                else if (listKost.size()>0){
                    empty.setVisibility(View.GONE);
                    listView.setVisibility(View.VISIBLE);
                    vLoading.setVisibility(View.GONE);
                    loadingPrgress.setVisibility(View.GONE);
                }
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), databaseError.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
