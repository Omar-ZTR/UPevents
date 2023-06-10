package com.example.upevents;

import static com.example.upevents.plistAdpter.listp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class profil extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseReference database;
    ListAdapter plistAdapter;
    static ArrayList<model> list;
    ImageView pr;
    TextView us;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        pr = findViewById(R.id.pro);
        us = findViewById(R.id.nus);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        recyclerView = findViewById(R.id.listp);
        Glide.with(this).load(currentUser.getPhotoUrl()).into(pr);
        us.setText(currentUser.getDisplayName());
        database = FirebaseDatabase.getInstance().getReference("Myevnt");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        plistAdapter = new ListAdapter(this,list);
        recyclerView.setAdapter(plistAdapter);
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    model evnt = dataSnapshot.getValue(model.class);
                    list.add(evnt);
                }
                plistAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu m) {
        getMenuInflater().inflate(R.menu.mbar,m);
        MenuItem ic = m.findItem(R.id.profile);
        ic.setVisible(false);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.home:
                FirebaseAuth.getInstance().signOut();
                Intent logout = new Intent(this,login.class);
                startActivity(logout);
                finish();
                break;
            case R.id.profile:
                Intent j = new Intent(profil.this,profil.class);
                startActivity(j);
                break;
            case R.id.search:
                Intent i = new Intent(profil.this,home.class);
                startActivity(i);


                break;
        }
        return false;
    }
}