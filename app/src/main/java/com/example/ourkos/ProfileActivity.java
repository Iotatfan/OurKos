package com.example.ourkos;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

public class ProfileActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private ImageView imageViewProfile;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private EditText editTextUsername;
    private AppCompatTextView appCompatTextViewJK, appCompatTextViewEmail, appCompatTextViewPhone;
    private FloatingActionButton yes;
    private ProgressBar mLoading;
    private Toolbar toolbar;
    private final int SINGLE_IMAGE = 2;
    private Uri imageUri;
    private FirebaseStorage storage;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().hide();

        initView();
        backButton();
        getUserData();
        updateButton();
        changePicture();

    }

    private void initView(){
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        editTextUsername = findViewById(R.id.editTextUsername);
        appCompatTextViewEmail = findViewById(R.id.editTextEmail);
        appCompatTextViewJK = findViewById(R.id.editTextGender);
        appCompatTextViewPhone = findViewById(R.id.editTextPhone);
        mLoading = findViewById(R.id.imgLoading);
        yes = findViewById(R.id.btnYes);
        toolbar = findViewById(R.id.toolbar);
        imageViewProfile = findViewById(R.id.profilePic);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    private void backButton(){
        toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_white_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getUserData(){
        appCompatTextViewEmail.setText(user.getEmail());

//        Intent profile = getIntent();
//        String username = profile.getStringExtra("username");
//        String gender = profile.getStringExtra("gender");
//        String phone = profile.getStringExtra("phone");
//        String img = profile.getStringExtra("img_url");

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid());

        mLoading.setVisibility(View.VISIBLE);
        imageViewProfile.setVisibility(View.GONE);

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String username = dataSnapshot.child("username").getValue().toString();
                String gender = dataSnapshot.child("gender").getValue().toString();
                String phone = dataSnapshot.child("phone").getValue().toString();
                String img = dataSnapshot.child("profilePic").child("imglink").getValue().toString();

                editTextUsername.setText(username, EditText.BufferType.EDITABLE);
                appCompatTextViewJK.setText(gender);
                appCompatTextViewPhone.setText(phone);
                if(!img.isEmpty()) {
                    Glide.with(ProfileActivity.this).load(img).into(imageViewProfile);
//                    imageViewProfile.setImageURI();
                }

                mLoading.setVisibility(View.GONE);
                imageViewProfile.setVisibility(View.VISIBLE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ProfileActivity.this, databaseError.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void changePicture() {

        imageViewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, SINGLE_IMAGE);
            }
        });

    }

    private void updateButton() {

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editTextUsername.getText().toString();

                if(username.isEmpty()) {
                    editTextUsername.setError("Nama ga bole kosong");
                }

                mDatabase.child("username").setValue(username, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                        if(databaseError!=null) {
                            Toast.makeText(ProfileActivity.this, "Data Nama Gagal di Update", Toast.LENGTH_LONG).show();
                        }
                        else {
                            Toast.makeText(ProfileActivity.this, "Data Nama Berhasil di Update", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        if(requestCode == SINGLE_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();

            storage = FirebaseStorage.getInstance();
            storageReference = storage.getReference().child("image").child(user.getUid()).child("profielPic");
            final StorageReference imgName = storageReference.child("profile_"+user.getUid());
            imgName.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    imgName.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String Url = String.valueOf(uri);
                            String folder = "profilePic";

                            HashMap<String,String> imgHash = new HashMap<>();
                            imgHash.put("imglink",Url);

                            mDatabase.child(folder).setValue(imgHash);

                        }
                    });
                }
            });
            Toast.makeText(this, "Foto Profil Berhasil Di Update", Toast.LENGTH_LONG).show();
        }

    }

}
