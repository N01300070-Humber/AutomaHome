package ca.humbermail.n01300070.automahome.ui.settings;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import ca.humbermail.n01300070.automahome.R;

public class AccountSettingsFragment extends Fragment {
    
    public AccountSettingsFragment() {
        // Required empty public constructor
    }
    
    public static AccountSettingsFragment newInstance() {
        return new AccountSettingsFragment();
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_settings_account, container, false);
        
        return root;
    }


}