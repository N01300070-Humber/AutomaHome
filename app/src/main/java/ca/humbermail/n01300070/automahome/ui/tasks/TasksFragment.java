package ca.humbermail.n01300070.automahome.ui.tasks;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import ca.humbermail.n01300070.automahome.R;
import ca.humbermail.n01300070.automahome.components.CategorizedDeviceOrTaskButtonRecyclerViewAdapter;
import ca.humbermail.n01300070.automahome.components.DeviceOrTaskButtonView;
import ca.humbermail.n01300070.automahome.components.RecyclerViewCategoryPadding;
import ca.humbermail.n01300070.automahome.data.LoginDataSource;
import ca.humbermail.n01300070.automahome.data.RealtimeDatabaseDataSource;
import ca.humbermail.n01300070.automahome.data.model.Condition;
import ca.humbermail.n01300070.automahome.ui.CustomActivity;

public class TasksFragment extends Fragment {
	private Context context;
	
	private RecyclerView recyclerView;
	private ExtendedFloatingActionButton createTaskFAB;
	private RealtimeDatabaseDataSource realtimeDatabaseDataSource;
	private LoginDataSource loginDataSource;


	private CategorizedDeviceOrTaskButtonRecyclerViewAdapter categoryAdapter;
	private View.OnClickListener categoryOnClickListener;

	final int ADD_TASK = 1;
	
	public View onCreateView(@NonNull LayoutInflater inflater,
							 ViewGroup container, Bundle savedInstanceState) {
		TasksViewModel tasksViewModel = new ViewModelProvider(this).get(TasksViewModel.class);
		View root = inflater.inflate(R.layout.fragment_tasks, container, false);
		context = requireActivity().getApplicationContext();

		CustomActivity parentActivity = (CustomActivity) requireActivity();
		loginDataSource = parentActivity.getLoginDataSource();
		realtimeDatabaseDataSource = parentActivity.getRealtimeDatabaseDataSource();
		realtimeDatabaseDataSource.setCurrentHome("testhome");

		context = getActivity().getApplicationContext();
		
		createTaskFAB = root.findViewById(R.id.extendedFAB_add_task);
		recyclerView = root.findViewById(R.id.recyclerView_tasks);
		
		//Floating Action Button
		createTaskFAB.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				startEditTaskActivity();
			}
		});
		
		//Recycler View
		categoryOnClickListener = new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				startEditTaskActivity();
			}
		};
		categoryAdapter = new CategorizedDeviceOrTaskButtonRecyclerViewAdapter( context,
				tasksViewModel.generatePlaceholderCategorizedTaskDataList(context,
						categoryOnClickListener) );
		
		recyclerView.setLayoutManager(new LinearLayoutManager(context));
		recyclerView.setAdapter(categoryAdapter);
		recyclerView.addItemDecoration(new RecyclerViewCategoryPadding( (int) context.getResources().getDimension(R.dimen.activity_vertical_margin) ));
		
		// End of onCreateView
		return root;
	}

	@Override
	public void onStart() {
		super.onStart();

		realtimeDatabaseDataSource.listenForTaskValueChanges();
	}

	private void startEditTaskActivity() {
		startActivityForResult(new Intent(context, EditTaskActivity.class),ADD_TASK);
	}
}