package ca.humbermail.n01300070.automahome.ui.devices.control;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.google.firebase.database.ServerValue;

import java.util.Map;

import ca.humbermail.n01300070.automahome.R;
import ca.humbermail.n01300070.automahome.components.ColourPickerView;
import ca.humbermail.n01300070.automahome.data.DeviceDataPaths;
import ca.humbermail.n01300070.automahome.data.RealtimeDatabaseDataSource;

public class ControlLightFragment extends Fragment {
	private Context context;
	private ControlDevicesActivity controlDevicesActivity;
	private String deviceId;
	
	private ColourPickerView colourPickerView;
	
	private RealtimeDatabaseDataSource realtimeDatabaseDataSource;
	
	public ControlLightFragment() {
		// Required empty public constructor
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View root = inflater.inflate(R.layout.fragment_control_light, container, false);
		context = getActivity().getApplicationContext();
		controlDevicesActivity = (ControlDevicesActivity) getActivity();
		
		deviceId = controlDevicesActivity.getDeviceId();
		
		realtimeDatabaseDataSource = controlDevicesActivity.getRealtimeDatabaseDataSource();
		
		colourPickerView = root.findViewById(R.id.colourPickerView_controlLight);
		
		realtimeDatabaseDataSource.onDeviceDataValueChange(deviceId, null, true).observe(getViewLifecycleOwner(), new Observer<Object>() {
			@Override
			public void onChanged(Object object) {
				OnDatabaseColourChanged(object);
			}
		});
		colourPickerView.addOnNumberChangeListener(new ColourPickerView.OnColourChangeListener() {
			@Override
			public void onColourChanged(ColourPickerView colourPickerView, int colour, boolean fromTextBox, boolean fromUser, boolean shouldPropagate) {
				OnColourViewPickerColourChanged(colour, fromTextBox, fromUser, shouldPropagate);
			}
		});
		
		return root;
	}
	
	private void OnDatabaseColourChanged(Object object) {
		int colour;
				int intensityRed;
				int intensityGreen;
				int intensityBlue;
				
				if (object instanceof Map) {
					Map<String, Object> deviceData = (Map<String, Object>) object;
					Object value;
					
					value = deviceData.get(DeviceDataPaths.LIGHT_INTENSITY_RED);
					if (value instanceof Long) {
						intensityRed = ((Long) value).intValue();
					} else {
						Log.d("ControlLight", "Device data red intensity value is null");
						intensityRed = 0;
					}
					
					value = deviceData.get(DeviceDataPaths.LIGHT_INTENSITY_GREEN);
					if (value instanceof Long) {
						intensityGreen = ((Long) value).intValue();
					} else {
						Log.d("ControlLight", "Device data green intensity value is null");
						intensityGreen = 0;
					}
					
					value = deviceData.get(DeviceDataPaths.LIGHT_INTENSITY_BLUE);
					if (value instanceof Long) {
						intensityBlue = ((Long) value).intValue();
					} else {
						Log.d("ControlLight", "Device data blue intensity value is null");
						intensityBlue = 0;
					}
				} else {
					Log.d("ControlLight", "Device data values object is null");
					intensityRed = 0;
					intensityGreen = 0;
					intensityBlue = 0;
				}
				
				colour = Color.rgb(intensityRed, intensityGreen, intensityBlue);
				
				colourPickerView.setColour(colour);
	}
	
	private void OnColourViewPickerColourChanged(int colour, boolean fromTextBox, boolean fromUser, boolean shouldPropagate) {
		if (fromUser) {
			if (shouldPropagate) {
				setDatabaseColour(colour);
			}
			
			if (fromTextBox) {
				((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE))
						.hideSoftInputFromWindow(
								controlDevicesActivity.getCurrentFocus().getWindowToken(),
								InputMethodManager.HIDE_NOT_ALWAYS
						);
				colourPickerView.clearFocus();
			}
		}
	}
	
	private void setDatabaseColour(int colour) {
		realtimeDatabaseDataSource.setDeviceData(deviceId, DeviceDataPaths.LIGHT_INTENSITY_RED, (colour & 0x00FF0000) >> 16);
		realtimeDatabaseDataSource.setDeviceData(deviceId, DeviceDataPaths.LIGHT_INTENSITY_GREEN, (colour & 0x0000FF00) >> 8 );
		realtimeDatabaseDataSource.setDeviceData(deviceId, DeviceDataPaths.LIGHT_INTENSITY_BLUE, colour & 0x000000FF);
		realtimeDatabaseDataSource.setDeviceData(deviceId, DeviceDataPaths.LIGHT_TIMESTAMP, ServerValue.TIMESTAMP);
	}
}