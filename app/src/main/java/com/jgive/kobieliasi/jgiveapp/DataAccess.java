package com.jgive.kobieliasi.jgiveapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
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

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Kobi Eliasi on 25/02/2017.
 * Access to the data on jgive's server
 * Updated by Kobi Eliasi on 13/06/2017.
 * Add getOrganizations functions
 */

public class DataAccess {

    private final String JGIVE_URL = "http://api.jgive.com";
    private Context context;
    private RequestQueue mRequestQueue;
    private ProgressDialog progressDialog;

    public DataAccess(Context context) {
        this.context = context;
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
                        if (response.isEmpty()) {

                        }//end if
                        else {

                        }//end else
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
                        if (response.isNull("Error")) {

                        }//end if
                        else {

                        }//end else
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

    public boolean getOrganizations() {
        progressDialog = ProgressDialog.show(context, "Updating", "Please wait", true);
        String URL = JGIVE_URL + "/organizations/";
        // Request a Json response from the provided URL.
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Check if the request failed
                        if (response.isNull("Error")) {
                            //TODO: case of no data
                        }//end if
                        else {
                            //TODO: case of data
                        }//end else
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

    public boolean getProfile(int id) {
        progressDialog = ProgressDialog.show(context, "Getting profile", "Please wait", true);
        String URL = JGIVE_URL + "/profile/" + id;
        // Request a Json response from the provided URL.
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Check if the request failed
                        if (response.isNull("Error")) {
                            //TODO: case of no data
                        }//end if
                        else {
                            //TODO: case of data
                        }//end else
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

    public void getFacebookProfile(){
        progressDialog = ProgressDialog.show(context, "Getting profile", "Please wait", true);
        GraphRequest request = GraphRequest.newMeRequest(
                AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.d("DataAccess", response.toString());
                        try {
                            String id = object.getString("id");
                            String email = object.getString("email");
                            String firstName = object.getString("first_name");
                            String lastName = object.getString("last_name");
                            String picture = object.getString("picture");
                            //String user_website = object.getString("user_website"); // TODO: Enable this
                        }// end try
                        catch (JSONException e) {
                            Log.d("DataAccess", e.toString());
                        }//end catch
                        progressDialog.dismiss();
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,email,first_name,last_name,picture"); // TODO: Add user_website
        request.setParameters(parameters);
        request.executeAsync();
    }
}
