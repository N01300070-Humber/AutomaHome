package ca.humbermail.n01300070.automahome.ui.tasks;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import ca.humbermail.n01300070.automahome.data.model.Task;
import ca.humbermail.n01300070.automahome.ui.CustomActivity;
import ca.humbermail.n01300070.automahome.ui.main.NavDrawerActivity;

public class TasksFragment extends Fragment {
	private Context context;
	
	private TasksViewModel tasksViewModel;
	
	private RecyclerView recyclerView;
	private ExtendedFloatingActionButton createTaskFAB;
	private RealtimeDatabaseDataSource realtimeDatabaseDataSource;
	private LoginDataSource loginDataSource;


	private CategorizedDeviceOrTaskButtonRecyclerViewAdapter categoryAdapter;
	private View.OnClickListener categoryOnClickListener;
	
	public View onCreateView(@NonNull LayoutInflater inflater,
							 ViewGroup container, Bundle savedInstanceState) {
		Log.d("TasksFragment", "onCreateView called");
		
		tasksViewModel = new ViewModelProvider(this).get(TasksViewModel.class);
		View root = inflater.inflate(R.layout.fragment_tasks, container, false);
		context = requireContext();

		NavDrawerActivity navDrawerActivity = (NavDrawerActivity) requireActivity();
		loginDataSource = navDrawerActivity.getLoginDataSource();
		realtimeDatabaseDataSource = navDrawerActivity.getRealtimeDatabaseDataSource();
		
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
		categoryAdapter = new CategorizedDeviceOrTaskButtonRecyclerViewAdapter(context);
		
		recyclerView.setLayoutManager(new LinearLayoutManager(context));
		recyclerView.setAdapter(categoryAdapter);
		recyclerView.addItemDecoration(new RecyclerViewCategoryPadding( (int) context.getResources().getDimension(R.dimen.activity_vertical_margin) ));
		
		// End of onCreateView
		return root;
	}

	@Override
	public void onStart() {
		Log.d("TasksFragment", "onStart called");
		super.onStart();

		realtimeDatabaseDataSource.onTaskValuesChange().observe(this, new Observer<List<Task>>() {
			@Override
			public void onChanged(List<Task> tasks) {
				Log.d("TasksFragment", "Change detected in tasks data in database");
				
				categoryAdapter.setCategoryDataList(
						tasksViewModel.getCategorizedTaskDataList(
								context,
								tasks,
								categoryOnClickListener
						));
			}
		});
	}

	private void startEditTaskActivity() {
		Log.d("TasksFragment", "startEditTaskActivity called");
		
		startActivity(new Intent(context, EditTaskActivity.class));
	}
	
	@Override
	public void onStop() {
		Log.d("TasksFragment", "onStop called");
		super.onStop();
		
		realtimeDatabaseDataSource.removeTasksValueChangesListener();
	}
}