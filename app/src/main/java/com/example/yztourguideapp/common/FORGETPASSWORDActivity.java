package com.example.yztourguideapp.common;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.yztourguideapp.R;
import com.google.firebase.auth.FirebaseAuth;

public class FORGETPASSWORDActivity extends AppCompatActivity {
    private EditText emailedittext;
    private Button button;
    private ProgressBar progressBar;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_forgetpassword);
        emailedittext=findViewById(R.id.email);
        button=findViewById(R.id.resetbtn);
        progressBar=findViewById(R.id.progressBar);
        auth=FirebaseAuth.getInstance();
        //if you press reset password button
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetpassword();
            }
        });
    }
    private void resetpassword(){
        String email = emailedittext.getText().toString().trim();
        //validation of email
        if (Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            progressBar.setVisibility(View.VISIBLE);
            auth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {
                if (task.isSuccessful()){
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(FORGETPASSWORDActivity.this, "check your email to reset the password", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), LOGINActivity.class));
                }
                else {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(FORGETPASSWORDActivity.this, "try again! something wrong happend", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), LOGINActivity.class));
                }
            });
        }
        else{
            emailedittext.setError("enter valid email");
        }
    }

}