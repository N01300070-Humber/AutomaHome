package ca.humbermail.n01300070.automahome.ui.devices.control;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import ca.humbermail.n01300070.automahome.R;
import ca.humbermail.n01300070.automahome.data.RealtimeDatabaseDataSource;
import ca.humbermail.n01300070.automahome.data.model.DeviceOrTaskButtonData;
import ca.humbermail.n01300070.automahome.ui.CustomActivity;
import ca.humbermail.n01300070.automahome.ui.devices.edit.EditDevicesActivity;

public class ControlDevicesActivity extends CustomActivity {
	private Fragment fragment;
	private Button editDeviceButton;
	
	private RealtimeDatabaseDataSource realtimeDatabaseDataSource;
	
	private String deviceType;
	private String deviceId;
	private String deviceName;
	private String favoritesCategory;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_control_devices);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		editDeviceButton = findViewById(R.id.button_editDevice);
		deviceId = getIntent().getExtras().getString(EditDevicesActivity.EXTRA_DEVICE_ID);
		deviceType = getIntent().getExtras().getString(DeviceOrTaskButtonData.ARG_DEVICE);
		deviceName = getIntent().getExtras().getString(EditDevicesActivity.EXTRA_DEVICE_NAME);
		favoritesCategory = getIntent().getExtras().getString(EditDevicesActivity.EXTRA_DEVICE_CATEGORY);
		
		setRealtimeDatabaseDataSource(new RealtimeDatabaseDataSource());
		realtimeDatabaseDataSource = getRealtimeDatabaseDataSource();
		
		if (deviceType == null) {
			Toast.makeText(getApplicationContext(), "Error: failed to get device type", Toast.LENGTH_SHORT).show();
			finish();
			return;
		}
		switch (deviceType) {
			case DeviceOrTaskButtonData.DEVICE_LIGHTS:
				fragment = new ControlLightFragment();
				break;
			case DeviceOrTaskButtonData.DEVICE_MOVEMENT_SENSOR:
				fragment = new ControlMovementSensorFragment();
				break;
			case DeviceOrTaskButtonData.DEVICE_THERMOSTAT:
				fragment = new ControlThermostatFragment();
				break;
			default:
				Toast.makeText(getApplicationContext(), "Error: device type " + deviceType + " is unknown", Toast.LENGTH_SHORT).show();
				finish();
				return;
		}
		getSupportFragmentManager().beginTransaction().add(R.id.fragment_controlDevice, fragment).commit();
	}
	
	
	/**
	 * Handles back button onClick event
	 *
	 * @param item non-null MenuItem
	 * @return Boolean
	 */
	@Override
	public boolean onOptionsItemSelected(@NonNull MenuItem item) {
		finish();
		return true;
	}
	
	
	public void buttonEditDeviceClicked(View view) {
		Intent intent = new Intent(getApplicationContext(), EditDevicesActivity.class);
		intent.putExtra(EditDevicesActivity.EXTRA_DEVICE_ID, deviceId);
		intent.putExtra(DeviceOrTaskButtonData.ARG_DEVICE, deviceType);
		intent.putExtra(EditDevicesActivity.EXTRA_DEVICE_NAME, deviceName);
		intent.putExtra(EditDevicesActivity.EXTRA_DEVICE_CATEGORY, favoritesCategory);
		startActivity(intent);
	}
	
	
	public String getDeviceId() {
		return deviceId;
	}
}