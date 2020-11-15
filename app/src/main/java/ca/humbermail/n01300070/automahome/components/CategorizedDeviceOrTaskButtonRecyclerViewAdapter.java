package ca.humbermail.n01300070.automahome.components;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ca.humbermail.n01300070.automahome.data.model.CategoryData;
import ca.humbermail.n01300070.automahome.data.model.DeviceOrTaskData;

public class CategorizedDeviceOrTaskButtonRecyclerViewAdapter extends RecyclerView.Adapter<CategorizedDeviceOrTaskButtonRecyclerViewAdapter.CategoryViewHolder> {
	
	private final Context context;
	private final ArrayList<CategoryData> categoryDataList;
	
	/**
	 * ViewHolder for categories
	 */
	public static class CategoryViewHolder extends RecyclerView.ViewHolder {
		CategoryView categoryView;
		
		public CategoryViewHolder(CategoryView categoryView) {
			super(categoryView);
			this.categoryView = categoryView;
		}
	}
	
	public CategorizedDeviceOrTaskButtonRecyclerViewAdapter(Context context, ArrayList<CategoryData> categoryDataList) {
		this.context = context;
		this.categoryDataList = categoryDataList;
	}
	
	@NonNull
	@Override
	public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		CategoryView view = new CategoryView(parent.getContext());
		CategoryViewHolder holder = new CategoryViewHolder(view);
		
		view.setLayoutParams(new ViewGroup.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT
		));
		view.setOrientation(LinearLayout.VERTICAL);
		
		return holder;
	}
	
	@Override
	public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
		CategoryData data = categoryDataList.get(position);
		
		holder.categoryView.setHeaderText(data.getHeaderText());
		holder.categoryView.setHeaderTextAppearance(data.getHeaderTextAppearance());
		
		if (!holder.categoryView.initialized) {
			holder.categoryView.setHeaderPadding(data.getHeaderSidePadding());
			holder.categoryView.setRecyclerViewLayoutManager(data.getLayoutManager());
			holder.categoryView.setRecyclerViewAdapter(data.getViewAdapter());
			holder.categoryView.addRecyclerViewItemDecoration(data.getItemDecoration());
			holder.categoryView.initialized = true;
		}
	}
	
	@Override
	public int getItemCount() {
		return categoryDataList.size();
	}
	
	
	public void addCategory(int categoryPosition, CategoryData categoryData) {
		categoryDataList.add(categoryPosition, categoryData);
		notifyItemInserted(categoryPosition);
	}
	
	public void removeCategory(int categoryPosition) {
		categoryDataList.remove(categoryPosition);
		notifyItemRemoved(categoryPosition);
	}
	
	public CategoryData getCategoryData(int categoryPosition) {
		return categoryDataList.get(categoryPosition);
	}
	
	public void setCategoryData(int categoryPosition, CategoryData categoryData) {
		categoryDataList.set(categoryPosition, categoryData);
		notifyItemChanged(categoryPosition);
	}
	
	
	public void addCategoryItem(int categoryPosition, int itemPosition, DeviceOrTaskData deviceOrTaskData) {
		categoryDataList.get(categoryPosition).getViewAdapter().addItem(itemPosition, deviceOrTaskData);
		notifyItemChanged(categoryPosition);
	}
	
	public void removeCategoryItem(int categoryPosition, int itemPosition) {
		categoryDataList.get(categoryPosition).getViewAdapter().removeItem(itemPosition);
		notifyItemChanged(categoryPosition);
	}
	
	public DeviceOrTaskData getCategoryItemData(int categoryPosition, int itemPosition) {
		return categoryDataList.get(categoryPosition).getViewAdapter().getItemData(itemPosition);
	}
	
	public void setCategoryItemData(int categoryPosition, int itemPosition, DeviceOrTaskData deviceOrTaskData) {
		categoryDataList.get(categoryPosition).getViewAdapter().setItemData(itemPosition, deviceOrTaskData);
		notifyItemChanged(categoryPosition);
	}
	
	public void setAllExtraTextVisible(boolean visible) {
		for (CategoryData categoryData : categoryDataList) {
			categoryData.getViewAdapter().setAllExtraTextVisible(visible);
		}
		notifyDataSetChanged();
	}
}