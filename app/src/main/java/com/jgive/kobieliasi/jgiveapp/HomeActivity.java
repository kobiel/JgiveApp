package com.jgive.kobieliasi.jgiveapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Kobi Eliasi on 25/02/2017.
 * Home Activity
 */
public class HomeActivity extends AppCompatActivity {

    private ListView organizationsList;
    private ArrayAdapter<String> organizationsAdapter;

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
                startActivity(new Intent(HomeActivity.this, HomeActivity.class));
                return true;
            case R.id.action_search:
                //startActivity(new Intent(HomeActivity.this, SearchActivity.class));
                return true;
            case R.id.action_profile:
                startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
                return true;
            case R.id.action_tithe_calculator:
                startActivity(new Intent(HomeActivity.this, TitheActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }//end switch
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        organizationsList = (ListView)findViewById(R.id.organizationsListView);
    }

    @Override
    protected void onStart(){
        super.onStart();
        createLists();
    }

    private void createLists(){
        // Ask to get the records from the server
        DataAccess dataAccess = new DataAccess(this);
        ArrayList<String> organizations = new ArrayList<String>();
        if (dataAccess.getOrganizations()) {
            //TODO: get the data from the real server
            organizations.add("https://d1qvck26m1aukd.cloudfront.net/app/public/media_objects/image_4x3_hes/000/001/305/thumbnail/a9b6db9859297fa5ade4dbfc79190565ce742c9e.jpg?1488280473;Head;Org;BlaBla;THEBAR");
            organizations.add("https://d1qvck26m1aukd.cloudfront.net/app/public/media_objects/image_4x3_hes/000/002/394/thumbnail/05bb193ca94631aeb776c55ad7892eb946bae9e2.jpg?1496134678;Head2;Org2;BlaBla2;THEBAR2");
        }//end if
        else {
            organizations.add("https://s3-us-west-2.amazonaws.com/jgive-production/app/public/ckeditor_assets/pictures/3/content_about_3-a88af3b531acda93051d3a5585a15daa.png;Something went wrong;Please contact the support;empty;empty");
        }//end else
        // Set the list adapter
        organizationsAdapter = new OrganizationsListViewAdapter(this, R.layout.list_organizations, R.id.organizationsListView, organizations);
        // Sets the data behind the list view
        organizationsList.setAdapter(organizationsAdapter);
    }
}
