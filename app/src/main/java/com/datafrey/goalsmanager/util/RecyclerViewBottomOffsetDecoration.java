package com.datafrey.goalsmanager.util;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewBottomOffsetDecoration extends RecyclerView.ItemDecoration {

    private int bottomOffset;

    public RecyclerViewBottomOffsetDecoration(int bottomOffset) {
        this.bottomOffset = bottomOffset;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        int dataSize = state.getItemCount();
        int position = parent.getChildAdapterPosition(view);

        if (dataSize > 0 && position == dataSize - 1) {
            outRect.set(0, 0, 0, bottomOffset);
        } else {
            outRect.set(0, 0, 0, 0);
        }
    }
}
