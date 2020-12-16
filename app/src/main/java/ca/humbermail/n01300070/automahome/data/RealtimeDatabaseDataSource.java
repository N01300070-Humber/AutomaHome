package ca.humbermail.n01300070.automahome.data;

import android.util.Log;
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
import java.util.Objects;

import ca.humbermail.n01300070.automahome.data.model.Condition;
import ca.humbermail.n01300070.automahome.data.model.Device;
import ca.humbermail.n01300070.automahome.data.model.Home;
import ca.humbermail.n01300070.automahome.data.model.Operation;
import ca.humbermail.n01300070.automahome.data.model.Task;
import ca.humbermail.n01300070.automahome.data.model.User;

public class RealtimeDatabaseDataSource {
	private final static String USERS_REFERENCE = "users";
	private final static String USERS_NAME_PATH = "name";
	private final static String USERS_EMAIL_PATH = "email";
	private final static String HOMES_REFERENCE = "homes";
	private final static String HOMES_ID_PATH = "id";
	private final static String HOMES_NAME_PATH = "name";
	private final static String HOMES_OWNER_PATH = "owner";
	private final static String HOMES_EDITORS_PATH = "editors";
	private final static String DEVICES_REFERENCE = "devices";
	private final static String DEVICES_ID_PATH = "id";
	private final static String DEVICES_HOME_ID_PATH = "homeId";
	private final static String DEVICES_NAME_PATH = "name";
	private final static String DEVICES_ROOM_PATH = "room";
	private final static String DEVICES_TYPE_PATH = "type";
	private final static String DEVICES_CATEGORY_PATH = "category";
	private final static String TASKS_REFERENCE = "tasks";
	private final static String TASKS_ID_PATH = "homeId";
	private final static String TASKS_HOME_ID_PATH = "homeId";
	private final static String TASKS_NAME_PATH = "name";
	private final static String TASKS_NOTE_PATH = "note";
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
	
	private final FirebaseDatabase database = FirebaseDatabase.getInstance();
	
	private final MutableLiveData<List<User>> userValues = new MutableLiveData<>();
	private final MutableLiveData<List<Home>> homeValues = new MutableLiveData<>();
	private final MutableLiveData<List<Pair<String, Boolean>>> homeEditorValues = new MutableLiveData<>();
	private final MutableLiveData<List<Device>> deviceValues = new MutableLiveData<>();
	private final MutableLiveData<List<Task>> taskValues = new MutableLiveData<>();
	private final MutableLiveData<List<Condition>> taskConditionValues = new MutableLiveData<>();
	private final MutableLiveData<List<Operation>> taskOperationValues = new MutableLiveData<>();
	
	private ValueEventListener usersValueEventListener;
	private ValueEventListener homesValueEventListener;
	private ValueEventListener homeEditorsValueEventListener;
	private ValueEventListener devicesValueEventListener;
	private ValueEventListener tasksValueEventListener;
	private ValueEventListener taskConditionsValueEventListener;
	private ValueEventListener taskOperationsValueEventListener;
	
	// Users
	private User createUser(String key, String name, String email) {
		return new User(key, name, email);
	}
	
	public void addCurrentUser(LoginDataSource loginDataSource, String displayName, String emailAddress) {
		Log.d("DatabaseDataSource", "addCurrentUser called");
		
		DatabaseReference reference = database.getReference(USERS_REFERENCE);
		
		String key = loginDataSource.getUserID();
		
		reference.child(key).setValue(createUser(key, displayName, emailAddress));
	}
	
	public void removeCurrentUser(LoginDataSource loginDataSource) {
		Log.d("DatabaseDataSource", "removeCurrentUser called");
		
		String currentUserId = loginDataSource.getUserID();
		
		List<Home> homes = homeValues.getValue();
		if (homes != null) {
			for (Home home : homes) {
				if (home.getOwner().equals(currentUserId)) {
					removeHome(home.getId());
				}
			}
		}
		
		database.getReference(USERS_REFERENCE)
				.child(currentUserId)
				.removeValue();
	}
	
	public void updateCurrentUserDisplayName(LoginDataSource loginDataSource, String displayName) {
		Log.d("DatabaseDataSource", "updateCurrentUserDisplayName called");
		
		database.getReference(USERS_REFERENCE)
				.child(loginDataSource.getUserID())
				.child(USERS_NAME_PATH)
				.setValue(displayName);
	}
	
