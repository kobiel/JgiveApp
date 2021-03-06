package com.jgive.kobieliasi.jgiveapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Kobi Eliasi on 25/02/2017.
 * Access to the data on jgive's server
 * Updated by Kobi Eliasi on 13/06/2017.
 * Add getOrganizations functions
 */

public class DataAccess {

    private final String JGIVE_URL = "http://api.jgive.com";
    private final int FACEBOOK_LOGIN = 100;
    private final int JGIVE_LOGIN = 101;
    private final int GET_ORGANIZATIONS = 200;

    private Context context;
    private Handler handler;
    private DBHandler dbHandler;
    private RequestQueue mRequestQueue;
    private ProgressDialog progressDialog;

    public DataAccess(Context context, Handler handler) {
        this.context = context;
        this.handler = handler;
        dbHandler = new DBHandler(context);
        mRequestQueue = Volley.newRequestQueue(context);
    }

    public boolean register(String email, String password) {
        progressDialog = ProgressDialog.show(context, "Connecting", "Please wait", true);
        String URL = JGIVE_URL + "/register/" + email + "/" + password;
        // Send a Json request to the provided URL.
        StringRequest request = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Check if the registration succeeded
                        if (!response.isEmpty()) {
                            // TODO: case of data
                        }//end if
                        progressDialog.dismiss();
                    }//end onResponse
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(context, context.getString(R.string.error_message), Toast.LENGTH_LONG).show();
            }//end onErrorResponse
        });
        // Add the request to the RequestQueue.
        mRequestQueue.add(request);
        return false;
    }

    public boolean login(String email, String password) {
        progressDialog = ProgressDialog.show(context, "Connecting", "Please wait", true);
        String URL = JGIVE_URL + "/login/" + email + "/" + password;
        // Request a Json response from the provided URL.
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Check if the login failed
                        if (!response.isNull("Error")) {
                            // TODO: case of data
                        }//end if
                        progressDialog.dismiss();
                    }//end onResponse
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(context, context.getString(R.string.error_message), Toast.LENGTH_LONG).show();
            }//end onErrorResponse
        });
        // Add the request to the RequestQueue.
        mRequestQueue.add(request);
        return false;
    }

    public void getOrganizations() {
        progressDialog = ProgressDialog.show(context, "Updating", "Please wait", true);
        String URL = JGIVE_URL + "/organizations/";
        // Request a Json response from the provided URL.
        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Check if the request failed
                        if (!response.isNull("Error")) {
                            Log.d("DataAccess", response.toString());
                            // Send the data back to the activity
                            Message msg = handler.obtainMessage(GET_ORGANIZATIONS, dbHandler.getOrganizations());
                            // TODO: change the above line with the following when go live with server
                            //Message msg = handler.obtainMessage(GET_ORGANIZATIONS, response);
                            handler.sendMessage(msg);
                        }//end if
                        progressDialog.dismiss();
                    }//end onResponse
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO: delete following 2 lines when go live with server
                Message msg = handler.obtainMessage(GET_ORGANIZATIONS, dbHandler.getOrganizations());
                handler.sendMessage(msg);
                progressDialog.dismiss();
                // TODO: uncomment this when go live with server
                //Toast.makeText(context, context.getString(R.string.error_message), Toast.LENGTH_LONG).show();
            }//end onErrorResponse
        });
        // Add the request to the RequestQueue.
        mRequestQueue.add(request);
    }

    public void updateProfile(ArrayList<String> newProfile) {
        String picture = newProfile.get(0);
        String firstName = newProfile.get(1);
        String lastName = newProfile.get(2);
        String title = newProfile.get(3);
        String country = newProfile.get(4);
        String biography = newProfile.get(5);
        String website = newProfile.get(6);
        String monthlyUpdate = newProfile.get(7);
        // TODO: update the server
    }

    public void getProfile(long id) {
        progressDialog = ProgressDialog.show(context, "Getting profile", "Please wait", true);
        String URL = JGIVE_URL + "/profile/" + id;
        // Request a Json response from the provided URL.
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Check if the request failed
                        if (!response.isNull("Error")) {
                            Log.d("DataAccess", response.toString());
                            // Send the data back to the activity
                            Message msg = handler.obtainMessage(JGIVE_LOGIN, response);
                            handler.sendMessage(msg);
                        }//end if
                        progressDialog.dismiss();
                    }//end onResponse
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(context, context.getString(R.string.error_message), Toast.LENGTH_LONG).show();
            }//end onErrorResponse
        });
        // Add the request to the RequestQueue.
        mRequestQueue.add(request);
    }

    public void getFacebookProfile(){
        progressDialog = ProgressDialog.show(context, "Getting profile", "Please wait", true);
        GraphRequest request = GraphRequest.newMeRequest(
                AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.d("DataAccess", response.toString());
                        // Send the data back to the activity
                        Message msg = handler.obtainMessage(FACEBOOK_LOGIN, object);
                        handler.sendMessage(msg);
                        // Dismiss the progress dialog
                        progressDialog.dismiss();
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,email,first_name,last_name,picture,about,website");
        request.setParameters(parameters);
        request.executeAsync();
    }
}
