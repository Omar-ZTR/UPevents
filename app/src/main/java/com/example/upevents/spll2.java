package com.example.upevents;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class spll2 extends AppCompatActivity {

ImageView im;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spll2);
        getSupportActionBar().hide();
        im=(ImageView) findViewById(R.id.imsp);
        im.animate().rotation(360f).setDuration(1000).start();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(spll2.this,MainActivity.class));
                finish();
            }
        },1500);
    }


}