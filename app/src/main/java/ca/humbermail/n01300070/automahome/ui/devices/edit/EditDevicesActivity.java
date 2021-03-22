package ca.humbermail.n01300070.automahome.ui.devices.edit;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

import ca.humbermail.n01300070.automahome.R;
import ca.humbermail.n01300070.automahome.components.FavoriteSelectView;
import ca.humbermail.n01300070.automahome.data.DeviceDataPaths;
import ca.humbermail.n01300070.automahome.data.PreferenceKeys;
import ca.humbermail.n01300070.automahome.data.RealtimeDatabaseDataSource;
import ca.humbermail.n01300070.automahome.data.model.DeviceOrTaskButtonData;
import ca.humbermail.n01300070.automahome.ui.CustomActivity;

public class EditDevicesActivity extends CustomActivity {
	public static String EXTRA_DEVICE_ID = "deviceId";
	public static String EXTRA_DEVICE_NAME = "deviceName";
	public static String EXTRA_DEVICE_CATEGORY = "category";
	
	private static final String DEFAULT_NAME = "Untitled Device";
	
	private Button saveButton;
	private Button deleteButton;
	private Fragment fragment;
	private FavoriteSelectView favoriteSelectView;
	private TextInputLayout roomTextInputLayout;
	private TextInputLayout room2TextInputLayout;
	private AutoCompleteTextView roomAutoCompleteText;
	private AutoCompleteTextView room2AutoCompleteText;
	private String deviceType;
	private TextInputEditText nameEditText;
	
