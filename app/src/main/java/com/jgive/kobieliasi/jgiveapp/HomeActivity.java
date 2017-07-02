package com.jgive.kobieliasi.jgiveapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by Kobi Eliasi on 25/02/2017.
 * Home Activity
 */
public class HomeActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private final int GET_ORGANIZATIONS = 200;

    String user_email = "";
    private Handler handler;
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
                Intent intent1 = new Intent(HomeActivity.this, HomeActivity.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent1.putExtra("user_email", user_email);
                startActivity(intent1);
                return true;
            case R.id.action_search:
                /*
                Intent intent2 = new Intent(HomeActivity.this, SearchActivity.class);
                intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent2.putExtra("user_email", user_email);
                startActivity(intent2);
                */
                return true;
            case R.id.action_profile:
                Intent intent3 = new Intent(HomeActivity.this, ProfileActivity.class);
                intent3.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent3.putExtra("user_email", user_email);
                startActivity(intent3);
                return true;
            case R.id.action_tithe_calculator:
                Intent intent4 = new Intent(HomeActivity.this, TitheActivity.class);
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
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Get the connected user email
        user_email = getIntent().getStringExtra("user_email");

        // Set handler to communicate with the DataAccess
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                Log.d("HomeActivity", msg.toString());
                switch (msg.what) {
                    case GET_ORGANIZATIONS:
                        try {
                            ArrayList<String> organizations = new ArrayList<String>();
                            for (int i = 0; i < ((JSONArray)msg.obj).length(); i++) {
                                // Set default values
                                String id = "";
                                String project_name = "";
                                String org_name = "";
                                String details = "";
                                String picture = "";
                                String goal_current = "";
                                String goal_total = "";

                                // Get the data from the JSON
                                if (((JSONArray)msg.obj).getJSONObject(i).has("id")) {
                                    id = ((JSONArray)msg.obj).getJSONObject(i).get("id").toString();
                                }//end if
                                if (((JSONArray)msg.obj).getJSONObject(i).has("project_name")) {
                                    project_name = ((JSONArray)msg.obj).getJSONObject(i).get("project_name").toString();
                                }//end if
                                if (((JSONArray)msg.obj).getJSONObject(i).has("org_name")) {
                                    org_name = ((JSONArray)msg.obj).getJSONObject(i).get("org_name").toString();
                                }//end if
                                if (((JSONArray)msg.obj).getJSONObject(i).has("details")) {
                                    details = ((JSONArray)msg.obj).getJSONObject(i).get("details").toString();
                                }//end if
                                if (((JSONArray)msg.obj).getJSONObject(i).has("picture")) {
                                    picture = ((JSONArray)msg.obj).getJSONObject(i).get("picture").toString();
                                }//end if
                                if (((JSONArray)msg.obj).getJSONObject(i).has("goal_current")) {
                                    goal_current = ((JSONArray)msg.obj).getJSONObject(i).get("goal_current").toString();
                                }//end if
                                if (((JSONArray)msg.obj).getJSONObject(i).has("goal_total")) {
                                    goal_total = ((JSONArray)msg.obj).getJSONObject(i).get("goal_total").toString();
                                }//end if
                                organizations.add(picture + ";" + project_name + ";" + org_name + ";" + details + ";" + goal_current);
                            }//end for

                            // Set the list adapter
                             organizationsAdapter = new OrganizationsListViewAdapter(HomeActivity.this, R.layout.list_organizations, R.id.organizationsListView, organizations);
                            // Sets the data behind the list view
                            organizationsList.setAdapter(organizationsAdapter);
                            // Attach a listener to the list view
                            organizationsList.setOnItemClickListener(HomeActivity.this);
                        }//end try
                        catch (JSONException e) {
                            Log.d("HomeActivity", e.toString());
                        }//end catch
                }//end switch
            }
        };

        organizationsList = (ListView)findViewById(R.id.organizationsListView);
    }

    @Override
    protected void onStart(){
        super.onStart();
        createLists();
    }

    private void createLists(){
        // Ask to get the records from the server
        DataAccess dataAccess = new DataAccess(this , handler);
        dataAccess.getOrganizations();
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int onItemClick, long id){
        Intent intent = new Intent(HomeActivity.this, DonationActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("user_email", user_email);
        intent.putExtra("itemClickID", onItemClick);
        startActivity(intent);
    }
}
