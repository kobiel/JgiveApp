package com.jgive.kobieliasi.jgiveapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Kobi Eliasi on 07/01/2017.
 */

public class DBHandler extends SQLiteOpenHelper {

    final static int INCOME = 0;
    final static int EXPENSE = 1;
    final static int DONATION = 2;
    final static int PROVISION = 3;

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "jgive.db";

    protected DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Incomes table creation
    private static final String CREATE_INCOMES_TABLE =
            "CREATE TABLE " + Constants.Incomes.TABLE_NAME + " (" +
                    Constants.Incomes._ID +             " INTEGER PRIMARY KEY," +
                    Constants.Incomes.USER_NAME +       " TEXT," +
                    Constants.Incomes.SOURCE +          " TEXT," +
                    Constants.Incomes.AMOUNT +          " REAL," +
                    Constants.Incomes.YEAR +            " INTEGER," +
                    Constants.Incomes.MONTH +           " INTEGER" +
                    ");";

    // Incomes table dropping
    private static final String DROP_INCOMES_TABLE =
            "DROP TABLE IF EXIST " + Constants.Incomes.TABLE_NAME;

    // Expenses table creation
    private static final String CREATE_EXPENSES_TABLE =
            "CREATE TABLE " + Constants.Expenses.TABLE_NAME + " (" +
                    Constants.Expenses._ID +             " INTEGER PRIMARY KEY," +
                    Constants.Expenses.USER_NAME +       " TEXT," +
                    Constants.Expenses.TARGET +          " TEXT," +
                    Constants.Expenses.AMOUNT +          " REAL," +
                    Constants.Expenses.YEAR +            " INTEGER," +
                    Constants.Expenses.MONTH +           " INTEGER" +
                    ");";

    // Expenses table dropping
    private static final String DROP_EXPENSES_TABLE =
            "DROP TABLE IF EXIST " + Constants.Expenses.TABLE_NAME;

    // Donations table creation
    private static final String CREATE_DONATIONS_TABLE =
            "CREATE TABLE " + Constants.Donations.TABLE_NAME + " (" +
                    Constants.Donations._ID +             " INTEGER PRIMARY KEY," +
                    Constants.Donations.USER_NAME +       " TEXT," +
                    Constants.Donations.TARGET +          " TEXT," +
                    Constants.Donations.AMOUNT +          " REAL," +
                    Constants.Donations.YEAR +            " INTEGER," +
                    Constants.Donations.MONTH +           " INTEGER" +
                    ");";

    // Donations table dropping
    private static final String DROP_DONATIONS_TABLE =
            "DROP TABLE IF EXIST " + Constants.Donations.TABLE_NAME;

    // Provisions table creation
    private static final String CREATE_PROVISIONS_TABLE =
            "CREATE TABLE " + Constants.Provisions.TABLE_NAME + " (" +
                    Constants.Provisions._ID +             " INTEGER PRIMARY KEY," +
                    Constants.Provisions.USER_NAME +       " TEXT," +
                    Constants.Provisions.YEAR +            " INTEGER," +
                    Constants.Provisions.MONTH +           " INTEGER," +
                    Constants.Provisions.AMOUNT +          " REAL" +
                    ");";

