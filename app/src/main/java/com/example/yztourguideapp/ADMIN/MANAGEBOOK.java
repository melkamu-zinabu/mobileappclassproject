package com.example.yztourguideapp.ADMIN;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yztourguideapp.MODEL.Bookmodel;
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

public class MANAGEBOOK extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;
    StorageReference Storageref;
    DatabaseReference databaseReference;
    private TextView editTextfullname,editTextphone,editTextdate,editTextdestination,stateview;
    private Button btnbutton,editbtnbutton;
    private ImageView imageView;
    private ProgressBar progressbar;
    private Uri imageurl;
    boolean isimageadd=false;
    final static  int REQUEST_CODE_IMAGE=101;
    Bookmodel bookmodel;
    private String bookid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //to remove hide bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_managebook);
        editTextfullname=findViewById(R.id.fullname);
        editTextphone=findViewById(R.id.phonenumber);
        editTextdate=findViewById(R.id.date);
        progressbar=findViewById(R.id.idPBLoad);
        editTextdestination=findViewById(R.id.destination);
        // stateview=findViewById(R.id.state);
        // editbtnbutton=findViewById(R.id.editbtnbutton);
        imageView=findViewById(R.id.imageupload);
        btnbutton=findViewById(R.id.btnbutton);
        // progressbar=findViewById(R.id.progressone);
        //to accet key send from the other activity
        Intent intent=getIntent();
        String bookkey=intent.getExtras().getString("bookkey");
        //to get reference to the database
        firebaseDatabase=FirebaseDatabase.getInstance();
        Storageref= FirebaseStorage.getInstance().getReference().child("bookimage").child(bookkey+".jpg");
        databaseReference=firebaseDatabase.getReference().child("book").child(bookkey);
        // retrieve using addvlueEventlistener
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                     String fullname=snapshot.child("fullname").getValue().toString();
                     String phonenumber=snapshot.child("phonenumber").getValue().toString();
                     String date=snapshot.child("date").getValue().toString();
                     String destination=snapshot.child("destination").getValue().toString();
                     String imageurl=snapshot.child("imageurl").getValue().toString();
                     //String state = snapshot.child("state").getValue().toString();
                     //use picasso to load image
                     Picasso.get().load(imageurl).into(imageView);
                     editTextfullname.setText(fullname);
                     editTextphone.setText(phonenumber);
                     editTextdate.setText(date);
                     editTextdestination.setText(destination);
                     //stateview.setText(state);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        // set click listener for delete button
        btnbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressbar.setVisibility(View.VISIBLE);
                deletebook();
            }
        });
//        // yhe ksera dont worry
//        stateview.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //embi kale addvlueventlisner memoker
//                if(stateview.getText().toString().equals("pending")) {
//                    stateview.setText("accepted");
//                    String x=stateview.getText().toString();
//                    String key = databaseReference.getKey();
//                    databaseReference.child("book").child(key).child("state").setValue(x);
//
//                }
//                else {
//                    stateview.setText("pending");
//                    String x=stateview.getText().toString();
//                    String key = databaseReference.getKey();
//                    databaseReference.child("book").child(key).child("state").setValue(x);
//                }
//            }
//        });
    }
    private void deletebook() {
        //to remove data from realtimedatabase
        databaseReference.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                // to remove image from cloud storage
                Storageref.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        progressbar.setVisibility(View.INVISIBLE);
                        Toast.makeText(getApplicationContext(), "DELETED ", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), SEEUPLOADEDANNOUNCEActivity_for_admin.class));
                    }
                });

            }
        });
    }
}