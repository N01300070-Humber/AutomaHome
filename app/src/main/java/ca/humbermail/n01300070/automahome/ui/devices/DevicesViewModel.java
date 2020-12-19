package ca.humbermail.n01300070.automahome.ui.devices;

import android.content.Context;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import ca.humbermail.n01300070.automahome.R;
import ca.humbermail.n01300070.automahome.components.DeviceOrTaskButtonRecyclerViewAdapter;
import ca.humbermail.n01300070.automahome.components.NonScrollingGridLayoutManager;
import ca.humbermail.n01300070.automahome.components.RecyclerViewItemDivider;
import ca.humbermail.n01300070.automahome.data.model.CategoryData;
import ca.humbermail.n01300070.automahome.data.model.Device;
import ca.humbermail.n01300070.automahome.data.model.DeviceOrTaskButtonData;

public class DevicesViewModel extends ViewModel {
	private static final String[] HEADERS = {"Lights", "Sensors", "Heating / Cooling", "Unknown"};
	private final MutableLiveData<String> mText;

	public DevicesViewModel() {
		mText = new MutableLiveData<>();
		mText.setValue("This is the device fragment");
	}

	public LiveData<String> getText() { return mText; }

	public ArrayList<CategoryData> getCategorizedDeviceDataList(Context context, List<Device> devices, View.OnClickListener onClickListener)  {
		ArrayList<ArrayList<DeviceOrTaskButtonData>> categoryItemDataList = getCategoryItemDataList(context, devices);
		ArrayList<CategoryData> categoryDataList = new ArrayList<>(HEADERS.length);

		for (int i = 0; i < HEADERS.length; i++) {
			CategoryData categoryData = new CategoryData();
			categoryData.setHeaderText(HEADERS[i]);
			categoryData.setHeaderTextAppearance(R.style.TextAppearance_MaterialComponents_Headline6);
			categoryData.setHeaderSidePadding((int) context.getResources().getDimension(R.dimen.activity_horizontal_margin));

			categoryData.setLayoutManager(new NonScrollingGridLayoutManager(context,2));
			categoryData.setItemDecoration(new RecyclerViewItemDivider(
					(int) context.getResources().getDimension(R.dimen.recycler_divider_space),
					(int) context.getResources().getDimension(R.dimen.category_divider_space),
					(int) context.getResources().getDimension(R.dimen.activity_horizontal_margin) ));

			DeviceOrTaskButtonRecyclerViewAdapter categoryAdapter = new DeviceOrTaskButtonRecyclerViewAdapter(context,onClickListener);
			categoryAdapter.setItemDataList(categoryItemDataList.get(i));
			categoryData.setViewAdapter(categoryAdapter);

			categoryDataList.add(categoryData);
		}

		return categoryDataList;
	}

	private ArrayList<ArrayList<DeviceOrTaskButtonData>> getCategoryItemDataList(Context context, List<Device> devices)  {
		ArrayList<ArrayList<DeviceOrTaskButtonData>> categoryItemDataList = new ArrayList<>(3);
		ArrayList<DeviceOrTaskButtonData> lightsDeviceDataList = new ArrayList<>();
		ArrayList<DeviceOrTaskButtonData> sensorDeviceDataList = new ArrayList<>();
		ArrayList<DeviceOrTaskButtonData> temperatureDeviceDataList = new ArrayList<>();
		ArrayList<DeviceOrTaskButtonData> unknownDeviceDataList = new ArrayList<>();

		for (Device device : devices) {
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
					lightsDeviceDataList.add(deviceData);
					break;
				case DeviceOrTaskButtonData.DEVICE_MOVEMENT_SENSOR:
					deviceData.setIcon(ContextCompat.getDrawable(context, R.drawable.ic_device_movement_sensor));
					deviceData.setContentDescription(context.getString(R.string.content_description_device_type_movement_sensor));
					sensorDeviceDataList.add(deviceData);
					break;
				case DeviceOrTaskButtonData.DEVICE_THERMOSTAT:
					deviceData.setIcon(ContextCompat.getDrawable(context, R.drawable.ic_device_thermostat));
					deviceData.setContentDescription(context.getString(R.string.content_description_device_type_thermostat));
					temperatureDeviceDataList.add(deviceData);
					break;
				default:
					deviceData.setIcon(ContextCompat.getDrawable(context, R.drawable.ic_devices));
					deviceData.setContentDescription(context.getString(R.string.content_description_device_type_unknown));
					unknownDeviceDataList.add(deviceData);
			}
			deviceData.setDeviceType(device.getType());
			deviceData.setBackgroundColour(context.getColor(R.color.device_button_default));
		}

