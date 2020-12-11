package ca.humbermail.n01300070.automahome.data;

import android.util.Pair;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import ca.humbermail.n01300070.automahome.data.model.Device;
import ca.humbermail.n01300070.automahome.data.model.Home;
import ca.humbermail.n01300070.automahome.data.model.Task;

public class RealtimeDatabaseDataSource {
	private final static String HOMES_REFERENCE = "homes";
	private final static String HOMES_NAME_PATH = "name";
	private final static String HOMES_EDITORS_PATH = "editors";
	private final static String DEVICES_REFERENCE = "devices";
	private final static String DEVICES_HOME_ID_PATH = "homeId";
	private final static String DEVICES_NAME_PATH = "name";
	private final static String DEVICES_CATEGORY_PATH = "category";
	private final static String TASKS_REFERENCE = "tasks";
	
	
	private String currentHomeId;
	private final LoginDataSource loginDataSource = new LoginDataSource();
	
	private final FirebaseDatabase database = FirebaseDatabase.getInstance();
	
	private final MutableLiveData<List<Home>> homeValues = new MutableLiveData<>();
	private final MutableLiveData<List<Pair<String, Boolean>>> homeEditorValues = new MutableLiveData<>();
	private final MutableLiveData<List<Device>> deviceValues = new MutableLiveData<>();
	private final MutableLiveData<List<Task>> taskValues = new MutableLiveData<>();
	
	private ValueEventListener homesValueEventListener;
	private ValueEventListener homeEditorsValueEventListener;
	private ValueEventListener devicesValueEventListener;
	private ValueEventListener tasksValueEventListener;
	
	
	public void setCurrentHome(String homeId) {
		this.currentHomeId = homeId;
	}
	
	
	// Homes
	private Home createHome(String key, String name) {
		return new Home(key, name, loginDataSource.getCurrentUID());
	}
	
	public void addHome(String name) {
		DatabaseReference reference = database.getReference(HOMES_REFERENCE);
		
		String key = reference.push().getKey();
		assert key != null;
		
		reference.child(key).setValue( createHome(key, name) );
		// TODO: Add current user to editors
	}
	
	public void removeHome(String key) {
		database.getReference(HOMES_REFERENCE)
				.child(key)
				.removeValue();
	}
	
	public void updateHomeName(String key, String name) {
		database.getReference(HOMES_REFERENCE)
				.child(key)
				.child(HOMES_NAME_PATH)
				.setValue(name);
	}
	
	public void listenForHomesValueChanges() {
		homesValueEventListener = new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull DataSnapshot snapshot) {
				ArrayList<Home> homes = new ArrayList<>();
				
				if (snapshot.exists()) {
					Iterable<DataSnapshot> iterable = snapshot.getChildren();
					
					for (DataSnapshot dataSnapshot : iterable) {
						homes.add((Home) dataSnapshot.getValue());
					}
				}
				
				homeValues.postValue(homes);
			}
			
			@Override
			public void onCancelled(@NonNull DatabaseError error) {
			
			}
		};
		
		database.getReference(HOMES_REFERENCE).addValueEventListener(homesValueEventListener);
	}
	
	public void removeHomesValueChangesListener() {
		database.getReference(HOMES_REFERENCE).addValueEventListener(homesValueEventListener);
	}
	
	public LiveData<List<Home>> onHomeValuesChange() {
		listenForHomesValueChanges();
		return homeValues;
	}
	
	
	// Home Editors
	public void addHomeEditor(String uid) {
		database.getReference(HOMES_REFERENCE)
				.child(currentHomeId)
				.child(HOMES_EDITORS_PATH)
				.child(uid)
				.setValue(false);
	}
	
	public void removeHomeEditor(String uid) {
		database.getReference(HOMES_REFERENCE)
				.child(currentHomeId)
				.child(HOMES_EDITORS_PATH)
				.child(uid)
				.removeValue();
	}
	
	public void updateHomeEditor(String uid, boolean accepted) {
		database.getReference(HOMES_REFERENCE)
				.child(currentHomeId)
				.child(HOMES_EDITORS_PATH)
				.child(uid)
				.setValue(accepted);
	}
	
	public void listenForHomeEditorsValueChanges() {
		homeEditorsValueEventListener = new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull DataSnapshot snapshot) {
				ArrayList<Pair<String, Boolean>> editors = new ArrayList<>();
				
				if (snapshot.exists()) {
					Iterable<DataSnapshot> iterable = snapshot.getChildren();
					
					for (DataSnapshot dataSnapshot : iterable) {
						editors.add(new Pair<>(
								dataSnapshot.getKey(),
								(Boolean) dataSnapshot.getValue()
						));
					}
				}
				
				homeEditorValues.postValue(editors);
			}
			
			@Override
			public void onCancelled(@NonNull DatabaseError error) {
			
			}
		};
		
		database.getReference(HOMES_REFERENCE)
				.child(currentHomeId)
				.child(HOMES_EDITORS_PATH)
				.addValueEventListener(homeEditorsValueEventListener);
	}
	
	public void removeHomeEditorsValueChangesListener() {
		database.getReference(HOMES_REFERENCE)
				.child(currentHomeId)
				.child(HOMES_EDITORS_PATH)
				.removeEventListener(homeEditorsValueEventListener);
	}
	
	public LiveData<List<Pair<String, Boolean>>> onHomeEditorValuesChange() {
		listenForHomeEditorsValueChanges();
		return homeEditorValues;
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
		
		database.getReference(DEVICES_REFERENCE)
				.orderByChild(DEVICES_HOME_ID_PATH)
				.equalTo(currentHomeId)
				.addValueEventListener(devicesValueEventListener);
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
