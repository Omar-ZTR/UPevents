package com.example.upevents;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    Button si,log;
    private FirebaseAuth authok;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        log=findViewById(R.id.l);
        si=findViewById(R.id.r);
        authok = FirebaseAuth.getInstance();
        getSupportActionBar().hide();

        log.setOnClickListener(view -> {
            if (authok.getCurrentUser()!= null) {
                Intent i = new Intent(getApplicationContext(), home.class);
                startActivity(i);}
            else {
                Intent i = new Intent(getApplicationContext(), login.class);
                startActivity(i);
            }
        });
        si.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), siginup.class);
            startActivity(i);
        });

    }

}