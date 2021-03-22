package ca.humbermail.n01300070.automahome.ui.devices.edit;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.google.firebase.database.ServerValue;

import ca.humbermail.n01300070.automahome.R;
import ca.humbermail.n01300070.automahome.components.NumberPickerView;
import ca.humbermail.n01300070.automahome.data.DeviceDataPaths;
import ca.humbermail.n01300070.automahome.data.PreferenceKeys;
import ca.humbermail.n01300070.automahome.data.RealtimeDatabaseDataSource;
import ca.humbermail.n01300070.automahome.utils.Convert;

public class EditThermostatFragment extends Fragment {
	private Context context;
	private EditDevicesActivity editDevicesActivity;
	private String deviceId;
	
	private NumberPickerView targetTemperatureNumberPicker;
	private NumberPickerView temperatureRangeNumberPicker;
	
	private RealtimeDatabaseDataSource realtimeDatabaseDataSource;
	private SharedPreferences settingsPreferences;
	
	public EditThermostatFragment() {
		// Required empty public constructor
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View root = inflater.inflate(R.layout.fragment_edit_device_thermostat, container, false);
		context = getActivity().getApplicationContext();
		editDevicesActivity = (EditDevicesActivity) getActivity();
		deviceId = editDevicesActivity.getDeviceId();
		
		settingsPreferences = context.getSharedPreferences(PreferenceKeys.KEY_SETTINGS, Context.MODE_PRIVATE);
		realtimeDatabaseDataSource = editDevicesActivity.getRealtimeDatabaseDataSource();
		
		targetTemperatureNumberPicker = root.findViewById(R.id.numberPickerView_targetTemperature_editThermostat);
		temperatureRangeNumberPicker = root.findViewById(R.id.numberPicker_temperatureRange_editThermostat);
		
		realtimeDatabaseDataSource.onDeviceDataValueChange(deviceId, DeviceDataPaths.THERMOSTAT_TARGET_TEMPERATURE, true).observe(getViewLifecycleOwner(), new Observer<Object>() {
			@Override
			public void onChanged(Object object) {
				onTargetTemperatureChange(object);
			}
		});
		realtimeDatabaseDataSource.onDeviceDataValueChange(deviceId, DeviceDataPaths.THERMOSTAT_TEMPERATURE_RANGE, true).observe(getViewLifecycleOwner(), new Observer<Object>() {
			@Override
			public void onChanged(Object object) {
				onTemperatureRangeChange(object);
			}
		});
		targetTemperatureNumberPicker.addOnNumberChangeListener(new NumberPickerView.OnNumberChangeListener() {
			@Override
			public void onNumberChanged(NumberPickerView numberPickerView, float number, boolean fromKeyboard) {
				onTargetTemperatureNumberPickerViewNumberChanged(numberPickerView, number, fromKeyboard);
			}
		});
		temperatureRangeNumberPicker.addOnNumberChangeListener(new NumberPickerView.OnNumberChangeListener() {
			@Override
			public void onNumberChanged(NumberPickerView numberPickerView, float number, boolean fromKeyboard) {
				onTemperatureRangeNumberPickerViewNumberChange(numberPickerView, number, fromKeyboard);
			}
		});
		
		return root;
	}
	
	
	private void onTargetTemperatureChange(Object object) {
		Log.d("EditThermostat", "Device data target temperature value changed");
		String temperatureUnit = settingsPreferences.getString(PreferenceKeys.KEY_SETTINGS_TEMPERATURE_UNIT, PreferenceKeys.VALUE_SETTINGS_TEMPERATURE_UNIT_CELSIUS);
		double temperature;
		
		if (object instanceof Double || object instanceof Long) {
			if (object instanceof Long) {
				temperature = ((Long) object).floatValue();
			} else {
				temperature = (Double) object;
			}
		} else {
			Log.d("EditThermostat", "Received target temperature is not a number");
			temperature = getResources().getInteger(R.integer.defaultTemperature);
			realtimeDatabaseDataSource.setDeviceData(deviceId, DeviceDataPaths.THERMOSTAT_TARGET_TEMPERATURE, temperature);
			setDatabaseTimestamp();
		}
		
		TypedValue numberInterval = new TypedValue();
		switch (temperatureUnit) {
			case PreferenceKeys.VALUE_SETTINGS_TEMPERATURE_UNIT_FAHRENHEIT:
				targetTemperatureNumberPicker.setNumber((float) Convert.celsiusToFahrenheit(temperature));
				targetTemperatureNumberPicker.setSuffixText(getText(R.string.degrees_fahrenheit).toString());
				getResources().getValue(R.dimen.defaultNumberIntervalFahrenheit, numberInterval, true);
				break;
			case PreferenceKeys.VALUE_SETTINGS_TEMPERATURE_UNIT_CELSIUS:
			default:
				targetTemperatureNumberPicker.setNumber((float) temperature);
				targetTemperatureNumberPicker.setSuffixText(getText(R.string.degrees_celsius).toString());
				getResources().getValue(R.dimen.defaultNumberIntervalCelsius, numberInterval, true);
		}
		targetTemperatureNumberPicker.setInterval(numberInterval.getFloat());
	}
	
