package ca.humbermail.n01300070.automahome.ui.favorites;

import android.content.Context;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import ca.humbermail.n01300070.automahome.R;
import ca.humbermail.n01300070.automahome.components.DeviceOrTaskButtonRecyclerViewAdapter;
import ca.humbermail.n01300070.automahome.components.NonScrollingGridLayoutManager;
import ca.humbermail.n01300070.automahome.components.RecyclerViewItemDivider;
import ca.humbermail.n01300070.automahome.data.model.CategoryData;
import ca.humbermail.n01300070.automahome.data.model.Device;
import ca.humbermail.n01300070.automahome.data.model.DeviceOrTaskButtonData;
import ca.humbermail.n01300070.automahome.data.model.Task;

public class FavoritesViewModel extends ViewModel {
	private TreeMap<String, ArrayList<DeviceOrTaskButtonData>> deviceButtonCategoryMap;
	private TreeMap<String, ArrayList<DeviceOrTaskButtonData>> taskButtonCategoryMap;
	
	public FavoritesViewModel() {
		deviceButtonCategoryMap = new TreeMap<>();
		taskButtonCategoryMap = new TreeMap<>();
	}
	
	private Set<String> getCategories() {
		Set<String> categories = new TreeSet<>();
		
		categories.addAll(deviceButtonCategoryMap.keySet());
		categories.addAll(taskButtonCategoryMap.keySet());
		
		return categories;
	}
	
	public ArrayList<CategoryData> getCategoryDataList(Context context, View.OnClickListener onClickListener) {
		ArrayList<CategoryData> categoryDataList = new ArrayList<>();
		
		for (String categoryName : getCategories()) {
			CategoryData categoryData = new CategoryData();
			ArrayList<DeviceOrTaskButtonData> categoryContents = new ArrayList<>();
			
			categoryData.setHeaderText(categoryName);
			categoryData.setHeaderTextAppearance(R.style.TextAppearance_MaterialComponents_Headline6);
			categoryData.setHeaderSidePadding((int) context.getResources().getDimension(R.dimen.activity_horizontal_margin));
			categoryData.setItemDecoration(new RecyclerViewItemDivider(
					(int) context.getResources().getDimension(R.dimen.recycler_divider_space),
					(int) context.getResources().getDimension(R.dimen.category_divider_space),
					(int) context.getResources().getDimension(R.dimen.activity_horizontal_margin)));
			
			if (taskButtonCategoryMap.containsKey(categoryName)) {
				categoryContents.addAll(taskButtonCategoryMap.get(categoryName));
			}
			if (deviceButtonCategoryMap.containsKey(categoryName)) {
				categoryContents.addAll(deviceButtonCategoryMap.get(categoryName));
			}
			
			categoryData.setLayoutManager(new NonScrollingGridLayoutManager(context, 2));
			categoryData.setViewAdapter(new DeviceOrTaskButtonRecyclerViewAdapter(context, categoryContents, onClickListener));
			
			categoryDataList.add(categoryData);
		}
		
		return categoryDataList;
	}
	
	public void setDeviceData(Context context, List<Device> devices) {
		deviceButtonCategoryMap.clear();
		
		for (Device device : devices) {
			String categoryName = device.getCategory();
			
			if (!categoryName.isEmpty()) {
				if (!deviceButtonCategoryMap.containsKey(categoryName)) {
					deviceButtonCategoryMap.put(categoryName, new ArrayList<DeviceOrTaskButtonData>());
				}
				
				DeviceOrTaskButtonData deviceData = new DeviceOrTaskButtonData(
						DeviceOrTaskButtonData.TYPE_DEVICE,
						device.getId(),
						device.getName(),
						device.getRoom(),
						device.getCategory()
				);
				switch (device.getType()) {
					case DeviceOrTaskButtonData.DEVICE_LIGHTS:
						deviceData.setIcon(ContextCompat.getDrawable(context, R.drawable.ic_device_lights));
						deviceData.setContentDescription(context.getString(R.string.content_description_device_type_lights));
						break;
					case DeviceOrTaskButtonData.DEVICE_MOVEMENT_SENSOR:
						deviceData.setIcon(ContextCompat.getDrawable(context, R.drawable.ic_device_movement_sensor));
						deviceData.setContentDescription(context.getString(R.string.content_description_device_type_movement_sensor));
						break;
					case DeviceOrTaskButtonData.DEVICE_THERMOSTAT:
						deviceData.setIcon(ContextCompat.getDrawable(context, R.drawable.ic_device_thermostat));
						deviceData.setContentDescription(context.getString(R.string.content_description_device_type_thermostat));
						break;
					default:
						deviceData.setIcon(ContextCompat.getDrawable(context, R.drawable.ic_devices));
						deviceData.setContentDescription(context.getString(R.string.content_description_device_type_unknown));
				}
				deviceData.setDeviceType(device.getType());
				deviceData.setBackgroundColour(context.getColor(R.color.device_button_default));
				
				deviceButtonCategoryMap.get(categoryName).add(deviceData);
			}
		}
	}
	
	public void setTaskData(Context context, List<Task> tasks) {
		taskButtonCategoryMap.clear();
		
		for (Task task : tasks) {
			String categoryName = task.getCategory();
			
			if (!categoryName.isEmpty()) {
				if (!taskButtonCategoryMap.containsKey(categoryName)) {
					taskButtonCategoryMap.put(categoryName, new ArrayList<DeviceOrTaskButtonData>());
				}

				DeviceOrTaskButtonData taskData = new DeviceOrTaskButtonData(
						DeviceOrTaskButtonData.TYPE_TASK,
						task.getId(),
						task.getName(),
						task.getNote(),
						task.getCategory(),
						ContextCompat.getDrawable(context, R.drawable.ic_task),
						context.getString(R.string.content_description_type_task),
						context.getColor(R.color.task_button_default)
				);
				
				taskButtonCategoryMap.get(categoryName).add(taskData);
			}
		}
	}
}