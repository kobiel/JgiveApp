package com.jgive.kobieliasi.jgiveapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Kobi Eliasi on 31/12/2016.
 * Login Activity
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private Button registerButton;
    private DataAccess dataAccess;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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

        emailEditText = (EditText)findViewById(R.id.emailEditText);
        passwordEditText = (EditText)findViewById(R.id.passwordEditText);
        loginButton = (Button)findViewById(R.id.loginButton);
        registerButton = (Button)findViewById(R.id.registerButton);
        loginButton.setOnClickListener(this);
        registerButton.setOnClickListener(this);

        dataAccess = new DataAccess(this);

        sharedPreferences = getSharedPreferences("jgiveDataFile", MODE_PRIVATE);
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
                    }//end if
                }//end if
                break;
            case R.id.registerButton:
                if (validateInput(email, password)) {
                    if (dataAccess.register(email, password)) {
                        sharedPreferences.edit().putString("login_email", email).apply();
                        sharedPreferences.edit().putString("login_password", password).apply();
                    }//end if
                }//end if
                break;
            case R.id.facebookLogo:
                // TODO: connect using facebook
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
