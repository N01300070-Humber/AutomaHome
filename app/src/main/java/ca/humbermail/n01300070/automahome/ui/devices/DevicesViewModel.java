package ca.humbermail.n01300070.automahome.ui.devices;

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

public class DevicesViewModel extends ViewModel {
	
	public DevicesViewModel() {
	
	}
	
	// TODO: Replace placeholder data generator function with one that gets real data
	public ArrayList<CategoryData> generatePlaceholderCategorizedDeviceDataList(Context context, View.OnClickListener onClickListener) {
		Random random = new Random();
		
		String[] headers = {"Lights", "Sensors", "Heating / Cooling"};
		String[] deviceNames = {"Name of Light", "Name of Movement Sensor", "Name of Thermostat"};
		String[] deviceTypes = {DeviceOrTaskButtonData.DEVICE_LIGHTS,
				DeviceOrTaskButtonData.DEVICE_MOVEMENT_SENSOR, DeviceOrTaskButtonData.DEVICE_THERMOSTAT};
		ArrayList<CategoryData> categoryDataList = new ArrayList<>(headers.length);
		
		for (int i = 0; i < headers.length; i++) {
			CategoryData categoryData = new CategoryData();
			
			categoryData.setHeaderText(headers[i]);
			categoryData.setHeaderTextAppearance(R.style.TextAppearance_MaterialComponents_Headline6);
			categoryData.setHeaderSidePadding((int) context.getResources().getDimension(R.dimen.activity_horizontal_margin));
			
			int numDevices = random.nextInt(8) + 1;
			categoryData.setLayoutManager(new NonScrollingGridLayoutManager(context, 2));
			categoryData.setViewAdapter(new DeviceOrTaskButtonRecyclerViewAdapter( context,
					generatePlaceholderDeviceDataList(context, numDevices, deviceNames[i], deviceTypes[i]), onClickListener ));
			categoryData.setItemDecoration(new RecyclerViewItemDivider(
					(int) context.getResources().getDimension(R.dimen.recycler_divider_space),
					(int) context.getResources().getDimension(R.dimen.category_divider_space),
					(int) context.getResources().getDimension(R.dimen.activity_horizontal_margin) ));
			
			categoryDataList.add(categoryData);
		}
		
		return categoryDataList;
	}
	
	// TODO: Remove placeholder data generator function
	public ArrayList<DeviceOrTaskButtonData> generatePlaceholderDeviceDataList(Context context, int numDevices, String deviceName, String deviceType) {
		ArrayList<DeviceOrTaskButtonData> deviceDataList = new ArrayList<>(numDevices);
		
		for (int i = 0; i < numDevices; i++) {
			DeviceOrTaskButtonData deviceData = new DeviceOrTaskButtonData(
					DeviceOrTaskButtonData.TYPE_DEVICE,
					deviceName,
					"Room",
					ContextCompat.getDrawable(context, R.drawable.ic_devices),
					context.getString(R.string.content_description_type_device),
					context.getColor(R.color.accent_200)
			);
			deviceData.setDeviceType(deviceType);
			
			deviceDataList.add(deviceData);
		}
		
		return deviceDataList;
	}
}