	public void updateCurrentUserEmailAddress(LoginDataSource loginDataSource, String emailAddress) {
		Log.d("DatabaseDataSource", "updateCurrentUserEmailAddress called");
		
		database.getReference(USERS_REFERENCE)
				.child(loginDataSource.getUserID())
				.child(USERS_EMAIL_PATH)
				.setValue(emailAddress);
	}
	
	
	// Homes
	public void setCurrentHomeId(String homeId) {
		this.currentHomeId = homeId;
	}
	
	public String getCurrentHomeId() {
		return currentHomeId;
	}
	
	
	private Home createHome(String key, String name, String userID) {
		return new Home(key, name, userID);
	}
	
	public void addHome(String name, LoginDataSource loginDataSource) {
		Log.d("DatabaseDataSource", "updateCurrentUserEmailAddress called");
		
		DatabaseReference reference = database.getReference(HOMES_REFERENCE);
		
		String key = reference.push().getKey();
		assert key != null;
		
		String uid = loginDataSource.getUserID();
		reference.child(key).setValue(createHome(key, name, uid));
		addHomeEditor(key, uid, true);
		setCurrentHomeId(key);
	}
	
	public void removeHome() {
		Log.d("DatabaseDataSource", "updateCurrentUserEmailAddress called");
		
		removeHome(currentHomeId);
	}
	
	public void removeHome(String homeId) {
		Log.d("DatabaseDataSource", "updateCurrentUserEmailAddress called");
		
		database.getReference(HOMES_REFERENCE)
				.child(homeId)
				.removeValue();
		// TODO: Remove connected devices
		// TODO: Remove connected tasks
	}
	
	public void updateHomeName(String name) {
		Log.d("DatabaseDataSource", "updateCurrentUserEmailAddress called");
		
		updateHomeName(currentHomeId, name);
	}
	
	public void updateHomeName(String homeId, String name) {
		Log.d("DatabaseDataSource", "updateCurrentUserEmailAddress called");
		
		database.getReference(HOMES_REFERENCE)
				.child(homeId)
				.child(HOMES_NAME_PATH)
				.setValue(name);
	}
	
	private void listenForHomesValueChanges(final String uid) {
		Log.d("DatabaseDataSource", "listenForHomesValueChanges called");
		
		homesValueEventListener = new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull DataSnapshot snapshot) {
				Log.d("DatabaseDataSource", "Detected change in Homes data");
				
				final ArrayList<Home> homes = new ArrayList<>();
				
				if (snapshot.exists()) {
					Iterable<DataSnapshot> iterable = snapshot.getChildren();
					
					for (DataSnapshot dataSnapshot : iterable) {
						Boolean acceptedInvite = (Boolean) dataSnapshot.child(HOMES_EDITORS_PATH).child(uid).getValue();
						
						if (acceptedInvite != null && acceptedInvite) {
							homes.add(new Home(
									(String) Objects.requireNonNull(dataSnapshot.child(HOMES_ID_PATH).getValue()),
									(String) Objects.requireNonNull(dataSnapshot.child(HOMES_NAME_PATH).getValue()),
									(String) Objects.requireNonNull(dataSnapshot.child(HOMES_OWNER_PATH).getValue())
							));
						}
					}
				}
				
				homeValues.postValue(homes);
			}
			
