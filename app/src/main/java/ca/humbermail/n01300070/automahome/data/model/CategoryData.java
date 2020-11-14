package ca.humbermail.n01300070.automahome.data.model;

import androidx.recyclerview.widget.RecyclerView;

import ca.humbermail.n01300070.automahome.components.DeviceOrTaskButtonRecyclerViewAdapter;

public class CategoryData {
	private String headerText;
	private int headerTextAppearance;
	private int headerSidePadding;
	private RecyclerView.LayoutManager layoutManager;
	private DeviceOrTaskButtonRecyclerViewAdapter viewAdapter;
	private RecyclerView.ItemDecoration itemDecoration;
	
	public String getHeaderText() {
		return headerText;
	}
	
	public void setHeaderText(String headerText) {
		this.headerText = headerText;
	}
	
	public int getHeaderTextAppearance() {
		return headerTextAppearance;
	}
	
	public void setHeaderTextAppearance(int headerTextAppearance) {
		this.headerTextAppearance = headerTextAppearance;
	}
	
	public int getHeaderSidePadding() {
		return headerSidePadding;
	}
	
	public void setHeaderSidePadding(int headerSidePadding) {
		this.headerSidePadding = headerSidePadding;
	}
	
	public RecyclerView.LayoutManager getLayoutManager() {
		return layoutManager;
	}
	
	public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
		this.layoutManager = layoutManager;
	}
	
	public DeviceOrTaskButtonRecyclerViewAdapter getViewAdapter() {
		return viewAdapter;
	}
	
	public void setViewAdapter(DeviceOrTaskButtonRecyclerViewAdapter viewAdapter) {
		this.viewAdapter = viewAdapter;
	}
	
	public RecyclerView.ItemDecoration getItemDecoration() {
		return itemDecoration;
	}
	
	public void setItemDecoration(RecyclerView.ItemDecoration itemDecoration) {
		this.itemDecoration = itemDecoration;
	}
}
