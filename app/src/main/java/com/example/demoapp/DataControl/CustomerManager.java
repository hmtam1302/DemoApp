package com.example.demoapp.DataControl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.demoapp.model.Customer;

import java.util.ArrayList;
import java.util.List;

public class CustomerManager extends SQLiteOpenHelper {
    private final String TAG = "AccountManager";
    private static final String DATABASE_NAME = "account_manager";
    private static final String TABLE_NAME = "customer";
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String USER_NAME = "user_name";
    private static final String PASSWORD = "password";
    private static final String DATE_OF_BIRTH = "date_of_birth";
    private static final String GENDER = "gender";
    private static final String EMAIL = "email";
    private static final String PHONE = "phone";
    private static final int VERSION = 1;
    private Context context;
    private String SQLQuery = "CREATE TABLE " + TABLE_NAME + " (" +
            ID + " integer primary key, " +
            NAME + " TEXT, " +
            USER_NAME + " TEXT, " +
            PASSWORD + " TEXT, " +
            DATE_OF_BIRTH + " TEXT, "+
            GENDER + " TEXT, " +
            EMAIL + " TEXT, " +
            PHONE + " TEXT)";

    public CustomerManager(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        this.context = context;
        Log.d(TAG, "AccountManager: ");
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQLQuery);
        Log.d(TAG, "onCreate: ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.d(TAG, "onUpgrade: ");
    }
    public void addCustomer(Customer customer) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME, customer.getName());
        values.put(USER_NAME, customer.getUsername());
        values.put(PASSWORD, customer.getPassword());
        values.put(DATE_OF_BIRTH, customer.getDateOfBirth());
        values.put(GENDER, customer.getGender());
        values.put(EMAIL, customer.getEmail());
        values.put(PHONE, customer.getPhoneNumber());
        db.insert(TABLE_NAME, null, values);
        db.close();
        Log.d(TAG, "addCustomer: Successfully!");
    }

    public List<Customer> getAllCustomer() {
        List<Customer> listCustomer = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()) {
            do {
                Customer customer = new Customer();
                customer.setID(cursor.getInt(0));
                customer.setName(cursor.getString(1));
                customer.setUsername(cursor.getString(2));
                customer.setPassword(cursor.getString(3));
                customer.setDateOfBirth(cursor.getString(4));
                customer.setGender(cursor.getString(5));
                customer.setEmail(cursor.getString(6));
                customer.setPhoneNumber(cursor.getString(7));
                listCustomer.add(customer);
            } while (cursor.moveToNext());
        }
        db.close();
        return listCustomer;
    }

    public int updateCustomer(Customer customer, int ID) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME, customer.getName());
        contentValues.put(USER_NAME, customer.getUsername());
        contentValues.put(PASSWORD, customer.getPassword());
        contentValues.put(DATE_OF_BIRTH, customer.getDateOfBirth());
        contentValues.put(GENDER, customer.getGender());
        contentValues.put(EMAIL, customer.getEmail());
        contentValues.put(PHONE, customer.getPhoneNumber());
        return db.update(TABLE_NAME, contentValues, "ID =" + ID, null);
    }

    public int deleteCustomer(Customer customer) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, ID+"=?", null);
    }
}

