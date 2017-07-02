package com.jgive.kobieliasi.jgiveapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Arrays;

public class UpdateProfileActivity extends AppCompatActivity {

    private String user_email = "";
    private Handler handler;
    private ImageView profileImage;
    private EditText firstName;
    private EditText lastName;
    private EditText title;
    private EditText country;
    private EditText biography;
    private EditText website;
    private EditText monthlyUpdate;

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
                Intent intent1 = new Intent(UpdateProfileActivity.this, HomeActivity.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent1.putExtra("user_email", user_email);
                startActivity(intent1);
                return true;
            case R.id.action_search:
                /*
                Intent intent2 = new Intent(UpdateProfileActivity.this, SearchActivity.class);
                intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent2.putExtra("user_email", user_email);
                startActivity(intent2);
                */
                return true;
            case R.id.action_profile:
                Intent intent3 = new Intent(UpdateProfileActivity.this, ProfileActivity.class);
                intent3.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent3.putExtra("user_email", user_email);
                startActivity(intent3);
                return true;
            case R.id.action_tithe_calculator:
                Intent intent4 = new Intent(UpdateProfileActivity.this, TitheActivity.class);
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
        setContentView(R.layout.activity_update_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user_picture = "";// TODO: get the new picture to update
                String user_firstName = firstName.getText().toString();
                String user_lastName = lastName.getText().toString();
                String user_title = title.getText().toString();
                String user_country = country.getText().toString();
                String user_biography = biography.getText().toString();
                String user_website = website.getText().toString();
                String user_monthlyUpdate = monthlyUpdate.getText().toString();
                ArrayList<String> newProfile = new ArrayList<>(Arrays.asList(user_picture, user_firstName, user_lastName, user_title,
                        user_country, user_biography, user_website, user_monthlyUpdate));

                // Ask to get the records from the server
                DataAccess dataAccess = new DataAccess(UpdateProfileActivity.this, handler);
                dataAccess.updateProfile(newProfile);
                // Go back to the profile
                Intent intent = new Intent(UpdateProfileActivity.this, ProfileActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("user_email", user_email);
                startActivity(intent);
            }
        });

        // Get the connected user email
        user_email = getIntent().getStringExtra("user_email");

        // Set handler to communicate with the DataAccess
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                Log.d("UpdateProfileActivity", msg.toString());
            }
        };

        // Set the view components
        profileImage = (ImageView) findViewById(R.id.profileImageView);
        firstName = (EditText) findViewById(R.id.firstNameEditText);
        lastName = (EditText)findViewById(R.id.lastNameEditText);
        title = (EditText)findViewById(R.id.titleEditText);
        country = (EditText)findViewById(R.id.countryEditText);
        biography = (EditText)findViewById(R.id.biographyEditText);
        website = (EditText)findViewById(R.id.websiteEditText);
        monthlyUpdate = (EditText)findViewById(R.id.monthlyUpdateEditText);

        // Read the data from the profile activity
        Bitmap bitmap = getIntent().getParcelableExtra("profileImage");
        profileImage.setImageBitmap(bitmap);
        firstName.setText(getIntent().getStringExtra("firstName"));
        lastName.setText(getIntent().getStringExtra("lastName"));
        title.setText(getIntent().getStringExtra("title"));
        country.setText(getIntent().getStringExtra("country"));
        biography.setText(getIntent().getStringExtra("biography"));
        website.setText(getIntent().getStringExtra("website"));
        monthlyUpdate.setText(getIntent().getStringExtra("monthlyUpdate"));
    }

}
