package com.jgive.kobieliasi.jgiveapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Kobi Eliasi on 07/01/2017.
 */

public class DBHandler extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "jgive.db";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Incomes table creation
    private static final String CREATE_INCOMES_TABLE =
            "CREATE TABLE IF NOT EXISTS " + Constants.Incomes.TABLE_NAME + " (" +
                    Constants.Incomes._ID +             " INTEGER PRIMARY KEY," +
                    Constants.Incomes.USER_NAME +       " TEXT," +
                    Constants.Incomes.SOURCE +          " TEXT," +
                    Constants.Incomes.AMOUNT +          " REAL" +
                    Constants.Incomes.YEAR +            " INTEGER" +
                    Constants.Incomes.MONTH +           " INTEGER" +
                    ");";

    // Incomes table dropping
    private static final String DROP_INCOMES_TABLE =
            "DROP TABLE IF EXIST " + Constants.Incomes.TABLE_NAME;

    // Expenses table creation
    private static final String CREATE_EXPENSES_TABLE =
            "CREATE TABLE IF NOT EXISTS " + Constants.Expenses.TABLE_NAME + " (" +
                    Constants.Expenses._ID +             " INTEGER PRIMARY KEY," +
                    Constants.Expenses.USER_NAME +       " TEXT," +
                    Constants.Expenses.TARGET +          " TEXT," +
                    Constants.Expenses.AMOUNT +          " REAL" +
                    Constants.Expenses.YEAR +            " INTEGER" +
                    Constants.Expenses.MONTH +           " INTEGER" +
                    ");";

    // Expenses table dropping
    private static final String DROP_EXPENSES_TABLE =
            "DROP TABLE IF EXIST " + Constants.Expenses.TABLE_NAME;

    // Donations table creation
    private static final String CREATE_DONATIONS_TABLE =
            "CREATE TABLE IF NOT EXISTS " + Constants.Donations.TABLE_NAME + " (" +
                    Constants.Donations._ID +             " INTEGER PRIMARY KEY," +
                    Constants.Donations.USER_NAME +       " TEXT," +
                    Constants.Donations.TARGET +          " TEXT," +
                    Constants.Donations.AMOUNT +          " REAL" +
                    Constants.Donations.YEAR +            " INTEGER" +
                    Constants.Donations.MONTH +           " INTEGER" +
                    ");";

    // Donations table dropping
    private static final String DROP_DONATIONS_TABLE =
            "DROP TABLE IF EXIST " + Constants.Donations.TABLE_NAME;

    // Provisions table creation
    private static final String CREATE_PROVISIONS_TABLE =
            "CREATE TABLE IF NOT EXISTS " + Constants.Provisions.TABLE_NAME + " (" +
                    Constants.Provisions._ID +             " INTEGER PRIMARY KEY," +
                    Constants.Provisions.USER_NAME +       " TEXT," +
                    Constants.Provisions.YEAR +            " INTEGER" +
                    Constants.Provisions.MONTH +           " INTEGER" +
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

    public boolean addIncome(String user, String source, Double amount, int year, int month){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Constants.Incomes.USER_NAME, user);
        values.put(Constants.Incomes.SOURCE, source);
        values.put(Constants.Incomes.AMOUNT, amount);
        values.put(Constants.Incomes.YEAR, year);
        values.put(Constants.Incomes.MONTH, month);

        // Inserting Row
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

    public boolean addExpense(String user, String target, Double amount, int year, int month){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Constants.Expenses.USER_NAME, user);
        values.put(Constants.Expenses.TARGET, target);
        values.put(Constants.Expenses.AMOUNT, amount);
        values.put(Constants.Expenses.YEAR, year);
        values.put(Constants.Expenses.MONTH, month);

        // Inserting Row
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

    public boolean addDonation(String user, String target, Double amount, int year, int month){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Constants.Donations.USER_NAME, user);
        values.put(Constants.Donations.TARGET, target);
        values.put(Constants.Donations.AMOUNT, amount);
        values.put(Constants.Donations.YEAR, year);
        values.put(Constants.Donations.MONTH, month);

        // Inserting Row
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

    public boolean addProvisions(String user, int year, int month, Double amount){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Constants.Provisions.USER_NAME, user);
        values.put(Constants.Provisions.YEAR, year);
        values.put(Constants.Provisions.MONTH, month);
        values.put(Constants.Provisions.AMOUNT, amount);

        // Inserting Row
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
}
