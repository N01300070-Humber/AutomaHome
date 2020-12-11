package ca.humbermail.n01300070.automahome.data;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ca.humbermail.n01300070.automahome.data.model.Device;
import ca.humbermail.n01300070.automahome.data.model.Home;
import ca.humbermail.n01300070.automahome.data.model.Task;

public class RealtimeDatabaseDataSource {
	private final static String HOMES_REFERENCE = "homes";
	private final static String HOMES_NAME_PATH = "name";
	private final static String DEVICES_REFERENCE = "devices";
	private final static String DEVICES_NAME_PATH = "name";
	private final static String DEVICES_CATEGORY_PATH = "category";
	private final static String TASKS_REFERENCE = "tasks";
	
	
	private String currentHomeId;
	private final LoginDataSource loginDataSource = new LoginDataSource();
	
	private final FirebaseDatabase database = FirebaseDatabase.getInstance();
	
	private MutableLiveData<List<Device>> homeValues;
	private MutableLiveData<List<Device>> deviceValues;
	private MutableLiveData<List<Task>> taskValues;
	
	private ValueEventListener homesValueEventListener;
	private ValueEventListener devicesValueEventListener;
	private ValueEventListener tasksValueEventListener;
	
	
	public void setCurrentHome(String homeId) {
		this.currentHomeId = homeId;
	}
	
	
	// Devices
	private Device createDevice(String key, String name, String type, String category) {
		return new Device(key, currentHomeId, name, type, category);
	}
	
	public void addDevice(String name, String type, String category) {
		DatabaseReference reference = database.getReference(DEVICES_REFERENCE);
		
		String key = reference.push().getKey();
		assert key != null;
		
		reference.child(key).setValue( createDevice(key, name, type, category) );
	}
	
	public void removeDevice(String key) {
		database.getReference(DEVICES_REFERENCE)
				.child(key)
				.removeValue();
	}
	
	public void updateDeviceName(String key, String name) {
		database.getReference(DEVICES_REFERENCE)
				.child(key)
				.child(DEVICES_NAME_PATH)
				.setValue(name);
	}
	
	public void updateDeviceCategory(String key, String category) {
		database.getReference(DEVICES_REFERENCE)
				.child(key)
				.child(DEVICES_CATEGORY_PATH)
				.setValue(category);
	}
	
	public void listenForDevicesValueChanges() {
		devicesValueEventListener = new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull DataSnapshot snapshot) {
				ArrayList<Device> devices = new ArrayList<>();
				
				if (snapshot.exists()) {
					Iterable<DataSnapshot> iterable = snapshot.getChildren();
					
					for (DataSnapshot dataSnapshot : iterable) {
						devices.add((Device) dataSnapshot.getValue());
					}
				}
				
				deviceValues.postValue(devices);
			}
			
			@Override
			public void onCancelled(@NonNull DatabaseError error) {
			
			}
		};
		
		database.getReference(DEVICES_REFERENCE).addValueEventListener(devicesValueEventListener);
	}
	
	public void removeDevicesValueChangesListener() {
		database.getReference(DEVICES_REFERENCE).addValueEventListener(devicesValueEventListener);
	}
	
	public LiveData<List<Device>> onDeviceValuesChange() {
		listenForDevicesValueChanges();
		return deviceValues;
	}
	
	
	// Tasks
	
	
}