	private RealtimeDatabaseDataSource realtimeDatabaseDataSource;
	private String deviceId;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_devices);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		saveButton = findViewById(R.id.button_editDevice_save);
		deleteButton = findViewById(R.id.button_editDevice_delete);
		roomTextInputLayout = findViewById(R.id.textInputLayout_deviceLocation_editDevice);
		room2TextInputLayout = findViewById(R.id.textInputLayout_deviceLocation2_editDevice);
		roomAutoCompleteText = findViewById(R.id.autoCompleteText_deviceLocation_editDevice);
		room2AutoCompleteText = findViewById(R.id.autoCompleteText_deviceLocation2_editDevice);
		favoriteSelectView = findViewById(R.id.favoriteSelectView_editDevice);
		nameEditText = findViewById(R.id.editText_editDevice);
		
		favoriteSelectView.setAutoCompleteLabels(generateCategoryList());
		
		setRealtimeDatabaseDataSource(new RealtimeDatabaseDataSource());
		realtimeDatabaseDataSource = getRealtimeDatabaseDataSource();
		realtimeDatabaseDataSource.setCurrentHomeId(
				getSharedPreferences(PreferenceKeys.KEY_SESSION, Context.MODE_PRIVATE)
						.getString(PreferenceKeys.KEY_SESSION_SELECTED_HOME, "")
		);
		
		// Get Extras (deviceType is required but other values can be null)
		Bundle bundle = getIntent().getExtras();
		deviceId = bundle.getString(EXTRA_DEVICE_ID);
		nameEditText.setText(bundle.getString(EXTRA_DEVICE_NAME));
		deviceType = bundle.getString(DeviceOrTaskButtonData.ARG_DEVICE);
		if (deviceId == null) {
			deviceId = realtimeDatabaseDataSource.addDevice(DEFAULT_NAME, deviceType, "", "");
		}
		String category = bundle.getString(EXTRA_DEVICE_CATEGORY);
		if (category != null && !category.isEmpty()) {
			favoriteSelectView.setChecked(true);
			favoriteSelectView.setText(category);
		}
		
		switch (deviceType) {
			case DeviceOrTaskButtonData.DEVICE_LIGHTS:
				fragment = new EditLightFragment();
				break;
			case DeviceOrTaskButtonData.DEVICE_MOVEMENT_SENSOR:
				roomTextInputLayout.setHint(getString(R.string.side_device_location, "A"));
				room2TextInputLayout.setHint(getString(R.string.side_device_location, "B"));
				room2TextInputLayout.setVisibility(View.VISIBLE);
				realtimeDatabaseDataSource.onDeviceDataValueChange(deviceId, DeviceDataPaths.MOVEMENT_SIDE_B, true).observe(this, new Observer<Object>() {
					@Override
					public void onChanged(Object object) {
						onRoom2Changed(object);
					}
				});
				fragment = new EditMovementSensorFragment();
				break;
			case DeviceOrTaskButtonData.DEVICE_THERMOSTAT:
				fragment = new EditThermostatFragment();
				break;
			default:
				Toast.makeText(getApplicationContext(), "Error: failed to get device type", Toast.LENGTH_SHORT).show();
				finish();
				return;
		}
		realtimeDatabaseDataSource.onDeviceValueChange(deviceId, RealtimeDatabaseDataSource.DEVICES_ROOM_PATH, true).observe(this, new Observer<Object>() {
			@Override
			public void onChanged(Object object) {
				onRoomChanged(object);
			}
		});
		
		getSupportFragmentManager().beginTransaction().add(R.id.fragment_editDevice, fragment).commit();
	}
	
	
	private void onRoomChanged(Object object) {
		if (!(object instanceof String)) {
			Log.e("EditDevicesActivity", "Device room not a string");
			return;
		}
		roomAutoCompleteText.setText((String) object);
	}
	
	private void onRoom2Changed(Object object) {
		if (!(object instanceof String)) {
			Log.e("EditDevicesActivity", "Device room not a string");
			return;
		}
		room2AutoCompleteText.setText((String) object);
	}
	
	
	public void discardButtonClicked(View view) {
		Log.d("EditDevicesActivity", "The deviceId is " + deviceId);
		realtimeDatabaseDataSource.removeDevice(deviceId);
		Toast.makeText(getApplicationContext(), "Device deleted", Toast.LENGTH_SHORT).show();
		finish();
	}
	
	public void saveButtonClicked(View view) {
		Log.d("EditDevicesActivity", "The deviceId is " + deviceId);
		saveName();
		saveFavoriteCategory();
		saveRoom();
		Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
		setResult(Activity.RESULT_OK);
		finish();
	}
	
	private void saveName() {
		String deviceName = nameEditText.getText().toString().trim();
		if (deviceName.isEmpty()) {
			deviceName = DEFAULT_NAME;
		}
		realtimeDatabaseDataSource.updateDeviceName(deviceId, deviceName);
	}
	
	private void saveFavoriteCategory() {
		if (favoriteSelectView.isChecked()) {
			realtimeDatabaseDataSource.updateDeviceCategory(deviceId, favoriteSelectView.getText());
		} else {
			realtimeDatabaseDataSource.updateDeviceCategory(deviceId, "");
		}
	}
	
	private void saveRoom() {
		realtimeDatabaseDataSource.updateDeviceRoom(deviceId, roomAutoCompleteText.getText().toString());
		if (deviceType.equals(DeviceOrTaskButtonData.DEVICE_MOVEMENT_SENSOR)) {
			realtimeDatabaseDataSource.setDeviceData(deviceId, DeviceDataPaths.MOVEMENT_SIDE_B, room2AutoCompleteText.getText().toString());
		}
	}
	
	private ArrayList<String> generateCategoryList() {
		int numCategories = 6;
		ArrayList<String> categoryList = new ArrayList<>(numCategories);
		
		for (int i = 0; i < numCategories; i++) {
			categoryList.add("Category " + (i + 1));
		}
		
		return categoryList;
	}
	
	/**
	 * Handles back button onClick event
	 *
	 * @param item non-null MenuItem
	 * @return Boolean
	 */
	@Override
	public boolean onOptionsItemSelected(@NonNull MenuItem item) {
		setResult(Activity.RESULT_CANCELED);
		finish();
		return true;
	}
	
	public String getDeviceId() {
		return deviceId;
	}
}