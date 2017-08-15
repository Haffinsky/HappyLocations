package com.haffa.happylocations;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.haffa.happylocations.GestureHandler.SimpleItemTouchHelperCallback;

import java.util.ArrayList;
import java.util.Set;

import static com.haffa.happylocations.Data.DatabaseHelper.DISPLAY_TEXT;
import static com.haffa.happylocations.Data.DatabaseHelper.LATITUDE;
import static com.haffa.happylocations.Data.DatabaseHelper.LONGITUDE;
import static com.haffa.happylocations.Data.LocationContentProvider.CONTENT_AUTHORITY;
import static com.haffa.happylocations.Utilities.RetriveMyApplicationContext.getAppContext;


public class FavoritesFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    RecyclerView recyclerView;
    FavoritesAdapter adapter;
    static final int LOCATION_LOADER = 1;
    Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY + "/locations");
    String[] projection = {DISPLAY_TEXT, LATITUDE, LONGITUDE};
    private ItemTouchHelper itemTouchHelper;
    public FavoritesFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getLoaderManager().initLoader(LOCATION_LOADER, null, this);


        View rootView =  inflater.inflate(R.layout.fragment_favorites, container, false);

        adapter = new FavoritesAdapter();
        recyclerView = (RecyclerView) rootView.findViewById(R.id.favorites_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getAppContext()));
        recyclerView.setAdapter(adapter);

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
        itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        return rootView;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case LOCATION_LOADER:
                return new CursorLoader(
                        getActivity(),
                        BASE_CONTENT_URI,
                        projection,
                        null,
                        null,
                        null
                );
            default:
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader loader) {
        adapter.swapCursor(null);
    }
}
