package com.example.ourkos.ui.settings;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.ourkos.ChangePassword;
import com.example.ourkos.FirebaseDBCreateKostActivity;
import com.example.ourkos.LoginActivity;
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

    private Button logoutBtn, profileBtn, changePwButton, notifButton, helpButton;
    private Button pemilikBtn;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private DatabaseReference database;
    private String type;
    private ImageView imagePemilik;
    private View vPemilk;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        settingsViewModel = ViewModelProviders.of(this).get(SettingsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_settings, container, false);
        settingsViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
//                textView.setText(s);
            }
        });

        initView(root);

        if(type!="Pemilik Kos"){
            pemilikBtn.setVisibility(View.INVISIBLE);
            imagePemilik.setVisibility(View.INVISIBLE);
            vPemilk.setVisibility(View.INVISIBLE);
        }
        pemilikBtn.setVisibility(View.VISIBLE);
        imagePemilik.setVisibility(View.VISIBLE);
        vPemilk.setVisibility(View.VISIBLE);
        initLogout(root);
        initChangePassword(root);
        initProfile(root);
        iniPemilik();

        return root;
    }

    private void initView(View root) {
        pemilikBtn=root.findViewById(R.id.pemilikBtn);
        imagePemilik=root.findViewById(R.id.imagepemilik);
        vPemilk=root.findViewById(R.id.vpemilik);
        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        database= FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid());
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                type = dataSnapshot.child("type").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity().getApplicationContext(), databaseError.getMessage(), Toast.LENGTH_LONG).show();
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
                Intent pemilik =new Intent(getActivity(), FirebaseDBCreateKostActivity.class);
                startActivity(pemilik);
            }
        });
    }
}