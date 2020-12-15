package ca.humbermail.n01300070.automahome.ui.tasks;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import ca.humbermail.n01300070.automahome.R;
import ca.humbermail.n01300070.automahome.components.ConditionOrOperationView;
import ca.humbermail.n01300070.automahome.components.ConditionOrOperationViewAdapter;
import ca.humbermail.n01300070.automahome.components.FavoriteSelectView;
import ca.humbermail.n01300070.automahome.components.ListLinePadding;
import ca.humbermail.n01300070.automahome.components.NonScrollingLinerLayoutManager;
import ca.humbermail.n01300070.automahome.data.PreferenceKeys;
import ca.humbermail.n01300070.automahome.data.RealtimeDatabaseDataSource;
import ca.humbermail.n01300070.automahome.data.model.Condition;
import ca.humbermail.n01300070.automahome.data.model.ConditionOrOperationViewData;
import ca.humbermail.n01300070.automahome.data.model.Operation;
import ca.humbermail.n01300070.automahome.ui.CustomActivity;
import ca.humbermail.n01300070.automahome.ui.tasks.condition.EditConditionActivity;
import ca.humbermail.n01300070.automahome.ui.tasks.operation.EditOperationActivity;

public class EditTaskActivity extends CustomActivity {
	public static final String EXTRA_TASK_ID = "taskId";
	public static final String EXTRA_TASK_NAME = "taskName";
	public static final String EXTRA_CONDITION_ID = "conditionId";
	public static final String EXTRA_OPERATION_ID = "operationId";
	
	private static final String DEFAULT_NAME = "Untitled Task";
	
	private RealtimeDatabaseDataSource realtimeDatabaseDataSource;
	private String taskId;
	
	private Button addConditionButton;
	private Button addOperationButton;
	private Button saveButton;
	private Button deleteButton;
	private TextInputEditText nameEditText;
	private FavoriteSelectView favoriteSelectView;
	private RecyclerView conditionsRecyclerView;
	private RecyclerView operationsRecyclerView;
	