		categoryItemDataList.add(lightsDeviceDataList);
		categoryItemDataList.add(sensorDeviceDataList);
		categoryItemDataList.add(temperatureDeviceDataList);
		categoryItemDataList.add(unknownDeviceDataList);
		return categoryItemDataList;
	}

//	// TODO: Replace placeholder data generator function with one that gets real data
//	public ArrayList<CategoryData> generatePlaceholderCategorizedDeviceDataList(Context context, View.OnClickListener onClickListener) {
//		Random random = new Random();
//
//		String[] headers = {"Lights", "Sensors", "Heating / Cooling"};
//		String[] deviceNames = {"Name of Light", "Name of Movement Sensor", "Name of Thermostat"};
//		String[] deviceTypes = {DeviceOrTaskButtonData.DEVICE_LIGHTS,
//				DeviceOrTaskButtonData.DEVICE_MOVEMENT_SENSOR, DeviceOrTaskButtonData.DEVICE_THERMOSTAT};
//		ArrayList<CategoryData> categoryDataList = new ArrayList<>(headers.length);
//
//		for (int i = 0; i < headers.length; i++) {
//			CategoryData categoryData = new CategoryData();
//
//			categoryData.setHeaderText(headers[i]);
//			categoryData.setHeaderTextAppearance(R.style.TextAppearance_MaterialComponents_Headline6);
//			categoryData.setHeaderSidePadding((int) context.getResources().getDimension(R.dimen.activity_horizontal_margin));
//
//			int numDevices = random.nextInt(8) + 1;
//			categoryData.setLayoutManager(new NonScrollingGridLayoutManager(context, 2));
//			categoryData.setViewAdapter(new DeviceOrTaskButtonRecyclerViewAdapter( context,
//					generatePlaceholderDeviceDataList(context, numDevices, deviceNames[i], deviceTypes[i]), onClickListener ));
//			categoryData.setItemDecoration(new RecyclerViewItemDivider(
//					(int) context.getResources().getDimension(R.dimen.recycler_divider_space),
//					(int) context.getResources().getDimension(R.dimen.category_divider_space),
//					(int) context.getResources().getDimension(R.dimen.activity_horizontal_margin) ));
//
//			categoryDataList.add(categoryData);
//		}
//
//		return categoryDataList;
//	}
//
//	// TODO: Remove placeholder data generator function
//	public ArrayList<DeviceOrTaskButtonData> generatePlaceholderDeviceDataList(Context context, int numDevices, String deviceName, String deviceType) {
//		ArrayList<DeviceOrTaskButtonData> deviceDataList = new ArrayList<>(numDevices);
//
//		for (int i = 0; i < numDevices; i++) {
//			DeviceOrTaskButtonData deviceData = new DeviceOrTaskButtonData(
//					DeviceOrTaskButtonData.TYPE_DEVICE,
//					null,
//					deviceName,
//					"Room",
//					ContextCompat.getDrawable(context, R.drawable.ic_devices),
//					context.getString(R.string.content_description_type_device),
//					context.getColor(R.color.accent_200)
//			);
//			deviceData.setDeviceType(deviceType);
//
//			deviceDataList.add(deviceData);
//		}
//
//		return deviceDataList;
//	}
}