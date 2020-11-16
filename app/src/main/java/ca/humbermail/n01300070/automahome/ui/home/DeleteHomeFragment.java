package ca.humbermail.n01300070.automahome.ui.home;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import ca.humbermail.n01300070.automahome.R;

public class DeleteHomeFragment extends Fragment {

    public DeleteHomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_delete_home, container, false);
        // Inflate the layout for this fragment
        return root;
    }

    // Home fragment event handlers
    /**
     * onClick event handler for deleteHomeButton
     * @param view Source view
     */
    Button deleteHomeButton;

    public void deleteHomeBtn_Clicked(View view){

        if (findViewById(R.id.nav_host_fragment) != null) {
            // Set the Main Fragment
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.nav_host_fragment, deleteHomeFragment )
                    .addToBackStack(null)
                    .commit();
            getSupportFragmentManager().executePendingTransactions();
        }
    }
}
