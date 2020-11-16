package ca.humbermail.n01300070.automahome.components;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ListLinePadding extends RecyclerView.ItemDecoration {

    private int paddingSize;

    public ListLinePadding(int paddingSize)
    {
        this.paddingSize = paddingSize;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        outRect.bottom = paddingSize;
        outRect.top = paddingSize;
    }
}
