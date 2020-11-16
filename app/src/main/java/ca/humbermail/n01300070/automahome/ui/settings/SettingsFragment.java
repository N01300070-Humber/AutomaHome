package ca.humbermail.n01300070.automahome.ui.settings;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import ca.humbermail.n01300070.automahome.R;
import ca.humbermail.n01300070.automahome.WelcomeActivity;

public class SettingsFragment extends Fragment {
	Context context;
	
	ViewPager viewPager;
	TabLayout tabs;
	
	SectionsPagerAdapter sectionsPagerAdapter;
	
	public View onCreateView(@NonNull LayoutInflater inflater,
							 ViewGroup container, Bundle savedInstanceState) {
		View root = inflater.inflate(R.layout.fragment_settings, container, false);
		context = getActivity().getApplicationContext();
		
		viewPager = root.findViewById(R.id.viewPager_settings);
		tabs = root.findViewById(R.id.tabs_settings);
		
		sectionsPagerAdapter = new SectionsPagerAdapter(context, getChildFragmentManager());
		viewPager.setAdapter(sectionsPagerAdapter);
		tabs.setupWithViewPager(viewPager);
		
		return root;

	}
	
	public void deleteButton_onClick(View view){
		// TODO: Delete
	}
	
	
}