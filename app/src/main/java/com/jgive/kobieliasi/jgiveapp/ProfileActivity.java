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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;


public class ProfileActivity extends AppCompatActivity {

    private final int FACEBOOK_LOGIN = 100;
    private final int JGIVE_LOGIN = 101;

    private String user_email = "";
    private ImageView profileImage;
    private TextView welcomeTitle;
    private TextView firstName;
    private TextView lastName;
    private TextView title;
    private TextView country;
    private TextView biography;
    private TextView website;
    private TextView monthlyUpdate;
    private LoginButton facebookLogoutButton;
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
                Intent intent1 = new Intent(ProfileActivity.this, HomeActivity.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent1.putExtra("user_email", user_email);
                startActivity(intent1);
                return true;
            case R.id.action_search:
                /*
                Intent intent2 = new Intent(ProfileActivity.this, SearchActivity.class);
                intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent2.putExtra("user_email", user_email);
                startActivity(intent2);
                */
                return true;
            case R.id.action_profile:
                Intent intent3 = new Intent(ProfileActivity.this, ProfileActivity.class);
                intent3.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent3.putExtra("user_email", user_email);
                startActivity(intent3);
                return true;
            case R.id.action_tithe_calculator:
                Intent intent4 = new Intent(ProfileActivity.this, TitheActivity.class);
                intent4.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent4.putExtra("user_email", user_email);
                startActivity(intent4);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }//end switch
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Add Facebook login and logging
        FacebookSdk.sdkInitialize(getApplicationContext());

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
                }//end if
                else {
                    // Create new intent
                    Intent intent = new Intent(ProfileActivity.this, UpdateProfileActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("user_email", user_email);
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

        // Get the connected user email
        user_email = getIntent().getStringExtra("user_email");

        profileImage = (ImageView) findViewById(R.id.profileImageView);
        welcomeTitle = (TextView)findViewById(R.id.welcomeTitleTextView);
        firstName = (TextView)findViewById(R.id.firstNameTextView);
        lastName = (TextView)findViewById(R.id.lastNameTextView);
        title = (TextView)findViewById(R.id.titleTextView);
        country = (TextView)findViewById(R.id.countryTextView);
        biography = (TextView)findViewById(R.id.biographyTextView);
        website = (TextView)findViewById(R.id.websiteTextView);
        monthlyUpdate = (TextView)findViewById(R.id.monthlyUpdateTextView);

        // Set the facebook logout button
        facebookLogoutButton = (LoginButton) findViewById(R.id.facebook_login_button);
        facebookLogoutButton.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v) {
                LoginManager.getInstance().logOut();
                startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
            }
        });

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
                            facebookLogoutButton.setVisibility(View.VISIBLE);
                        }// end try
                        catch (JSONException e) {
                            Log.d("ProfileActivity", e.toString());
                        }//end catch
                        break;
                    case JGIVE_LOGIN:
                        try {
                            String user_picture = "https://d1qvck26m1aukd.cloudfront.net/defaults/users/avatars/missing.jpg";
                            String user_firstName = "";
                            String user_lastName = "";
                            String user_title = "";
                            String user_country = "";
                            String user_biography = "";
                            String user_website = "";
                            String user_monthlyUpdate = "";
                            if (((JSONObject) msg.obj).has("picture")) {
                                user_picture = ((JSONObject)msg.obj).get("picture").toString();
                            }//end if
                            if (((JSONObject) msg.obj).has("first_name")) {
                                user_firstName = ((JSONObject) msg.obj).get("first_name").toString();
                            }//end if
                            if (((JSONObject) msg.obj).has("last_name")) {
                                user_lastName = ((JSONObject) msg.obj).get("last_name").toString();
                            }//end if
                            if (((JSONObject) msg.obj).has("title")) {
                                user_title = ((JSONObject) msg.obj).get("title").toString();
                            }//end if
                            if (((JSONObject) msg.obj).has("country")) {
                                user_country = ((JSONObject) msg.obj).get("country").toString();
                            }//end if
                            if (((JSONObject) msg.obj).has("biography")) {
                                user_biography = ((JSONObject) msg.obj).get("biography").toString();
                            }//end if
                            if (((JSONObject) msg.obj).has("website")) {
                                user_website = ((JSONObject) msg.obj).get("website").toString();
                            }//end if
                            if (((JSONObject) msg.obj).has("monthlyUpdate")) {
                                user_monthlyUpdate = ((JSONObject) msg.obj).get("monthlyUpdate").toString();
                            }//end if
                            // Set the view components
                            new ImageDownloaderTask(profileImage).execute(user_picture);
                            welcomeTitle.setText(welcomeTitle.getText().toString() + " " + user_firstName);
                            firstName.setText(user_firstName);
                            lastName.setText(user_lastName);
                            title.setText(user_title);
                            country.setText(user_country);
                            biography.setText(user_biography);
                            website.setText(user_website);
                            monthlyUpdate.setText(user_monthlyUpdate);
                            facebookLogoutButton.setVisibility(View.INVISIBLE);
                            break;
                        }//end try
                        catch (JSONException e) {
                            Log.d("ProfileActivity", e.toString());
                        }//end catch
                        break;
                }//end switch
            }
        };

        // Ask to get the records from the server
        DataAccess dataAccess = new DataAccess(this, handler);
        if (login_via.equals("jgive")) {
            // TODO: get user ID to pull the profile from the server
            long id = 0;
            dataAccess.getProfile(id);
        }//end if
        else if (login_via.equals("facebook")) {
            dataAccess.getFacebookProfile();
        }//end else if
    }

}
