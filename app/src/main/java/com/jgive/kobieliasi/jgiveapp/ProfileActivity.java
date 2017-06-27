package com.jgive.kobieliasi.jgiveapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

public class ProfileActivity extends AppCompatActivity {

    private NetworkImageView profileImage;
    private TextView welcomeTitle;
    private TextView firstName;
    private TextView lastName;
    private TextView title;
    private TextView country;
    private TextView biography;
    private TextView website;
    private TextView monthlyUpdate;
    private SharedPreferences sharedPreferences;

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
                startActivity(new Intent(ProfileActivity.this, HomeActivity.class));
                return true;
            case R.id.action_search:
                //startActivity(new Intent(ProfileActivity.this, SearchActivity.class));
                return true;
            case R.id.action_profile:
                startActivity(new Intent(ProfileActivity.this, ProfileActivity.class));;
                return true;
            case R.id.action_tithe_calculator:
                startActivity(new Intent(ProfileActivity.this, TitheActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }//end switch
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the login method from the data file
                String login_via = sharedPreferences.getString("login_via", "jgive");
                if (login_via.equals("facebook")) {
                    Snackbar.make(view, "Please edit your profile at Facebook", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    return;
                }//end if
                else {
                    // Create new intent
                    Intent intent = new Intent(ProfileActivity.this, UpdateProfileActivity.class);
                    // Pass all profile data to update profile activity
                    String temp = (String) profileImage.getTag();
                    intent.putExtra("profileImage", (String) profileImage.getTag());
                    intent.putExtra("firstName", firstName.getText().toString());
                    intent.putExtra("lastName", lastName.getText().toString());
                    intent.putExtra("title", title.getText().toString());
                    intent.putExtra("country", country.getText().toString());
                    intent.putExtra("biography", biography.getText().toString());
                    intent.putExtra("website", website.getText().toString());
                    intent.putExtra("monthlyUpdate", monthlyUpdate.getText().toString());
                    // Call update profile activity
                    startActivity(intent);
                }//else
            }
        });


        profileImage = (NetworkImageView)findViewById(R.id.profileNetworkImageView);
        welcomeTitle = (TextView)findViewById(R.id.welcomeTitle);
        firstName = (TextView)findViewById(R.id.firstNameTextView);
        lastName = (TextView)findViewById(R.id.lastNameTextView);
        title = (TextView)findViewById(R.id.titleTextView);
        country = (TextView)findViewById(R.id.countryTextView);
        biography = (TextView)findViewById(R.id.biographyTextView);
        website = (TextView)findViewById(R.id.websiteTextView);
        monthlyUpdate = (TextView)findViewById(R.id.monthlyUpdateTextView);

        // Shared Preferences file to get and save the app data
        sharedPreferences = getSharedPreferences("jgiveDataFile", MODE_PRIVATE);
        // Get the login method from the data file
        String login_via = sharedPreferences.getString("login_via", "jgive");

        // Ask to get the records from the server
        DataAccess dataAccess = new DataAccess(this);
        if (login_via.equals("jgive")) {
            if (dataAccess.getProfile(1)) {

            }//end if
            else {

            }//end else
        }//end if
        else if (login_via.equals("facebook")) {
            dataAccess.getFacebookProfile();
        }//end else if
    }

}
