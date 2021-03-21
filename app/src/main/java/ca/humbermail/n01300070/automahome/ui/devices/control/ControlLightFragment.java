package ca.humbermail.n01300070.automahome.ui.devices.control;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.ServerValue;

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
		setOnColourViewSelectionChangeListener();
		setOnInputEditTextEditorActionListener();
		
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
					intensityRed = deviceData.get(DeviceDataPaths.LIGHT_INTENSITY_RED).intValue();
					intensityGreen = deviceData.get(DeviceDataPaths.LIGHT_INTENSITY_GREEN).intValue();
					intensityBlue = deviceData.get(DeviceDataPaths.LIGHT_INTENSITY_BLUE).intValue();
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
	
	private void setOnColourViewSelectionChangeListener() {
		colourPickerView.subscribe(new ColorObserver() {
			@Override
			public void onColor(int colour, boolean fromUser, boolean shouldPropagate) {
				inputEditText.setText(String.format("%06X", (0xFFFFFF & colour)));
				
				if (fromUser) {
					colourPreview.setBackground(new ColorDrawable(colour));
					
					if (shouldPropagate) {
						setDatabaseColour(colour);
					}
				}
			}
		});
	}
	
	private void setOnInputEditTextEditorActionListener() {
		inputEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_DONE) {
					int colour;
					StringBuilder hexString;
					
					hexString = new StringBuilder(textView.getText().toString());
					if (hexString.length() < 6) {
						while (hexString.length() < 6) {
							hexString.append('0');
						}
						textView.setText(hexString.toString());
					}
					hexString.insert(0,'#');
					colour = Color.parseColor(hexString.toString());
					
					colourPreview.setBackground(new ColorDrawable(colour));
					colourPickerView.setInitialColor(colour);
					setDatabaseColour(colour);
					
					((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE))
							.hideSoftInputFromWindow(
									controlDevicesActivity.getCurrentFocus().getWindowToken(),
									InputMethodManager.HIDE_NOT_ALWAYS
							);
					textInputLayout.clearFocus();
					return true;
				}
				return false;
			}
		});
	}
	
	private void setDatabaseColour(int colour) {
		realtimeDatabaseDataSource.setDeviceData(deviceId, DeviceDataPaths.LIGHT_INTENSITY_RED, (colour & 0x00FF0000) >> 16);
		realtimeDatabaseDataSource.setDeviceData(deviceId, DeviceDataPaths.LIGHT_INTENSITY_GREEN, (colour & 0x0000FF00) >> 8 );
		realtimeDatabaseDataSource.setDeviceData(deviceId, DeviceDataPaths.LIGHT_INTENSITY_BLUE, colour & 0x000000FF);
		realtimeDatabaseDataSource.setDeviceData(deviceId, DeviceDataPaths.LIGHT_TIMESTAMP, ServerValue.TIMESTAMP);
	}
}