package com.haffa.happylocations.Data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;

/**
 * Created by Rafal on 8/15/2017.
 */

public class LocationContentProvider extends ContentProvider {

    public static final String CONTENT_AUTHORITY = "rafal.happylocations";
    public static final String PATH_LOCATIONS = "locations";
    private DatabaseHelper databaseHelper;
    public static final int LOCATIONS = 100;
    public static final int LOCATIONS_ID = 101;
    private static final UriMatcher uriMatcher = buildUriMatcher();

    public static UriMatcher buildUriMatcher(){
        UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(CONTENT_AUTHORITY, PATH_LOCATIONS, LOCATIONS);
        matcher.addURI(CONTENT_AUTHORITY, PATH_LOCATIONS + "/#", LOCATIONS_ID);

        return  matcher;
    }

    @Override
    public boolean onCreate() {
        databaseHelper = new DatabaseHelper(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(DatabaseHelper.TABLE_NAME);

        int uriType = uriMatcher.match(uri);
        switch (uriType){
            case LOCATIONS:
                break;
            case LOCATIONS_ID:
                queryBuilder.appendWhere(DatabaseHelper.ID  + "=" + uri.getLastPathSegment());
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        Cursor cursor = queryBuilder.query(db, projection, selection,
                selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int uriType = uriMatcher.match(uri);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        long id = 0;
        switch (uriType){
            case LOCATIONS:
            id = db.insert(databaseHelper.TABLE_NAME, null, values);
            break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse(PATH_LOCATIONS + "/" + id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
