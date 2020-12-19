package ca.humbermail.n01300070.automahome.ui.devices.edit;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

import ca.humbermail.n01300070.automahome.R;
import ca.humbermail.n01300070.automahome.components.FavoriteSelectView;
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
	private Spinner roomSpinner;
	private Spinner roomSpinner2;
	private TextView roomLocationHeader;
	private TextView roomLocationHeader2;
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
		roomSpinner = findViewById(R.id.spinner_editDevice);
		roomSpinner2 = findViewById(R.id.spinner_editDevice_2);
		roomLocationHeader = findViewById(R.id.textView_deviceLocation_editDevice);
		roomLocationHeader2 = findViewById(R.id.textView_editDevice_deviceLocation2);
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
				roomLocationHeader.setText(getString(R.string.side_device_location, "A"));
				roomLocationHeader2.setText(getString(R.string.side_device_location, "B"));
				roomLocationHeader2.setVisibility(View.VISIBLE);
				roomSpinner2.setVisibility(View.VISIBLE);
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
		getSupportFragmentManager().beginTransaction().add(R.id.fragment_editDevice, fragment).commit();
	}
	
	public void discardButtonClicked(View view) {
		Log.d("EditDevicesActivity","The deviceId is "+deviceId);
		realtimeDatabaseDataSource.removeDevice(deviceId);
		Toast.makeText(getApplicationContext(),"Device deleted",Toast.LENGTH_SHORT).show();
		finish();
	}

//    public void discardButtonClicked(View view) {
//        //TODO data handling
//        setResult(Activity.RESULT_CANCELED);
//        finish();
//    }
	
	public void saveButtonClicked(View view) {
		Log.d("EditDevicesActivity","The deviceId is "+deviceId);
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
		realtimeDatabaseDataSource.updateDeviceRoom(deviceId,roomSpinner.getSelectedItem().toString());
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
}