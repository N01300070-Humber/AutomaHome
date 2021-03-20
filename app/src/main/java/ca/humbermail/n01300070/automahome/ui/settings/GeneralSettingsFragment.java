package ca.humbermail.n01300070.automahome.ui.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;

import ca.humbermail.n01300070.automahome.R;
import ca.humbermail.n01300070.automahome.data.PreferenceKeys;
import ca.humbermail.n01300070.automahome.ui.main.NavDrawerActivity;

public class GeneralSettingsFragment extends Fragment {
	Context context;
	
	private NavDrawerActivity navDrawerActivity;
	
	MaterialButtonToggleGroup displayTempToggleGroup;
	MaterialButton celsiusButton;
	MaterialButton fahrenheitTempButton;
	Spinner themeSpinner;
	
	SharedPreferences settings;
	SharedPreferences.Editor settingsEditor;
	
	
	public GeneralSettingsFragment() {
		// Required empty public constructor
	}
	
	public static GeneralSettingsFragment newInstance() {
		return new GeneralSettingsFragment();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View root = inflater.inflate(R.layout.fragment_settings_general, container, false);
		context = requireActivity().getApplicationContext();
		
		navDrawerActivity = (NavDrawerActivity) requireActivity();
		
		displayTempToggleGroup = root.findViewById(R.id.toggleGroup_displayTemp);
		celsiusButton = root.findViewById(R.id.toggleButton_celsius);
		fahrenheitTempButton = root.findViewById(R.id.toggleButton_fahrenheit);
		
		themeSpinner = root.findViewById(R.id.spinner_selectTheme);
		
		settings = context.getSharedPreferences(PreferenceKeys.KEY_SETTINGS, Context.MODE_PRIVATE);
		settingsEditor = settings.edit();
		
		loadToggleButtons();
		
		//displayTempToggleGroup.setOn
		displayTempToggleGroup.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
			@Override
			public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
				if (isChecked) {
					if (checkedId == R.id.toggleButton_celsius) {
						settingsEditor.putString(PreferenceKeys.KEY_SETTINGS_TEMPERATURE_UNIT,
								PreferenceKeys.VALUE_SETTINGS_TEMPERATURE_UNIT_CELSIUS);
						
					} else if (checkedId == R.id.toggleButton_fahrenheit) {
						settingsEditor.putString(PreferenceKeys.KEY_SETTINGS_TEMPERATURE_UNIT,
								PreferenceKeys.VALUE_SETTINGS_TEMPERATURE_UNIT_FAHRENHEIT);
					}
					settingsEditor.apply();
				}
				
			}
		});
		
		
		themeSpinner.setSelection(settings.getInt(PreferenceKeys.KEY_SETTINGS_THEME, 0));
		themeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
				settingsEditor.putInt(PreferenceKeys.KEY_SETTINGS_THEME, (int) themeSpinner.getSelectedItemId());
				settingsEditor.apply();
				
				navDrawerActivity.overrideTheme(null);
				navDrawerActivity.setCurrentTheme((int) themeSpinner.getSelectedItemId());
			}
			
			@Override
			public void onNothingSelected(AdapterView<?> adapterView) {
			
			}
		});
		
		return root;
	}
	
	
	public void loadToggleButtons() {
		String kind_of_degree = settings.getString(PreferenceKeys.KEY_SETTINGS_TEMPERATURE_UNIT,
				PreferenceKeys.VALUE_SETTINGS_TEMPERATURE_UNIT_CELSIUS);
		
		if (kind_of_degree.equals(PreferenceKeys.VALUE_SETTINGS_TEMPERATURE_UNIT_CELSIUS)) {
			celsiusButton.setChecked(true);
		} else if (kind_of_degree.equals(PreferenceKeys.VALUE_SETTINGS_TEMPERATURE_UNIT_FAHRENHEIT)) {
			fahrenheitTempButton.setChecked(true);
		}
		
	}
	
	
}