    // Provisions table dropping
    private static final String DROP_PROVISIONS_TABLE =
            "DROP TABLE IF EXIST " + Constants.Provisions.TABLE_NAME;

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_INCOMES_TABLE);
        db.execSQL(CREATE_EXPENSES_TABLE);
        db.execSQL(CREATE_DONATIONS_TABLE);
        db.execSQL(CREATE_PROVISIONS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        // Drop older tables if existed
        db.execSQL(DROP_INCOMES_TABLE);
        db.execSQL(DROP_EXPENSES_TABLE);
        db.execSQL(DROP_DONATIONS_TABLE);
        db.execSQL(DROP_PROVISIONS_TABLE);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion){
        onUpgrade(db, oldVersion, newVersion);
    }

    protected boolean addIncome(String user, String source, Double amount, int year, int month){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Constants.Incomes.USER_NAME, user);
        values.put(Constants.Incomes.SOURCE, source);
        values.put(Constants.Incomes.AMOUNT, amount);
        values.put(Constants.Incomes.YEAR, year);
        values.put(Constants.Incomes.MONTH, month+1);

        // Inserting Row and get row ID or -1 if failed
        long rowInserted = db.insert(Constants.Incomes.TABLE_NAME, null, values);

        // Closing database connection
        db.close();
        // Check if row inserted
        if(rowInserted != -1) {
            return true;
        }//end if
        else {
            return false;
        }//end else
    }

    protected boolean addExpense(String user, String target, Double amount, int year, int month){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Constants.Expenses.USER_NAME, user);
        values.put(Constants.Expenses.TARGET, target);
        values.put(Constants.Expenses.AMOUNT, amount);
        values.put(Constants.Expenses.YEAR, year);
        values.put(Constants.Expenses.MONTH, month+1);

        // Inserting Row and get row ID or -1 if failed
        long rowInserted = db.insert(Constants.Expenses.TABLE_NAME, null, values);

        // Closing database connection
        db.close();
        // Check if row inserted
        if(rowInserted != -1) {
            return true;
        }//end if
        else {
            return false;
        }//end else
    }

    protected boolean addDonation(String user, String target, Double amount, int year, int month){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Constants.Donations.USER_NAME, user);
        values.put(Constants.Donations.TARGET, target);
        values.put(Constants.Donations.AMOUNT, amount);
        values.put(Constants.Donations.YEAR, year);
        values.put(Constants.Donations.MONTH, month+1);

        // Inserting Row and get row ID or -1 if failed
        long rowInserted = db.insert(Constants.Donations.TABLE_NAME, null, values);

        // Closing database connection
        db.close();
        // Check if row inserted
        if(rowInserted != -1) {
            return true;
        }//end if
        else {
            return false;
        }//end else
    }

    protected boolean addProvisions(String user, int year, int month, Double amount){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Constants.Provisions.USER_NAME, user);
        values.put(Constants.Provisions.YEAR, year);
        values.put(Constants.Provisions.MONTH, month+1);
        values.put(Constants.Provisions.AMOUNT, amount);

        // Inserting Row and get row ID or -1 if failed
        long rowInserted = db.insert(Constants.Provisions.TABLE_NAME, null, values);

        // Closing database connection
        db.close();
        // Check if row inserted
        if(rowInserted != -1) {
            return true;
        }//end if
        else {
            return false;
        }//end else
    }

    protected boolean updateProvisions(int id, Double amount){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Constants.Provisions.AMOUNT, amount);
        // WHERE clause
        String whereClause = Constants.Provisions.AMOUNT + " = ?";
        String[] whereArgs = { String.valueOf(amount) };

        // Updating Row id and get if raw affected
        int rowAffected = db.update(Constants.Provisions.TABLE_NAME, values, whereClause, whereArgs);

        // Closing database connection
        db.close();
        // Check if row affected
        if(rowAffected != -1) {
            return true;
        }//end if
        else {
            return false;
        }//end else
    }

    protected ArrayList<String> getIncomesForMonth(String user, int year, int month){
        SQLiteDatabase db = this.getReadableDatabase();

        // Define a projection that specifies which columns from the database to use
        String [] projection = {Constants.Incomes.SOURCE, Constants.Incomes.AMOUNT};
        // Filter results WHERE
        String selection = Constants.Incomes.USER_NAME + " = ? AND "
                + Constants.Incomes.YEAR + " = ? AND " + Constants.Incomes.MONTH + " = ?";
        String[] selectionArgs = { user, String.valueOf(year), String.valueOf(month) };
        // How you want the results sorted in the resulting Cursor
        String sortOrder = Constants.Incomes.SOURCE + " ASC";

        // Run the query
        Cursor cursor = db.query(
                Constants.Incomes.TABLE_NAME,       //The table to query
                projection,                         //The columns to return
                selection,                          //The columns for the WHERE clause
                selectionArgs,                      //The values for the WHERE clause
                null,                               //Group the rows
                null,                               //Filter by row groups
                sortOrder                           //The sort order
        );
        ArrayList<String> list = new ArrayList<>();
        while(cursor.moveToNext()) {
            list.add(cursor.getString(0) + ";" + cursor.getString(1));
        }//end while

        // Closing database connection
        cursor.close();
        db.close();
        return list;
    }

    protected ArrayList<String> getExpensesForMonth(String user, int year, int month){
        SQLiteDatabase db = this.getReadableDatabase();

        // Define a projection that specifies which columns from the database to use
        String [] projection = {Constants.Expenses.TARGET, Constants.Expenses.AMOUNT};
        // Filter results WHERE
        String selection = Constants.Expenses.USER_NAME + " = ? AND "
                + Constants.Expenses.YEAR + " = ? AND " + Constants.Expenses.MONTH + " = ?";
        String[] selectionArgs = { user, String.valueOf(year), String.valueOf(month) };
        // How you want the results sorted in the resulting Cursor
        String sortOrder = Constants.Expenses.TARGET + " ASC";

        // Run the query
        Cursor cursor = db.query(
                Constants.Expenses.TABLE_NAME,      //The table to query
                projection,                         //The columns to return
                selection,                          //The columns for the WHERE clause
                selectionArgs,                      //The values for the WHERE clause
                null,                               //Group the rows
                null,                               //Filter by row groups
                sortOrder                           //The sort order
        );
        ArrayList<String> list = new ArrayList<>();
        while(cursor.moveToNext()) {
            list.add(cursor.getString(0) + ";" + cursor.getString(1));
        }//end while

        // Closing database connection
        cursor.close();
        db.close();
        return list;
    }

    protected ArrayList<String> getDonationsForMonth(String user, int year, int month){
        SQLiteDatabase db = this.getReadableDatabase();

        // Define a projection that specifies which columns from the database to use
        String [] projection = {Constants.Donations.TARGET, Constants.Donations.AMOUNT};
        // Filter results WHERE
        String selection = Constants.Donations.USER_NAME + " = ? AND "
                + Constants.Donations.YEAR + " = ? AND " + Constants.Donations.MONTH + " = ?";
        String[] selectionArgs = { user, String.valueOf(year), String.valueOf(month) };
        // How you want the results sorted in the resulting Cursor
        String sortOrder = Constants.Donations.TARGET + " ASC";

        // Run the query
        Cursor cursor = db.query(
                Constants.Donations.TABLE_NAME,    //The table to query
                projection,                         //The columns to return
                selection,                          //The columns for the WHERE clause
                selectionArgs,                      //The values for the WHERE clause
                null,                               //Group the rows
                null,                               //Filter by row groups
                sortOrder                           //The sort order
        );
        ArrayList<String> list = new ArrayList<>();
        while(cursor.moveToNext()) {
            list.add(cursor.getString(0) + ";" + cursor.getString(1));
        }//end while

        // Closing database connection
        cursor.close();
        db.close();
        return list;
    }

    protected ArrayList<String> getProvisionForMonth(String user, int year, int month){
        SQLiteDatabase db = this.getReadableDatabase();

        // Define a projection that specifies which columns from the database to use
        String [] projection = {Constants.Provisions._ID, Constants.Provisions.AMOUNT};
        // Filter results WHERE
        String selection = Constants.Provisions.USER_NAME + " = ? AND "
                + Constants.Provisions.YEAR + " = ? AND " + Constants.Provisions.MONTH + " = ?";
        String[] selectionArgs = { user, String.valueOf(year), String.valueOf(month) };

        Cursor cursor = db.query(
                Constants.Provisions.TABLE_NAME,    //The table to query
                projection,                         //The columns to return
                selection,                          //The columns for the WHERE clause
                selectionArgs,                      //The values for the WHERE clause
                null,                               //Group the rows
                null,                               //Filter by row groups
                null                                //The sort order
        );
        ArrayList<String> list = new ArrayList<>();
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToNext();
            list.add(cursor.getString(0));
            list.add(cursor.getString(1));
        }//end if

        // Closing database connection
        cursor.close();
        db.close();
        return list;
    }

    protected ArrayList<String> getYears(String user){
        SQLiteDatabase db = this.getReadableDatabase();

        // Define a projection that specifies which columns from the database to use
        String [] projection = {Constants.Incomes.YEAR};
        // Filter results WHERE
        String selection = Constants.Provisions.USER_NAME + " = ?";
        String[] selectionArgs = { user };
        // Distinct
        boolean distinct = true;
        String groupBy = Constants.Incomes.YEAR;

        Cursor cursor = db.query(
                distinct,
                Constants.Provisions.TABLE_NAME,    //The table to query
                projection,                         //The columns to return
                selection,                          //The columns for the WHERE clause
                selectionArgs,                      //The values for the WHERE clause
                groupBy,                            //Group the rows
                null,                               //Filter by row groups
                null,                               //The sort order
                null                                //The limit
        );
        ArrayList<String> list = new ArrayList<>();
        while(cursor.moveToNext()) {
            list.add(cursor.getString(0));
        }//end while

        // Closing database connection
        cursor.close();
        db.close();
        return list;
    }
}