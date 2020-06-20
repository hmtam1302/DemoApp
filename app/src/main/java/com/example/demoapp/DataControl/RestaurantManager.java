package com.example.demoapp.DataControl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.demoapp.model.Customer;
import com.example.demoapp.model.Restaurant;

import java.util.ArrayList;
import java.util.List;

public class RestaurantManager extends SQLiteOpenHelper {
    private final String TAG = "RestaurantManager";
    private static final String DATABASE_NAME = "restaurant_manager";
    private static final String TABLE_NAME = "restaurant";
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String LOGO_NAME = "logo_name";
    private static final String DESCRIPTION = "description";
    private static final String RATING = "rating";
    private static final int VERSION = 1;
    private Context context;
    private String SQLQuery = "CREATE TABLE " + TABLE_NAME + " (" +
            ID + " integer primary key, " +
            NAME + " TEXT, " +
            LOGO_NAME + " TEXT, " +
            DESCRIPTION + " TEXT, " +
            RATING + " TEXT)";

    public RestaurantManager(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        this.context = context;
        Log.d(TAG, "RestaurantManager: ");
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
    public void addRestaurant(Restaurant restaurant) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME, restaurant.getName());
        values.put(LOGO_NAME, restaurant.getLogoName());
        values.put(DESCRIPTION, restaurant.getDiscription());
        values.put(RATING, restaurant.getRating());
        db.insert(TABLE_NAME, null, values);
        db.close();
        Log.d(TAG, "addRestaurant: Successfully!");
    }

    public List<Restaurant> getAllRestaurant() {
        List<Restaurant> listRestaurant = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()) {
            do {
                Restaurant restaurant = new Restaurant();
                restaurant.setID(cursor.getInt(0));
                restaurant.setName(cursor.getString(1));
                restaurant.setLogoName(cursor.getString(2));
                restaurant.setDiscription(cursor.getString(3));
                restaurant.setRating(cursor.getString(4));
                listRestaurant.add(restaurant);
            } while (cursor.moveToNext());
        }
        db.close();
        return listRestaurant;
    }

    public int updateCustomer(Restaurant restaurant) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME, restaurant.getName());
        contentValues.put(LOGO_NAME, restaurant.getLogoName());
        contentValues.put(DESCRIPTION, restaurant.getDiscription());
        contentValues.put(RATING, restaurant.getRating());
        return db.update(TABLE_NAME, contentValues, ID + "=?", new String[]{String.valueOf(restaurant.getID())});
    }

    public int deleteCustomer(Restaurant restaurant) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, ID+"=?", new String[]{String.valueOf(restaurant.getID())});
    }
}
