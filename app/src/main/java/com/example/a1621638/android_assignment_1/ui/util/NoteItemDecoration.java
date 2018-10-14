package com.example.a1621638.android_assignment_1.ui.util;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/*
    I have taken this class from StackOverflow.
    Link: https://stackoverflow.com/questions/28531996/android-recyclerview-gridlayoutmanager-column-spacing
    This is simply to create padding between the different elements of the RecyclerView.
    I have modified it slightly to look the way I want it to.
 */
public class NoteItemDecoration extends RecyclerView.ItemDecoration {
    private int space;

    public NoteItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {
        outRect.bottom = space;

        int position = parent.getChildLayoutPosition(view);

        // Add top margin only for the first item and second item to avoid double space between items
        if (position == 0 || position == 1) {
            outRect.top = space;
        } else {
            outRect.top = 0;
        }

        if (position % 2 == 0) {
            outRect.left = space;
            outRect.right = space / 2;
        } else {
            outRect.left = space / 2;
            outRect.right = space;
        }
    }
}