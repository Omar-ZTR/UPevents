package com.example.upevents;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class home extends AppCompatActivity {


    EditText textsearch ;
    Button butser;

    RecyclerView recyclerView;
    DatabaseReference database;
    ListAdapter listAdapter;
    static ArrayList<model> list;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        textsearch = (EditText) findViewById(R.id.ser);
        butser =(Button)findViewById(R.id.bser);

        recyclerView = findViewById(R.id.listev);
        database = FirebaseDatabase.getInstance().getReference("evnt");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        listAdapter = new ListAdapter(this,list);
        recyclerView.setAdapter(listAdapter);
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    model evnt = dataSnapshot.getValue(model.class);
                    list.add(evnt);
                }
                listAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        butser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String t =textsearch.getText().toString();
                listAdapter.getFilter().filter(t);
            }
        });
    }
//    @Override
//     public boolean onOptionsItemSelected(MenuItem item){
//        int id = item.getItemId();
//        if (id==R.id.search) {
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//
//
//    }
    @Override
       public boolean onCreateOptionsMenu(Menu m) {
        getMenuInflater().inflate(R.menu.mbar,m);
        MenuItem ic = m.findItem(R.id.search);
        ic.setVisible(false);

        return true;
    }
//    public boolean onCreateOptionsMenu(Menu m) {
//        getMenuInflater().inflate(R.menu.mbar,m);
//        MenuItem menuItem = m.findItem(R.id.search);
//        SearchView searchView= (SearchView) menuItem.getActionView();
//        searchView.setMaxWidth(Integer.MAX_VALUE);
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//
//                String searchstr=newText;
//                listAdapter.getFilter().filter(newText);
//
//
//                return false;
//            }
//        });
//        return super.onCreateOptionsMenu(m);
//    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.home:
                FirebaseAuth.getInstance().signOut();
                Intent loginActivity = new Intent(this,login.class);
                startActivity(loginActivity);
                finish();
                break;
            case R.id.profile:
                Intent j = new Intent(home.this,profil.class);
                startActivity(j);
                break;
            case R.id.search:
                Intent i = new Intent(home.this,home.class);
                startActivity(i);


                break;
        }
        return false;
    }
}