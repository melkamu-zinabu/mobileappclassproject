package com.example.yztourguideapp.ADMIN;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.yztourguideapp.R;
import com.example.yztourguideapp.common.REGISTRATIONActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class UPLOADHELPActivity extends AppCompatActivity {
    EditText editText1,editText2;
    private ProgressBar progressBar;
    private Button btn;
    DatabaseReference Dataref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploadhelp);
        editText1=findViewById(R.id.helptitle);
        editText2=findViewById(R.id.helpdescr);
        progressBar=findViewById(R.id.progressBar3);
        btn=findViewById(R.id.helpbtn);
        Dataref = FirebaseDatabase.getInstance().getReference().child("help");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performhelp();
            }
        });
    }

    private void performhelp() {
        String title =editText1.getText().toString();
        String description=editText2.getText().toString();
        final String key=title;//it will return random key
        progressBar.setVisibility(View.VISIBLE);
        //to validate input
        if (title.isEmpty()){
            editText1.setError("ENTER TITLE");
        }
        else if (description.isEmpty()){
            editText2.setError("enter description ");
        }
        else{
            progressBar.setVisibility(View.VISIBLE);
            Map<String,Object> helpinfo= new HashMap<>();
            helpinfo.put("title",title);
            helpinfo.put("description",description);
            //store data
            Dataref.child(key).setValue(helpinfo).addOnSuccessListener(unused -> {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext(), "help added", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), SEEHELPActivity_for_admin.class));
                finish();

            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressBar.setVisibility(View.INVISIBLE);
                    startActivity(new Intent(getApplicationContext(), SEEHELPActivity_for_admin.class));
                    Toast.makeText(getApplicationContext(), "some error encounterd", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
        }


    }
}