package ca.humbermail.n01300070.automahome.ui.devices;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import ca.humbermail.n01300070.automahome.R;
import ca.humbermail.n01300070.automahome.components.CategorizedDeviceOrTaskButtonRecyclerViewAdapter;
import ca.humbermail.n01300070.automahome.components.DeviceOrTaskButtonView;
import ca.humbermail.n01300070.automahome.components.RecyclerViewCategoryPadding;
import ca.humbermail.n01300070.automahome.data.model.DeviceOrTaskData;
import ca.humbermail.n01300070.automahome.ui.devices.control.ControlDevicesActivity;

public class DevicesFragment extends Fragment {
	
	private Context context;
	
	private Spinner spinner;
	private RecyclerView recyclerView;
	private ExtendedFloatingActionButton addDeviceFAB;
	
	private CategorizedDeviceOrTaskButtonRecyclerViewAdapter categoryAdapter;
	private View.OnClickListener categoryOnClickListener;
	
	public View onCreateView(@NonNull final LayoutInflater inflater,
							 final ViewGroup container, Bundle savedInstanceState) {
		DevicesViewModel devicesViewModel = new ViewModelProvider(this).get(DevicesViewModel.class);
		View root = inflater.inflate(R.layout.fragment_devices, container, false);
		context = getActivity().getApplicationContext();

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
				intent.putExtra(DeviceOrTaskData.ARG_DEVICE, deviceOrTaskButtonView.getDeviceType());
				
				startActivity(intent);
			}
		};
		categoryAdapter = new CategorizedDeviceOrTaskButtonRecyclerViewAdapter( context,
				devicesViewModel.generatePlaceholderCategorizedDeviceDataList(context,
						categoryOnClickListener) );
		
		recyclerView.setLayoutManager(new LinearLayoutManager(context));
		recyclerView.setAdapter(categoryAdapter);
		recyclerView.addItemDecoration(new RecyclerViewCategoryPadding( (int) getResources().getDimension(R.dimen.activity_vertical_margin) ));
		
		// End of onCreateView
		return root;
	}
}