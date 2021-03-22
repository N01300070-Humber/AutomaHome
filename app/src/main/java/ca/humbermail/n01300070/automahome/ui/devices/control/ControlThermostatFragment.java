package ca.humbermail.n01300070.automahome.ui.devices.control;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.firebase.database.ServerValue;

import java.text.DecimalFormat;
import java.util.Map;

import ca.humbermail.n01300070.automahome.R;
import ca.humbermail.n01300070.automahome.data.DeviceDataPaths;
import ca.humbermail.n01300070.automahome.data.PreferenceKeys;
import ca.humbermail.n01300070.automahome.data.RealtimeDatabaseDataSource;
import ca.humbermail.n01300070.automahome.utils.Convert;

public class ControlThermostatFragment extends Fragment {
	private Context context;
	private ControlDevicesActivity controlDevicesActivity;
	private String deviceId;
	
	private TextView textViewTemperature;
	private TextView textViewHumidity;
	private MaterialButtonToggleGroup heatingCoolingButtonToggleGroup;
	private MaterialButton heatingButton;
	private MaterialButton coolingButton;
	private MaterialButtonToggleGroup fanButtonToggleGroup;
	private MaterialButton fanButton;
	
	private RealtimeDatabaseDataSource realtimeDatabaseDataSource;
	private SharedPreferences settingsPreferences;
	
