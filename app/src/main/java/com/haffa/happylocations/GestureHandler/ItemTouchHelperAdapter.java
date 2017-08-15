package com.haffa.happylocations.GestureHandler;

/**
 * Created by Rafal on 8/15/2017.
 */

public interface ItemTouchHelperAdapter {

    void onItemMove(int fromPosition, int toPosition);

    void onItemDismiss(int position);
}
