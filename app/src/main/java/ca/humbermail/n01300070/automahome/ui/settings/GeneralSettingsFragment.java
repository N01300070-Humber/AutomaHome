package ca.humbermail.n01300070.automahome.ui.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
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
		saveToggleGroupBtn();
		loadToggleButtons();


		themeSpinner = root.findViewById(R.id.spinner_selectTheme);

		settings = context.getSharedPreferences(PreferenceKeys.SETTINGS, Context.MODE_PRIVATE);
		settingsEditor = settings.edit();

		//displayTempToggleGroup.setOn
		displayTempToggleGroup.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
			@Override
			public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
				if(isChecked){

					String kind_of_degree = "";

					if(checkedId==R.id.toggleButton_celsius){
						System.out.println(checkedId);
						System.out.println("-------------+++++");
						kind_of_degree = "celsius";

					}else if (checkedId==R.id.toggleButton_fahrenheit){
						System.out.println("-------------");
						System.out.println(checkedId);
						kind_of_degree = "fahrenheit";
					}

					SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
					SharedPreferences.Editor editor = sharedPref.edit();
					String KIND_OF_DEGREE_DEFAULT = "KIND_OF_DEGREE_DEFAULT";
					editor.putString(KIND_OF_DEGREE_DEFAULT, kind_of_degree);
					editor.apply();
				}

			}
		});






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

	public void saveToggleGroupBtn(){
		settings = getActivity().getSharedPreferences("AppPreferences", context.MODE_PRIVATE);
		settingsEditor = settings.edit();
		settingsEditor.putBoolean("buttons checked",celsiusButton.isChecked());
		settingsEditor.putBoolean("buttons checked",fahrenheitTempButton.isChecked());
		settingsEditor.apply();
	}

	public void loadToggleButtons(){

		SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);

		String KIND_OF_DEGREE_DEFAULT = "KIND_OF_DEGREE_DEFAULT";
		String kind_of_degree_default = "celsius";
		String kind_of_degree = sharedPref.getString(KIND_OF_DEGREE_DEFAULT, kind_of_degree_default);

		if (kind_of_degree == "celsius")
			celsiusButton.setChecked(true);
		else if (kind_of_degree == "fahrenheit")
			fahrenheitTempButton.setChecked(true);


	}


}