	private void onTemperatureRangeChange(Object object) {
		Log.d("EditThermostat", "Device data temperature range value changed");
		String temperatureUnit = settingsPreferences.getString(PreferenceKeys.KEY_SETTINGS_TEMPERATURE_UNIT, PreferenceKeys.VALUE_SETTINGS_TEMPERATURE_UNIT_CELSIUS);
		double temperature;
		
		if (object instanceof Double || object instanceof Long) {
			if (object instanceof Long) {
				temperature = ((Long) object).floatValue();
			} else {
				temperature = (Double) object;
			}
		} else {
			Log.d("EditThermostat", "Received temperature range is not a number");
			temperature = getResources().getInteger(R.integer.defaultTemperatureRange);
			realtimeDatabaseDataSource.setDeviceData(deviceId, DeviceDataPaths.THERMOSTAT_TEMPERATURE_RANGE, temperature);
			setDatabaseTimestamp();
		}
		
		TypedValue numberInterval = new TypedValue();
		switch (temperatureUnit) {
			case PreferenceKeys.VALUE_SETTINGS_TEMPERATURE_UNIT_FAHRENHEIT:
				temperatureRangeNumberPicker.setNumber((float) Convert.celsiusToFahrenheit(temperature));
				temperatureRangeNumberPicker.setSuffixText(getText(R.string.degrees_fahrenheit).toString());
				getResources().getValue(R.dimen.defaultNumberIntervalFahrenheit, numberInterval, true);
				break;
			case PreferenceKeys.VALUE_SETTINGS_TEMPERATURE_UNIT_CELSIUS:
			default:
				temperatureRangeNumberPicker.setNumber((float) temperature);
				temperatureRangeNumberPicker.setSuffixText(getText(R.string.degrees_celsius).toString());
				getResources().getValue(R.dimen.defaultNumberIntervalCelsius, numberInterval, true);
		}
		temperatureRangeNumberPicker.setInterval(numberInterval.getFloat());
	}
	
	private void onTargetTemperatureNumberPickerViewNumberChanged(NumberPickerView numberPickerView, float number, boolean fromKeyboard) {
		String temperatureUnit = settingsPreferences.getString(PreferenceKeys.KEY_SETTINGS_TEMPERATURE_UNIT, PreferenceKeys.VALUE_SETTINGS_TEMPERATURE_UNIT_CELSIUS);
		double temperature;
		
		switch (temperatureUnit) {
			case PreferenceKeys.VALUE_SETTINGS_TEMPERATURE_UNIT_FAHRENHEIT:
				temperature = Convert.fahrenheitToCelsius(number);
				break;
			case PreferenceKeys.VALUE_SETTINGS_TEMPERATURE_UNIT_CELSIUS:
			default:
				temperature = number;
		}
		
		realtimeDatabaseDataSource.setDeviceData(deviceId, DeviceDataPaths.THERMOSTAT_TARGET_TEMPERATURE, temperature);
		setDatabaseTimestamp();
		
		if (fromKeyboard) {
			((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE))
					.hideSoftInputFromWindow(
							editDevicesActivity.getCurrentFocus().getWindowToken(),
							InputMethodManager.HIDE_NOT_ALWAYS
					);
			numberPickerView.clearFocus();
		}
	}
	
	private void onTemperatureRangeNumberPickerViewNumberChange(NumberPickerView numberPickerView, float number, boolean fromKeyboard) {
		String temperatureUnit = settingsPreferences.getString(PreferenceKeys.KEY_SETTINGS_TEMPERATURE_UNIT, PreferenceKeys.VALUE_SETTINGS_TEMPERATURE_UNIT_CELSIUS);
		double temperature;
		
		switch (temperatureUnit) {
			case PreferenceKeys.VALUE_SETTINGS_TEMPERATURE_UNIT_FAHRENHEIT:
				temperature = Convert.fahrenheitToCelsius(number);
				break;
			case PreferenceKeys.VALUE_SETTINGS_TEMPERATURE_UNIT_CELSIUS:
			default:
				temperature = number;
		}
		
		realtimeDatabaseDataSource.setDeviceData(deviceId, DeviceDataPaths.THERMOSTAT_TEMPERATURE_RANGE, temperature);
		setDatabaseTimestamp();
		
		if (fromKeyboard) {
			((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE))
					.hideSoftInputFromWindow(
							editDevicesActivity.getCurrentFocus().getWindowToken(),
							InputMethodManager.HIDE_NOT_ALWAYS
					);
			numberPickerView.clearFocus();
		}
	}
	
	private void setDatabaseTimestamp() {
		realtimeDatabaseDataSource.setDeviceData(deviceId, DeviceDataPaths.THERMOSTAT_TIMESTAMP, ServerValue.TIMESTAMP);
	}
}
