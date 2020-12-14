package ca.humbermail.n01300070.automahome.ui.devices;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.List;

import ca.humbermail.n01300070.automahome.R;
import ca.humbermail.n01300070.automahome.components.CategorizedDeviceOrTaskButtonRecyclerViewAdapter;
import ca.humbermail.n01300070.automahome.components.DeviceOrTaskButtonView;
import ca.humbermail.n01300070.automahome.components.RecyclerViewCategoryPadding;
import ca.humbermail.n01300070.automahome.data.LoginDataSource;
import ca.humbermail.n01300070.automahome.data.RealtimeDatabaseDataSource;
import ca.humbermail.n01300070.automahome.data.model.Device;
import ca.humbermail.n01300070.automahome.data.model.DeviceOrTaskButtonData;
import ca.humbermail.n01300070.automahome.ui.devices.DeviceSearchActivity;
import ca.humbermail.n01300070.automahome.ui.devices.DevicesViewModel;
import ca.humbermail.n01300070.automahome.ui.devices.control.ControlDevicesActivity;
import ca.humbermail.n01300070.automahome.ui.main.NavDrawerActivity;

public class DevicesFragment extends Fragment {

	private Context context;

	private DevicesViewModel devicesViewModel;

	private Spinner spinner;
	private RecyclerView recyclerView;
	private ExtendedFloatingActionButton addDeviceFAB;
	private RealtimeDatabaseDataSource realtimeDatabaseDataSource;
	private LoginDataSource loginDataSource;

	private CategorizedDeviceOrTaskButtonRecyclerViewAdapter categoryAdapter;
	private View.OnClickListener categoryOnClickListener;

	public View onCreateView(@NonNull final LayoutInflater inflater,
							 final ViewGroup container, Bundle savedInstanceState) {
		Log.d("DevicesFragment","onCreateView called");

		devicesViewModel = new ViewModelProvider(this).get(DevicesViewModel.class);
		View root = inflater.inflate(R.layout.fragment_devices, container, false);
		context = getActivity().getApplicationContext();

		NavDrawerActivity navDrawerActivity = (NavDrawerActivity) requireActivity();
		loginDataSource = navDrawerActivity.getLoginDataSource();
		realtimeDatabaseDataSource = navDrawerActivity.getRealtimeDatabaseDataSource();

		spinner = root.findViewById(R.id.spinner_device_rooms);
		addDeviceFAB = root.findViewById(R.id.extendedFAB_add_device);
		recyclerView = root.findViewById(R.id.recyclerView_devices);

		//Floating Action Button
		addDeviceFAB.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				startActivity(new Intent(context, DeviceSearchActivity.class));
			}
		});

		//Recycler View
		categoryOnClickListener = new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				DeviceOrTaskButtonView deviceOrTaskButtonView = (DeviceOrTaskButtonView) view;
				Intent intent = new Intent();

				intent.setClass(context, ControlDevicesActivity.class);
				intent.putExtra(DeviceOrTaskButtonData.ARG_DEVICE, deviceOrTaskButtonView.getDeviceType());

				startActivity(intent);
			}
		};
		categoryAdapter = new CategorizedDeviceOrTaskButtonRecyclerViewAdapter(context);

		recyclerView.setLayoutManager(new LinearLayoutManager(context));
		recyclerView.setAdapter(categoryAdapter);
		recyclerView.addItemDecoration(new RecyclerViewCategoryPadding( (int) getResources().getDimension(R.dimen.activity_vertical_margin) ));

		// End of onCreateView
		return root;
	}

	@Override
	public void onStart() {
		Log.d("DeviceFragment","onStart called");
		super.onStart();

		realtimeDatabaseDataSource.onDeviceValuesChange().observe(this, new Observer<List<Device>>() {
			@Override
			public void onChanged(List<Device> devices) {
				Log.d("DeviceFragment","Changed detected in devices data in database");

				categoryAdapter.setCategoryDataList(
						devicesViewModel.getCategorizedDeviceDataList(
								context,
								devices,
								categoryOnClickListener
						));
			}
		});
	}

	@Override
	public void onStop() {
		Log.d("DeviceFragment","onStop called");
		super.onStop();

		realtimeDatabaseDataSource.removeDevicesValueChangesListener();
	}
}