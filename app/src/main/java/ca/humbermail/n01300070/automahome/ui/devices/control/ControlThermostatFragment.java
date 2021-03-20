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

import java.text.DecimalFormat;
import java.util.Locale;

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
		
		settingsPreferences = context.getSharedPreferences(PreferenceKeys.KEY_SETTINGS, Context.MODE_PRIVATE);
		
		deviceId = controlDevicesActivity.getDeviceId();
		
		realtimeDatabaseDataSource = controlDevicesActivity.getRealtimeDatabaseDataSource();
		
		textViewTemperature = root.findViewById(R.id.textView_control_thermostat_temperature);
		textViewHumidity = root.findViewById(R.id.textView_control_thermostat_humidity);
		
		setOnTemperatureChangeListener();
		setOnHumidityChangeListener();
		
		return root;
	}
	
	private void setOnTemperatureChangeListener() {
		Log.d("ControlThermostat", "setOnTemperatureChangeListener called");
		
		realtimeDatabaseDataSource.onDeviceDataValueChange(deviceId, DeviceDataPaths.THERMOSTAT_TEMPERATURE).observe(getViewLifecycleOwner(), new Observer<Object>() {
			@Override
			public void onChanged(Object object) {
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
		});
	}
	
	private void setOnHumidityChangeListener() {
		Log.d("ControlThermostat", "setOnHumidityChangeListener called");
		
		realtimeDatabaseDataSource.onDeviceDataValueChange(deviceId, DeviceDataPaths.THERMOSTAT_HUMIDITY).observe(getViewLifecycleOwner(), new Observer<Object>() {
			@Override
			public void onChanged(Object object) {
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
		});
	}
}
