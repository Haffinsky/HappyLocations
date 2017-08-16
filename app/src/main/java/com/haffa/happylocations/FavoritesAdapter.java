package com.haffa.happylocations;

import android.content.ContentResolver;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.haffa.happylocations.Data.DatabaseHelper;
import com.haffa.happylocations.GestureHandler.ItemTouchHelperAdapter;
import com.haffa.happylocations.GestureHandler.ItemTouchHelperViewHolder;
import com.haffa.happylocations.GestureHandler.OnListChangedListener;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static com.haffa.happylocations.Data.DatabaseHelper.DISPLAY_TEXT;
import static com.haffa.happylocations.Data.DatabaseHelper.ID;
import static com.haffa.happylocations.Data.DatabaseHelper.LATITUDE;
import static com.haffa.happylocations.Data.DatabaseHelper.LONGITUDE;
import static com.haffa.happylocations.Data.DatabaseHelper.TABLE_NAME;
import static com.haffa.happylocations.Data.LocationContentProvider.CONTENT_AUTHORITY;
import static com.haffa.happylocations.Utilities.RetriveMyApplicationContext.getAppContext;

/**
 * Created by Rafal on 8/14/2017.
 */

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.ViewHolder>  implements ItemTouchHelperAdapter {

    Cursor cursor;
    Set<String> savedSet;
    Set<String> retrievedSet = new HashSet<>();
    private ArrayList<String> locations;
    OnListChangedListener onListChangedListener;
    String key = "key";
    ArrayList<String> retrievedList = new ArrayList<>();
    ContentResolver resolver = getAppContext().getContentResolver();
    Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY + "/locations");
    String[] projection = {DISPLAY_TEXT, LATITUDE, LONGITUDE};
    DatabaseHelper databaseHelper = new DatabaseHelper(getAppContext());

    public FavoritesAdapter(){

        locations = databaseHelper.GetAllVLocations(TABLE_NAME, new String[]{DISPLAY_TEXT});

        if (locations.size() != retrievedList.size()) {
            retrievedList = new ArrayList<>(locations);
        }  else {
           // retrievedList = tinyDB.getListString(key);
            Log.v("RETRIEVING LITS", "ATYY");
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
            holder.locationTextView.setText(retrievedList.get(position));
}

    @Override
    public int getItemCount() {
        return retrievedList.size();
    }

    public void swapCursor(final Cursor cursor) {
        this.cursor = cursor;
        this.notifyDataSetChanged();
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {

        String prev = retrievedList.remove(fromPosition);
        retrievedList.add(toPosition > fromPosition ? toPosition - 1 : toPosition, prev);

        notifyItemMoved(fromPosition, toPosition);
        Gson gson = new Gson();

        Log.v("The first entry is", retrievedList.get(0));
        Log.v("The second entry is", retrievedList.get(1));

        //TODO: do the magic logic here, save it as json in shared prefs and read in the onCreate

        String jsonListOfLocations = gson.toJson(retrievedList);

        Log.v("THE LIST IS ", jsonListOfLocations);


       /* databaseHelper = new DatabaseHelper(getAppContext());
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        notifyItemMoved(fromPosition, toPosition);

        cursor.moveToPosition(fromPosition);

        Log.v("FROM POSITION ", String.valueOf(fromPosition));
        Log.v("TO POSITION ", String.valueOf(toPosition));

        fromValues.put(DISPLAY_TEXT, cursor.getString(1));
        fromValues.put(LONGITUDE, cursor.getString(2));
        fromValues.put(LATITUDE, cursor.getString(3));

        String fromID = cursor.getString(0);

        cursor.moveToPosition(toPosition);

        String toID = cursor.getString(0);

        toValues.put(DISPLAY_TEXT, cursor.getString(1));
        toValues.put(LONGITUDE, cursor.getString(2));
        toValues.put(LATITUDE, cursor.getString(3));



        db.update(TABLE_NAME, fromValues, ID + " = ?", new String[]{toID});
        db.update(TABLE_NAME, toValues, ID + " = ?", new String[]{fromID});*/
    }

    @Override
    public void onItemDismiss(int position) {
        databaseHelper = new DatabaseHelper(getAppContext());
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        cursor.moveToPosition(position);
        String deletePosition = cursor.getString(0);
        db.delete(TABLE_NAME, ID  + " = ?", new String[]{deletePosition});


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

}
