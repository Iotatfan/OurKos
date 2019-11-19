package com.example.ourkos;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class SearchListActivity extends AppCompatActivity {

    private String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchlist);
        Intent intent = getIntent();
        name = intent.getStringExtra("place");
        getSupportActionBar().setTitle(name);

        ListView listView = findViewById(R.id.listV);
        List<Kost> listKost = new ArrayList<Kost>();
        ArrayList<String> img = new ArrayList<>();

        Kost kost;
        kost = new Kost();
        if(name.equals(kost.getRegion())){
            listKost.add(kost);
            listView.setAdapter(new KostAdapter(this,R.layout.list_item_search,listKost));
        }
    }
}
