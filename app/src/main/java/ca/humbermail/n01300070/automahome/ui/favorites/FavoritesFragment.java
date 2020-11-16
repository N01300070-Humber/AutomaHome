package ca.humbermail.n01300070.automahome.ui.favorites;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ca.humbermail.n01300070.automahome.R;
import ca.humbermail.n01300070.automahome.components.CategorizedDeviceOrTaskButtonRecyclerViewAdapter;
import ca.humbermail.n01300070.automahome.components.DeviceOrTaskButtonView;
import ca.humbermail.n01300070.automahome.components.RecyclerViewCategoryPadding;
import ca.humbermail.n01300070.automahome.data.model.DeviceOrTaskData;
import ca.humbermail.n01300070.automahome.ui.devices.control.ControlDevicesActivity;
import ca.humbermail.n01300070.automahome.ui.tasks.EditTaskActivity;

public class FavoritesFragment extends Fragment {
	
	private Context context;
	private RecyclerView recyclerView;
	private CategorizedDeviceOrTaskButtonRecyclerViewAdapter categoryAdapter;
	private View.OnClickListener categoryOnClickListener;
	
	
	public View onCreateView(@NonNull LayoutInflater inflater,
							 ViewGroup container, Bundle savedInstanceState) {
		FavoritesViewModel favoritesViewModel = new ViewModelProvider(this).get(FavoritesViewModel.class);
		final View root = inflater.inflate(R.layout.fragment_favorites, container, false);
		context = getActivity().getApplicationContext();
		
		recyclerView = root.findViewById(R.id.recyclerView_favorites);
		
		categoryOnClickListener = new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				DeviceOrTaskButtonView deviceOrTaskButtonView = (DeviceOrTaskButtonView) view;
				Intent intent = new Intent();
				
				switch (deviceOrTaskButtonView.getType()) {
					case DeviceOrTaskData.TYPE_DEVICE:
						intent.setClass(context, ControlDevicesActivity.class);
						intent.putExtra(DeviceOrTaskData.ARG_DEVICE, deviceOrTaskButtonView.getDeviceType());
						break;
					case DeviceOrTaskData.TYPE_TASK:
						intent.setClass(context, EditTaskActivity.class);
						break;
					default:
						return;
				}
				
				startActivity(intent);
			}
		};
		categoryAdapter = new CategorizedDeviceOrTaskButtonRecyclerViewAdapter(context,
				FavoritesViewModel.generatePlaceholderCategoryDataList(context,
						categoryOnClickListener) );
		
		
		recyclerView.setLayoutManager(new LinearLayoutManager(context));
		recyclerView.setAdapter(categoryAdapter);
		recyclerView.addItemDecoration(new RecyclerViewCategoryPadding((int) getResources().getDimension(R.dimen.activity_vertical_margin) ));
		
		return root;
	}
	
}