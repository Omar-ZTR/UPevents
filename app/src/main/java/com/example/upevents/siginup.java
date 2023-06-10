package com.example.upevents;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class siginup extends AppCompatActivity {

    TextInputEditText username,emaill , password , cp ;
    TextView glog;
    Button log;
    ImageButton ret;
    ImageView ImgUserPhoto;
    static int PReqCode = 1;
    static int REQUESCODE = 1;
    Uri pickedImgUri;

    private FirebaseAuth  auth;
    FirebaseFirestore fstore;
    String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_siginup);
        emaill = findViewById(R.id.us);
        password = findViewById(R.id.ps);
        username = findViewById(R.id.usernamm);
        cp = findViewById(R.id.rp);
        log = findViewById(R.id.sn);
        glog = findViewById(R.id.have);
        ret = findViewById(R.id.rim);
        ImgUserPhoto=findViewById(R.id.imsi);


        auth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = emaill.getText().toString();
                String usernamed = username.getText().toString();
                String pass = password.getText().toString();
                String repass = cp.getText().toString();
                if (TextUtils.isEmpty(user) || TextUtils.isEmpty(pass) || TextUtils.isEmpty(repass) ||TextUtils.isEmpty(usernamed) ) {
                    Toast.makeText(siginup.this, "All fields required", Toast.LENGTH_SHORT).show();
                } else if ((pass.length() < 6)||(! pass.equals(repass)) ) {
                    Toast.makeText(siginup.this, "password failed ", Toast.LENGTH_SHORT).show();

                } else {
                    registerUser(user, pass);
//                    Intent i = new Intent(siginup.this,login.class);
//                    startActivity(i);

                }
            }
        });
        glog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(siginup.this,login.class);
                startActivity(i);
            }
        });
        ret.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        ImgUserPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Build.VERSION.SDK_INT >= 22) {

                    checkAndRequestForPermission();


                } else {
                    openGallery();
                }


            }
        });
    }


            private void registerUser(String email, String password) {

                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(siginup.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        String mail = emaill.getText().toString();
                        String fullname = username.getText().toString();
                        if (task.isSuccessful()) {
                            userID = auth.getCurrentUser().getUid();
                            DocumentReference documentReference = fstore.collection("users").document(userID);
                            Map<String,Object> user = new HashMap<>();
                            user.put("userName",fullname);
                            user.put("email",mail);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Log.d(TAG,"onSuccess: user profile created for " + userID);
                                    updateUserInfo(fullname, pickedImgUri, auth.getCurrentUser());
                                }
                            });
                            Toast.makeText(siginup.this, "registering successful  ", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(siginup.this, "failed registration!!! ", Toast.LENGTH_SHORT).show();
                        }
                    }


                });

            }







//    private void CreateUserAccount(String email, final String name, String password) {
//
//        auth.createUserWithEmailAndPassword(email, password)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//
//                            showMessage("Account created");
//                            updateUserInfo(name, pickedImgUri, auth.getCurrentUser());
//
//
//                        } else {
//                            showMessage("account creation failed" + task.getException().getMessage());
//                            regBtn.setVisibility(View.VISIBLE);
//                            loadingProgress.setVisibility(View.INVISIBLE);
//
//                        }
//                    }
//                });
//
//
//    }

    private void updateUserInfo(final String name, Uri pickedImgUri, final FirebaseUser currentUser) {
        StorageReference mStorage = FirebaseStorage.getInstance().getReference().child("users_photos");
        final StorageReference imageFilePath = mStorage.child(pickedImgUri.getLastPathSegment());
        imageFilePath.putFile(pickedImgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                imageFilePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        UserProfileChangeRequest profleUpdate = new UserProfileChangeRequest.Builder()
                                .setDisplayName(name)
                                .setPhotoUri(uri)
                                .build();


                        currentUser.updateProfile(profleUpdate)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if (task.isSuccessful()) {
                                            showMessage("Register Complete");
                                            updateUI();
                                        }

                                    }
                                });

                    }
                });


            }
        });


    }

    private void updateUI() {
        Intent homeActivity = new Intent(getApplicationContext(), home.class);
        startActivity(homeActivity);
        finish();


    }

    private void showMessage(String message) {

        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, REQUESCODE);
    }

    private void checkAndRequestForPermission() {


        if (ContextCompat.checkSelfPermission(siginup.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(siginup.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {

                Toast.makeText(siginup.this, "Please accept for required permission", Toast.LENGTH_SHORT).show();

            } else {
                ActivityCompat.requestPermissions(siginup.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        PReqCode);
            }

        } else
            openGallery();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == REQUESCODE && data != null) {

            pickedImgUri = data.getData();
            ImgUserPhoto.setImageURI(pickedImgUri);


        }


    }}
