package com.jgive.kobieliasi.jgiveapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.facebook.AccessToken;
import com.facebook.Profile;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Kobi Eliasi on 24/12/2016.
 * Main Activity
 */
public class MainActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // define one second
        int second = 1000;
        // start a timer with logo display then goto Login activity
        Timer timer = new Timer();
        TimerTask loginTimerTask = new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        };
        timer.schedule(loginTimerTask, 5*second);

        sharedPreferences = getSharedPreferences("jgiveDataFile", MODE_PRIVATE);
        String email = sharedPreferences.getString("login_email", "");
        String password = sharedPreferences.getString("login_password", "");

        if (Profile.getCurrentProfile() != null && AccessToken.getCurrentAccessToken() != null) {
            // Logged via facebook
            loginTimerTask.cancel();
            timer.schedule(new TimerTask() {
                public void run() {
                    startActivity(new Intent(MainActivity.this, HomeActivity.class));
                }
            }, 4*second);
        }//end if
        else if (!email.equals("") && !password.equals("")) {
            loginTimerTask.cancel();
            // TODO: try to connect to the server and confirm the credentials
            timer.schedule(new TimerTask() {
                public void run() {
                    startActivity(new Intent(MainActivity.this, HomeActivity.class));
                }
            }, 4*second);
        }//end if
    }
}
