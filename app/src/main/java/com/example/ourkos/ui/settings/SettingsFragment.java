package com.example.ourkos.ui.settings;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.ourkos.LoginActivity;
import com.example.ourkos.ProfileActivity;
import com.example.ourkos.R;
import com.google.firebase.auth.FirebaseAuth;

public class SettingsFragment extends Fragment {

    private SettingsViewModel settingsViewModel;

    private Button logoutBtn;
    private Button profileBtn;
    private FirebaseAuth auth;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        settingsViewModel =
                ViewModelProviders.of(this).get(SettingsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_settings, container, false);
//        final TextView textView = root.findViewById(R.id.text_notifications);
        settingsViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
//                textView.setText(s);
            }
        });

        initLogout(root);
        iniProfile(root);

        return root;
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

    private void iniProfile(View rootView) {
        profileBtn = rootView.findViewById(R.id.profileBtn);

        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profile = new Intent(getActivity(), ProfileActivity.class);
                startActivity(profile);
            }
        });
    }
}