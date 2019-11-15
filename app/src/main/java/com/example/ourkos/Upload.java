package com.example.ourkos;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
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

import java.util.ArrayList;

public class Upload extends AppCompatActivity {
    private Button btncover,btnbangun,btnkamar,btnmandi,upload,upload2,upload3,upload4,btnselesai;
    private FirebaseUser user;
    private FirebaseAuth auth;
    private DatabaseReference database;
    private ProgressBar progressBar;
    private FirebaseStorage storage;
    private final int MULTIPLE_IMAGE = 1;
    private final int SINGLE_IMAGE=2;
    private Uri ImageUri;
    private String nama;
    private StorageReference storageReference;
    ArrayList<Uri> ImageList = new ArrayList<Uri>();
    Kost kost;
    private int uploadcount;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        getSupportActionBar().hide();
        Intent intent = getIntent();
        nama = intent.getStringExtra("nama");
        initViews();
        progressBar.setVisibility(View.INVISIBLE);
        pilihFoto();
        getUserData();
        uploadFoto();
        kelar();
    }
    private void initViews(){
        btnbangun=findViewById(R.id.btnbangun);
        btncover=findViewById(R.id.btncover);
        btnkamar=findViewById(R.id.btnkamar);
        btnmandi=findViewById(R.id.btnmandi);
        btnselesai=findViewById(R.id.btn_selesai);
        upload=findViewById(R.id.btnupload1);
        upload2=findViewById(R.id.btnupload2);
        upload3=findViewById(R.id.btnupload3);
        upload4=findViewById(R.id.btnupload4);
        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        progressBar=findViewById(R.id.progressBar1);
        database=FirebaseDatabase.getInstance().getReference();
    }
    private void getUserData(){
        database.child("kost").child(user.getUid()).child(nama).child("detail").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                kost = dataSnapshot.getValue(Kost.class);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), databaseError.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
    private void pilihFoto(){
        btncover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,SINGLE_IMAGE);
            }
        });
        btnbangun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
                startActivityForResult(intent,MULTIPLE_IMAGE);
            }
        });
        btnkamar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
                startActivityForResult(intent,MULTIPLE_IMAGE);
            }
        });
        btnmandi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
                startActivityForResult(intent,MULTIPLE_IMAGE);
            }
        });
    }
    private void uploadFoto(){
        upload2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                storage=FirebaseStorage.getInstance();
                storageReference=storage.getReference().child("image").child(user.getUid()).child(kost.getNamaKost());
                for(uploadcount=0;uploadcount<ImageList.size();uploadcount++){
                    Uri oneImage=ImageList.get(uploadcount);
                    final StorageReference imageName=storageReference.child("imageBangunan" + uploadcount);
                    imageName.putFile(oneImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            imageName.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String Url =String.valueOf(uri);
                                    StoreLink(Url);
                                }
                            });
                        }
                    });
                }
            }
        });
    }
    private void StoreLink(String Url){
        ArrayList<String> imag=new ArrayList<String>();
        imag.add(Url);

        database.child("kost").child(user.getUid()).child(kost.getNamaKost()).child("image").push().setValue(imag);
        ImageList.clear();
        progressBar.setVisibility(View.INVISIBLE);
    }
    private void kelar(){
        btnselesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Upload.this,OwnerActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode == SINGLE_IMAGE && resultCode == RESULT_OK && data != null && data.getData()!=null){
            ImageUri =data.getData();
        }
        else if(requestCode==MULTIPLE_IMAGE && resultCode == RESULT_OK && data.getClipData()!=null){
            int count = data.getClipData().getItemCount();
            int currentcount=0;
            while(currentcount < count){
                ImageUri=data.getClipData().getItemAt(currentcount).getUri();
                ImageList.add(ImageUri);
                currentcount = currentcount + 1 ;
            }
        }
    }
}
