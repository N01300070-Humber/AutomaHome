package ca.humbermail.n01300070.automahome.ui.manageHome;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

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
import ca.humbermail.n01300070.automahome.ui.main.NavDrawerActivity;

public class ManageHomeFragment extends Fragment {
	private Context context;
	
	private NavDrawerActivity navDrawerActivity;
	private LoginDataSource loginDataSource;
	private RealtimeDatabaseDataSource realtimeDatabaseDataSource;
	
//	private final ArrayList<Home> homes = new ArrayList<>();
	private boolean blockNextSpinnerUpdate = false;
	
	private Spinner selectHomeSpinner;
	private RecyclerView NetworksRecyclerView;
	private RecyclerView UsersRecyclerView;
	private Button addNetworkButton;
	private Button addUserButton;
	private Button deleteHomeButton;
	private Button renameHomeButton;
	private Button createHomeButton;
	
//	private ArrayAdapter<String> homesAdapter;
	private IconTextViewAdapter networksAdapter;
	private IconTextViewAdapter usersAdapter;
	private View.OnClickListener networksOnClickListener;
	private View.OnClickListener usersOnClickListener;
	
	public View onCreateView(@NonNull LayoutInflater inflater,
							 ViewGroup container, Bundle savedInstanceState) {
		Log.d("ManageHomeFragment", "onCreateView called");
		ManageHomeViewModel homeViewModel = new ViewModelProvider(this).get(ManageHomeViewModel.class);
		View root = inflater.inflate(R.layout.fragment_manage_home, container, false);
		context = requireContext();
		
		navDrawerActivity = (NavDrawerActivity) requireActivity();
		loginDataSource = navDrawerActivity.getLoginDataSource();
		realtimeDatabaseDataSource = navDrawerActivity.getRealtimeDatabaseDataSource();
		
		selectHomeSpinner = root.findViewById(R.id.spinner_mangeHome_selectHome);
		NetworksRecyclerView = root.findViewById(R.id.recyclerView_networks);
		UsersRecyclerView = root.findViewById(R.id.recyclerView_users);
		addNetworkButton = root.findViewById(R.id.button_addNetwork);
		addUserButton = root.findViewById(R.id.button_addUser);
		deleteHomeButton = root.findViewById(R.id.button_deleteHome);
		renameHomeButton = root.findViewById(R.id.button_renameHome);
		createHomeButton = root.findViewById(R.id.button_createHome);
		
		selectHomeSpinner.setAdapter(navDrawerActivity.getHomesAdapter());
		selectHomeSpinner.setSelection(navDrawerActivity.getHomesSpinnerPosition());
		
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
		renameHomeButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				renameHomeButton_Clicked(view);
			}
		});
		createHomeButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				createHomeButton_Clicked(view);
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
		
//		homesAdapter = new ArrayAdapter<>(context, R.layout.text_view_auto_complete_label);
		networksAdapter = new IconTextViewAdapter(context, generateNetworksDataList(), networksOnClickListener);
		usersAdapter = new IconTextViewAdapter(context, usersOnClickListener);
		
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
		Log.d("ManageHomeFragment", "onStart called");
		super.onStart();
		
		navDrawerActivity.setOnHomeSpinnerItemChangedListener(new NavDrawerActivity.OnHomeSpinnerItemChangedListener() {
			@Override
			public void onHomeSpinnerItemChanged(AdapterView<?> adapterView, View view, int position, long id) {
				if (blockNextSpinnerUpdate) {
					blockNextSpinnerUpdate = false;
				} else {
					selectHomeSpinner.setSelection(position);
				}
			}
		});
