package ca.humbermail.n01300070.automahome.components;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewCategoryPadding extends RecyclerView.ItemDecoration {
	private final int outerPaddingSize;
	
	public RecyclerViewCategoryPadding(int outerPaddingSize) {
		this.outerPaddingSize = outerPaddingSize;
	}
	
	@Override
	public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
							   @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
		
		int viewPosition = parent.getChildAdapterPosition(view);
		int numItems = parent.getAdapter().getItemCount();
		
		if (viewPosition == 0) {
			outRect.top = outerPaddingSize;
		}
		else {
			if (viewPosition == numItems-1) {
				outRect.bottom = outerPaddingSize;
			}
		}
	}
	
}
