package com.haffa.happylocations;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.haffa.happylocations.Data.DatabaseHelper;
import com.haffa.happylocations.GestureHandler.ItemTouchHelperAdapter;
import com.haffa.happylocations.GestureHandler.ItemTouchHelperViewHolder;
import com.haffa.happylocations.GestureHandler.OnListChangedListener;


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.haffa.happylocations.Data.DatabaseHelper.DISPLAY_TEXT;
import static com.haffa.happylocations.Data.DatabaseHelper.ID;
import static com.haffa.happylocations.Data.DatabaseHelper.LATITUDE;
import static com.haffa.happylocations.Data.DatabaseHelper.LONGITUDE;
import static com.haffa.happylocations.Data.DatabaseHelper.TABLE_NAME;
import static com.haffa.happylocations.Data.LocationContentProvider.CONTENT_AUTHORITY;
import static com.haffa.happylocations.Utilities.RetriveMyApplicationContext.getAppContext;
import static com.haffa.happylocations.Utilities.RetriveMyApplicationContext.getInstance;

/**
 * Created by Rafal on 8/14/2017.
 */

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.ViewHolder>  implements ItemTouchHelperAdapter {

    Cursor cursor;
    private ArrayList<String> locations;
    String key = "key";
    Type listType = new TypeToken<List<String>>() {
    }.getType();
    private ArrayList<String> retrievedList;
    DatabaseHelper databaseHelper = new DatabaseHelper(getAppContext());

    public FavoritesAdapter() {

        String retrievedJsonListOfLocations = readFromSharedPreferences(key);

        retrievedList = new Gson().fromJson(retrievedJsonListOfLocations, listType);
        locations = databaseHelper.GetAllVLocations(TABLE_NAME, new String[]{DISPLAY_TEXT});

        if (retrievedJsonListOfLocations == null || retrievedList.size() == 1 || retrievedList.size() < locations.size()) {
            locations = databaseHelper.GetAllVLocations(TABLE_NAME, new String[]{DISPLAY_TEXT});
        } else {
            locations = new Gson().fromJson(retrievedJsonListOfLocations, listType);
        }
    }




    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.location_item,
                parent,
                false);

        return new ViewHolder(root);
    }

    @Override
    public void onBindViewHolder(FavoritesAdapter.ViewHolder holder, int position) {
            holder.locationTextView.setText(locations.get(position));
}

    @Override
    public int getItemCount() {
        return locations.size();
    }

    public void swapCursor(final Cursor cursor) {
        this.cursor = cursor;
        this.notifyDataSetChanged();
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(locations, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(locations, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);

        Gson gson = new Gson();

        String jsonListOfLocations = gson.toJson(locations);
        saveToSharedPreferences(key, jsonListOfLocations);

    }

    @Override
    public void onItemDismiss(int position) {
        databaseHelper = new DatabaseHelper(getAppContext());
        SQLiteDatabase db = databaseHelper.getWritableDatabase();


        locations.remove(position);


        Gson gson = new Gson();
        String jsonListOfLocations = gson.toJson(locations);
        saveToSharedPreferences(key, jsonListOfLocations);

        cursor.moveToPosition(position);

        String deletePosition = cursor.getString(0);
        db.delete(TABLE_NAME, ID  + " = ?", new String[]{deletePosition});


        notifyItemRemoved(position);


    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            ItemTouchHelperViewHolder {
        private TextView locationTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            locationTextView = (TextView) itemView.findViewById(R.id.locationTextView);
        }

        @Override
        public void onClick(View v) {

        }

        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY);
        }

        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(0);
        }
    }
    public void saveToSharedPreferences(String key, String json){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getAppContext());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, json);
        editor.apply();
    }
    public String readFromSharedPreferences(String key){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getAppContext());
        String string = preferences.getString(key, null);
        return string;
    }

}
