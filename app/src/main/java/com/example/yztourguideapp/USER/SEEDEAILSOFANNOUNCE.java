package com.example.yztourguideapp.USER;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yztourguideapp.ADMIN.SEEUPLOADEDANNOUNCEActivity_for_admin;
import com.example.yztourguideapp.MODEL.Tourmodel;
import com.example.yztourguideapp.R;
import com.example.yztourguideapp.USER.UPLOADBOOKActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
public class SEEDEAILSOFANNOUNCE extends AppCompatActivity {
    private TextView textview1, textview2;
    private ImageView  imageView;
    private Button button1,button2;
    DatabaseReference ref,dbref;
    StorageReference storageReference;
    Tourmodel tourmodel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seedeailsofannounce);
        textview1=findViewById(R.id.imagetitle);
        textview2=findViewById(R.id.textView_view_activity);
        imageView=findViewById(R.id.image_single_view_activity);
        //button1=findViewById(R.id.idBtnedit);
        button2=findViewById(R.id.deletebtn);
        String tourkey=getIntent().getStringExtra("tourkey");
        dbref=FirebaseDatabase.getInstance().getReference().child("upload_tour").child(tourkey);
        storageReference= FirebaseStorage.getInstance().getReference().child("upload_tour_image").child(tourkey+".jpg");
        ref= FirebaseDatabase.getInstance().getReference().child("upload_tour");
        // to retrieve data from database using .addvalueEventListner
        ref.child(tourkey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String title=snapshot.child("title").getValue().toString();
                    String imagedescr=snapshot.child("imagedescr").getValue().toString();
                    String imageurl=snapshot.child("imageurl").getValue().toString();
                    //to load image from database we use picasso
                    Picasso.get().load(imageurl).into(imageView);
                    textview1.setText(title);
                    textview2.setText(imagedescr);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        // to redirect to next activity
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), UPLOADBOOKActivity.class));
               // finish();
            }
        });

}
}

