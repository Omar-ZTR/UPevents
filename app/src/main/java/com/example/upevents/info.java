package com.example.upevents;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class info extends AppCompatActivity {

    TextView n,t,p,c,w ,e,loc,pr,des,cat;
    FrameLayout called;
    ImageView im;
    Button partt;
    int[] imgev = {R.drawable.dddddd,R.drawable.epi1,R.drawable.epi2,R.drawable.evvvh,
            R.drawable.pryty,R.drawable.dddddd,R.drawable.epi2,R.drawable.epi1,R.drawable.evvvh};
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        Resources res = getResources();
        getSupportActionBar().hide();
        im = (ImageView) findViewById(R.id.imgi);
        n = (TextView)findViewById(R.id.namei);
        t=(TextView) findViewById(R.id.ttmm);
        w=(TextView) findViewById(R.id.wak);
        p=(TextView) findViewById(R.id.phon);
        c=(TextView) findViewById(R.id.crt);
        e=(TextView) findViewById(R.id.email);
        loc=(TextView) findViewById(R.id.loc);
        pr=(TextView) findViewById(R.id.pri);
        des=(TextView) findViewById(R.id.desc);
        cat=(TextView) findViewById(R.id.cat);
        partt=(Button) findViewById(R.id.part);


        called=(FrameLayout) findViewById(R.id.call);

        Intent i= getIntent();
        Bundle b = i.getExtras();
        if (b != null){
            String named = b.getString("name");
            String timed = b.getString("date");
            String tell = b.getString("phone");
            String creat = b.getString("cre");
            String loca = b.getString("loc");
            String prix = b.getString("pri");
            String descc = b.getString("desc");
            String email = b.getString("email");
            String wake = b.getString("time");
            int vis = b.getInt("inv");

            int pos = b.getInt("pos");
            im.setImageResource(imgev[pos]);
            n.setText(named);
            t.setText(timed);
            p.setText(tell);
            w.setText(wake);
            c.setText(creat);
            e.setText(email);
            loc.setText(loca);
            pr.setText(prix);
            des.setText(descc);


        }


        String u = getIntent().getStringExtra("user");

        partt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                partt.setVisibility(View.INVISIBLE);
                final String name = n.getText().toString();
                final String time = w.getText().toString();
                final String descr = des.getText().toString();
                final String email = e.getText().toString();
                final String phone = p.getText().toString();
                final String localisation = loc.getText().toString();
                final String price = pr.getText().toString();
                final String date = t.getText().toString();
                final String createur = c.getText().toString();



                    Myevnt post= new Myevnt( name,createur,date,time,email,phone, u, price,localisation,descr);
                    addevnt(post);
            }
        });
        called.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("tel:"+p.getText().toString());
                Intent intent = new Intent(Intent.ACTION_CALL,uri);
                if (ContextCompat.checkSelfPermission(info.this,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                }
                else
                {
                    startActivity(intent); }
            }
        });

    }

    private void addevnt(Myevnt post) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Myevnt").push();
        String key = myRef.getKey();
        post.setPostKey(key);

        myRef.setValue(post).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                showMessage("evnt added");


            }
        });


    }


    private void showMessage(String message) {
        Toast.makeText(getApplicationContext(),message, Toast.LENGTH_LONG).show();
    }

    }