	private ConditionOrOperationViewAdapter conditionsAdapter;
	private ConditionOrOperationViewAdapter operationsAdapter;
	private View.OnClickListener conditionsOnClickListener;
	private View.OnClickListener operationsOnClickListener;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_task);
		Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
		
		addConditionButton = findViewById(R.id.button_addCondition);
		addOperationButton = findViewById(R.id.button_addOperation);
		saveButton = findViewById(R.id.button_editTask_save);
		deleteButton = findViewById(R.id.button_editTask_delete);
		nameEditText = findViewById(R.id.editText_taskName);
		favoriteSelectView = findViewById(R.id.favoriteSelectView_editTask);
		conditionsRecyclerView = findViewById(R.id.recyclerView_conditions);
		operationsRecyclerView = findViewById(R.id.recyclerView_operations);
		
		setRealtimeDatabaseDataSource(new RealtimeDatabaseDataSource());
		realtimeDatabaseDataSource = getRealtimeDatabaseDataSource();
		
		realtimeDatabaseDataSource.setCurrentHomeId(
				getSharedPreferences(PreferenceKeys.KEY_SESSION, Context.MODE_PRIVATE)
						.getString(PreferenceKeys.KEY_SESSION_SELECTED_HOME, "")
		);
		
		// Get Extras
		Bundle bundle = getIntent().getExtras();
		if (bundle == null) {
			taskId = realtimeDatabaseDataSource.addTask(DEFAULT_NAME, "", "");
			nameEditText.setText(DEFAULT_NAME);
		} else {
			taskId = bundle.getString(EXTRA_TASK_ID);
			nameEditText.setText(bundle.getString(EXTRA_TASK_NAME));
		}
		
		nameEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
				return nameEditTextDone(textView, i, keyEvent);
			}
		});
		conditionsOnClickListener = new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				conditionsRecyclerViewItemClicked(view);
			}
		};
		operationsOnClickListener = new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				operationsRecyclerViewItemClicked(view);
			}
		};
		
		favoriteSelectView.setAutoCompleteLabels(generateCategoryList());
		conditionsAdapter = new ConditionOrOperationViewAdapter(getApplicationContext(), conditionsOnClickListener);
		operationsAdapter = new ConditionOrOperationViewAdapter(getApplicationContext(), operationsOnClickListener);
		
		//Conditions Recycler
		conditionsRecyclerView.setLayoutManager(new NonScrollingLinerLayoutManager(getApplicationContext()));
		conditionsRecyclerView.setAdapter(conditionsAdapter);
		conditionsRecyclerView.addItemDecoration(new ListLinePadding((int) getResources().getDimension(R.dimen.recycler_divider_space)));
		conditionsRecyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
		
		//Operations Recycler
		operationsRecyclerView.setLayoutManager(new NonScrollingLinerLayoutManager(getApplicationContext()));
		operationsRecyclerView.setAdapter(operationsAdapter);
		operationsRecyclerView.addItemDecoration(new ListLinePadding((int) getResources().getDimension(R.dimen.recycler_divider_space)));
		operationsRecyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		
		realtimeDatabaseDataSource.onTaskConditionValuesChange(taskId)
				.observe(this, new Observer<List<Condition>>() {
					@Override
					public void onChanged(List<Condition> conditions) {
						conditionsAdapter.setDataList(getConditionViewDataList(conditions));
//						conditionsAdapter.setDataList(generateConditionList());
					}
				});
		realtimeDatabaseDataSource.onTaskOperationValuesChange(taskId)
				.observe(this, new Observer<List<Operation>>() {
					@Override
					public void onChanged(List<Operation> operations) {
						operationsAdapter.setDataList(getOperationViewDataList(operations));
//						operationsAdapter.setDataList(generateOperationList());
					}
				});
	}
	
	private ArrayList<ConditionOrOperationViewData> getConditionViewDataList(List<Condition> conditions) {
		ArrayList<ConditionOrOperationViewData> conditionViewDataList = new ArrayList<>(conditions.size());
		
		for (Condition condition : conditions) {
			String mainText = "?"; // TODO: create text for the main line in ConditionOrOperationView
			String typeText = condition.getType(); // TODO: create text for the type line in ConditionOrOperationView
			
			ConditionOrOperationViewData conditionViewData = new ConditionOrOperationViewData(
					ConditionOrOperationViewData.TYPE_CONDITION,
					condition.getId(),
					condition.getType(),
					mainText,
					typeText,
					false
			);
			
			conditionViewDataList.add(conditionViewData);
		}
		
		return conditionViewDataList;
	}
	
	private ArrayList<ConditionOrOperationViewData> getOperationViewDataList(List<Operation> operations) {
		ArrayList<ConditionOrOperationViewData> operationViewDataList = new ArrayList<>(operations.size());
		
		for (Operation operation : operations) {
			String mainText = "?"; // TODO: create text for the main line in ConditionOrOperationView
			String typeText = operation.getType(); // TODO: create text for the type line in ConditionOrOperationView
			
			ConditionOrOperationViewData conditionViewData = new ConditionOrOperationViewData(
					ConditionOrOperationViewData.TYPE_CONDITION,
					operation.getId(),
					operation.getType(),
					mainText,
					typeText,
					false
			);
			
			operationViewDataList.add(conditionViewData);
		}
		
		return operationViewDataList;
	}
	
	// TODO: Replace placeholder data generator function with one that gets real data
	private ArrayList<String> generateCategoryList() {
		int numCategories = 6;
		ArrayList<String> categoryList = new ArrayList<>(numCategories);
		
		for (int i = 0; i < numCategories; i++) {
			categoryList.add("Category " + (i + 1));
		}
		
		return categoryList;
	}
	
	// TODO: Replace placeholder data generator function with one that gets real data
	private ArrayList<ConditionOrOperationViewData> generateConditionList() {
		String[] typeList = {ConditionOrOperationViewData.CONDITION_SCHEDULE, ConditionOrOperationViewData.CONDITION_TEMPERATURE};
		String[] mainTextList = {"10:30 on Week Days", "Temperature equal to 23°C"};
		String[] typeTextList = {"Schedule", "Temperature"};
		ArrayList<ConditionOrOperationViewData> operationDataList = new ArrayList<>();
		
		for (int i = 0; i < typeList.length; i++) {
			ConditionOrOperationViewData conditionData = new ConditionOrOperationViewData(
					ConditionOrOperationViewData.TYPE_CONDITION,
					null,
					typeList[i],
					mainTextList[i],
					typeTextList[i]
			);
			
			operationDataList.add(conditionData);
		}
		
		return operationDataList;
	}
	
	// TODO: Replace placeholder data generator function with one that gets real data
	private ArrayList<ConditionOrOperationViewData> generateOperationList() {
		String[] typeList = {ConditionOrOperationViewData.OPERATION_LIGHTS, ConditionOrOperationViewData.OPERATION_THERMOSTAT};
		String[] mainTextList = {"Set Brightness to 70% and Temperature to 30%", "Set target temp to 28°C"};
		String[] typeTextList = {"Lights", "Thermostat"};
		ArrayList<ConditionOrOperationViewData> conditionDataList = new ArrayList<>();
		
		for (int i = 0; i < typeList.length; i++) {
			ConditionOrOperationViewData operationData = new ConditionOrOperationViewData(
					ConditionOrOperationViewData.TYPE_OPERATION,
					null,
					typeList[i],
					mainTextList[i],
					typeTextList[i]
			);
			
			conditionDataList.add(operationData);
		}
		
		return conditionDataList;
	}
	
	private boolean nameEditTextDone(TextView textView, int keyCode, KeyEvent keyEvent) {
		if (keyCode == EditorInfo.IME_ACTION_DONE) {
			realtimeDatabaseDataSource.updateTaskName(taskId, Objects.requireNonNull(textView.getText()).toString());
			return true;
		}
		return false;
	}
	
	private void conditionsRecyclerViewItemClicked(View view) {
		ConditionOrOperationView conditionView = (ConditionOrOperationView) view;
		Intent intent = new Intent();
		
		intent.setClass(this, EditConditionActivity.class);
		intent.putExtra(ConditionOrOperationViewData.ARG_CONDITION, conditionView.getConditionOrOperationType());
		intent.putExtra(EXTRA_CONDITION_ID, conditionView.getConditionOrOperationId());
		
		startActivity(intent);
	}
	
	private void operationsRecyclerViewItemClicked(View view) {
		ConditionOrOperationView operationView = (ConditionOrOperationView) view;
		Intent intent = new Intent();
		
		intent.setClass(this, EditOperationActivity.class);
		intent.putExtra(ConditionOrOperationViewData.ARG_OPERATION, operationView.getConditionOrOperationType());
		intent.putExtra(EXTRA_OPERATION_ID, operationView.getConditionOrOperationId());
		
		startActivity(intent);
	}
	
	public void addConditionButtonClicked(View view) {
		startActivity(new Intent(this, EditConditionActivity.class));
	}
	
	public void addOperationButtonClicked(View view) {
		startActivity(new Intent(this, EditOperationActivity.class));
	}
	
	public void deleteButtonClicked(View view) {
		realtimeDatabaseDataSource.removeTask(taskId);
		finish();
	}
	
	public void saveButtonClicked(View view) {
		//TODO data handling
		realtimeDatabaseDataSource.updateTaskName(taskId, Objects.requireNonNull(nameEditText.getText()).toString());
		
//		String taskId = realtimeDatabaseDataSource.addTask("New task", "Test", "TestCategory");
//		realtimeDatabaseDataSource.addTaskOperation(taskId, 0, "Test", "TestDevice");
//		realtimeDatabaseDataSource.addTaskCondition(taskId, 0, "Test", "TestDevice");
		
		Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show(); // TODO: Remove placeholder toast
		finish();
	}
	
	
	@Override
	protected void onStop() {
		super.onStop();
		
		realtimeDatabaseDataSource.removeTaskConditionsValueChangesListener(taskId);
		realtimeDatabaseDataSource.removeTaskOperationsValueChangesListener(taskId);
	}
	
	/**
	 * Handles back button onClick event
	 *
	 * @param item non-null MenuItem
	 * @return Boolean
	 */
	@Override
	public boolean onOptionsItemSelected(@NonNull MenuItem item) {
		finish();
		return true;
	}
}