package ca.humbermail.n01300070.automahome.ui.devices;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.Random;

import ca.humbermail.n01300070.automahome.R;
import ca.humbermail.n01300070.automahome.components.DeviceOrTaskButtonRecyclerViewAdapter;
import ca.humbermail.n01300070.automahome.components.DeviceOrTaskButtonView;
import ca.humbermail.n01300070.automahome.components.RecyclerViewItemDivider;
import ca.humbermail.n01300070.automahome.data.model.DeviceOrTaskButtonData;
import ca.humbermail.n01300070.automahome.ui.devices.edit.EditDevicesActivity;

public class DeviceSearchActivity extends AppCompatActivity {
	
	int REQUEST_NEW_DEVICE = 2;
	
	private ProgressBar searchProgress;
	
	private RecyclerView recyclerView;
	
	private DeviceOrTaskButtonRecyclerViewAdapter resultsAdapter;
	private View.OnClickListener resultsOnClickListener;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_device_search);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		recyclerView = findViewById(R.id.recyclerView_deviceSearch_results);
		
		resultsOnClickListener = new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				DeviceOrTaskButtonView deviceOrTaskButtonView = (DeviceOrTaskButtonView) view;
				
				Intent intent = new Intent(getApplicationContext(), EditDevicesActivity.class);
				intent.putExtra(DeviceOrTaskButtonData.ARG_DEVICE, deviceOrTaskButtonView.getDeviceType());
				startActivityForResult(intent, REQUEST_NEW_DEVICE);
			}
		};
		resultsAdapter = new DeviceOrTaskButtonRecyclerViewAdapter(getApplicationContext(), generateDeviceResultsDataList(), resultsOnClickListener);
		
		recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
		recyclerView.setAdapter(resultsAdapter);
		recyclerView.addItemDecoration(new RecyclerViewItemDivider(
				(int) getResources().getDimension(R.dimen.recycler_divider_space),
				(int) getResources().getDimension(R.dimen.activity_vertical_margin),
				(int) getResources().getDimension(R.dimen.activity_horizontal_margin)
		));
	}
	
	// TODO: Replace with method that obtains real data
	private ArrayList<DeviceOrTaskButtonData> generateDeviceResultsDataList() {
		Random random = new Random();
		int listLength = random.nextInt(10) + 1;
		ArrayList<DeviceOrTaskButtonData> deviceResultsDataList = new ArrayList<>(listLength);
		String[] deviceNames = {"Name of Light", "Name of Movement Sensor", "Name of Thermostat"};
		String[] deviceTypes = {DeviceOrTaskButtonData.DEVICE_LIGHTS,
				DeviceOrTaskButtonData.DEVICE_MOVEMENT_SENSOR, DeviceOrTaskButtonData.DEVICE_THERMOSTAT};
		
		for (int i = 0; i < listLength; i++) {
			int randInt = random.nextInt(3);
			
			DeviceOrTaskButtonData deviceData = new DeviceOrTaskButtonData(
					DeviceOrTaskButtonData.TYPE_DEVICE,
					null,
					deviceNames[randInt],
					"Room",
					"",
					ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_devices),
					getString(R.string.content_description_type_device),
					getColor(R.color.accent_200)
			);
			deviceData.setDeviceType(deviceTypes[randInt]);
			
			deviceResultsDataList.add(deviceData);
		}
		
		return deviceResultsDataList;
	}
	
	/**
	 * Handles back button onClick event
	 * @param item non-null MenuItem
	 * @return Boolean
	 */
	@Override
	public boolean onOptionsItemSelected(@NonNull MenuItem item) {
		finish();
		return true;
	}
	
	protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if (requestCode == REQUEST_NEW_DEVICE) {
			if (resultCode == RESULT_OK) {
				finish();
			}
		}
	}
}