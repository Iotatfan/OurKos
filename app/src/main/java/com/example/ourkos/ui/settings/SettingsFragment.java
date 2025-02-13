package com.example.ourkos.ui.settings;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.ourkos.ChangePassword;
import com.example.ourkos.LoginActivity;
import com.example.ourkos.OwnerActivity;
import com.example.ourkos.ProfileActivity;
import com.example.ourkos.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SettingsFragment extends Fragment {

    private SettingsViewModel settingsViewModel;
    private DatabaseReference mDatabase;
    private Button logoutBtn, profileBtn, changePwButton, notifButton, helpButton;
    private LinearLayout kos;
    private Button pemilikBtn;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private DatabaseReference database;
    private ImageView imagePemilik;
    private View vPemilk, vLoading;
    private ProgressBar loadingPrgress;
    String username, gender, phone, img;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        settingsViewModel = ViewModelProviders.of(this).get(SettingsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_settings, container, false);

        initView(root);
        initLogout(root);
        initChangePassword(root);
        initProfile(root);
        iniPemilik();

        return root;
    }

    private void initView(View root) {

        pemilikBtn = root.findViewById(R.id.pemilikBtn);
        imagePemilik = root.findViewById(R.id.imagepemilik);
        vPemilk = root.findViewById(R.id.vpemilik);
        kos = root.findViewById(R.id.linearKos);
        vLoading = root.findViewById(R.id.white_view);
        loadingPrgress = root.findViewById(R.id.loading);

        vLoading.setVisibility(View.VISIBLE);
        loadingPrgress.setVisibility(View.VISIBLE);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        database = FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid());

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                 String type = dataSnapshot.child("type").getValue().toString();
                username = dataSnapshot.child("username").getValue().toString();
                gender = dataSnapshot.child("gender").getValue().toString();
//                phone = dataSnapshot.child("phone").getValue().toString();
//                img = dataSnapshot.child("profilePic").child("imglink").getValue().toString();

                if(type.equals("Pemilik Kos")){
                    pemilikBtn.setVisibility(View.VISIBLE);
                    imagePemilik.setVisibility(View.VISIBLE);
                    vPemilk.setVisibility(View.VISIBLE);
                    kos.setVisibility(View.VISIBLE);
                    vLoading.setVisibility(View.GONE);
                    loadingPrgress.setVisibility(View.GONE);
//                    Toast.makeText(getActivity().getApplicationContext(), "Masuk", Toast.LENGTH_LONG).show();
                }
                else{
                    pemilikBtn.setVisibility(View.GONE);
                    imagePemilik.setVisibility(View.GONE);
                    vPemilk.setVisibility(View.GONE);
                    kos.setVisibility(View.GONE);
                    vLoading.setVisibility(View.GONE);
                    loadingPrgress.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Toast.makeText(getActivity().getApplicationContext(), databaseError.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initLogout(View rootView) {
        logoutBtn = rootView.findViewById(R.id.logoutBtn);
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent logout = new Intent(getActivity(), LoginActivity.class);
                auth.getInstance().signOut();
                startActivity(logout);

            }
        });
    }

    private void initProfile(View rootView) {
        profileBtn = rootView.findViewById(R.id.profileBtn);
        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profile = new Intent(getActivity(), ProfileActivity.class);
//                profile.putExtra("username", username);
//                profile.putExtra("gender", gender);
//                profile.putExtra("phone", phone);
//                profile.putExtra("img_url", img);
                startActivity(profile);
            }
        });
    }

    private void initChangePassword(View root) {

        changePwButton = root.findViewById(R.id.passBtn);
        changePwButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent changePw = new Intent(getActivity(), ChangePassword.class);
                startActivity(changePw);
            }
        });
    }

    private void iniPemilik(){
        pemilikBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pemilik = new Intent(getActivity(), OwnerActivity.class);
                startActivity(pemilik);
            }
        });
    }

}