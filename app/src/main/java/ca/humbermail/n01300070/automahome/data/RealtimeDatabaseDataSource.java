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

import ca.humbermail.n01300070.automahome.data.model.Condition;
import ca.humbermail.n01300070.automahome.data.model.Device;
import ca.humbermail.n01300070.automahome.data.model.Home;
import ca.humbermail.n01300070.automahome.data.model.Operation;
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
	private final static String TASKS_HOME_ID_PATH = "homeId";
	private final static String TASKS_NAME_PATH = "name";
	private final static String TASKS_CATEGORY_PATH = "category";
	private final static String TASK_CONDITIONS_PATH = "conditions";
	private final static String TASK_CONDITIONS_POSITION_PATH = "position";
	private final static String TASK_CONDITIONS_DEVICE_ID_PATH = "deviceId";
	private final static String TASK_CONDITIONS_DATA_PATH = "data";
	private final static String TASK_OPERATIONS_PATH = "operations";
	private final static String TASK_OPERATIONS_POSITION_PATH = "position";
	private final static String TASK_OPERATIONS_DEVICE_ID_PATH = "deviceId";
	private final static String TASK_OPERATIONS_DATA_PATH = "data";
	
	private final static String NO_CATEGORY = ""; //Used for devices and tasks that are not favourited
	private final static String NO_DEVICE = ""; //Used for conditions and operations that do not interact with a device
	
	
	private String currentHomeId;
	private final LoginDataSource loginDataSource = new LoginDataSource();
	
	private final FirebaseDatabase database = FirebaseDatabase.getInstance();
	
	private final MutableLiveData<List<Home>> homeValues = new MutableLiveData<>();
	private final MutableLiveData<List<Pair<String, Boolean>>> homeEditorValues = new MutableLiveData<>();
	private final MutableLiveData<List<Device>> deviceValues = new MutableLiveData<>();
	private final MutableLiveData<List<Task>> taskValues = new MutableLiveData<>();
	private final MutableLiveData<List<Condition>> taskConditionValues = new MutableLiveData<>();
	private final MutableLiveData<List<Operation>> taskOperationValues = new MutableLiveData<>();
	
	private ValueEventListener homesValueEventListener;
	private ValueEventListener homeEditorsValueEventListener;
	private ValueEventListener devicesValueEventListener;
	private ValueEventListener tasksValueEventListener;
	private ValueEventListener taskConditionsValueEventListener;
	private ValueEventListener taskOperationsValueEventListener;
	
	
	public void setCurrentHome(String homeId) {
		this.currentHomeId = homeId;
	}
	
	
	// Homes
	private Home createHome(String key, String name) {
		return new Home(key, name, loginDataSource.getUserID());
	}
	
	public void addHome(String name) {
		DatabaseReference reference = database.getReference(HOMES_REFERENCE);
		
		String key = reference.push().getKey();
		assert key != null;
		
		reference.child(key).setValue( createHome(key, name) );
		// TODO: Add current user to editors
	}
	
	public void removeHome(String homeId) {
		database.getReference(HOMES_REFERENCE)
				.child(homeId)
				.removeValue();
		// TODO: Remove connected devices
		// TODO: Remove connected tasks
	}
	
	public void updateHomeName(String homeId, String name) {
		database.getReference(HOMES_REFERENCE)
				.child(homeId)
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
		database.getReference(HOMES_REFERENCE).removeEventListener(homesValueEventListener);
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
	
	public void removeDevice(String deviceId) {
		database.getReference(DEVICES_REFERENCE)
				.child(deviceId)
				.removeValue();
	}
	
	public void updateDeviceName(String deviceId, String name) {
		database.getReference(DEVICES_REFERENCE)
				.child(deviceId)
				.child(DEVICES_NAME_PATH)
				.setValue(name);
	}
	
	public void updateDeviceCategory(String deviceId, String category) {
		database.getReference(DEVICES_REFERENCE)
				.child(deviceId)
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
		database.getReference(DEVICES_REFERENCE).removeEventListener(devicesValueEventListener);
	}
	
	public LiveData<List<Device>> onDeviceValuesChange() {
		listenForDevicesValueChanges();
		return deviceValues;
	}
	
	
	// Tasks
	private Task createTask(String key, String name, String category) {
		return new Task(key, currentHomeId, name, category);
	}
	
	public void addTask(String name, String type, String category) {
		DatabaseReference reference = database.getReference(TASKS_REFERENCE);
		
		String key = reference.push().getKey();
		assert key != null;
		
		reference.child(key).setValue( createTask(key, name, category) );
	}
	
	public void removeTask(String key) {
		database.getReference(TASKS_REFERENCE)
				.child(key)
				.removeValue();
	}
	
	public void updateTaskName(String key, String name) {
		database.getReference(TASKS_REFERENCE)
				.child(key)
				.child(TASKS_NAME_PATH)
				.setValue(name);
	}
	
	public void updateTaskCategory(String key, String category) {
		database.getReference(TASKS_REFERENCE)
				.child(key)
				.child(TASKS_CATEGORY_PATH)
				.setValue(category);
	}
	
	public void listenForTaskValueChanges() {
		tasksValueEventListener = new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull DataSnapshot snapshot) {
				ArrayList<Task> tasks = new ArrayList<>();
				
				if (snapshot.exists()) {
					Iterable<DataSnapshot> iterable = snapshot.getChildren();
					
					for (DataSnapshot dataSnapshot : iterable) {
						tasks.add((Task) dataSnapshot.getValue());
					}
				}
				
				taskValues.postValue(tasks);
			}
			
			@Override
			public void onCancelled(@NonNull DatabaseError error) {
			
			}
		};
		
		database.getReference(TASKS_REFERENCE)
				.orderByChild(TASKS_HOME_ID_PATH)
				.equalTo(currentHomeId)
				.addValueEventListener(tasksValueEventListener);
	}
	
	public void removeTasksValueChangesListener() {
		database.getReference(TASKS_REFERENCE).removeEventListener(tasksValueEventListener);
	}
	
	public LiveData<List<Task>> onTaskValuesChange() {
		listenForTaskValueChanges();
		return taskValues;
	}
	
	
	// Task Conditions
	private Condition createTaskCondition(String key, int position, String type, String referenceDeviceId) {
		return new Condition(key, position, type, referenceDeviceId);
	}
	
	public void addTaskCondition(String taskId, int position, String type, String referenceDeviceId) {
		DatabaseReference reference = database.getReference(TASKS_REFERENCE)
				.child(taskId)
				.child(TASK_CONDITIONS_PATH);
		
		String key = reference.push().getKey();
		assert key != null;
		
		reference.child(key).setValue( createTaskCondition(key, position, type, referenceDeviceId) );
	}
	
	public void removeTaskCondition(String taskId, String taskConditionId) {
		database.getReference(TASKS_REFERENCE)
				.child(taskId)
				.child(TASK_CONDITIONS_PATH)
				.child(taskConditionId)
				.removeValue();
	}
	
	public void updateTaskConditionPosition(String taskId, String taskConditionId, int position) {
		database.getReference(TASKS_REFERENCE)
				.child(taskId)
				.child(TASK_CONDITIONS_PATH)
				.child(taskConditionId)
				.child(TASK_CONDITIONS_POSITION_PATH)
				.setValue(position);
	}
	
	public void updateTaskConditionReferenceDevice(String taskId, String taskConditionId, String referenceDeviceId) {
		database.getReference(TASKS_REFERENCE)
				.child(taskId)
				.child(TASK_CONDITIONS_PATH)
				.child(taskConditionId)
				.child(TASK_CONDITIONS_DEVICE_ID_PATH)
				.setValue(referenceDeviceId);
	}
	
	public void listenForTaskConditionValueChanges(String taskId) {
		taskConditionsValueEventListener = new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull DataSnapshot snapshot) {
				ArrayList<Condition> conditions = new ArrayList<>();
				
				if (snapshot.exists()) {
					Iterable<DataSnapshot> iterable = snapshot.getChildren();
					
					for (DataSnapshot dataSnapshot : iterable) {
						conditions.add((Condition) dataSnapshot.getValue());
					}
				}
				
				taskConditionValues.postValue(conditions);
			}
			
			@Override
			public void onCancelled(@NonNull DatabaseError error) {
			
			}
		};
		
		database.getReference(TASKS_REFERENCE)
				.child(taskId)
				.child(TASK_CONDITIONS_PATH)
				.addValueEventListener(taskConditionsValueEventListener);
	}
	
	public void removeTaskConditionsValueChangesListener(String taskId) {
		database.getReference(TASKS_REFERENCE)
				.child(taskId)
				.child(TASK_CONDITIONS_PATH)
				.removeEventListener(taskConditionsValueEventListener);
	}
	
	public LiveData<List<Condition>> onTaskConditionValuesChange(String taskId) {
		listenForTaskConditionValueChanges(taskId);
		return taskConditionValues;
	}
	
	
	// Task Operations
	private Operation createTaskOperation(String key, int position, String type, String referenceDeviceId) {
		return new Operation(key, position, type, referenceDeviceId);
	}
	
	public void addTaskOperation(String taskId, int position, String type, String referenceDeviceId) {
		DatabaseReference reference = database.getReference(TASKS_REFERENCE)
				.child(taskId)
				.child(TASK_OPERATIONS_PATH);
		
		String key = reference.push().getKey();
		assert key != null;
		
		reference.child(key).setValue( createTaskOperation(key, position, type, referenceDeviceId) );
	}
	
	public void removeTaskOperation(String taskId, String taskOperationId) {
		database.getReference(TASKS_REFERENCE)
				.child(taskId)
				.child(TASK_OPERATIONS_PATH)
				.child(taskOperationId)
				.removeValue();
	}
	
	public void updateTaskOperationPosition(String taskId, String taskOperationId, int position) {
		database.getReference(TASKS_REFERENCE)
				.child(taskId)
				.child(TASK_OPERATIONS_PATH)
				.child(taskOperationId)
				.child(TASK_OPERATIONS_POSITION_PATH)
				.setValue(position);
	}
	
	public void updateTaskOperationReferenceDevice(String taskId, String taskOperationId, String referenceDeviceId) {
		database.getReference(TASKS_REFERENCE)
				.child(taskId)
				.child(TASK_OPERATIONS_PATH)
				.child(taskOperationId)
				.child(TASK_OPERATIONS_DEVICE_ID_PATH)
				.setValue(referenceDeviceId);
	}
	
	public void listenForTaskOperationValueChanges(String taskId) {
		taskOperationsValueEventListener = new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull DataSnapshot snapshot) {
				ArrayList<Operation> operations = new ArrayList<>();
				
				if (snapshot.exists()) {
					Iterable<DataSnapshot> iterable = snapshot.getChildren();
					
					for (DataSnapshot dataSnapshot : iterable) {
						operations.add((Operation) dataSnapshot.getValue());
					}
				}
				
				taskOperationValues.postValue(operations);
			}
			
			@Override
			public void onCancelled(@NonNull DatabaseError error) {
			
			}
		};
		
		database.getReference(TASKS_REFERENCE)
				.child(taskId)
				.child(TASK_OPERATIONS_PATH)
				.addValueEventListener(taskOperationsValueEventListener);
	}
	
	public void removeTaskOperationsValueChangesListener(String taskId) {
		database.getReference(TASKS_REFERENCE)
				.child(taskId)
				.child(TASK_OPERATIONS_PATH)
				.removeEventListener(taskOperationsValueEventListener);
	}
	
	public LiveData<List<Operation>> onTaskOperationValuesChange(String taskId) {
		listenForTaskOperationValueChanges(taskId);
		return taskOperationValues;
	}
}
