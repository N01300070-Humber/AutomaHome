package ca.humbermail.n01300070.automahome.ui.tasks.operation;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ca.humbermail.n01300070.automahome.R;

public class ChooseOperationFragment extends Fragment {
	Context context;
	
	RecyclerView recyclerView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View root = inflater.inflate(R.layout.fragment_choose_operation, container, false);
		context = getActivity().getApplicationContext();
		
		recyclerView = root.findViewById(R.id.recyclerView_chooseOperation);
		
		return root;
	}
}