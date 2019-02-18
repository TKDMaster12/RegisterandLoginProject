package com.example.registerandloginproject;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                // This method will be executed once the timer is over
                //Intent is used to switch from one activity to another.
                Intent i = new Intent(getApplicationContext(), Home.class);
                //invoke the Next Activity.
                startActivity(i);
                //the current activity will get finished.
                finish();
            }
        }, 3000);
    }
}