	public ControlThermostatFragment() {
		// Required empty public constructor
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View root = inflater.inflate(R.layout.fragment_control_thermostat, container, false);
		context = getActivity().getApplicationContext();
		controlDevicesActivity = (ControlDevicesActivity) getActivity();
		deviceId = controlDevicesActivity.getDeviceId();
		
		textViewTemperature = root.findViewById(R.id.textView_temperature_controlThermostat);
		textViewHumidity = root.findViewById(R.id.textView_humidity_controlThermostat);
		heatingCoolingButtonToggleGroup = root.findViewById(R.id.buttonToggleGroup_heating_cooling_controlThermostat);
		fanButtonToggleGroup = root.findViewById(R.id.buttonToggleGroup_fan_controlThermostat);
		heatingButton = root.findViewById(R.id.toggleButton_heating_controlThermostat);
		coolingButton = root.findViewById(R.id.toggleButton_cooling_controlThermostat);
		fanButton = root.findViewById(R.id.toggleButton_fan_controlThermostat);
		
		settingsPreferences = context.getSharedPreferences(PreferenceKeys.KEY_SETTINGS, Context.MODE_PRIVATE);
		realtimeDatabaseDataSource = controlDevicesActivity.getRealtimeDatabaseDataSource();
		
		realtimeDatabaseDataSource.onDeviceDataValueChange(deviceId, DeviceDataPaths.THERMOSTAT_TEMPERATURE).observe(getViewLifecycleOwner(), new Observer<Object>() {
			@Override
			public void onChanged(Object object) {
				onTemperatureChanged(object);
			}
		});
		realtimeDatabaseDataSource.onDeviceDataValueChange(deviceId, DeviceDataPaths.THERMOSTAT_HUMIDITY).observe(getViewLifecycleOwner(), new Observer<Object>() {
			@Override
			public void onChanged(Object object) {
				onHumidityChanged(object);
			}
		});
		realtimeDatabaseDataSource.onDeviceDataValueChange(deviceId, null).observe(getViewLifecycleOwner(), new Observer<Object>() {
			@Override
			public void onChanged(Object object) {
				onHVACChanged(object);
			}
		});
		heatingButton.addOnCheckedChangeListener(new MaterialButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(MaterialButton button, boolean isChecked) {
				onHeatingButtonCheckedChanged(button, isChecked);
			}
		});
		coolingButton.addOnCheckedChangeListener(new MaterialButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(MaterialButton button, boolean isChecked) {
				onCoolingButtonCheckedChanged(button, isChecked);
			}
		});
		fanButton.addOnCheckedChangeListener(new MaterialButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(MaterialButton button, boolean isChecked) {
				onFanButtonCheckedChanged(button, isChecked);
			}
		});
		
		return root;
	}
	
	private void onTemperatureChanged(Object object) {
		Log.d("ControlThermostat", "Device data temperature value changed");
		String temperatureUnit = settingsPreferences.getString(PreferenceKeys.KEY_SETTINGS_TEMPERATURE_UNIT, PreferenceKeys.VALUE_SETTINGS_TEMPERATURE_UNIT_CELSIUS);
		double temperature;
		DecimalFormat decimalFormat = new DecimalFormat("0");
		decimalFormat.setMaximumFractionDigits(3);
		
		if (object instanceof Double || object instanceof Long) {
			if (object instanceof Long) {
				temperature = ((Long) object).floatValue();
			} else {
				temperature = (Double) object;
			}
			
			switch (temperatureUnit) {
				case PreferenceKeys.VALUE_SETTINGS_TEMPERATURE_UNIT_CELSIUS:
					textViewTemperature.setText(getString(R.string.display_temperature,
							decimalFormat.format(temperature),
							getText(R.string.degrees_celsius)
					));
					break;
				case PreferenceKeys.VALUE_SETTINGS_TEMPERATURE_UNIT_FAHRENHEIT:
					textViewTemperature.setText(getString(R.string.display_temperature,
							decimalFormat.format(Convert.celsiusToFahrenheit(temperature)),
							getText(R.string.degrees_fahrenheit)
					));
					break;
			}
		} else {
			Log.e("ControlThermostat", "Received temperature is not a number");
			textViewTemperature.setText(getString(R.string.error));
		}
	}
	
	private void onHumidityChanged(Object object) {
		Log.d("ControlThermostat", "Device data humidity value changed");
		double humidity;
		DecimalFormat decimalFormat = new DecimalFormat("0%");
		decimalFormat.setMaximumFractionDigits(3);
		
		if (object instanceof Double || object instanceof Long) {
			if (object instanceof Long) {
				humidity = ((Long) object).floatValue();
			} else {
				humidity = (Double) object;
			}
			humidity /= 100;
			
			textViewHumidity.setText(decimalFormat.format(humidity));
		} else {
			Log.e("ControlThermostat", "Received humidity is not a number");
			textViewHumidity.setText(getString(R.string.error));
		}
	}
	
	private void onHVACChanged(Object object) {
		Log.d("EditThermostat", "Device data values changed");
		boolean compressor;
		boolean reverseValve;
		boolean fan;
		
		if (object instanceof Map) {
			Map<String, Object> deviceData = (Map<String, Object>) object;
			Object value;
			
			value = deviceData.get(DeviceDataPaths.THERMOSTAT_COMPRESSOR);
			if (value instanceof Boolean) {
				compressor = (Boolean) value;
			} else {
				Log.d("EditThermostat", "Device data compressor value is null");
				compressor = false;
			}
			
			value = deviceData.get(DeviceDataPaths.THERMOSTAT_REVERSE_VALVE);
			if (value instanceof Boolean) {
				reverseValve = (Boolean) value;
			} else {
				Log.d("EditThermostat", "Device data reverseValve value is null");
				reverseValve = false;
			}
			
			value = deviceData.get(DeviceDataPaths.THERMOSTAT_FAN);
			if (value instanceof Boolean) {
				fan = (Boolean) value;
			} else {
				Log.d("EditThermostat", "Device data fan value is null");
				fan = false;
			}
		} else {
			Log.d("EditThermostat", "Device data values object is null");
			compressor = false;
			reverseValve = false;
			fan = false;
		}
		
		fanButton.setChecked(fan);
		if (compressor) {
			if (reverseValve) {
				heatingButton.setChecked(true);
			} else {
				coolingButton.setChecked(true);
			}
		} else {
			heatingButton.setChecked(false);
			coolingButton.setChecked(false);
		}
	}
	
	private void onHeatingButtonCheckedChanged(MaterialButton button, boolean isChecked) {
		if (isChecked) {
			realtimeDatabaseDataSource.setDeviceData(deviceId, DeviceDataPaths.THERMOSTAT_COMPRESSOR, true);
			realtimeDatabaseDataSource.setDeviceData(deviceId, DeviceDataPaths.THERMOSTAT_REVERSE_VALVE, true);
			setDatabaseTimestamp();
			fanButton.setChecked(true);
		} else {
			realtimeDatabaseDataSource.setDeviceData(deviceId, DeviceDataPaths.THERMOSTAT_REVERSE_VALVE, false);
			setDatabaseTimestamp();
			if (!coolingButton.isChecked()) {
				realtimeDatabaseDataSource.setDeviceData(deviceId, DeviceDataPaths.THERMOSTAT_COMPRESSOR, false);
				fanButton.setChecked(false);
			}
		}
	}
	
	private void onCoolingButtonCheckedChanged(MaterialButton button, boolean isChecked) {
		if (isChecked) {
			realtimeDatabaseDataSource.setDeviceData(deviceId, DeviceDataPaths.THERMOSTAT_COMPRESSOR, true);
			setDatabaseTimestamp();
			fanButton.setChecked(true);
		} else if (!heatingButton.isChecked()) {
			realtimeDatabaseDataSource.setDeviceData(deviceId, DeviceDataPaths.THERMOSTAT_COMPRESSOR, false);
			setDatabaseTimestamp();
			fanButton.setChecked(false);
		}
	}
	
	private void onFanButtonCheckedChanged(MaterialButton button, boolean isChecked) {
		realtimeDatabaseDataSource.setDeviceData(deviceId, DeviceDataPaths.THERMOSTAT_FAN, isChecked);
		setDatabaseTimestamp();
		if (!isChecked) {
			if (heatingButton.isChecked()) {
				heatingButton.setChecked(false);
			} else if (coolingButton.isChecked()) {
				coolingButton.setChecked(false);
			}
		}
	}
	
	private void setDatabaseTimestamp() {
		realtimeDatabaseDataSource.setDeviceData(deviceId, DeviceDataPaths.THERMOSTAT_TIMESTAMP, ServerValue.TIMESTAMP);
	}
}
