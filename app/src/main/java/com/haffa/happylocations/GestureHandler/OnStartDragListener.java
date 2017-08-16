package com.haffa.happylocations.GestureHandler;

import android.support.v7.widget.RecyclerView;

/**
 * Created by Rafal on 8/16/2017.
 */

public interface OnStartDragListener {
    /**
     * Called when a view is requesting a start of a drag.
     *
     * @param viewHolder The holder of the view to drag.
     */
    void onStartDrag(RecyclerView.ViewHolder viewHolder);
}