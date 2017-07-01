package com.jgive.kobieliasi.jgiveapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Kobi Eliasi on 07/01/2017.
 * local SQLite DB handler
 */

public class DBHandler extends SQLiteOpenHelper {

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
        String whereClause = Constants.Provisions._ID + " = ?";
        String[] whereArgs = { String.valueOf(id) };

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

    // TODO: Delete this when go live with server
    protected JSONArray getOrganizations() {
        String string = "[\n" +
                "  {\n" +
                "    \"id\": \"2058\",\n" +
                "    \"project_name\": \"Winning with Gadi\",\n" +
                "    \"org_name\": \"Mekimi\",\n" +
                "    \"details\": \"Update: You are amazing!With the help of over 3,000 donors we reached the goal of stage 1 –...\",\n" +
                "    \"picture\": \"https://d1qvck26m1aukd.cloudfront.net/app/public/media_objects/image_4x3_hes/000/002/394/thumbnail/05bb193ca94631aeb776c55ad7892eb946bae9e2.jpg?1496134678\",\n" +
                "    \"goal_current\": \"874,240\",\n" +
                "    \"goal_total\": \"800,000\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"1179\",\n" +
                "    \"project_name\": \"Two year old Adam Gomon\",\n" +
                "    \"org_name\": \"Struggle for Life\",\n" +
                "    \"details\": \"Adam Gomon, only 2 years old, has been diagnosed with a dangerous blood disease and requires an...\",\n" +
                "    \"picture\": \"https://img.youtube.com/vi/huwLueOilak/0.jpg\",\n" +
                "    \"goal_current\": \"267,365\",\n" +
                "    \"goal_total\": \"865,000\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"1318\",\n" +
                "    \"project_name\": \"Building the Path of our 3 Fathers for...\",\n" +
                "    \"org_name\": \"Makor Chaim\",\n" +
                "    \"details\": \"3 years have passed. 3 years without Gilad, Naftali and Ayal.\",\n" +
                "    \"picture\": \"https://img.youtube.com/vi/OfmBrTAKRg8/0.jpg\",\n" +
                "    \"goal_current\": \"262,534\",\n" +
                "    \"goal_total\": \"1,000,000\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"2204\",\n" +
                "    \"project_name\": \"Ratzim La'Mishna - Summer Mishna...\",\n" +
                "    \"org_name\": \"Halacha Education Center\",\n" +
                "    \"details\": \"Halacha Education Center brings you Ratzim La'Mishna: a game-changer in children's Mishna...\",\n" +
                "    \"picture\": \"https://img.youtube.com/vi/BiuGjJ7f6II/0.jpg\",\n" +
                "    \"goal_current\": \"182,794\",\n" +
                "    \"goal_total\": \"300,000\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"1900\",\n" +
                "    \"project_name\": \"Commemorative garden for Beniyah and Achikam\",\n" +
                "    \"org_name\": \"Makor Chaim\",\n" +
                "    \"details\": \"Adam Gomon, only 2 years old, has been diagnosed with a dangerous blood disease and requires an...\",\n" +
                "    \"picture\": \"https://d1qvck26m1aukd.cloudfront.net/app/public/media_objects/image_4x3_hes/000/002/193/thumbnail/a6fd730e4251d79ee33a811cfc1181ae66fd7049.png?1493500849\",\n" +
                "    \"goal_current\": \"109,564\",\n" +
                "    \"goal_total\": \"100,000\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"1923\",\n" +
                "    \"project_name\": \"Reut School Soup Kitchen\",\n" +
                "    \"org_name\": \"Society For Advancement of Education\",\n" +
                "    \"details\": \"Adam Gomon, only 2 years old, has been diagnosed with a dangerous blood disease and requires an...\",\n" +
                "    \"picture\": \"https://img.youtube.com/vi/huwLueOilak/0.jpg\",\n" +
                "    \"goal_current\": \"93,587\",\n" +
                "    \"goal_total\": \"100,000\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"1371\",\n" +
                "    \"project_name\": \"Lifting Natan\",\n" +
                "    \"org_name\": \"Migdal Chesed\",\n" +
                "    \"details\": \"While the Atiya family lost their son Nachman in the brutal attack in Migdal, their son Natan...\",\n" +
                "    \"picture\": \"https://d1qvck26m1aukd.cloudfront.net/app/public/media_objects/image_4x3_hes/000/001/553/thumbnail/812419b3cba049d7f960970bfb7a0d54b59b735e.jpg?1488281276\",\n" +
                "    \"goal_current\": \"72,214\",\n" +
                "    \"goal_total\": \"100,000\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"1267\",\n" +
                "    \"project_name\": \"Ten children made into orphans\",\n" +
                "    \"org_name\": \"Kupat Hair\",\n" +
                "    \"details\": \"Our mother had a simple flu, then suddenly she was gone. Here we were, ten children made into...\",\n" +
                "    \"picture\": \"https://d1qvck26m1aukd.cloudfront.net/app/public/media_objects/image_4x3_hes/000/001/435/thumbnail/327f7f5cc4f2efc44f228bd25ea1ae19308451f2.JPG?1488280742\",\n" +
                "    \"goal_current\": \"68,185\",\n" +
                "    \"goal_total\": \"100,000\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"2196\",\n" +
                "    \"project_name\": \"Or HaGanuz Synagogue to be enlarged\",\n" +
                "    \"org_name\": \"ginzy shimon\",\n" +
                "    \"details\": \"Rabbi Yehuda LeibAshlag z l - Baal HaSulam writes in the article  Haharevut  taken from the...\",\n" +
                "    \"picture\": \"https://img.youtube.com/vi/iopUdI0xf4M/0.jpg\",\n" +
                "    \"goal_current\": \"62,526\",\n" +
                "    \"goal_total\": \"550,000\"\n" +
                "  }\n" +
                "]";
        try {
            return new JSONArray(string);
        }
        catch (JSONException e){
            Log.d("DBHandler", e.toString());
        }
        return null;
    }
    // TODO: Delete this when go live with server
    protected JSONObject getOrganization(int id) {
        String string = "[\n" +
                "  {\n" +
                "    \"id\": \"2058\",\n" +
                "    \"project_name\": \"Winning with Gadi\",\n" +
                "    \"org_name\": \"Mekimi\",\n" +
                "    \"details\": \"Update: You are amazing!With the help of over 3,000 donors we reached the goal of stage 1 –...\",\n" +
                "    \"picture\": \"https://d1qvck26m1aukd.cloudfront.net/app/public/media_objects/image_4x3_hes/000/002/394/thumbnail/05bb193ca94631aeb776c55ad7892eb946bae9e2.jpg?1496134678\",\n" +
                "    \"goal_current\": \"874,240\",\n" +
                "    \"goal_total\": \"800,000\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"1179\",\n" +
                "    \"project_name\": \"Two year old Adam Gomon\",\n" +
                "    \"org_name\": \"Struggle for Life\",\n" +
                "    \"details\": \"Adam Gomon, only 2 years old, has been diagnosed with a dangerous blood disease and requires an...\",\n" +
                "    \"picture\": \"https://img.youtube.com/vi/huwLueOilak/0.jpg\",\n" +
                "    \"goal_current\": \"267,365\",\n" +
                "    \"goal_total\": \"865,000\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"1318\",\n" +
                "    \"project_name\": \"Building the Path of our 3 Fathers for...\",\n" +
                "    \"org_name\": \"Makor Chaim\",\n" +
                "    \"details\": \"3 years have passed. 3 years without Gilad, Naftali and Ayal.\",\n" +
                "    \"picture\": \"https://img.youtube.com/vi/OfmBrTAKRg8/0.jpg\",\n" +
                "    \"goal_current\": \"262,534\",\n" +
                "    \"goal_total\": \"1,000,000\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"2204\",\n" +
                "    \"project_name\": \"Ratzim La'Mishna - Summer Mishna...\",\n" +
                "    \"org_name\": \"Halacha Education Center\",\n" +
                "    \"details\": \"Halacha Education Center brings you Ratzim La'Mishna: a game-changer in children's Mishna...\",\n" +
                "    \"picture\": \"https://img.youtube.com/vi/BiuGjJ7f6II/0.jpg\",\n" +
                "    \"goal_current\": \"182,794\",\n" +
                "    \"goal_total\": \"300,000\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"1900\",\n" +
                "    \"project_name\": \"Commemorative garden for Beniyah and Achikam\",\n" +
                "    \"org_name\": \"Makor Chaim\",\n" +
                "    \"details\": \"Adam Gomon, only 2 years old, has been diagnosed with a dangerous blood disease and requires an...\",\n" +
                "    \"picture\": \"https://d1qvck26m1aukd.cloudfront.net/app/public/media_objects/image_4x3_hes/000/002/193/thumbnail/a6fd730e4251d79ee33a811cfc1181ae66fd7049.png?1493500849\",\n" +
                "    \"goal_current\": \"109,564\",\n" +
                "    \"goal_total\": \"100,000\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"1923\",\n" +
                "    \"project_name\": \"Reut School Soup Kitchen\",\n" +
                "    \"org_name\": \"Society For Advancement of Education\",\n" +
                "    \"details\": \"Adam Gomon, only 2 years old, has been diagnosed with a dangerous blood disease and requires an...\",\n" +
                "    \"picture\": \"https://img.youtube.com/vi/huwLueOilak/0.jpg\",\n" +
                "    \"goal_current\": \"93,587\",\n" +
                "    \"goal_total\": \"100,000\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"1371\",\n" +
                "    \"project_name\": \"Lifting Natan\",\n" +
                "    \"org_name\": \"Migdal Chesed\",\n" +
                "    \"details\": \"While the Atiya family lost their son Nachman in the brutal attack in Migdal, their son Natan...\",\n" +
                "    \"picture\": \"https://d1qvck26m1aukd.cloudfront.net/app/public/media_objects/image_4x3_hes/000/001/553/thumbnail/812419b3cba049d7f960970bfb7a0d54b59b735e.jpg?1488281276\",\n" +
                "    \"goal_current\": \"72,214\",\n" +
                "    \"goal_total\": \"100,000\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"1267\",\n" +
                "    \"project_name\": \"Ten children made into orphans\",\n" +
                "    \"org_name\": \"Kupat Hair\",\n" +
                "    \"details\": \"Our mother had a simple flu, then suddenly she was gone. Here we were, ten children made into...\",\n" +
                "    \"picture\": \"https://d1qvck26m1aukd.cloudfront.net/app/public/media_objects/image_4x3_hes/000/001/435/thumbnail/327f7f5cc4f2efc44f228bd25ea1ae19308451f2.JPG?1488280742\",\n" +
                "    \"goal_current\": \"68,185\",\n" +
                "    \"goal_total\": \"100,000\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"2196\",\n" +
                "    \"project_name\": \"Or HaGanuz Synagogue to be enlarged\",\n" +
                "    \"org_name\": \"ginzy shimon\",\n" +
                "    \"details\": \"Rabbi Yehuda LeibAshlag z l - Baal HaSulam writes in the article  Haharevut  taken from the...\",\n" +
                "    \"picture\": \"https://img.youtube.com/vi/iopUdI0xf4M/0.jpg\",\n" +
                "    \"goal_current\": \"62,526\",\n" +
                "    \"goal_total\": \"550,000\"\n" +
                "  }\n" +
                "]";
        try {
            return new JSONArray(string).getJSONObject(id);
        }
        catch (JSONException e){
            Log.d("DBHandler", e.toString());
        }
        return null;
    }
}