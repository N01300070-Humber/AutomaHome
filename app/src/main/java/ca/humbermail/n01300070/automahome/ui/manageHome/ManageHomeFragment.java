package ca.humbermail.n01300070.automahome.ui.manageHome;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ca.humbermail.n01300070.automahome.R;
import ca.humbermail.n01300070.automahome.components.IconTextData;
import ca.humbermail.n01300070.automahome.components.IconTextView;
import ca.humbermail.n01300070.automahome.components.IconTextViewAdapter;
import ca.humbermail.n01300070.automahome.components.ListLinePadding;
import ca.humbermail.n01300070.automahome.components.NonScrollingLinerLayoutManager;
import ca.humbermail.n01300070.automahome.data.LoginDataSource;
import ca.humbermail.n01300070.automahome.data.RealtimeDatabaseDataSource;
import ca.humbermail.n01300070.automahome.data.model.Home;
import ca.humbermail.n01300070.automahome.ui.CustomActivity;

public class ManageHomeFragment extends Fragment {
	private Context context;
	
	private LoginDataSource loginDataSource;
	private RealtimeDatabaseDataSource realtimeDatabaseDataSource;
	
	private final ArrayList<Home> homes = new ArrayList<>();
	
	private Spinner selectHomeSpinner;
	private RecyclerView NetworksRecyclerView;
	private RecyclerView UsersRecyclerView;
	private Button addNetworkButton;
	private Button addUserButton;
	private Button deleteHomeButton;
	
	private ArrayAdapter<String> homesAdapter;
	private IconTextViewAdapter networksAdapter;
	private IconTextViewAdapter usersAdapter;
	private View.OnClickListener networksOnClickListener;
	private View.OnClickListener usersOnClickListener;
	
	public View onCreateView(@NonNull LayoutInflater inflater,
							 ViewGroup container, Bundle savedInstanceState) {
		ManageHomeViewModel homeViewModel = new ViewModelProvider(this).get(ManageHomeViewModel.class);
		View root = inflater.inflate(R.layout.fragment_manage_home, container, false);
		context = requireActivity().getApplicationContext();
		
		CustomActivity parentActivity = (CustomActivity) requireActivity();
		loginDataSource = parentActivity.getLoginDataSource();
		realtimeDatabaseDataSource = parentActivity.getRealtimeDatabaseDataSource();
		realtimeDatabaseDataSource.setCurrentHomeId("testhome"); // TODO: Replace with real data
		
		selectHomeSpinner = root.findViewById(R.id.spinner_mangeHome_selectHome);
		NetworksRecyclerView = root.findViewById(R.id.recyclerView_networks);
		UsersRecyclerView = root.findViewById(R.id.recyclerView_users);
		addNetworkButton = root.findViewById(R.id.button_addNetwork);
		addUserButton = root.findViewById(R.id.button_addUser);
		deleteHomeButton = root.findViewById(R.id.button_deleteHome);
		
		
		selectHomeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
				selectHomeSpinner_ItemSelected(adapterView, view, i, l);
			}
			
