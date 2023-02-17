package com.example.yztourguideapp.common;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yztourguideapp.ADMIN.SEEUPLOADEDANNOUNCEActivity_for_admin;
import com.example.yztourguideapp.ADMIN.UPLOADANNOUNCEActivity;
import com.example.yztourguideapp.R;
import com.example.yztourguideapp.USER.SEEUPLOADEDANNOUNCEActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class LOGINActivity extends AppCompatActivity {
    private TextView createacc,forgetpw;
    private EditText input_email,inputpassword;
    Button login_btn;
//    String email_pattern="[a-zA-Z0-9._-]+@[a-z]+.+[a-z]+";
    ProgressDialog progressDialog;
    FirebaseAuth mauth;
    FirebaseFirestore fstore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        //intialize
        createacc=findViewById(R.id.createaccount);
        input_email=(EditText)findViewById(R.id.email);
        inputpassword=(EditText)findViewById(R.id.password);
        forgetpw=findViewById(R.id.forgetpassword);
        login_btn =findViewById(R.id.loginbtn);
        progressDialog= new ProgressDialog(this);
        mauth=FirebaseAuth.getInstance();//INTIALIZE
        fstore=FirebaseFirestore.getInstance();
        //if you press login button
        login_btn.setOnClickListener(view -> perform_login());
        //if you press create account button
        createacc.setOnClickListener(view -> {
            startActivity(new Intent(LOGINActivity.this, REGISTRATIONActivity.class));
            finish();
        });
        //if you press forget password
        forgetpw.setOnClickListener(view -> {
            startActivity(new Intent(LOGINActivity.this, FORGETPASSWORDActivity.class));
            finish();
        });
    }

    private void perform_login() {
        String useremail = input_email.getText().toString().trim();
        String password = inputpassword.getText().toString();
        //validation user input
        if (useremail.isEmpty()||!Patterns.EMAIL_ADDRESS.matcher(useremail).matches()){
            input_email.setError("enter valid useremail");
        }
        else if (password.isEmpty()||password.length()<6){
            inputpassword.setError("enter proper password");
        }
        else{
            // setup the dialog box
            progressDialog.setMessage("please wait while login");
            progressDialog.setTitle("login");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            mauth.signInWithEmailAndPassword(useremail,password)
                    .addOnSuccessListener(authResult -> {
                        //check if the user is admin
                        checkifadmin(authResult.getUser().getUid());
                        progressDialog.dismiss();
                        Toast.makeText(LOGINActivity.this,"login successful", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        progressDialog.dismiss();
                        Toast.makeText(LOGINActivity.this,"failed to login", Toast.LENGTH_SHORT).show();
                    });
        }
    }

    private void checkifadmin(String uid) {
        DocumentReference df=fstore.collection("user").document(uid);
        df.get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.getString("isuser").equals("admin")){
                //user is admin
                redirect_user_to_next_activity();
            }
            if (documentSnapshot.getString("isuser").equals("user")){
                //user is normal user
                startActivity(new Intent(getApplicationContext(),SEEUPLOADEDANNOUNCEActivity.class));
                finish();
            }
        });
    }
    private void redirect_user_to_next_activity() {
        Intent intent=new Intent(this,  SEEUPLOADEDANNOUNCEActivity_for_admin.class);
        //IT IS LIKE FINISH NOT TO GOTO BACK
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

}
