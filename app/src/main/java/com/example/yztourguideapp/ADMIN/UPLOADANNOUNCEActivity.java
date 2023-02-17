package com.example.yztourguideapp.ADMIN;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yztourguideapp.R;
import com.example.yztourguideapp.common.LOGINActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

public class UPLOADANNOUNCEActivity extends AppCompatActivity {
    private ImageView imageViewadd;
    private EditText inputimagedescription,imagetitle;
    private TextView progressbarviewer;
    private ProgressBar progressBar;
    private Button uploadbtn;
    private Uri imageurl;
    boolean isimageadd=false;
    final static  int REQUEST_CODE_IMAGE=101;
    DatabaseReference Dataref;
    StorageReference Storageref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploadannounce);
        imageViewadd = findViewById(R.id.imageviewadd);
        inputimagedescription = findViewById(R.id.imagedescription);
        imagetitle = findViewById(R.id.imagetitle);
        progressbarviewer = findViewById(R.id.prgeressviewer);
        progressBar = findViewById(R.id.progressBar);
        uploadbtn = findViewById(R.id.uploadbtn);
        progressbarviewer.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        Dataref = FirebaseDatabase.getInstance().getReference().child("upload_tour");
        Storageref = FirebaseStorage.getInstance().getReference().child("upload_tour_image");

        ActivityResultLauncher<String> finalMGetContent= registerForActivityResult(new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri result) {
                        if (result != null) {
                            imageurl = result;
                            isimageadd = true;
                            // till now the image selected
                            //set to imageview
                            imageViewadd.setImageURI(imageurl);
                        }
                    }
                });

        imageViewadd.setOnClickListener(view ->finalMGetContent.launch("image/*"));
        uploadbtn.setOnClickListener(view -> {
            final String title = imagetitle.getText().toString();
            final String imagedesc = inputimagedescription.getText().toString();
            //input validation
            if (imageurl==null) {
                Toast.makeText(getApplicationContext(), "please select and fill", Toast.LENGTH_SHORT).show();

            }
            else if (title.isEmpty()){
                imagetitle.setError("fill title");
            }
            else{
                uploadimage(title,imagedesc);
                  }
        });
    }
    private void uploadimage(String title,final String imagedesc) {
        progressbarviewer.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        final String key=title;//it will return random key
        Storageref.child(key+".jpg").putFile(imageurl).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Storageref.child(key+".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        HashMap hashMap=new HashMap<>();
                        hashMap.put("title",title);
                        hashMap.put("imagedescr",imagedesc);
                        hashMap.put("imageurl",uri.toString());
                        //save data
                        Dataref.child(key).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                startActivity(new Intent(getApplicationContext(), SEEUPLOADEDANNOUNCEActivity_for_admin.class));
                                Toast.makeText(getApplicationContext(), "DATA uploaded successfully", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });
                    }
                });

            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double progress=(snapshot.getBytesTransferred()*100)/snapshot.getTotalByteCount();
                progressBar.setProgress((int)progress);
                progressbarviewer.setText(progress+"%");

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                startActivity(new Intent(getApplicationContext(), SEEUPLOADEDANNOUNCEActivity_for_admin.class));
                Toast.makeText(getApplicationContext(), "fail to upload", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }
}