			@Override
			public void onNothingSelected(AdapterView<?> adapterView) {
				selectHomeSpinner_NothingSelected(adapterView);
			}
		});
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
		
		homesAdapter = new ArrayAdapter<>(getContext(), R.layout.text_view_auto_complete_label);
		networksAdapter = new IconTextViewAdapter(context, generateNetworksDataList(), networksOnClickListener);
		usersAdapter = new IconTextViewAdapter(context, usersOnClickListener);
		
		selectHomeSpinner.setAdapter(homesAdapter);
		
		NetworksRecyclerView.setLayoutManager(new NonScrollingLinerLayoutManager(context));
		NetworksRecyclerView.setAdapter(networksAdapter);
		NetworksRecyclerView.addItemDecoration(new ListLinePadding((int) getResources().getDimension(R.dimen.recycler_divider_text_item_padding)));
		NetworksRecyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
		
		UsersRecyclerView.setLayoutManager(new NonScrollingLinerLayoutManager(context));
		UsersRecyclerView.setAdapter(usersAdapter);
		UsersRecyclerView.addItemDecoration(new ListLinePadding((int) getResources().getDimension(R.dimen.recycler_divider_text_item_padding)));
		UsersRecyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
		
		return root;
	}
	
	@Override
	public void onStart() {
		super.onStart();
		
		realtimeDatabaseDataSource.onHomeValuesChange(loginDataSource).observe(this, new Observer<List<Home>>() {
			@Override
			public void onChanged(List<Home> homes) {
				setHomesDataList(homes);
			}
		});
		realtimeDatabaseDataSource.onHomeEditorValuesChange().observe(this, new Observer<List<Pair<String, Boolean>>>() {
			@Override
			public void onChanged(List<Pair<String, Boolean>> editors) {
				setUsersDataList(editors);
			}
		});
	}
	
	@Override
	public void onStop() {
		super.onStop();
		
		realtimeDatabaseDataSource.removeHomesValueChangesListener();
		realtimeDatabaseDataSource.removeHomeEditorsValueChangesListener();
	}
	
	private void selectHomeSpinner_ItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
		Home selectedHome = homes.get(position);
		
		realtimeDatabaseDataSource.setCurrentHomeId(selectedHome.getId());
	}
	
	private void selectHomeSpinner_NothingSelected(AdapterView<?> adapterView) {
		Toast.makeText(context, R.string.error_current_home_inaccessible, Toast.LENGTH_SHORT).show();
	}
	
	public void networksRecyclerItemClicked(View view) {
		// TODO: Remove network from Wi-Fi Detection list
	}
	
	public void usersRecyclerItemClicked(View view) {
		if (((IconTextView) view).isIconVisible()) {
			// TODO: Remove user from users list
		}
	}
	
	public void addNetworkButton_Clicked(View view) {
		startActivity(new Intent(context, AddNetworkActivity.class));
		// TODO: Add network to Wi-Fi Detection list
	}
	
	public void addUserButton_Clicked(View view) {
		startActivity(new Intent(context, InviteUserActivity.class));
		// TODO: Add new user to users list
	}
	
	public void deleteHomeButton_Clicked(View view) {
		// TODO: Show confirmation prompt before deleting
		realtimeDatabaseDataSource.removeHome(realtimeDatabaseDataSource.getCurrentHomeId());
	}
	
//	@Override
//	public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//		super.onActivityResult(requestCode, resultCode, data);
//
//		if (requestCode == INVITE_REQUEST) {
//			if (resultCode == Activity.RESULT_OK) {
//				// TODO: send invite to
//			}
//		}
//	}
	
	private void setHomesDataList(List<Home> homes) {
		this.homes.clear();
		homesAdapter.clear();
		
		for (Home home : homes) {
			homesAdapter.add(home.getName());
		}
		
		this.homes.addAll(homes);
		homesAdapter.notifyDataSetChanged();
	}
	
	// TODO: Remove placeholder content generation function
	private ArrayList<IconTextData> generateNetworksDataList() {
		int arrayLength = new Random().nextInt(3) + 2;
		ArrayList<IconTextData> iconTextDataList = new ArrayList<>(arrayLength);
		
		for (int i = 0; i < arrayLength; i++) {
			IconTextData iconTextData = new IconTextData(
					"Network " + (i + 1),
					R.style.TextAppearance_MaterialComponents_Body1,
					ContextCompat.getDrawable(context, R.drawable.ic_remove_circle_outline),
					getResources().getColor(R.color.design_default_color_error, context.getTheme()),
					"Edit"
			);
			
			iconTextDataList.add(iconTextData);
		}
		
		return iconTextDataList;
	}
	
	private void setUsersDataList(List<Pair<String, Boolean>> editors) {
		ArrayList<IconTextData> iconTextDataList = new ArrayList<>(editors.size());
		
		for (Pair<String, Boolean> editor : editors) {
			IconTextData iconTextData = new IconTextData();
			
			String text = editor.first;
			boolean isCurrentUser = loginDataSource.getUserID().equals(text);
			if (isCurrentUser) {
				text = "You";
			}
			if (!editor.second) {
				text += " (" + getString(R.string.invited) + ")";
			}
			iconTextData.setText(text);
			iconTextData.setTextAppearance(R.style.TextAppearance_MaterialComponents_Body1);
			if (!isCurrentUser) {
				iconTextData.setIcon(ContextCompat.getDrawable(context, R.drawable.ic_person_remove));
				iconTextData.setIconContentDescription(getString(R.string.remove_user));
				iconTextData.setIconTint(getResources().getColor(R.color.design_default_color_error, context.getTheme()));
			}
			iconTextData.setIconVisible(!isCurrentUser);
			
			iconTextDataList.add(iconTextData);
		}
		
		usersAdapter.setIconTextDataList(iconTextDataList);
	}
	
	// TODO: Remove placeholder content generation function
	private ArrayList<IconTextData> generateUsersDataList() {
		int arrayLength = new Random().nextInt(7) + 2;
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
					ContextCompat.getDrawable(context, R.drawable.ic_person_remove),
					getResources().getColor(R.color.design_default_color_error, context.getTheme()),
					"Edit"
			);
			
			iconTextDataList.add(iconTextData);
		}
		
		return iconTextDataList;
	}
}