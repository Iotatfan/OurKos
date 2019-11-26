package com.example.ourkos.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ourkos.Message;
import com.example.ourkos.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private DatabaseReference database;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private ProgressBar loadingPrgress;
    private TextView empty;
    private View vLoading;
    private List<Message> pesan;
    private RecyclerView listChat;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel = ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        initView(root);
        getUserData();
        return root;
    }

    private void initView(View v){
        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        database= FirebaseDatabase.getInstance().getReference();
        pesan=new ArrayList<>();
        listChat=v.findViewById(R.id.list_chat);
        empty = v.findViewById(R.id.empty_view);
        loadingPrgress = v.findViewById(R.id.loading);
        vLoading=v.findViewById(R.id.white_view);
        vLoading.setVisibility(View.VISIBLE);
        loadingPrgress.setVisibility(View.VISIBLE);
        listChat.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        listChat.setLayoutManager(layoutManager);
    }
    private void getUserData(){
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}