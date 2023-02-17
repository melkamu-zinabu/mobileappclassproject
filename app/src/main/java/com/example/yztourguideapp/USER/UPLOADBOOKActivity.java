package com.example.yztourguideapp.USER;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yztourguideapp.ADMIN.SEEHELPActivity_for_admin;
import com.example.yztourguideapp.MODEL.Tourmodel;
import com.example.yztourguideapp.R;
import com.example.yztourguideapp.common.LOGINActivity;
import com.example.yztourguideapp.common.REGISTRATIONActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

public class UPLOADBOOKActivity extends AppCompatActivity {
    private MaterialToolbar toolbar;
    DatabaseReference Dataref;
    FirebaseDatabase firebaseDatabase;
    StorageReference Storageref;
    TextView textView;
    private DrawerLayout drawerLayout;
    FirebaseAuth firebaseAuth;
    private EditText editTextfullname,editTextphone,editTextdate,editTextdestination;
    private Button btnbutton;
    private ImageView imageView;
    private ProgressBar progressbar;
    Tourmodel tourmodel;
    Uri imageurl;
    boolean isimageadd=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_uploadbook);
        textView=findViewById(R.id.tvone);
        editTextfullname=findViewById(R.id.fullname);
        editTextphone=findViewById(R.id.phonenumber);
        editTextdate=findViewById(R.id.date);
        editTextdestination=findViewById(R.id.destination);
        btnbutton=findViewById(R.id.btnbutton);
        imageView=findViewById(R.id.imageupload);
        progressbar=findViewById(R.id.progressone);
        firebaseDatabase=FirebaseDatabase.getInstance();
        Dataref = firebaseDatabase.getInstance().getReference().child("book");
        Storageref = FirebaseStorage.getInstance().getReference().child("bookimage");

        ActivityResultLauncher<String> finalMGetContent= registerForActivityResult(new ActivityResultContracts.GetContent(),
                result -> {
                    if (result != null) {
                        imageurl = result;
                        isimageadd = true;
                        // till now the image selected
                        //set to imageview
                        imageView.setImageURI(imageurl);
                        textView.setText("");
                    }
                });
        imageView.setOnClickListener(view ->finalMGetContent.launch("image/*"));

        btnbutton.setOnClickListener(view -> {
            final String fullname = editTextfullname.getText().toString();
            final String phonenumber = editTextphone.getText().toString();
            final String date = editTextdate.getText().toString();
            final String destination = editTextdestination.getText().toString();
            //validation
            if (destination.isEmpty()){
                editTextdestination.setError("fill destination");
            }
            else if (fullname.isEmpty()){
                editTextfullname.setError("fill the name");
            }
            else if (phonenumber.length()<9||phonenumber.length()>11){
                editTextphone.setError("fill the phone correctly");
            }
            else if (date.isEmpty()){
                editTextdate.setError("fill the date correctly");
            }
            else if(imageurl==null) {
                Toast.makeText(getApplicationContext(), "please select the image", Toast.LENGTH_SHORT).show();
            }
            else{
                uploadimage(fullname,phonenumber,date,destination);

            }
        });
    }

    private void uploadimage(String fullname, String phonenumber,
                             String date, String destination) {
        progressbar.setVisibility(View.VISIBLE);

        //
      //  FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
       // String bookid =firebaseAuth.getCurrentUser().getUid();

        final String bookid=fullname;
        Storageref.child(bookid+".jpg").putFile(imageurl).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Storageref.child(bookid+".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        HashMap hashMap=new HashMap<>();
                        hashMap.put("fullname",fullname);
                        hashMap.put("phonenumber",phonenumber);
                        hashMap.put("date",date);
                        hashMap.put("destination",destination);
                        hashMap.put("imageurl",uri.toString());
                        //
                       // hashMap.put("state","pending");
                        //
                        Dataref.child(bookid).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                progressbar.setVisibility(View.INVISIBLE);
                                startActivity(new Intent(getApplicationContext(),SEEUPLOADEDANNOUNCEActivity.class));
                                Toast.makeText(getApplicationContext(), "DATA uploaded successfully", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });
                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressbar.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext(), "fail to upload", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), SEEUPLOADEDANNOUNCEActivity.class));
                finish();

            }
        });

//        toolbar=findViewById(R.id.topappbar);
//        drawerLayout=findViewById(R.id.my_drawer_layout);
//        NavigationView navigationView=findViewById(R.id.naviagtion_view);
//        toolbar.setNavigationOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));
//        navigationView.setNavigationItemSelectedListener(item -> {
//            int id=item.getItemId();
//            Intent intent;
//            drawerLayout.closeDrawer(GravityCompat.START);
//            switch (id)
//            {
//                case R.id.nav_announce:
//                    intent=new Intent(getApplicationContext(), SEEUPLOADEDANNOUNCEActivity.class);
//                    startActivity(intent);
//                    // finish();
//                    return true;
//                case R.id.nav_book:
//                    intent=new Intent(getApplicationContext(), UPLOADBOOKActivity.class);
//                    startActivity(intent);
//                    // finish();
//                    return true;
//                case R.id.nav_help:
//                    intent=new Intent(getApplicationContext(), SEEHELPActivity.class);
//                    startActivity(intent);
//                    // finish();
//                    return true;
//                case R.id.nav_logout:
//                    firebaseAuth.signOut();
//                    intent=new Intent(getApplicationContext(), LOGINActivity.class);
//                    startActivity(intent);
//                    finish();
//                    return true;
//                default:
//                    return true;
//            }
//        });



    }
}