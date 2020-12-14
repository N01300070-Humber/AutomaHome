package ca.humbermail.n01300070.automahome.ui.settings;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import ca.humbermail.n01300070.automahome.R;

public class SettingsFragment extends Fragment {
	Context context;
	
	ViewPager viewPager;
	TabLayout tabs;
	
	SectionsPagerAdapter sectionsPagerAdapter;
	
	public View onCreateView(@NonNull LayoutInflater inflater,
							 ViewGroup container, Bundle savedInstanceState) {
		View root = inflater.inflate(R.layout.fragment_settings, container, false);
		context = requireActivity().getApplicationContext();
		
		viewPager = root.findViewById(R.id.viewPager_settings);
		tabs = root.findViewById(R.id.tabs_settings);
		
		sectionsPagerAdapter = new SectionsPagerAdapter(context, getChildFragmentManager());
		viewPager.setAdapter(sectionsPagerAdapter);
		tabs.setupWithViewPager(viewPager);
		
		return root;

	}
}