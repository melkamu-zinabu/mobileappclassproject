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

public class MANAGEANNOUNCEMENT extends AppCompatActivity {
    private TextView textview1, textview2;
    private ImageView  imageView;
    private ProgressBar progressBar;
    private Button button1,button2;
    DatabaseReference ref,dbref;
    StorageReference storageReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manageannouncement);
        progressBar=findViewById(R.id.idPBLoad);
        textview1=findViewById(R.id.imagetitle);
        textview2=findViewById(R.id.textView_view_activity);
        imageView=findViewById(R.id.image_single_view_activity);
        //button1=findViewById(R.id.idBtnedit);
        button2=findViewById(R.id.deletebtn);
        //to accept key send from the other activity
        Intent intent=getIntent();
        String tourkey=intent.getExtras().getString("tourkey");
        dbref=FirebaseDatabase.getInstance().getReference().child("upload_tour").child(tourkey);
        storageReference= FirebaseStorage.getInstance().getReference().child("upload_tour_image").child(tourkey+".jpg");
        ref= FirebaseDatabase.getInstance().getReference().child("upload_tour");
        //to retrieve database from the database we use valueEventlistener
        ref.child(tourkey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String title=snapshot.child("title").getValue().toString();
                    String imagedescr=snapshot.child("imagedescr").getValue().toString();
                    String imageurl=snapshot.child("imageurl").getValue().toString();
                    //picasso to load image  from the storage  database
                    Picasso.get().load(imageurl).into(imageView);
                    textview1.setText(title);
                    textview2.setText(imagedescr);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        //when we click delete button
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                dbref.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                progressBar.setVisibility(View.INVISIBLE);
                                Toast.makeText(MANAGEANNOUNCEMENT.this, "DELETED ", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), SEEUPLOADEDANNOUNCEActivity_for_admin.class));

                            }
                        });

                    }
                });
            }
        });
    }
}