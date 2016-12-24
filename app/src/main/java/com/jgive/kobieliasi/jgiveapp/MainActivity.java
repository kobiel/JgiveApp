package com.jgive.kobieliasi.jgiveapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int seconds = 1000;
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                startActivity(new Intent(MainActivity.this, TitheActivity.class));
            }
        }, 5*seconds);
    }
}
