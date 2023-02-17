package com.example.yztourguideapp.ADMIN;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yztourguideapp.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class MANAGEHELP extends AppCompatActivity {
    private TextView textview1, textview2;
    private Button buttonone;
    ProgressBar progressBar;
    DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_managehelp);
        textview1=findViewById(R.id.helptitleone);
        textview2=findViewById(R.id.helpdescrone);
        //button1=findViewById(R.id.idBtnedit);
        buttonone=findViewById(R.id.btndeletehelpone);
        progressBar=findViewById(R.id.progressBarhelpone);
        //to accept key from from other activity
        String helpkey=getIntent().getStringExtra("helpkey");
        ref=FirebaseDatabase.getInstance().getReference().child("help");
        ref.child(helpkey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String title=snapshot.child("title").getValue().toString();
                    String description=snapshot.child("description").getValue().toString();
                    textview1.setText(title);
                    textview2.setText(description);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        buttonone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ref.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getApplicationContext(), "DELETED ", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), SEEHELPActivity_for_admin.class));



                    }
                });
            }
        });




    }
}