package ca.humbermail.n01300070.automahome.ui.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import ca.humbermail.n01300070.automahome.R;


public class AddNetworkFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_add_network, container, false);
        // Inflate the layout for this fragment
        return root;
    }

    // Home fragment event handlers
    /**
     * onClick event handler for AddNetworkButton
     * @param view Source view
     */
    ImageButton addNetworkButton;

    public void addNetworkBtn_Clicked(View view){

        if (findViewById(R.id.nav_host_fragment) != null) {
            // Set the Main Fragment
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.nav_host_fragment, addNetworkFragment )
                    .addToBackStack(null)
                    .commit();
            getSupportFragmentManager().executePendingTransactions();
        }
    }
}