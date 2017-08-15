package com.haffa.happylocations;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.haffa.happylocations.Data.DatabaseHelper;
import com.haffa.happylocations.GestureHandler.ItemTouchHelperAdapter;
import com.haffa.happylocations.GestureHandler.ItemTouchHelperViewHolder;



import static com.haffa.happylocations.Data.DatabaseHelper.ID;
import static com.haffa.happylocations.Data.DatabaseHelper.TABLE_NAME;
import static com.haffa.happylocations.Utilities.RetriveMyApplicationContext.getAppContext;

/**
 * Created by Rafal on 8/14/2017.
 */

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.ViewHolder>  implements ItemTouchHelperAdapter {

    Cursor cursor;
    DatabaseHelper databaseHelper;

    public FavoritesAdapter(){
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
            cursor.moveToPosition(position);
            holder.locationTextView.setText(cursor.getString(0));
}

    @Override
    public int getItemCount() {
        if (cursor == null) {
            return 0;
        } else return cursor.getCount();
    }
    public void swapCursor(final Cursor cursor) {
        this.cursor = cursor;
        this.notifyDataSetChanged();
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        //TODO: add item position swapping
    }

    @Override
    public void onItemDismiss(int position) {
        databaseHelper = new DatabaseHelper(getAppContext());
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        db.delete(TABLE_NAME, ID  + " = ?", new String[]{String.valueOf(position) + 1});
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
