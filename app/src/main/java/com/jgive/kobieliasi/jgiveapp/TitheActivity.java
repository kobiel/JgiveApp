package com.jgive.kobieliasi.jgiveapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Kobi Eliasi on 24/12/2016.
 * Tithe Activity
 */
public class TitheActivity extends AppCompatActivity {

    String yearPosition = "";
    int monthPosition = 0;

    ListView incomesList;
    ListView expensesList;
    ListView donationsList;
    ArrayAdapter<String> incomesAdapter;
    ArrayAdapter<String> expensesAdapter;
    ArrayAdapter<String> donationsAdapter;

    TextView provisionTextView;

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
                startActivity(new Intent(TitheActivity.this, HomeActivity.class));
                return true;
            case R.id.action_search:
                ;
                return true;
            case R.id.action_profile:
                ;
                return true;
            case R.id.action_tithe_calculator:
                startActivity(new Intent(TitheActivity.this, TitheActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }//end switch
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tithe);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TitheActivity.this, TitheUpdateActivity.class));
            }
        });

        // Declare the spinners
        final Spinner yearSpinner = (Spinner) findViewById(R.id.inputYearSpinner);
        Spinner monthSpinner = (Spinner) findViewById(R.id.inputMonthSpinner);
        // Create an ArrayAdapters using the strings array and a default spinner layout
        ArrayAdapter<CharSequence> yearAdapter = ArrayAdapter.createFromResource(this,
                R.array.years_array, android.R.layout.simple_spinner_item);
        final ArrayAdapter<CharSequence> monthAdapter = ArrayAdapter.createFromResource(this,
                R.array.months_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapters to the spinners
        yearSpinner.setAdapter(yearAdapter);
        monthSpinner.setAdapter(monthAdapter);
        // Define the actions when select an item on the year spinner
        yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                yearPosition = yearSpinner.getSelectedItem().toString();;
                createLists();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {}
        });
        // Define the actions when select an item on the months spinner
        monthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                monthPosition = position;
                createLists();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {}
        });

        incomesList = (ListView)findViewById(R.id.incomesListView);
        expensesList = (ListView)findViewById(R.id.expensesListView);
        donationsList = (ListView)findViewById(R.id.donationsListView);

        provisionTextView = (TextView)findViewById(R.id.provisionsAmountTextView);
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        createLists();
    }

    private void createLists(){
        if (yearPosition.equals("Year") || monthPosition == 0){
            return;
        }//end if

        // Ask to get the records from the DB
        DBHandler dbHandler = new DBHandler(this);
        ArrayList<String> incomes = dbHandler.getIncomesForMonth("User", Integer.valueOf(yearPosition), monthPosition);
        ArrayList<String> expenses = dbHandler.getExpensesForMonth("User", Integer.valueOf(yearPosition), monthPosition);
        ArrayList<String> donations = dbHandler.getDonationsForMonth("User", Integer.valueOf(yearPosition), monthPosition);
        ArrayList<String> provisions = dbHandler.getProvisionForMonth("User", Integer.valueOf(yearPosition), monthPosition);

        // Set lists adapters
        incomesAdapter = new TitheListViewAdapter(this, R.layout.list_tithe, R.id.incomesListView, incomes);
        expensesAdapter = new TitheListViewAdapter(this, R.layout.list_tithe, R.id.expensesListView, expenses);
        donationsAdapter = new TitheListViewAdapter(this, R.layout.list_tithe, R.id.donationsListView, donations);

        // Sets the data behind the list view
        incomesList.setAdapter(incomesAdapter);
        expensesList.setAdapter(expensesAdapter);
        donationsList.setAdapter(donationsAdapter);

        // Set the list view height
        setListViewHeight(incomesList);
        setListViewHeight(expensesList);
        setListViewHeight(donationsList);

        // Set the provision in the text view
        if (provisions.size() > 0) {
            provisionTextView.setText(provisions.get(1));
        }//end if
        else {
            provisionTextView.setText("");
        }//end else
    }

    /**** Method for Setting the Height of the ListView dynamically.
     **** Hack to fix the issue of not showing all the items of the ListView
     **** when placed inside a ScrollView  ****/
    public static void setListViewHeight(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
}
