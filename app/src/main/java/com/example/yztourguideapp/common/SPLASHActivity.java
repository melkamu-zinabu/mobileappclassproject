package com.example.yztourguideapp.common;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.example.yztourguideapp.ADMIN.ADDADMIN;
import com.example.yztourguideapp.ADMIN.MANAGEHELP;
import com.example.yztourguideapp.ADMIN.SEEBOOK;
import com.example.yztourguideapp.ADMIN.SEEHELPActivity_for_admin;
import com.example.yztourguideapp.ADMIN.SEEUPLOADEDANNOUNCEActivity_for_admin;
import com.example.yztourguideapp.R;
import com.example.yztourguideapp.USER.SEEHELPActivity;
import com.example.yztourguideapp.USER.UPLOADBOOKActivity;

//@SuppressLint("CustomSplashScreen")
public class SPLASHActivity extends AppCompatActivity {
     SharedPreferences sharedPreferences;
     @Override
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
        //show the activity in full screen OR HIDE status bar
         this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
         setContentView(R.layout.activity_splashactivity);
         Handler handler= new Handler();
         handler.postDelayed(() -> {
           //this will execute after 2000 sec
            sharedPreferences =getSharedPreferences("on_boarding_screen",MODE_PRIVATE) ;
            Boolean is_first_time=sharedPreferences .getBoolean("first_time",true);
            //differentiate b/n onboarding and login screen
             //if you install for the first time it will execute the if clause
            if(is_first_time){
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putBoolean("first_time",false);
                editor.commit();
                startActivity(new Intent(getApplicationContext(), onboardingpage.class));
                finish();
            }
             else{
            startActivity(new Intent(getApplicationContext(), LOGINActivity.class));
            finish();
            }

        },2000);
    }
}

