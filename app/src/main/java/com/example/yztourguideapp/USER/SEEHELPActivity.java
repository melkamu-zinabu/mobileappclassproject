package com.example.yztourguideapp.USER;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yztourguideapp.ADMIN.SEEHELPActivity_for_admin;
import com.example.yztourguideapp.ADMIN.selectlistnerforhelp;
import com.example.yztourguideapp.MODEL.Helpmodel;
import com.example.yztourguideapp.R;
import com.example.yztourguideapp.common.LOGINActivity;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import androidx.annotation.Nullable;

import android.widget.ProgressBar;
import android.widget.SearchView;

import com.example.yztourguideapp.Adapterforp.adapterforhelp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;

public class SEEHELPActivity extends AppCompatActivity implements selectlistnerforhelp {
    private RecyclerView recyclerView;
    private DrawerLayout drawerLayout;
    private DatabaseReference database;
    private ProgressBar loadingPB;
    private SearchView searchView;
    adapterforhelp adapter;
    ArrayList<Helpmodel> listitem;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seehelp);
        firebaseAuth=FirebaseAuth.getInstance();
        searchView=findViewById(R.id.inputsearch);
        searchView.clearFocus();
        loadingPB=findViewById(R.id.idPBLoad);
        recyclerView=findViewById(R.id.recylerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(true);
        database= FirebaseDatabase.getInstance().getReference().child("help");
        listitem=new ArrayList<>();
        adapter=new adapterforhelp(getApplicationContext(),listitem,this);
        // to search from database
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterlist(newText);
                return true;
            }

            private void filterlist(String newText) {
                ArrayList<Helpmodel> filteredlist=new ArrayList<>();
                for (Helpmodel helpmodel:listitem){
                    if (helpmodel.getTitle().toLowerCase().contains(newText.toLowerCase())){
                        filteredlist.add(helpmodel);
                    }

                }
                if (filteredlist.isEmpty()){
                    Toast.makeText(getApplicationContext(), "NO DATA FOUND", Toast.LENGTH_SHORT).show();
                }
                else{
                    adapter.setFilteredList(filteredlist);
                }
            }
        });
        //set the adapter to the recycler view
        recyclerView.setAdapter(adapter);
        // retrieve dfrom database using addchild event listner or we can use addvalueEventlistener
        database.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                // on below line we are hiding our progress bar.
                loadingPB.setVisibility(View.GONE);
                // adding snapshot to our array list on below line.
                listitem.add(snapshot.getValue(Helpmodel.class));

                // notifying our adapter that data has changed.
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                // this method is called when new child is added
                // we are notifying our adapter and making progress bar
                // visibility as gone.
                loadingPB.setVisibility(View.GONE);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                // notifying our adapter when child is removed.
                adapter.notifyDataSetChanged();
                loadingPB.setVisibility(View.GONE);

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                // notifying our adapter when child is moved.
                adapter.notifyDataSetChanged();
                loadingPB.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        // to set up custom action bar for this activity
        MaterialToolbar toolbar=findViewById(R.id.topappbar);
        drawerLayout=findViewById(R.id.my_drawer_layout);
        NavigationView navigationView=findViewById(R.id.naviagtion_view);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);

            }
        });
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id=item.getItemId();
                Intent intent;
                drawerLayout.closeDrawer(GravityCompat.START);
                switch (id)
                {
                    case R.id.nav_announce:
                        intent=new Intent(getApplicationContext(), SEEUPLOADEDANNOUNCEActivity.class);
                        startActivity(intent);
                        // finish();
                        return true;
                    case R.id.nav_book:
                        intent=new Intent(getApplicationContext(), UPLOADBOOKActivity.class);
                        startActivity(intent);
                        // finish();
                        return true;
                    case R.id.nav_help:
                        intent=new Intent(getApplicationContext(), SEEHELPActivity.class);
                        startActivity(intent);
                        // finish();
                        return true;
                    case R.id.nav_logout:
                        firebaseAuth.signOut();
                        intent=new Intent(getApplicationContext(), LOGINActivity.class);
                        startActivity(intent);
                        finish();
                        return true;
                    default:
                        return true;
                }
            }
        });


    }


    @Override
    public void onitemclick(Helpmodel helpmodel) {

    }

}
