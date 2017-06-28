package com.jgive.kobieliasi.jgiveapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class ProfileActivity extends AppCompatActivity {

    private final int FACEBOOK_LOGIN = 100;
    private ImageView profileImage;
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
                    profileImage.buildDrawingCache();
                    Bitmap bitmap = profileImage.getDrawingCache();
                    intent.putExtra("profileImage", bitmap);
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

        profileImage = (ImageView) findViewById(R.id.profileImageView);
        welcomeTitle = (TextView)findViewById(R.id.welcomeTitleTextView);
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

        Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case FACEBOOK_LOGIN:
                        try {
                            String user_picture = "https://d1qvck26m1aukd.cloudfront.net/defaults/users/avatars/missing.jpg";
                            String user_firstName = "";
                            String user_lastName = "";
                            String user_biography = "";
                            String user_website = "";
                            if (((JSONObject)msg.obj).has("picture")) {
                                if (((JSONObject)msg.obj).getJSONObject("picture").has("data")) {
                                    if (((JSONObject)msg.obj).getJSONObject("picture").getJSONObject("data").has("url")) {
                                        user_picture = ((JSONObject)msg.obj).getJSONObject("picture").getJSONObject("data").get("url").toString();
                                    }//end if
                                }//end if
                            }//end if
                            if (((JSONObject)msg.obj).has("first_name")) {
                                user_firstName = ((JSONObject)msg.obj).get("first_name").toString();
                            }//end if
                            if (((JSONObject)msg.obj).has("last_name")) {
                                user_lastName = ((JSONObject)msg.obj).get("last_name").toString();
                            }//end if
                            if (((JSONObject)msg.obj).has("about")) {
                                user_biography = ((JSONObject)msg.obj).get("about").toString();
                            }//end if
                            if (((JSONObject)msg.obj).has("website")) {
                                user_website = ((JSONObject)msg.obj).get("website").toString();
                            }//end if
                            // Set the view components
                            new ImageDownloaderTask(profileImage).execute(user_picture);
                            welcomeTitle.setText(welcomeTitle.getText().toString() + " " + user_firstName);
                            firstName.setText(user_firstName);
                            lastName.setText(user_lastName);
                            biography.setText(user_biography);
                            website.setText(user_website);
                        }// end try
                        catch (JSONException e) {
                            Log.d("ProfileActivity", e.toString());
                        }//end catch
                }//end switch
            }
        };

        // Ask to get the records from the server
        DataAccess dataAccess = new DataAccess(this, handler);
        if (login_via.equals("jgive")) {
            if (dataAccess.getProfile(0)) {
                // TODO: get the profile from the server
            }//end if
            else {

            }//end else
        }//end if
        else if (login_via.equals("facebook")) {
            dataAccess.getFacebookProfile();
        }//end else if
    }

}
