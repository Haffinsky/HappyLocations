package com.haffa.happylocations.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rafal on 8/15/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    static final String DATABASE_NAME = "location.db";
    public static final String TABLE_NAME = "locations";
    static final int DATABASE_VERSION = 1;
    //tables
    public static String ID = "id";
    public static String SORT_ORDER = "sortOrder";
    public static final String DISPLAY_TEXT = "displayText";
    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";

    String SQL_CREATE_LOCATION_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
            ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            DISPLAY_TEXT + " TEXT UNIQUE, " +
            LATITUDE + " TEXT UNIQUE, " +
            LONGITUDE + " TEXT UNIQUE " + " );";

    //for testing purposes
    String SQL_DROP_LOCATION_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_LOCATION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DROP_LOCATION_TABLE);
        onCreate(db);
    }
    public void updateLocation(String ID, int newPos, SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put(ID, newPos);
        db.update(TABLE_NAME, cv, ID + "=" + newPos, null);
    }
    public void dropAndRecreateFavoritesDatabase(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(SQL_DROP_LOCATION_TABLE);
        db.execSQL(SQL_CREATE_LOCATION_TABLE);
    }
    public ArrayList<String> GetAllVLocations(String aTable,String[] aColumn)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<String> list = new ArrayList<String>();
        Cursor cursor = db.query(aTable, aColumn, null, null, null, null, null);
        if (cursor.moveToFirst())
        {
            do
            {
                list.add(cursor.getString(0));
            }
            while (cursor.moveToNext());
        }
        if (cursor != null && !cursor.isClosed())
        {
            cursor.close();
        }

        return list;
    }
}
