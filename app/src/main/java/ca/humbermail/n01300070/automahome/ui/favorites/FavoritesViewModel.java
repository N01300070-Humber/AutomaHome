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
import ca.humbermail.n01300070.automahome.data.model.DeviceOrTaskButtonData;

public class FavoritesViewModel extends ViewModel {
	
	public FavoritesViewModel() {
	
	}
	
	public ArrayList<DeviceOrTaskButtonData> getDeviceAndTaskDataList() {
		int numItems;
		ArrayList<DeviceOrTaskButtonData> deviceAndTaskDataList;
		
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
			
			categoryData.setHeaderText("Category " + (i + 1));
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
	public static ArrayList<DeviceOrTaskButtonData> generatePlaceholderDeviceAndTaskDataList(Context context, int numTasks, int numDevices) {
		Random random = new Random();
		ArrayList<DeviceOrTaskButtonData> deviceOrTaskDataList = new ArrayList<>(numTasks + numDevices);
		
		for (int i = 0; i < numTasks; i++) {
			DeviceOrTaskButtonData taskData = new DeviceOrTaskButtonData(
					DeviceOrTaskButtonData.TYPE_TASK,
					"Task Name",
					"Note",
					ContextCompat.getDrawable(context, R.drawable.ic_task),
					context.getString(R.string.content_description_type_task),
					context.getColor(R.color.task_button_default)
			);
			
			deviceOrTaskDataList.add(taskData);
		}
		
		
		int numLights = random.nextInt(numDevices);
		for (int i = 0; i < numLights; i++) {
			DeviceOrTaskButtonData deviceData = new DeviceOrTaskButtonData(
					DeviceOrTaskButtonData.TYPE_DEVICE,
					"Light Name",
					"Room",
					ContextCompat.getDrawable(context, R.drawable.ic_devices),
					context.getString(R.string.content_description_type_device),
					context.getColor(R.color.device_button_default)
			);
			deviceData.setDeviceType(DeviceOrTaskButtonData.DEVICE_LIGHTS);
			
			deviceOrTaskDataList.add(deviceData);
		}
		
		int numSensors = random.nextInt(numDevices - numLights);
		for (int i = 0; i < numSensors; i++) {
			DeviceOrTaskButtonData deviceData = new DeviceOrTaskButtonData(
					DeviceOrTaskButtonData.TYPE_DEVICE,
					"Movement Sensor Name",
					"Room",
					ContextCompat.getDrawable(context, R.drawable.ic_devices),
					context.getString(R.string.content_description_type_device),
					context.getColor(R.color.accent_200)
			);
			deviceData.setDeviceType(DeviceOrTaskButtonData.DEVICE_MOVEMENT_SENSOR);
			
			deviceOrTaskDataList.add(deviceData);
		}
		
		for (int i = 0; i < numDevices - numLights - numSensors; i++) {
			DeviceOrTaskButtonData deviceData = new DeviceOrTaskButtonData(
					DeviceOrTaskButtonData.TYPE_DEVICE,
					"Thermostat Name",
					"Room",
					ContextCompat.getDrawable(context, R.drawable.ic_devices),
					context.getString(R.string.content_description_type_device),
					context.getColor(R.color.accent_200)
			);
			deviceData.setDeviceType(DeviceOrTaskButtonData.DEVICE_THERMOSTAT);
			
			deviceOrTaskDataList.add(deviceData);
		}
		
		return deviceOrTaskDataList;
	}
}