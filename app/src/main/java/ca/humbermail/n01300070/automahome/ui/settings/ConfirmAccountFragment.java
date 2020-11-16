package ca.humbermail.n01300070.automahome.ui.settings;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import ca.humbermail.n01300070.automahome.R;

public class ConfirmAccountFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        SettingsViewModel settingsViewModel = new ViewModelProvider(this).get(SettingsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_confirm_account, container, false);
        //final TextView textView = root.findViewById(R.id.text_settings);
        //settingsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
        //	@Override
        //	public void onChanged(@Nullable String s) {
        //	textView.setText(s);
        //	}
        //});

        // Inflate the layout for this fragment
        return root;

    }

    Button confirmAccountButton;

    public void confirmBtn_Clicked(View view){

        if (findViewById(R.id.nav_host_fragment) != null) {
            // Set the Main Fragment
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.nav_host_fragment, confirmAccountFragment )
                    .addToBackStack(null)
                    .commit();
            getSupportFragmentManager().executePendingTransactions();
        }
    }
}