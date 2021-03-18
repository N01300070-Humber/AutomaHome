package ca.humbermail.n01300070.automahome.ui.devices.control;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import ca.humbermail.n01300070.automahome.R;
import ca.humbermail.n01300070.automahome.components.DescriptiveTextView;
import ca.humbermail.n01300070.automahome.components.ListLinePadding;
import ca.humbermail.n01300070.automahome.components.DescriptiveTextViewAdapter;
import ca.humbermail.n01300070.automahome.data.DeviceDataPaths;
import ca.humbermail.n01300070.automahome.data.LoginDataSource;
import ca.humbermail.n01300070.automahome.data.RealtimeDatabaseDataSource;
import ca.humbermail.n01300070.automahome.data.model.DescriptiveTextViewData;
import ca.humbermail.n01300070.automahome.data.model.Home;

public class ControlMovementSensorFragment extends Fragment {
	private Context context;

	private RecyclerView detectionLog;
	private DescriptiveTextViewAdapter adapter;

	private ArrayList<DescriptiveTextViewData> logViewDataList;
	private String deviceId;
	private ControlDevicesActivity controlDevicesActivity;
	
	private RealtimeDatabaseDataSource realtimeDatabaseDataSource;
	
	public ControlMovementSensorFragment() {
		// Required empty public constructor
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View root = inflater.inflate(R.layout.fragment_control_movement_sensor, container, false);
		context = getActivity().getApplicationContext();
		controlDevicesActivity = (ControlDevicesActivity) getActivity();
		
		deviceId = controlDevicesActivity.getDeviceId();
		
		realtimeDatabaseDataSource = controlDevicesActivity.getRealtimeDatabaseDataSource();
		
		detectionLog = root.findViewById(R.id.recyclerView_control_movementSensor);
		
		logViewDataList = new ArrayList<>();
		adapter = new DescriptiveTextViewAdapter(context, logViewDataList);
		
		detectionLog.setLayoutManager(new LinearLayoutManager(context));
		detectionLog.setAdapter(adapter);
		
		detectionLog.addItemDecoration(new ListLinePadding((int) context.getResources().getDimension(R.dimen.recycler_divider_space)));
		detectionLog.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
		
		setOnDeviceDataValuesChangeListener();
		
		return root;
	}
	
	
	private void setOnDeviceDataValuesChangeListener() {
		Log.d("ControlMovementSensor", "setOnDeviceDataValuesChangeListener called");
		realtimeDatabaseDataSource.onDeviceDataValueChange(deviceId, DeviceDataPaths.MOVEMENT_LOG).observe(getViewLifecycleOwner(), new Observer<Object>() {
			@Override
			public void onChanged(Object object) {
				Log.d("ControlMovementSensor","Device data values changed");
				
				Map< String, Map<String, Object> > stringMapMap;
				
				logViewDataList.clear();
				if (!(object instanceof Map)) {
					Log.d("ControlMovementSensor","Device data values object is null");
					logViewDataList.add(new DescriptiveTextViewData(getString(R.string.log_entry_movement_no_data), null));
					return;
				}
				
				stringMapMap = (Map< String, Map<String, Object> >) object;
				
				for (Map.Entry<String, Map<String, Object>> stringMapEntry : stringMapMap.entrySet()) {
					DescriptiveTextViewData descriptiveTextViewData = new DescriptiveTextViewData();
					String direction = (String) stringMapEntry.getValue().get("direction");
					long timestamp = (long) stringMapEntry.getValue().get("timestamp");
					Date date = new Date(timestamp * 1000L);
					SimpleDateFormat simpleWeekDayFormat = new SimpleDateFormat("EEEE");
					SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("h:mm a");
					
					// TODO: Replace hard coded room strings with actual room names
					switch (direction) {
						case "left":
							descriptiveTextViewData.setMainText(getString(R.string.log_entry_movement, "Room 1", "Room 2"));
							break;
						case "right":
							descriptiveTextViewData.setMainText(getString(R.string.log_entry_movement, "Room 2", "Room 1"));
							break;
						default:
							descriptiveTextViewData.setMainText(getString(R.string.log_entry_movement_unknown_direction));
					}
					
					descriptiveTextViewData.setDescriptionText(getString(R.string.log_entry_week_time_format,
							simpleWeekDayFormat.format(date),
							simpleTimeFormat.format(date))
					);
					
					logViewDataList.add(descriptiveTextViewData);
				}
				
				adapter.notifyDataSetChanged();
			}
		});
	}
}