//		realtimeDatabaseDataSource.onHomeValuesChange(loginDataSource).observe(this, new Observer<List<Home>>() {
//			@Override
//			public void onChanged(List<Home> homes) {
//				setHomesDataList(homes);
//				setHomeSpinnerToHome(realtimeDatabaseDataSource.getCurrentHomeId());
//			}
//		});
		realtimeDatabaseDataSource.onHomeEditorValuesChange().observe(this, new Observer<List<Pair<String, Boolean>>>() {
			@Override
			public void onChanged(List<Pair<String, Boolean>> editors) {
				setUsersDataList(editors);
			}
		});
	}
	
	@Override
	public void onStop() {
		Log.d("ManageHomeFragment", "onStop called");
		super.onStop();
		
		navDrawerActivity.removeOnHomeSpinnerItemChangedListener();
//		realtimeDatabaseDataSource.removeHomesValueChangesListener();
		realtimeDatabaseDataSource.removeHomeEditorsValueChangesListener();
	}
	
	private void selectHomeSpinner_ItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
		Log.d("ManageHomeFragment", "selectHomeSpinner_ItemSelected called");
		
		navDrawerActivity.setHomesSpinnerPosition(i);
	}
	
	private void selectHomeSpinner_NothingSelected(AdapterView<?> adapterView) {
		Log.d("ManageHomeFragment", "selectHomeSpinner_NothingSelected called");
		
		navDrawerActivity.homeSpinner_NothingSelected(adapterView);
	}
	
	private void networksRecyclerItemClicked(View view) {
		Log.d("ManageHomeFragment", "networksRecyclerItemClicked called");
		// TODO: Remove network from Wi-Fi Detection list
	}
	
	private void usersRecyclerItemClicked(View view) {
		if (((IconTextView) view).isIconVisible()) {
			Log.d("ManageHomeFragment", "usersRecyclerItemClicked called");
			// TODO: Remove user from users list
		}
	}
	
	private void addNetworkButton_Clicked(View view) {
		Log.d("ManageHomeFragment", "addNetworkButton_Clicked called");
		
		startActivity(new Intent(context, AddNetworkActivity.class));
		// TODO: Add network to Wi-Fi Detection list
	}
	
	private void addUserButton_Clicked(View view) {
		Log.d("ManageHomeFragment", "addUserButton_Clicked called");
		
		startActivity(new Intent(context, InviteUserActivity.class));
		// TODO: Add new user to users list
	}
	
	private void deleteHomeButton_Clicked(View view) {
		Log.d("ManageHomeFragment", "deleteHomeButton_Clicked called");
		// TODO: Show confirmation prompt before deleting
		realtimeDatabaseDataSource.removeHome(realtimeDatabaseDataSource.getCurrentHomeId());
	}
	
	private void renameHomeButton_Clicked(View view) {
		Log.d("ManageHomeFragment", "renameHomeButton_Clicked called");
		
		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
		dialogBuilder.setTitle(R.string.prompt_rename_home_title);
		dialogBuilder.setMessage(R.string.prompt_rename_home_message);
		
		final EditText nameEditText = new EditText(context);
		nameEditText.setInputType(InputType.TYPE_CLASS_TEXT);
		// TODO: Show current home name
		dialogBuilder.setView(nameEditText);
		
		dialogBuilder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int i) {
				realtimeDatabaseDataSource.updateHomeName(nameEditText.getText().toString());
			}
		});
		dialogBuilder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int i) {
				dialogInterface.cancel();
			}
		});
		
		dialogBuilder.show();
	}
	
	private void createHomeButton_Clicked(View view) {
		Log.d("ManageHomeFragment", "createHomeButton_Clicked called");
		
		navDrawerActivity.showCreateNewHomeDialog();
	}
	
//	@Override
//	public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//		Log.d("ManageHomeFragment", "onActivityResult called");
//		super.onActivityResult(requestCode, resultCode, data);
//
//		if (requestCode == INVITE_REQUEST) {
//			if (resultCode == Activity.RESULT_OK) {
//				// TODO: send invite to
//			}
//		}
//	}
	
	// TODO: Remove placeholder content generation function
	private ArrayList<IconTextData> generateNetworksDataList() {
		Log.d("ManageHomeFragment", "generateNetworksDataList called");
		
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
		Log.d("ManageHomeFragment", "setUsersDataList called");
		
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
}