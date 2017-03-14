package com.mgmt.restaurant.restaurantmanagement.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mgmt.restaurant.restaurantmanagement.model.CustomerDetails;

import java.util.ArrayList;
import java.util.List;

public class CustomerDetailsDatabaseHelper extends SQLiteOpenHelper {
    // Database Info
    private static final String DATABASE_NAME = "CustomerDatabase";
    private static final int DATABASE_VERSION = 1;

    // Table Names
    private static final String TABLE_USERS = "customers";

    // Table Columns
    private static final String KEY_USER_ID = "id";
    private static final String KEY_FIRST_NAME = "firstname";
    private static final String KEY_LAST_NAME = "lastname";

    private String[] allColumns = { KEY_USER_ID,
            KEY_FIRST_NAME, KEY_LAST_NAME };

    private static CustomerDetailsDatabaseHelper sInstance;

    public static synchronized CustomerDetailsDatabaseHelper getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new CustomerDetailsDatabaseHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    /**
     * Constructor should be private to prevent direct instantiation.
     * Make a call to the static method "getInstance()" instead.
     */
    private CustomerDetailsDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Called when the database connection is being configured.
    // Configure database settings for things like foreign key support, write-ahead logging, etc.
    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    // Called when the database is created for the FIRST time.
    // If a database already exists on disk with the same DATABASE_NAME, this method will NOT be called.
    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS +
                "(" +
                    KEY_USER_ID + " INTEGER PRIMARY KEY," +
                    KEY_FIRST_NAME + " TEXT," +
                    KEY_LAST_NAME + " TEXT" +
                ")";

        db.execSQL(CREATE_USERS_TABLE);
    }
    
    // Called when the database needs to be upgraded.
    // This method will only be called if a database already exists on disk with the same DATABASE_NAME,
    // but the DATABASE_VERSION is different than the version of the database that exists on disk.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            // Simplest implementation is to drop all old tables and recreate them
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
            onCreate(db);
        }
    }

    public void putCustomers(List<CustomerDetails> customers){
        if(customers!=null && customers.size()>0){
            for (int i = 0; i < customers.size() ; i++) {
                createCustomer(customers.get(i));
            }
        }
    }

    public long createCustomer(CustomerDetails cust) {
        ContentValues values = new ContentValues();
        SQLiteDatabase database = getWritableDatabase();
        values.put(KEY_FIRST_NAME, cust.getCustomerFirstName());
        values.put(KEY_LAST_NAME, cust.getCustomerLastName());

        long insertId = database.insert(TABLE_USERS, null,
                values);

        return insertId;
    }


    public ArrayList<CustomerDetails> getAllCustomers() {
        ArrayList<CustomerDetails> comments = new ArrayList<CustomerDetails>();
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query(TABLE_USERS,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            CustomerDetails comment = cursorToComment(cursor);
            comments.add(comment);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return comments;
    }

    private CustomerDetails cursorToComment(Cursor cursor) {
        CustomerDetails comment = new CustomerDetails(cursor.getString(1),cursor.getString(2),Integer.parseInt(cursor.getString(0)));
/*        comment.setId(Integer.parseInt(cursor.getString(0)));
        comment.setCustomerFirstName(cursor.getString(1));
        comment.setCustomerLastName(cursor.getString(2));*/
        return comment;
    }


}