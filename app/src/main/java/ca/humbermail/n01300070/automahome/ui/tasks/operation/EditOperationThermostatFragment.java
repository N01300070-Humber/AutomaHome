package ca.humbermail.n01300070.automahome.ui.tasks.operation;

import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import java.util.ArrayList;

import ca.humbermail.n01300070.automahome.R;
import ca.humbermail.n01300070.automahome.components.NumberPickerView;
import ca.humbermail.n01300070.automahome.data.PreferenceKeys;

public class EditOperationThermostatFragment extends Fragment {
	private Context context;
	
	private AutoCompleteTextView autoCompleteTextView;
	private NumberPickerView temperatureNumberPickerView;
	
	private ArrayAdapter<String> adapter;
	SharedPreferences settings;
	SharedPreferences.Editor settingsEditor;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View root = inflater.inflate(R.layout.fragment_edit_operation_thermostat, container, false);
		context = getActivity().getApplicationContext();
		
		autoCompleteTextView = root.findViewById(R.id.autoCompleteText_editOperationThermostat_deviceSelect);
		temperatureNumberPickerView = root.findViewById(R.id.numberPickerView_editOperationThermostat_temperature);
		
		adapter = new ArrayAdapter<String>(getContext(), R.layout.text_view_auto_complete_label, generateDeviceList());
		autoCompleteTextView.setAdapter(adapter);
		temperatureNumberPickerView.setNumber(32); // TODO: Replace hardcoded number with saved temperature or default (convert if user preference is fahrenheit)


		settings = context.getSharedPreferences(PreferenceKeys.KEY_SETTINGS, Context.MODE_PRIVATE);
		settingsEditor = settings.edit();

		String kind_of_degree_default = getString(R.string.celsius);
		String kind_of_degree = settings.getString(getString(R.string.KIND_OF_DEGREE_DEFAULT), kind_of_degree_default);

		if (kind_of_degree.equals(getString(R.string.fahrenheit))) {
			temperatureNumberPickerView.setSuffixText(getString(R.string.degrees_fahrenheit));// TODO: Replace getString(resource) with unit preference from user settings
		} else if (kind_of_degree.equals(getString(R.string.celsius))) {
			temperatureNumberPickerView.setSuffixText(getString(R.string.degrees_celsius));
		}
		
		return root;
	}
	
	private ArrayList<String> generateDeviceList() {
		int numDevices = 6;
		ArrayList<String> categoryList = new ArrayList<>(numDevices);
		
		for (int i = 0; i < numDevices; i++) {
			categoryList.add("Thermostat " + (i + 1));
		}
		
		return categoryList;
	}
}