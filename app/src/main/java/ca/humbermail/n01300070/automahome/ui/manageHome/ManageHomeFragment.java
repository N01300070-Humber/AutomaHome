package ca.humbermail.n01300070.automahome.ui.manageHome;

import android.content.Context;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Random;
import java.util.RandomAccess;

import ca.humbermail.n01300070.automahome.R;
import ca.humbermail.n01300070.automahome.components.ConditionOrOperationViewAdapter;
import ca.humbermail.n01300070.automahome.components.IconTextData;
import ca.humbermail.n01300070.automahome.components.IconTextView;
import ca.humbermail.n01300070.automahome.components.IconTextViewAdapter;
import ca.humbermail.n01300070.automahome.components.ListLinePadding;
import ca.humbermail.n01300070.automahome.components.NonScrollingGridLayoutManager;
import ca.humbermail.n01300070.automahome.components.NonScrollingLinerLayoutManager;

public class ManageHomeFragment extends Fragment {

	Context context;
	
	private Spinner selectHomeSpinner;
	private RecyclerView NetworksRecyclerView;
	private RecyclerView UsersRecyclerView;
	private Button addNetworkButton;
	private Button addUserButton;
	private Button deleteHomeButton;
	
	private IconTextViewAdapter networksAdapter;
	private IconTextViewAdapter usersAdapter;
	private View.OnClickListener networksOnClickListener;
	private View.OnClickListener usersOnClickListener;

	public View onCreateView(@NonNull LayoutInflater inflater,
							 ViewGroup container, Bundle savedInstanceState) {
		HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
		View root = inflater.inflate(R.layout.fragment_manage_home, container, false);
		context = getActivity().getApplicationContext();
		
		selectHomeSpinner = root.findViewById(R.id.spinner_mangeHome_selectHome);
		NetworksRecyclerView = root.findViewById(R.id.recyclerView_networks);
		UsersRecyclerView = root.findViewById(R.id.recyclerView_users);
		addNetworkButton = root.findViewById(R.id.button_addNetwork);
		addUserButton = root.findViewById(R.id.button_addUser);
		deleteHomeButton = root.findViewById(R.id.button_deleteHome);
		
		
		addNetworkButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				addNetworkButton_Clicked(view);
			}
		});
		addUserButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				addUserButton_Clicked(view);
			}
		});
		deleteHomeButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				deleteHomeButton_Clicked(view);
			}
		});
		networksOnClickListener = new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				networksRecyclerItemClicked(view);
			}
		};
		usersOnClickListener = new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				usersRecyclerItemClicked(view);
			}
		};
		
		networksAdapter = new IconTextViewAdapter(context, generateNetworksDataList(), networksOnClickListener);
		usersAdapter = new IconTextViewAdapter(context, generateUsersDataList(), usersOnClickListener);
		
		NetworksRecyclerView.setLayoutManager(new NonScrollingLinerLayoutManager(context));
		NetworksRecyclerView.setAdapter(networksAdapter);
		NetworksRecyclerView.addItemDecoration(new ListLinePadding((int) getResources().getDimension(R.dimen.recycler_divider_space)));
		NetworksRecyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
		
		UsersRecyclerView.setLayoutManager(new NonScrollingLinerLayoutManager(context));
		UsersRecyclerView.setAdapter(usersAdapter);
		UsersRecyclerView.addItemDecoration(new ListLinePadding((int) getResources().getDimension(R.dimen.recycler_divider_space)));
		UsersRecyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
		
		return root;
	}
	
	public void networksRecyclerItemClicked(View view) {
		// TODO Open edit network activity (Needed for Milestone 2)
	}
	
	public void usersRecyclerItemClicked(View view) {
		// TODO Open edit user activity (Needed for Milestone 2)
	}
	
	public void addNetworkButton_Clicked(View view){
		// TODO: Open add network activity (Needed for Milestone 2)
	}
	
	public void addUserButton_Clicked(View view){
		// TODO: Open invite user activity (Needed for Milestone 2)
	}
	
	public void deleteHomeButton_Clicked(View view){
		// TODO: Delete the currently selected home
	}
	
	private ArrayList<IconTextData> generateNetworksDataList() {
		int arrayLength = new Random().nextInt(3)+2;
		ArrayList<IconTextData> iconTextDataList = new ArrayList<>(arrayLength);
		
		for (int i = 0; i < arrayLength; i++) {
			IconTextData iconTextData = new IconTextData(
					"Network " + (i + 1),
					R.style.TextAppearance_MaterialComponents_Body1,
					ContextCompat.getDrawable(context, R.drawable.ic_edit),
					getResources().getColor(R.color.design_default_color_primary, context.getTheme()),
					"Edit"
			);
			
			iconTextDataList.add(iconTextData);
		}
		
		return iconTextDataList;
	}
	
	private ArrayList<IconTextData> generateUsersDataList() {
		int arrayLength = new Random().nextInt(7)+2;
		ArrayList<IconTextData> iconTextDataList = new ArrayList<>(arrayLength);
		
		IconTextData firstIconTextData = new IconTextData(
				"You",
				R.style.TextAppearance_MaterialComponents_Body1
		);
		iconTextDataList.add(firstIconTextData);
		
		for (int i = 1; i < arrayLength; i++) {
			IconTextData iconTextData = new IconTextData(
					"User " + (i + 1),
					R.style.TextAppearance_MaterialComponents_Body1,
					ContextCompat.getDrawable(context, R.drawable.ic_edit),
					getResources().getColor(R.color.design_default_color_primary, context.getTheme()),
					"Edit"
			);
			
			iconTextDataList.add(iconTextData);
		}
		
		return iconTextDataList;
	}
}