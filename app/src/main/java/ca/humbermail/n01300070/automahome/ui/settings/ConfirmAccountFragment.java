package ca.humbermail.n01300070.automahome.ui.settings;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ca.humbermail.n01300070.automahome.R;

public class ConfirmAccountFragment extends Fragment {
	Context context;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View root = inflater.inflate(R.layout.fragment_confirm_account, container, false);
		context = getActivity().getApplicationContext();
		
		
		
		return root;
		
	}
	
	public void confirmBtn_Clicked(View view) {
		// TODO: save data and leave fragment
        /*if (findViewById(R.id.nav_host_fragment) != null) {
            // Set the Main Fragment
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.nav_host_fragment, confirmAccountFragment )
                    .addToBackStack(null)
                    .commit();
            getSupportFragmentManager().executePendingTransactions();
        }*/
	}
}