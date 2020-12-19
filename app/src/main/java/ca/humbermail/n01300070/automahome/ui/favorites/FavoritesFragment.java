package ca.humbermail.n01300070.automahome.ui.favorites;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ca.humbermail.n01300070.automahome.R;
import ca.humbermail.n01300070.automahome.components.CategorizedDeviceOrTaskButtonRecyclerViewAdapter;
import ca.humbermail.n01300070.automahome.components.DeviceOrTaskButtonView;
import ca.humbermail.n01300070.automahome.components.RecyclerViewCategoryPadding;
import ca.humbermail.n01300070.automahome.data.RealtimeDatabaseDataSource;
import ca.humbermail.n01300070.automahome.data.model.Device;
import ca.humbermail.n01300070.automahome.data.model.DeviceOrTaskButtonData;
import ca.humbermail.n01300070.automahome.data.model.Task;
import ca.humbermail.n01300070.automahome.ui.devices.DevicesFragment;
import ca.humbermail.n01300070.automahome.ui.devices.control.ControlDevicesActivity;
import ca.humbermail.n01300070.automahome.ui.devices.edit.EditDevicesActivity;
import ca.humbermail.n01300070.automahome.ui.main.NavDrawerActivity;
import ca.humbermail.n01300070.automahome.ui.tasks.EditTaskActivity;

public class FavoritesFragment extends Fragment {
	private Context context;
	FavoritesViewModel favoritesViewModel;
	
	private RecyclerView recyclerView;
	private CategorizedDeviceOrTaskButtonRecyclerViewAdapter categoryAdapter;
	private View.OnClickListener categoryOnClickListener;
	
	private RealtimeDatabaseDataSource realtimeDatabaseDataSource;
	
	public View onCreateView(@NonNull LayoutInflater inflater,
							 ViewGroup container, Bundle savedInstanceState) {
		favoritesViewModel = new ViewModelProvider(this).get(FavoritesViewModel.class);
		final View root = inflater.inflate(R.layout.fragment_favorites, container, false);
		context = requireContext();
		
		NavDrawerActivity navDrawerActivity = (NavDrawerActivity) requireActivity();
		realtimeDatabaseDataSource = navDrawerActivity.getRealtimeDatabaseDataSource();
		
		recyclerView = root.findViewById(R.id.recyclerView_favorites);
		
		categoryOnClickListener = new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				DeviceOrTaskButtonView deviceOrTaskButtonView = (DeviceOrTaskButtonView) view;
				Intent intent = new Intent();
				
				switch (deviceOrTaskButtonView.getType()) {
					case DeviceOrTaskButtonData.TYPE_DEVICE:
						intent.setClass(context, ControlDevicesActivity.class);
						intent.putExtra(DeviceOrTaskButtonData.ARG_DEVICE, deviceOrTaskButtonView.getDeviceType());
						intent.putExtra(EditDevicesActivity.EXTRA_DEVICE_ID, deviceOrTaskButtonView.getDeviceOrTaskId());
						intent.putExtra(EditDevicesActivity.EXTRA_DEVICE_NAME, deviceOrTaskButtonView.getName());
						intent.putExtra(EditDevicesActivity.EXTRA_DEVICE_CATEGORY, deviceOrTaskButtonView.getFavoritesCategory());
						break;
					case DeviceOrTaskButtonData.TYPE_TASK:
						intent.setClass(context, EditTaskActivity.class);
						intent.putExtra(EditTaskActivity.EXTRA_TASK_ID, deviceOrTaskButtonView.getDeviceOrTaskId());
						intent.putExtra(EditTaskActivity.EXTRA_TASK_NAME, deviceOrTaskButtonView.getName());
						intent.putExtra(EditTaskActivity.EXTRA_TASK_CATEGORY, deviceOrTaskButtonView.getFavoritesCategory());
						break;
					default:
						return;
				}
				
				startActivity(intent);
			}
		};
		categoryAdapter = new CategorizedDeviceOrTaskButtonRecyclerViewAdapter(context);
		
		recyclerView.setLayoutManager(new LinearLayoutManager(context));
		recyclerView.setAdapter(categoryAdapter);
		recyclerView.addItemDecoration(new RecyclerViewCategoryPadding((int) getResources().getDimension(R.dimen.activity_vertical_margin) ));
		
		return root;
	}
	
	@Override
	public void onStart() {
		super.onStart();
		
		realtimeDatabaseDataSource.onDeviceValuesChange().observe(this, new Observer<List<Device>>() {
			@Override
			public void onChanged(List<Device> devices) {
				favoritesViewModel.setDeviceData(context, devices);
				categoryAdapter.setCategoryDataList(
						favoritesViewModel.getCategoryDataList(context, categoryOnClickListener)
				);
			}
		});
		realtimeDatabaseDataSource.onTaskValuesChange().observe(this, new Observer<List<Task>>() {
			@Override
			public void onChanged(List<Task> tasks) {
				favoritesViewModel.setTaskData(context, tasks);
				categoryAdapter.setCategoryDataList(
						favoritesViewModel.getCategoryDataList(context, categoryOnClickListener)
				);
			}
		});
	}
	
	@Override
	public void onStop() {
		super.onStop();
		
		realtimeDatabaseDataSource.removeDevicesValueChangesListener();
		realtimeDatabaseDataSource.removeTasksValueChangesListener();
	}
}