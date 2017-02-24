package com.jgive.kobieliasi.jgiveapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Kobi Eliasi on 24/12/2016.
 * Main Activity
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // define one second
        int second = 1000;
        // start a timer with logo display then goto Tithe activity
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        }, 5*second);
    }
}
