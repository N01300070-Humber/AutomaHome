package ca.humbermail.n01300070.automahome.ui.tasks.operation;

import androidx.fragment.app.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ca.humbermail.n01300070.automahome.R;
import ca.humbermail.n01300070.automahome.components.NumberPickerView;

public class EditOperationThermostatFragment extends Fragment {
	private Context context;
	
	NumberPickerView temperatureNumberPickerView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View root = inflater.inflate(R.layout.fragment_edit_operation_thermostat, container, false);
		context = getActivity().getApplicationContext();
		
		temperatureNumberPickerView = root.findViewById(R.id.numberPickerView_editOperationThermostat_temperature);
		
		temperatureNumberPickerView.setNumber(32); // TODO: Replace hardcoded number with saved temperature or default (convert if user preference is fahrenheit)
		temperatureNumberPickerView.setSuffixText(getString(R.string.degrees_celsius)); // TODO: Replace getString(resource) with unit preference from user settings
		
		return root;
	}
}