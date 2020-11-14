package ca.humbermail.n01300070.automahome.components;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewItemDivider extends RecyclerView.ItemDecoration {
	private final int innerPaddingSize;
	private final int categoryPaddingSize;
	private final int outerPaddingSize;
	
	public RecyclerViewItemDivider(int innerPaddingSize, int categoryPaddingSize, int outerPaddingSize) {
		this.innerPaddingSize = innerPaddingSize;
		this.categoryPaddingSize = categoryPaddingSize;
		this.outerPaddingSize = outerPaddingSize;
	}
	
	@Override
	public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
							   @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
		
		int viewPosition = parent.getChildAdapterPosition(view);
		int numItems = parent.getAdapter().getItemCount();
		int gridColumns = ((GridLayoutManager) parent.getLayoutManager()).getSpanCount();
		int numLastRowItems = numItems % gridColumns;
		if (numLastRowItems == 0) {
			numLastRowItems = gridColumns;
		}
		
		outRect.top = innerPaddingSize;
		if (viewPosition >= numItems - numLastRowItems) {
			outRect.bottom = categoryPaddingSize;
		}
		if ((viewPosition+1) % gridColumns > 0) {
			outRect.right = innerPaddingSize;
		}
		else {
			outRect.right = outerPaddingSize;
		}
		if ((viewPosition+1) % gridColumns == gridColumns-1) {
			outRect.left = outerPaddingSize;
		}
	}
}
