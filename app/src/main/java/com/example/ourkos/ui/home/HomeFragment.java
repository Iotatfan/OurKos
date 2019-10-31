package com.example.ourkos.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.ourkos.R;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private HomeViewModel homeViewModel;
    ListView listView;
    SearchView searchView;
    ArrayList<String> list;
    ArrayAdapter adapter;

    View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        root = inflater.inflate(R.layout.fragment_home, container, false);
        searchView = root.findViewById(R.id.cari);
//        searchView.setIconified(true);
        searchView.setSubmitButtonEnabled(false);
        listView = root.findViewById(R.id.list);

        listView.setVisibility(root.GONE);

        list = new ArrayList<>();
        list.add("Semarang");
        list.add("Jakarta");
        list.add("Bandung");
        list.add("Surabaya");
        list.add("Solo");
        list.add("Yogyakarta");
        list.add("Malang");
        adapter = new ArrayAdapter<>(getActivity().getApplicationContext(),android.R.layout.simple_list_item_1,list);
        listView.setAdapter(adapter);
        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus){
                if(!hasFocus){
                    listView.setVisibility(root.GONE);
                }
                else{
                    listView.setVisibility(root.VISIBLE);
                }
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(list.contains(query)){
                    adapter.getFilter().filter(query);
//                    searchView.clearFocus();
                }else{
                    Toast.makeText(getActivity().getApplicationContext(), "No Match found",Toast.LENGTH_LONG).show();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selected = (String)adapterView.getItemAtPosition(i);
                searchView.setQuery(selected,true);
            }
        });
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        return root;
    }
}