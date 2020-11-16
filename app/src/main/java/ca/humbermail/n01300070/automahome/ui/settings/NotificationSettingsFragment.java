package ca.humbermail.n01300070.automahome.ui.settings;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;

import com.google.android.material.switchmaterial.SwitchMaterial;

import ca.humbermail.n01300070.automahome.R;

public class NotificationSettingsFragment extends Fragment {
    Context context;
    
    SwitchMaterial showErrorNotificationSwitch;
    
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
    
        showErrorNotificationSwitch = root.findViewById(R.id.switch_settings_showErrorNotifications);
        
        showErrorNotificationSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showErrorNotificationSwitch_Toggled(view);
            }
        });
        
        return root;
    }
    
    public void showErrorNotificationSwitch_Toggled(View view){
        // TODO: change errorNotification setting in user preferences
    }
}