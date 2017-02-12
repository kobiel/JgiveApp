package com.jgive.kobieliasi.jgiveapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Kobi Eliasi on 08/01/2017.
 * Tithe Update Activity
 */

public class TitheUpdateActivity extends AppCompatActivity {

    final static int INCOME = 0;
    final static int EXPENSE = 1;
    final static int DONATION = 2;

    int type = 0;
    TextView srcTrgTitleTextView;
    EditText srcTrgNameEditText;
    EditText amountEditText;
    DatePicker datePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tithe_update);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update();
            }
        });

        Spinner spinner = (Spinner) findViewById(R.id.inputRecordSpinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.types_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                type = position;
                switch (type) {
                    case INCOME:
                        srcTrgTitleTextView = (TextView)findViewById(R.id.srcTrgTitleTextView);
                        srcTrgNameEditText = (EditText)findViewById(R.id.srcTrgNameEditText);
                        srcTrgTitleTextView.setText(R.string.source);
                        srcTrgNameEditText.setHint(R.string.sourceName);
                        break;
                    case EXPENSE:
                    case DONATION:
                        srcTrgTitleTextView = (TextView)findViewById(R.id.srcTrgTitleTextView);
                        srcTrgNameEditText = (EditText)findViewById(R.id.srcTrgNameEditText);
                        srcTrgTitleTextView.setText(R.string.target);
                        srcTrgNameEditText.setHint(R.string.targetName);
                        break;
                }//end switch
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {}
        });

        // Set the date picker maximum date to today
        datePicker = (DatePicker)findViewById(R.id.datePicker);
        datePicker.setMaxDate(new Date().getTime());
    }

    private void update() {
        // Get the title
        srcTrgNameEditText = (EditText)findViewById(R.id.srcTrgNameEditText);
        String title = srcTrgNameEditText.getText().toString();

        // Get the amount
        amountEditText = (EditText)findViewById(R.id.amountEditText);
        String amount = amountEditText.getText().toString();

        if (title.isEmpty() || amount.isEmpty()) {
            System.out.print("Please enter all data");
            return;
        }//end if
        // Get the year and month
        int year = datePicker.getYear();
        int month = datePicker.getMonth();

        // Ask to insert the record into the DB
        DBHandler dbHandler = new DBHandler(this);
        boolean recordAdded = false;
        switch (type) {
            case INCOME:
                recordAdded = dbHandler.addIncome("User", title, Double.parseDouble(amount), year, month);
                break;
            case EXPENSE:
                recordAdded = dbHandler.addExpense("User", title, Double.parseDouble(amount), year, month);
                break;
            case DONATION:
                recordAdded = dbHandler.addDonation("User", title, Double.parseDouble(amount), year, month);
                break;
        }//end switch

        // Clearing the fields
        srcTrgNameEditText.setText("");
        amountEditText.setText("");

        // Alerting the user of success or fail
        if (recordAdded) {
            recordAdded = updateProvisions(type, year, month, Double.parseDouble(amount));
            if (recordAdded) {
                Toast.makeText(this, R.string.success, Toast.LENGTH_SHORT).show();
            }//end if
            else {
                Toast.makeText(this, R.string.partial_success, Toast.LENGTH_SHORT).show();
            }//end else
        }//end if
        else {
            Toast.makeText(this, R.string.error_message, Toast.LENGTH_SHORT).show();
        }//end else
    }

    private boolean updateProvisions(int type, int year, int month, double amount) {
        double newAmount = 0;
        switch (type) {
            case INCOME:
                newAmount = 0.1*amount;
                break;
            case EXPENSE:
                newAmount = -0.1*amount;
                break;
        }//end switch
        DBHandler dbHandler = new DBHandler(this);
        ArrayList<String> provision = dbHandler.getProvisionForMonth("User", year, month+1);
        // If no provision found in DB only add current
        if (provision.size() == 0) {
            return dbHandler.addProvisions("User", year, month, newAmount);
        }//end if
        else {
            String provisionID = provision.get(0);
            String provisionAmount = provision.get(1);
            double newProvisionAmount = Double.valueOf(provisionAmount) + newAmount;
            return dbHandler.updateProvisions(Integer.valueOf(provisionID), newProvisionAmount);
        }//end else
    }
}
