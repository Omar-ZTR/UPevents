package com.example.upevents;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity {

    TextInputEditText user;
    TextInputEditText pass;
    Button login;
    ImageButton rett;
    TextView gsing;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        user = findViewById(R.id.user);
        pass = findViewById(R.id.pasl);
        login = findViewById(R.id.bl);
        rett = findViewById(R.id.rtm);
        gsing = findViewById(R.id.gsin);

        auth = FirebaseAuth.getInstance();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String n = user.getText().toString();
                String p = pass.getText().toString();
                loginUser(n,p);
            }
        });
        gsing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(login.this,siginup.class);
                startActivity(i);
            }
        });
        rett.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

            private void loginUser(String email, String password) {

                auth.signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(login.this,"login Successful",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(login.this , home.class));
                        finish();

                    }
                });

            }

}