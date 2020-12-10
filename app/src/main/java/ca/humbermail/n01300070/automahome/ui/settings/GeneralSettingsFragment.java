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

public class GeneralSettingsFragment extends Fragment {
	Context context;
	
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
		
		displayTempToggleGroup = root.findViewById(R.id.toggleGroup_displayTemp);
		celsiusButton = root.findViewById(R.id.toggleButton_celsius);
		fahrenheitTempButton = root.findViewById(R.id.toggleButton_fahrenheit);
		themeSpinner = root.findViewById(R.id.spinner_selectTheme);
		
		settings = context.getSharedPreferences(PreferenceKeys.SETTINGS, Context.MODE_PRIVATE);
		settingsEditor = settings.edit();
		
		//displayTempToggleGroup.setOn
		
		themeSpinner.setSelection(settings.getInt(PreferenceKeys.SETTINGS_THEME, 0));
		themeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
				Toast.makeText(context, themeSpinner.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
				settingsEditor.putInt(PreferenceKeys.SETTINGS_THEME, (int) themeSpinner.getSelectedItemId());
				settingsEditor.apply();
			}
			
			@Override
			public void onNothingSelected(AdapterView<?> adapterView) {
			
			}
		});
		
		return root;
	}

}