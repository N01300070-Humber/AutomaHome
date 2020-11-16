package ca.humbermail.n01300070.automahome.ui.home;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import ca.humbermail.n01300070.automahome.R;

public class InviteUserFragment extends Fragment {


    public static InviteUserFragment newInstance() {
        InviteUserFragment inviteUser = new InviteUserFragment();
        return inviteUser;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_invite_user, container, false);
        //final TextView textView = root.findViewById(R.id.text_home);
        //TODO: fix later
        /*  homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/
        return root;
    }

    // Home fragment event handlers
    /**
     * onClick event handler for InviteUserButton
     * @param view Source view
     */
    ImageButton inviteUserButton;

    public void inviteUserBtn_Clicked(View view){
        Log.d("myTagDebug", "Mydebug");
        System.out.println("inviteUserBtn_Clicked");
		/*Toast.makeText(this, "Invite User Button Clicked", Toast.LENGTH_SHORT).show();
		Intent intent0 = new Intent(this, NavDrawerActivity.class);
		startActivity(intent0);*/

        if (findViewById(R.id.nav_host_fragment) != null) {
            // Set the Main Fragment
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.nav_host_fragment, inviteUserFragment)
                    .addToBackStack(null)
                    .commit();
            getSupportFragmentManager().executePendingTransactions();
        }
    }
}