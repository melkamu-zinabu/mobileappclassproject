package com.example.yztourguideapp.common;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yztourguideapp.R;
import com.example.yztourguideapp.USER.SEEUPLOADEDANNOUNCEActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class REGISTRATIONActivity extends AppCompatActivity {
    private TextView alreadyhaveaccount;
    private TextView input_email,inputpassword,inputconfirmpassword;
    private Button registerbtn;
    private String email_pattern="[a-zA-Z0-9._-]+@[a-z]+.+[a-z]+";
    private  ProgressDialog progressDialog;
     FirebaseAuth mauth;
     FirebaseFirestore fsore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrationactivity);
        input_email=findViewById(R.id.email);
        inputpassword=findViewById(R.id.password);
        inputconfirmpassword=findViewById(R.id.confirmpassword);
        registerbtn =findViewById(R.id.registerbtn);
        progressDialog= new ProgressDialog(this);
        mauth=FirebaseAuth.getInstance();//INTIALIZE
        fsore=FirebaseFirestore.getInstance();
        alreadyhaveaccount=findViewById(R.id.alreadthaveaccount);
        // if you have alread account
        alreadyhaveaccount.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), LOGINActivity.class));
            finish();
        });
        //if press register button
        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performauth();
            }
        });


    }
    private void performauth(){
        String email=input_email.getText().toString();
        String password=inputpassword.getText().toString();
        String confirm_password=inputconfirmpassword.getText().toString();
        //validation for user input

        if (email.isEmpty()||!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            input_email.setError("enter valid useremail");
        }
        else if (password.isEmpty()||password.length()<6){
            inputpassword.setError("enter proper password");
        }
        else if (!password.equals(confirm_password)){
            inputconfirmpassword.setError("password don't match");
        }
        else{
            //setup dialog box
                progressDialog.setMessage("please wait while registration");
                progressDialog.setTitle("REGISTRATION");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();
                mauth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        progressDialog.dismiss();
                        FirebaseUser firebaseUser = mauth.getCurrentUser();
                        Toast.makeText(getApplicationContext(), "REGISTRATION SUCCESSFUL", Toast.LENGTH_SHORT).show();
                        DocumentReference df = fsore.collection("user").document(firebaseUser.getUid());
                        Map<String, Object> userinfo = new HashMap<>();
                        userinfo.put("email", email);
                        userinfo.put("password", password);
                        //specify if user is admin
                        userinfo.put("isuser", "user");
                        df.set(userinfo);
                        //after register user, redirect user to other activity
                        redirect_user_to_next_activity();
                    }
                    else {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "failed to register", Toast.LENGTH_SHORT).show();
                     }
                });


        }


    }

    private void redirect_user_to_next_activity() {
        Intent intent=new Intent(this,  SEEUPLOADEDANNOUNCEActivity.class);
        //IT IS LIKE FINISH NOT TO GOTO BACK
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();



    }
}