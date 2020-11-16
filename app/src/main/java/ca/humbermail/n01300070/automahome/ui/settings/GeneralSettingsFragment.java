package ca.humbermail.n01300070.automahome.ui.settings;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ca.humbermail.n01300070.automahome.R;

public class GeneralSettingsFragment extends Fragment {
	
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
		
		return root;
	}
}