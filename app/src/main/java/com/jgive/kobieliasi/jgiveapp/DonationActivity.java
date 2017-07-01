package com.jgive.kobieliasi.jgiveapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

public class DonationActivity extends AppCompatActivity {

    DBHandler dbHandler;
    private ImageView organizationImage;
    private TextView title;
    private TextView organization;
    private TextView details;
    private EditText amountEditText;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_home:
                startActivity(new Intent(DonationActivity.this, HomeActivity.class));
                return true;
            case R.id.action_search:
                //startActivity(new Intent(DonationActivity.this, SearchActivity.class));
                return true;
            case R.id.action_profile:
                startActivity(new Intent(DonationActivity.this, ProfileActivity.class));
                return true;
            case R.id.action_tithe_calculator:
                startActivity(new Intent(DonationActivity.this, TitheActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }//end switch
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                amountEditText = (EditText)findViewById(R.id.donationAmountEditView);
                String target = title.getText().toString();
                Double amount = Double.parseDouble(amountEditText.getText().toString());
                dbHandler.addDonation("User", target, amount, 2017, 6);
                Snackbar.make(view, R.string.donation_thanks, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                // define one second
                int second = 1000;
                // start a timer with logo display then goto Login activity
                Timer timer = new Timer();
                TimerTask loginTimerTask = new TimerTask() {
                    @Override
                    public void run() {
                        startActivity(new Intent(DonationActivity.this, HomeActivity.class));
                    }
                };
                timer.schedule(loginTimerTask, Snackbar.LENGTH_LONG+second);
            }
        });

        // Get the clicked organization ID
        Intent intent = getIntent();
        int id = intent.getIntExtra("itemClickID", -1);

        dbHandler = new DBHandler(this);
        JSONObject jsonObject = dbHandler.getOrganization(id);

        organizationImage = (ImageView)findViewById(R.id.organizationNetworkImageView);
        title = (TextView)findViewById(R.id.titleTextView);
        organization = (TextView)findViewById(R.id.organizationTextView);
        details = (TextView)findViewById(R.id.detailsTextView);
        try {
            String picture = jsonObject.get("picture").toString();
            new ImageDownloaderTask(organizationImage).execute(picture);
            title.setText(jsonObject.get("project_name").toString());
            organization.setText(jsonObject.get("org_name").toString());
            details.setText(jsonObject.get("details").toString());
        }//end try
        catch (JSONException e) {
            Log.d("DonationActivity", e.toString());
        }//end catch
    }

}
