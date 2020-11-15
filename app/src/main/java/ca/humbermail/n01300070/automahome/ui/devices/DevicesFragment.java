package ca.humbermail.n01300070.automahome.ui.devices;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ca.humbermail.n01300070.automahome.R;
import ca.humbermail.n01300070.automahome.components.CategorizedDeviceOrTaskButtonRecyclerViewAdapter;
import ca.humbermail.n01300070.automahome.components.DeviceOrTaskButtonView;
import ca.humbermail.n01300070.automahome.components.RecyclerViewCategoryPadding;

public class DevicesFragment extends Fragment
{
	
	private Context context;
	private RecyclerView recyclerView;
	private Spinner spinner;
	private ArrayAdapter<String> spinnerAdapter;
	private CategorizedDeviceOrTaskButtonRecyclerViewAdapter categoryAdapter;
	private View.OnClickListener categoryOnClickListener;
	
	public View onCreateView(@NonNull LayoutInflater inflater,
							 ViewGroup container, Bundle savedInstanceState) {
		DevicesViewModel devicesViewModel = new ViewModelProvider(this).get(DevicesViewModel.class);
		View root = inflater.inflate(R.layout.fragment_devices, container, false);
		context = getActivity().getApplicationContext();

		// Spinner
		spinner = root.findViewById(R.id.spinner_device_rooms);
		
		// RecyclerView
		recyclerView = root.findViewById(R.id.recyclerView_devices);
		
		categoryOnClickListener = new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				// TODO: Replace placeholder on click function with navigation to the correct Control Device or Edit Task Activity
				DeviceOrTaskButtonView deviceOrTaskButtonView = (DeviceOrTaskButtonView) view;
				deviceOrTaskButtonView.setExtraText("Clicked");
			}
		};
		categoryAdapter = new CategorizedDeviceOrTaskButtonRecyclerViewAdapter( context,
				devicesViewModel.generatePlaceholderCategoryDataList(context,
						categoryOnClickListener) );
		
		recyclerView.setLayoutManager(new LinearLayoutManager(context));
		recyclerView.setAdapter(categoryAdapter);
		recyclerView.addItemDecoration(new RecyclerViewCategoryPadding( (int) getResources().getDimension(R.dimen.activity_vertical_margin) ));
		
		return root;
	}
}