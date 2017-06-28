package com.jgive.kobieliasi.jgiveapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class UpdateProfileActivity extends AppCompatActivity {

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
                startActivity(new Intent(UpdateProfileActivity.this, HomeActivity.class));
                return true;
            case R.id.action_search:
                //startActivity(new Intent(UpdateProfileActivity.this, SearchActivity.class));
                return true;
            case R.id.action_profile:
                startActivity(new Intent(UpdateProfileActivity.this, ProfileActivity.class));;
                return true;
            case R.id.action_tithe_calculator:
                startActivity(new Intent(UpdateProfileActivity.this, TitheActivity.class));
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
                // TODO: update the data on DB and Server
                startActivity(new Intent(UpdateProfileActivity.this, ProfileActivity.class));
            }
        });

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
