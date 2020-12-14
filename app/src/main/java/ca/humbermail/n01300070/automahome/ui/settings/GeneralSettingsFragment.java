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

	static final String KIND_OF_DEGREE_DEFAULT = "KIND_OF_DEGREE_DEFAULT";

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

		settings = context.getSharedPreferences(PreferenceKeys.KEY_SETTINGS, Context.MODE_PRIVATE);
		settingsEditor = settings.edit();

		loadToggleButtons();

		//displayTempToggleGroup.setOn
		displayTempToggleGroup.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
			@Override
			public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
				if(isChecked){

					String kind_of_degree = getString(R.string.celsius);

					if(checkedId==R.id.toggleButton_celsius){
						System.out.println(checkedId);
						System.out.println("-------------+++++");


					}else if (checkedId==R.id.toggleButton_fahrenheit){
						System.out.println("-------------");
						System.out.println(checkedId);
						kind_of_degree = getString(R.string.fahrenheit);
					}
					System.out.print("--------------"+kind_of_degree+"---------------");
					settingsEditor.putString(getString(R.string.KIND_OF_DEGREE_DEFAULT), kind_of_degree);
					settingsEditor.apply();
				}

			}
		});






		themeSpinner.setSelection(settings.getInt(PreferenceKeys.KEY_SETTINGS_THEME, 0));
		themeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

				Toast.makeText(context, themeSpinner.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
				settingsEditor.putInt(PreferenceKeys.KEY_SETTINGS_THEME, (int) themeSpinner.getSelectedItemId());
				settingsEditor.apply();
			}
			
			@Override
			public void onNothingSelected(AdapterView<?> adapterView) {
			
			}
		});
		
		return root;
	}


	public void loadToggleButtons(){
		String kind_of_degree_default = getString(R.string.celsius);
		String kind_of_degree = settings.getString(getString(R.string.KIND_OF_DEGREE_DEFAULT), kind_of_degree_default);

		//System.out.println("--------------");
		//System.out.println(kind_of_degree);
		//System.out.println(getString(R.string.fahrenheit));
		//System.out.println("--------------");
		if (kind_of_degree.equals(getString(R.string.celsius))) {
			celsiusButton.setChecked(true);
			//System.out.println("--------------++++++++++++++"+getString(R.string.celsius));
		} else if (kind_of_degree.equals(getString(R.string.fahrenheit))) {
			fahrenheitTempButton.setChecked(true);
			//System.out.println("--------------++++++++++++++"+getString(R.string.fahrenheit));
		}

	}


}