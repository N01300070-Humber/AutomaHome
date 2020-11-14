package ca.humbermail.n01300070.automahome.ui.favorites;

import android.content.Context;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Random;

import ca.humbermail.n01300070.automahome.R;
import ca.humbermail.n01300070.automahome.components.DeviceOrTaskButtonRecyclerViewAdapter;
import ca.humbermail.n01300070.automahome.components.NonScrollingGridLayoutManager;
import ca.humbermail.n01300070.automahome.components.RecyclerViewItemDivider;
import ca.humbermail.n01300070.automahome.data.model.CategoryData;
import ca.humbermail.n01300070.automahome.data.model.DeviceOrTaskData;

public class FavoritesViewModel extends ViewModel {
	
	public FavoritesViewModel() {
	
	}
	
	public ArrayList<DeviceOrTaskData> getDeviceAndTaskDataList() {
		int numItems;
		ArrayList<DeviceOrTaskData> deviceAndTaskDataList;
		
		numItems = 0; // TODO: Get number of items from device storage or database
		deviceAndTaskDataList = new ArrayList<>(numItems);
		
		// TODO: Set deviceAndTaskDataList data from device storage or database
		
		return deviceAndTaskDataList;
	}
	
	// TODO: Remove placeholder data generator function
	public static ArrayList<CategoryData> generatePlaceholderCategoryDataList(Context context, View.OnClickListener onClickListener) {
		Random random = new Random();
		
		int numCategories = random.nextInt(6) + 3;
		ArrayList<CategoryData> categoryDataList = new ArrayList<>(numCategories);
		
		for (int i = 0; i < numCategories; i++) {
			CategoryData categoryData = new CategoryData();
			
			categoryData.setHeaderText("Category " + i);
			categoryData.setHeaderTextAppearance(R.style.TextAppearance_MaterialComponents_Headline6);
			categoryData.setHeaderSidePadding((int) context.getResources().getDimension(R.dimen.activity_horizontal_margin));
			
			int numItems = random.nextInt(10) + 1;
			int numDevices = random.nextInt(numItems + 1) + 1;
			categoryData.setLayoutManager(new NonScrollingGridLayoutManager(context, 2));
			categoryData.setViewAdapter(new DeviceOrTaskButtonRecyclerViewAdapter( context,
					generatePlaceholderDeviceAndTaskDataList(context,
							numItems - numDevices, numDevices),
					onClickListener ));
			categoryData.setItemDecoration(new RecyclerViewItemDivider(
					(int) context.getResources().getDimension(R.dimen.recycler_divider_space),
					(int) context.getResources().getDimension(R.dimen.category_divider_space),
					(int) context.getResources().getDimension(R.dimen.activity_horizontal_margin) ));
			
			categoryDataList.add(categoryData);
		}
		
		return categoryDataList;
	}
	
	// TODO: Remove placeholder data generator function
	public static ArrayList<DeviceOrTaskData> generatePlaceholderDeviceAndTaskDataList(Context context, int numTasks, int numDevices) {
		ArrayList<DeviceOrTaskData> deviceOrTaskDataList = new ArrayList<>(numTasks + numDevices);
		
		for (int i = 0; i < numTasks; i++) {
			DeviceOrTaskData taskData = new DeviceOrTaskData(
					DeviceOrTaskData.TYPE_TASK,
					"Task Name",
					"Note",
					ContextCompat.getDrawable(context, R.drawable.ic_task),
					context.getString(R.string.content_description_type_task),
					context.getColor(R.color.primary_200)
			);
			
			deviceOrTaskDataList.add(taskData);
		}
		
		for (int i = 0; i < numDevices; i++) {
			DeviceOrTaskData deviceData = new DeviceOrTaskData(
					DeviceOrTaskData.TYPE_DEVICE,
					"Device Name",
					"Room",
					ContextCompat.getDrawable(context, R.drawable.ic_devices),
					context.getString(R.string.content_description_type_device),
					context.getColor(R.color.accent_200)
			);
			
			deviceOrTaskDataList.add(deviceData);
		}
		
		return deviceOrTaskDataList;
	}
}