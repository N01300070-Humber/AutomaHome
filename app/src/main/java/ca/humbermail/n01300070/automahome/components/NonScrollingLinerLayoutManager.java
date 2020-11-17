package ca.humbermail.n01300070.automahome.components;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;

public class NonScrollingLinerLayoutManager extends LinearLayoutManager {
	
	public NonScrollingLinerLayoutManager(Context context) {
		super(context);
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
