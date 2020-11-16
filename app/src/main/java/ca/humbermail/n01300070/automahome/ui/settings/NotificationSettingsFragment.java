package ca.humbermail.n01300070.automahome.ui.settings;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ca.humbermail.n01300070.automahome.R;

public class NotificationSettingsFragment extends Fragment {
    
    public NotificationSettingsFragment() {
        // Required empty public constructor
    }
    
    public static NotificationSettingsFragment newInstance() {
        return new NotificationSettingsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_settings_notification, container, false);
        
        return root;
    }
}