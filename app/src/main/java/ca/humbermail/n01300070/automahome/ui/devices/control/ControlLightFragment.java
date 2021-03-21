package ca.humbermail.n01300070.automahome.ui.devices.control;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Map;

import ca.humbermail.n01300070.automahome.R;
import ca.humbermail.n01300070.automahome.data.DeviceDataPaths;
import ca.humbermail.n01300070.automahome.data.RealtimeDatabaseDataSource;
import top.defaults.colorpicker.ColorObserver;
import top.defaults.colorpicker.ColorPickerView;

public class ControlLightFragment extends Fragment {
	private Context context;
	private ControlDevicesActivity controlDevicesActivity;
	private String deviceId;
	
	private TextInputLayout textInputLayout;
	private TextInputEditText inputEditText;
	private ColorPickerView colourPickerView;
	private View colourPreview;
	
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
		
		textInputLayout = root.findViewById(R.id.textInputLayout_controlLight);
		inputEditText = root.findViewById(R.id.inputEditText_controlLight);
		colourPickerView = root.findViewById(R.id.colourPicker_controlLight);
		colourPreview = root.findViewById(R.id.colourPreview_controlLight);
		
		setOnDatabaseColourChangeListener();
		
		return root;
	}
	
	private void setOnDatabaseColourChangeListener() {
		Log.d("ControlLight", "setOnColourChangeListener called");
		
		realtimeDatabaseDataSource.onDeviceDataValueChange(deviceId, null, true).observe(getViewLifecycleOwner(), new Observer<Object>() {
			@Override
			public void onChanged(Object object) {
				int colour;
				int intensityRed;
				int intensityGreen;
				int intensityBlue;
				
				if (object instanceof Map) {
					Map<String, Long> deviceData = (Map<String, Long>) object;
					intensityRed = deviceData.get(DeviceDataPaths.INTENSITY_RED_LED).intValue();
					intensityGreen = deviceData.get(DeviceDataPaths.INTENSITY_GREEN_LED).intValue();
					intensityBlue = deviceData.get(DeviceDataPaths.INTENSITY_BLUE_LED).intValue();
				} else {
					Log.d("ControlLight", "Device data values object is null");
					intensityRed = 0;
					intensityGreen = 0;
					intensityBlue = 0;
				}
				
				colour = Color.rgb(intensityRed, intensityGreen, intensityBlue);
				
				colourPreview.setBackground(new ColorDrawable(colour));
				colourPickerView.setInitialColor(colour);
			}
		});
	}
}