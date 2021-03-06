package com.jgive.kobieliasi.jgiveapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

/**
 * Created by Kobi Eliasi on 31/12/2016.
 * Login Activity
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    Handler handler;
    private LoginButton facebookLoginButton;
    private CallbackManager callbackManager;
    private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private Button registerButton;
    private DataAccess dataAccess;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Add Facebook login and logging
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        callbackManager = CallbackManager.Factory.create();

        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set handler to communicate with the DataAccess
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                Log.d("LoginActivity", msg.toString());
            }
        };

        // Set the facebook login button
        facebookLoginButton = (LoginButton) findViewById(R.id.facebook_login_button);
        // List of facebook permissions needed by the App
        facebookLoginButton.setReadPermissions(Arrays.asList("public_profile", "email", "user_about_me", "user_website"));
        // Register the callback for the facebook login
        facebookLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("LoginActivity", "facebook:onSuccess:" + loginResult);
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                Log.d("LoginActivity", response.toString());
                                try {
                                    String email = object.getString("email");
                                    sharedPreferences.edit().putString("login_email", email).apply();
                                    sharedPreferences.edit().putString("login_via", "facebook").apply();
                                    // Open the home screen
                                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    intent.putExtra("user_email", email);
                                    startActivity(intent);
                                }// end try
                                catch (JSONException e) {
                                    Log.d("LoginActivity", e.toString());
                                }//end catch
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,email");
                request.setParameters(parameters);
                request.executeAsync();
            }
            @Override
            public void onCancel() {
                Log.d("LoginActivity", "facebook:onCancel");
            }
            @Override
            public void onError(FacebookException exception) {
                Log.d("LoginActivity", "facebook:onError", exception);
            }
        });

        // Set the view components
        emailEditText = (EditText)findViewById(R.id.emailEditText);
        passwordEditText = (EditText)findViewById(R.id.passwordEditText);
        loginButton = (Button)findViewById(R.id.loginButton);
        registerButton = (Button)findViewById(R.id.registerButton);
        loginButton.setOnClickListener(this);
        registerButton.setOnClickListener(this);

        // To communicate with the server
        dataAccess = new DataAccess(this, handler);

        // Shared Preferences file to save the login data
        sharedPreferences = getSharedPreferences("jgiveDataFile", MODE_PRIVATE);
    }

    //  Getting the login result from facebook
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View v) {
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        switch (v.getId()){
            case R.id.loginButton:
                if (validateInput(email, password)) {
                    if (dataAccess.login(email, password)) {
                        sharedPreferences.edit().putString("login_email", email).apply();
                        sharedPreferences.edit().putString("login_password", password).apply();
                        sharedPreferences.edit().putString("login_via", "jgive").apply();
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        intent.putExtra("user_email", email);
                        startActivity(intent);
                    }//end if
                }//end if
                break;
            case R.id.registerButton:
                if (validateInput(email, password)) {
                    if (dataAccess.register(email, password)) {
                        sharedPreferences.edit().putString("login_email", email).apply();
                        sharedPreferences.edit().putString("login_password", password).apply();
                        sharedPreferences.edit().putString("login_via", "jgive").apply();
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        intent.putExtra("user_email", email);
                        startActivity(intent);
                    }//end if
                }//end if
                break;
        }//end switch
    }

    /**
     * Check the entered data at email and password fields
     * @return true if the user entered valid data
     */
    private boolean validateInput(String email, String password){
        // Get red color
        int redColor = ResourcesCompat.getColor(getResources(), R.color.red, getTheme());

        // Check email field
        if (email.equals("") || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.setText("");
            emailEditText.setHintTextColor(redColor);
        }//end if

        // Check password field
        if (password.equals("")) {
            passwordEditText.setText("");
            passwordEditText.setHintTextColor(redColor);
        }//end if
        else if (android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return true;
        }//end else if

        // Validation failed
        return false;
    }
}
