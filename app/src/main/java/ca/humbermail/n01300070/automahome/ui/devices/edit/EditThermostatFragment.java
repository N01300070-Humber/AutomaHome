package ca.humbermail.n01300070.automahome.ui.devices.edit;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;

import ca.humbermail.n01300070.automahome.R;
import ca.humbermail.n01300070.automahome.components.NumberPickerView;

public class EditThermostatFragment extends Fragment {
	private Context context;
	
	private TextView currentTemperatureTextView;
	private NumberPickerView targetTemperatureNumberPickerView;
	private MaterialButton heatingButton;
	private MaterialButton coolingButton;
	
	public EditThermostatFragment() {
		// Required empty public constructor
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View root = inflater.inflate(R.layout.fragment_edit_device_thermostat, container, false);
		context = getActivity().getApplicationContext();
		
		currentTemperatureTextView = root.findViewById(R.id.textView_editThermostat_currentTemp);
		targetTemperatureNumberPickerView = root.findViewById(R.id.numberPickerView_editThermostat_targetTemp);
		heatingButton = root.findViewById(R.id.toggleButton_editThermostat_heating);
		coolingButton = root.findViewById(R.id.toggleButton_editThermostat_cooling);
		
		//TODO replace hardcoded values for actual readings
		currentTemperatureTextView.setText(23 + getString(R.string.degrees_celsius));
		targetTemperatureNumberPickerView.setNumber(28);
		targetTemperatureNumberPickerView.setSuffixText(getString(R.string.degrees_celsius));
		
		return root;
	}
	
}
