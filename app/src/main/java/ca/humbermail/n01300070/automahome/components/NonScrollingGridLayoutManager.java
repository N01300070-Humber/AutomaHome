package ca.humbermail.n01300070.automahome.components;

import android.content.Context;

import androidx.recyclerview.widget.GridLayoutManager;

public class NonScrollingGridLayoutManager extends GridLayoutManager {
	
	public NonScrollingGridLayoutManager(Context context, int spanCount) {
		super(context, spanCount);
	}
	
	@Override
	public boolean canScrollVertically() {
		return false;
	}
	
	@Override
	public boolean canScrollHorizontally() {
		return false;
	}
}
