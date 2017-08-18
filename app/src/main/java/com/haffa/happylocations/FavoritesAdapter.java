package com.haffa.happylocations;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.haffa.happylocations.Data.DatabaseHelper;
import com.haffa.happylocations.GestureHandler.ItemTouchHelperAdapter;
import com.haffa.happylocations.GestureHandler.ItemTouchHelperViewHolder;


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.haffa.happylocations.Data.DatabaseHelper.DISPLAY_TEXT;
import static com.haffa.happylocations.Data.DatabaseHelper.ID;
import static com.haffa.happylocations.Data.DatabaseHelper.TABLE_NAME;
import static com.haffa.happylocations.Utilities.RetriveMyApplicationContext.getAppContext;

/**
 * Created by Rafal on 8/14/2017.
 */

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.ViewHolder> implements ItemTouchHelperAdapter {

    Cursor cursor;
    private ArrayList<String> locations;
    String key = "key";
    Type listType = new TypeToken<List<String>>() {
    }.getType();
    private ArrayList<String> retrievedList;
    DatabaseHelper databaseHelper = new DatabaseHelper(getAppContext());

    public FavoritesAdapter() {

        /*list of favorite objects retrieved from Json file
        I decided to save the array as a string because saving it
        as Set<String> did not preserve the order of items (as Sets
        don't do that*/

        String retrievedJsonListOfLocations = readFromSharedPreferences(key);

        retrievedList = new Gson().fromJson(retrievedJsonListOfLocations, listType);
        locations = databaseHelper.GetAllEntries(TABLE_NAME, new String[]{DISPLAY_TEXT});

        if (retrievedJsonListOfLocations == null || retrievedList.size() == 1 || retrievedList.size() < locations.size()) {
            /* if the list saved to shared preferences is smaller in size that the one saved to
            the database, the list from database is the current list*/
            locations = databaseHelper.GetAllEntries(TABLE_NAME, new String[]{DISPLAY_TEXT});
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

        /*this approach swaps the from and to position as well as
        ALL the positions in between */

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
        //saving the list as a json String
        String jsonListOfLocations = gson.toJson(locations);
        saveToSharedPreferences(key, jsonListOfLocations);
    }

    @Override
    public void onItemDismiss(int position) {
        databaseHelper = new DatabaseHelper(getAppContext());
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        Gson gson = new Gson();
        //removing the field from the list
        locations.remove(position);

        String jsonListOfLocations = gson.toJson(locations);
        saveToSharedPreferences(key, jsonListOfLocations);


        cursor.moveToPosition(position);

        String deletePosition = cursor.getString(0);
        //removing the field from the db
        db.delete(TABLE_NAME, ID + " = ?", new String[]{deletePosition});

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

    public void saveToSharedPreferences(String key, String json) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getAppContext());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, json);
        editor.apply();
    }

    public String readFromSharedPreferences(String key) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getAppContext());
        String string = preferences.getString(key, null);
        return string;
    }
}
