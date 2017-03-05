package com.jgive.kobieliasi.jgiveapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

/**
 * Created by Kobi Eliasi on 25/02/2017.
 * Access to the data on jgive's server
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


}
