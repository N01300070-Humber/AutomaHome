package ca.humbermail.n01300070.automahome.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import ca.humbermail.n01300070.automahome.R;

public class HomeFragment extends Fragment {
	
	public View onCreateView(@NonNull LayoutInflater inflater,
							 ViewGroup container, Bundle savedInstanceState) {
		HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
		View root = inflater.inflate(R.layout.fragment_home, container, false);
		//final TextView textView = root.findViewById(R.id.text_home);
		//homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
		//	@Override
		//	public void onChanged(@Nullable String s) {
		//		textView.setText(s);
		//	}

		final ImageButton inviteUserBtn = root.findViewById(R.id.inviteUser);
		inviteUserBtn.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
				fragmentManager
						.beginTransaction()
						.replace(R.id.inviteUser,InviteUserFragment.newInstance())
						.commit();
			}
		});


		return root;
	}
}