			@Override
			public void onCancelled(@NonNull DatabaseError error) {
			
			}
		};
		
		database.getReference(HOMES_REFERENCE)
				.addValueEventListener(homesValueEventListener);
	}
	
	public void removeHomesValueChangesListener() {
		Log.d("DatabaseDataSource", "removeHomesValueChangesListener called");
		
		database.getReference(HOMES_REFERENCE).removeEventListener(homesValueEventListener);
	}
	
	public LiveData<List<Home>> onHomeValuesChange(LoginDataSource loginDataSource) {
		Log.d("DatabaseDataSource", "onHomeValuesChange called");
		
		listenForHomesValueChanges(loginDataSource.getUserID());
		return homeValues;
	}
	
	
	// Home Editors
	public void addHomeEditor(String uid) {
		addHomeEditor(currentHomeId, uid, false);
	}
	
	private void addHomeEditor(String homeId, String uid, boolean acceptedInvite) {
		Log.d("DatabaseDataSource", "addHomeEditor called");
		
		database.getReference(HOMES_REFERENCE)
				.child(homeId)
				.child(HOMES_EDITORS_PATH)
				.child(uid)
				.setValue(acceptedInvite);
	}
	
	public void removeHomeEditor(String uid) {
		Log.d("DatabaseDataSource", "removeHomeEditor called");
		
		database.getReference(HOMES_REFERENCE)
				.child(currentHomeId)
				.child(HOMES_EDITORS_PATH)
				.child(uid)
				.removeValue();
	}
	
	public void updateHomeEditor(String uid, boolean acceptedInvite) {
		Log.d("DatabaseDataSource", "updateHomeEditor called");
		
		database.getReference(HOMES_REFERENCE)
				.child(currentHomeId)
				.child(HOMES_EDITORS_PATH)
				.child(uid)
				.setValue(acceptedInvite);
	}
	
	private void listenForHomeEditorsValueChanges() {
		Log.d("DatabaseDataSource", "listenForHomeEditorsValueChanges called");
		
		homeEditorsValueEventListener = new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull DataSnapshot snapshot) {
				Log.d("DatabaseDataSource", "Detected change in Home Editors data");
				
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
		Log.d("DatabaseDataSource", "removeHomeEditorsValueChangesListener called");
		
		database.getReference(HOMES_REFERENCE)
				.child(currentHomeId)
				.child(HOMES_EDITORS_PATH)
				.removeEventListener(homeEditorsValueEventListener);
	}
	
	public LiveData<List<Pair<String, Boolean>>> onHomeEditorValuesChange() {
		Log.d("DatabaseDataSource", "onHomeEditorValuesChange called");
		
		listenForHomeEditorsValueChanges();
		return homeEditorValues;
	}
	
	
	// Devices
	private Device createDevice(String key, String name, String type, String room, String category) {
		return new Device(key, currentHomeId, name, room, type, category);
	}
	
	public String addDevice(String name, String type, String room, String category) {
		Log.d("DatabaseDataSource", "addDevice called");
		
		DatabaseReference reference = database.getReference(DEVICES_REFERENCE);
		
		String key = reference.push().getKey();
		assert key != null;
		
		reference.child(key).setValue(createDevice(key, name, type, room, category));
		return key;
	}
	
	public void removeDevice(String deviceId) {
		Log.d("DatabaseDataSource", "removeDevice called");
		
		database.getReference(DEVICES_REFERENCE)
				.child(deviceId)
				.removeValue();
	}
	
	public void updateDeviceName(String deviceId, String name) {
		Log.d("DatabaseDataSource", "updateDeviceName called");
		
		database.getReference(DEVICES_REFERENCE)
				.child(deviceId)
				.child(DEVICES_NAME_PATH)
				.setValue(name);
	}
	
	public void updateDeviceCategory(String deviceId, String category) {
		Log.d("DatabaseDataSource", "updateDeviceCategory called");
		
		database.getReference(DEVICES_REFERENCE)
				.child(deviceId)
				.child(DEVICES_CATEGORY_PATH)
				.setValue(category);
	}

	public void updateDeviceRoom(String deviceId, String room) {
		Log.d("DatabaseDataSource","updateDeviceRoom called");

		database.getReference(DEVICES_REFERENCE)
				.child(deviceId)
				.child(DEVICES_ROOM_PATH)
				.setValue(room);
	}

	private void listenForDevicesValueChanges() {
		Log.d("DatabaseDataSource", "listenForDevicesValueChanges called");
		
		devicesValueEventListener = new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull DataSnapshot snapshot) {
				Log.d("DatabaseDataSource", "Detected change in Devices data");
				
				ArrayList<Device> devices = new ArrayList<>();
				
				if (snapshot.exists()) {
					Iterable<DataSnapshot> iterable = snapshot.getChildren();
					
					for (DataSnapshot dataSnapshot : iterable) {
						Device device = new Device(
								(String) Objects.requireNonNull(dataSnapshot.child(DEVICES_ID_PATH).getValue()),
								(String) Objects.requireNonNull(dataSnapshot.child(DEVICES_HOME_ID_PATH).getValue()),
								(String) Objects.requireNonNull(dataSnapshot.child(DEVICES_NAME_PATH).getValue()),
								(String) Objects.requireNonNull(dataSnapshot.child(DEVICES_ROOM_PATH).getValue()),
								(String) Objects.requireNonNull(dataSnapshot.child(DEVICES_TYPE_PATH).getValue()),
								(String) Objects.requireNonNull(dataSnapshot.child(DEVICES_CATEGORY_PATH).getValue())
						);
						
						devices.add(device);
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
		Log.d("DatabaseDataSource", "removeDevicesValueChangesListener called");
		
		database.getReference(DEVICES_REFERENCE).removeEventListener(devicesValueEventListener);
	}
	
	public LiveData<List<Device>> onDeviceValuesChange() {
		Log.d("DatabaseDataSource", "onDeviceValuesChange called");
		
		listenForDevicesValueChanges();
		return deviceValues;
	}
	
	
	// Tasks
	private Task createTask(String key, String name, String note, String category) {
		return new Task(key, currentHomeId, name, note, category);
	}
	
	public String addTask(String name, String note, String category) {
		Log.d("DatabaseDataSource", "addTask called");
		
		DatabaseReference reference = database.getReference(TASKS_REFERENCE);
		
		String key = reference.push().getKey();
		assert key != null;
		
		reference.child(key).setValue(createTask(key, name, note, category));
		return key;
	}
	
	public void removeTask(String key) {
		Log.d("DatabaseDataSource", "removeTask called");
		
		database.getReference(TASKS_REFERENCE)
				.child(key)
				.removeValue();
	}
	
	public void updateTaskName(String taskId, String name) {
		Log.d("DatabaseDataSource", "updateTaskName called");
		
		database.getReference(TASKS_REFERENCE)
				.child(taskId)
				.child(TASKS_NAME_PATH)
				.setValue(name);
	}
	
	public void updateTaskCategory(String taskId, String category) {
		Log.d("DatabaseDataSource", "updateTaskCategory called");
		
		database.getReference(TASKS_REFERENCE)
				.child(taskId)
				.child(TASKS_CATEGORY_PATH)
				.setValue(category);
	}
	
	private void listenForTaskValueChanges() {
		Log.d("DatabaseDataSource", "listenForTaskValueChanges called");
		
		tasksValueEventListener = new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull DataSnapshot snapshot) {
				Log.d("DatabaseDataSource", "Detected change in Tasks data");
				
				ArrayList<Task> tasks = new ArrayList<>();
				
				if (snapshot.exists()) {
					Iterable<DataSnapshot> iterable = snapshot.getChildren();
					
					for (DataSnapshot dataSnapshot : iterable) {
						Task task = new Task(
								(String) Objects.requireNonNull(dataSnapshot.child(TASKS_ID_PATH).getValue()),
								(String) Objects.requireNonNull(dataSnapshot.child(TASKS_HOME_ID_PATH).getValue()),
								(String) Objects.requireNonNull(dataSnapshot.child(TASKS_NAME_PATH).getValue()),
								(String) Objects.requireNonNull(dataSnapshot.child(TASKS_NOTE_PATH).getValue()),
								(String) Objects.requireNonNull(dataSnapshot.child(TASKS_CATEGORY_PATH).getValue())
						);
						
						tasks.add(task);
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
		Log.d("DatabaseDataSource", "removeTasksValueChangesListener called");
		
		database.getReference(TASKS_REFERENCE).removeEventListener(tasksValueEventListener);
	}
	
	public LiveData<List<Task>> onTaskValuesChange() {
		Log.d("DatabaseDataSource", "onTaskValuesChange called");
		
		listenForTaskValueChanges();
		return taskValues;
	}
	
	
	// Task Conditions
	private Condition createTaskCondition(String key, int position, String type, String referenceDeviceId) {
		return new Condition(key, position, type, referenceDeviceId);
	}
	
	public void addTaskCondition(String taskId, int position, String type, String referenceDeviceId) {
		Log.d("DatabaseDataSource", "addTaskCondition called");
		
		DatabaseReference reference = database.getReference(TASKS_REFERENCE)
				.child(taskId)
				.child(TASK_CONDITIONS_PATH);
		
		String key = reference.push().getKey();
		assert key != null;
		
		reference.child(key).setValue(createTaskCondition(key, position, type, referenceDeviceId));
	}
	
	public void removeTaskCondition(String taskId, String taskConditionId) {
		Log.d("DatabaseDataSource", "removeTaskCondition called");
		
		database.getReference(TASKS_REFERENCE)
				.child(taskId)
				.child(TASK_CONDITIONS_PATH)
				.child(taskConditionId)
				.removeValue();
	}
	
	public void updateTaskConditionPosition(String taskId, String taskConditionId, int position) {
		Log.d("DatabaseDataSource", "updateTaskConditionPosition called");
		
		database.getReference(TASKS_REFERENCE)
				.child(taskId)
				.child(TASK_CONDITIONS_PATH)
				.child(taskConditionId)
				.child(TASK_CONDITIONS_POSITION_PATH)
				.setValue(position);
	}
	
	public void updateTaskConditionReferenceDevice(String taskId, String taskConditionId, String referenceDeviceId) {
		Log.d("DatabaseDataSource", "updateTaskConditionReferenceDevice called");
		
		database.getReference(TASKS_REFERENCE)
				.child(taskId)
				.child(TASK_CONDITIONS_PATH)
				.child(taskConditionId)
				.child(TASK_CONDITIONS_DEVICE_ID_PATH)
				.setValue(referenceDeviceId);
	}
	
	private void listenForTaskConditionValueChanges(String taskId) {
		Log.d("DatabaseDataSource", "listenForTaskConditionValueChanges called");
		
		taskConditionsValueEventListener = new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull DataSnapshot snapshot) {
				Log.d("DatabaseDataSource", "Detected change in Task Conditions data");
				
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
				.orderByChild(TASK_CONDITIONS_POSITION_PATH)
				.addValueEventListener(taskConditionsValueEventListener);
	}
	
	public void removeTaskConditionsValueChangesListener(String taskId) {
		Log.d("DatabaseDataSource", "removeTaskConditionsValueChangesListener called");
		
		database.getReference(TASKS_REFERENCE)
				.child(taskId)
				.child(TASK_CONDITIONS_PATH)
				.removeEventListener(taskConditionsValueEventListener);
	}
	
	public LiveData<List<Condition>> onTaskConditionValuesChange(String taskId) {
		Log.d("DatabaseDataSource", "onTaskConditionValuesChange called");
		
		listenForTaskConditionValueChanges(taskId);
		return taskConditionValues;
	}
	
	
	// Task Operations
	private Operation createTaskOperation(String key, int position, String type, String referenceDeviceId) {
		return new Operation(key, position, type, referenceDeviceId);
	}
	
	public void addTaskOperation(String taskId, int position, String type, String referenceDeviceId) {
		Log.d("DatabaseDataSource", "addTaskOperation called");
		
		DatabaseReference reference = database.getReference(TASKS_REFERENCE)
				.child(taskId)
				.child(TASK_OPERATIONS_PATH);
		
		String key = reference.push().getKey();
		assert key != null;
		
		reference.child(key).setValue(createTaskOperation(key, position, type, referenceDeviceId));
	}
	
	public void removeTaskOperation(String taskId, String taskOperationId) {
		Log.d("DatabaseDataSource", "removeTaskOperation called");
		
		database.getReference(TASKS_REFERENCE)
				.child(taskId)
				.child(TASK_OPERATIONS_PATH)
				.child(taskOperationId)
				.removeValue();
	}
	
	public void updateTaskOperationPosition(String taskId, String taskOperationId, int position) {
		Log.d("DatabaseDataSource", "updateTaskOperationPosition called");
		
		database.getReference(TASKS_REFERENCE)
				.child(taskId)
				.child(TASK_OPERATIONS_PATH)
				.child(taskOperationId)
				.child(TASK_OPERATIONS_POSITION_PATH)
				.setValue(position);
	}
	
	public void updateTaskOperationReferenceDevice(String taskId, String taskOperationId, String referenceDeviceId) {
		Log.d("DatabaseDataSource", "updateTaskOperationReferenceDevice called");
		
		database.getReference(TASKS_REFERENCE)
				.child(taskId)
				.child(TASK_OPERATIONS_PATH)
				.child(taskOperationId)
				.child(TASK_OPERATIONS_DEVICE_ID_PATH)
				.setValue(referenceDeviceId);
	}
	
	private void listenForTaskOperationValueChanges(String taskId) {
		Log.d("DatabaseDataSource", "listenForTaskOperationValueChanges called");
		
		taskOperationsValueEventListener = new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull DataSnapshot snapshot) {
				Log.d("DatabaseDataSource", "Detected change in Task Operations data");
				
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
				.orderByChild(TASK_OPERATIONS_POSITION_PATH)
				.addValueEventListener(taskOperationsValueEventListener);
	}
	
	public void removeTaskOperationsValueChangesListener(String taskId) {
		Log.d("DatabaseDataSource", "removeTaskOperationsValueChangesListener called");
		
		database.getReference(TASKS_REFERENCE)
				.child(taskId)
				.child(TASK_OPERATIONS_PATH)
				.removeEventListener(taskOperationsValueEventListener);
	}
	
	public LiveData<List<Operation>> onTaskOperationValuesChange(String taskId) {
		Log.d("DatabaseDataSource", "onTaskOperationValuesChange called");
		
		listenForTaskOperationValueChanges(taskId);
		return taskOperationValues;
	}
}
