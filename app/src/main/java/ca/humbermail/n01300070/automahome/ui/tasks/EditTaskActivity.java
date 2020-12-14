package ca.humbermail.n01300070.automahome.ui.tasks;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Objects;

import ca.humbermail.n01300070.automahome.R;
import ca.humbermail.n01300070.automahome.components.ConditionOrOperationView;
import ca.humbermail.n01300070.automahome.components.ConditionOrOperationViewAdapter;
import ca.humbermail.n01300070.automahome.components.FavoriteSelectView;
import ca.humbermail.n01300070.automahome.components.ListLinePadding;
import ca.humbermail.n01300070.automahome.components.NonScrollingLinerLayoutManager;
import ca.humbermail.n01300070.automahome.data.PreferenceKeys;
import ca.humbermail.n01300070.automahome.data.RealtimeDatabaseDataSource;
import ca.humbermail.n01300070.automahome.data.model.ConditionOrOperationViewData;
import ca.humbermail.n01300070.automahome.ui.CustomActivity;
import ca.humbermail.n01300070.automahome.ui.tasks.condition.EditConditionActivity;
import ca.humbermail.n01300070.automahome.ui.tasks.operation.EditOperationActivity;

public class EditTaskActivity extends CustomActivity {
	
	private RealtimeDatabaseDataSource realtimeDatabaseDataSource;
	
	private Button addConditionButton;
	private Button addOperationButton;
	private Button saveButton;
	private Button discardButton;
	private FavoriteSelectView favoriteSelectView;
	private RecyclerView conditionsRecyclerView;
	private RecyclerView operationsRecyclerView;
	
	private ConditionOrOperationViewAdapter conditionsAdapter;
	private ConditionOrOperationViewAdapter operationsAdapter;
	private View.OnClickListener conditionsOnClickListener;
	private View.OnClickListener operationsOnClickListener;
	
	ArrayList<ConditionOrOperationViewData> getConditionsFromResults = new ArrayList<>();
	ArrayList<ConditionOrOperationViewData> getOperationsFromResults = new ArrayList<>();
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_task);
		Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
		
		addConditionButton = findViewById(R.id.button_addCondition);
		addOperationButton = findViewById(R.id.button_addOperation);
		saveButton = findViewById(R.id.button_editTask_save);
		discardButton = findViewById(R.id.button_editTask_discard);
		favoriteSelectView = findViewById(R.id.favoriteSelectView_editTask);
		conditionsRecyclerView = findViewById(R.id.recyclerView_conditions);
		operationsRecyclerView = findViewById(R.id.recyclerView_operations);
		
		setRealtimeDatabaseDataSource(new RealtimeDatabaseDataSource());
		realtimeDatabaseDataSource = getRealtimeDatabaseDataSource();
		
		realtimeDatabaseDataSource.setCurrentHomeId(
				getSharedPreferences(PreferenceKeys.KEY_SESSION, Context.MODE_PRIVATE)
						.getString(PreferenceKeys.KEY_SESSION_SELECTED_HOME, "")
		);
		
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
		
		//TODO
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
		if (!getConditionsFromResults.isEmpty()) {
			for (ConditionOrOperationViewData operationData : getConditionsFromResults) {
				operationDataList.add(operationData);
			}
		}
		
		for (int i = 0; i < typeList.length; i++) {
			ConditionOrOperationViewData operationData = new ConditionOrOperationViewData(
					ConditionOrOperationViewData.TYPE_CONDITION,
					typeList[i],
					mainTextList[i],
					typeTextList[i]
			);
			
			operationDataList.add(operationData);
		}
		
		return operationDataList;
	}
	
	// TODO: Replace placeholder data generator function with one that gets real data
	private ArrayList<ConditionOrOperationViewData> generateOperationList() {
		String[] typeList = {ConditionOrOperationViewData.OPERATION_LIGHTS, ConditionOrOperationViewData.OPERATION_THERMOSTAT};
		String[] mainTextList = {"Set Brightness to 70%, Temp to 30%", "Set target temp to 28°C"};
		String[] typeTextList = {"Lights", "Thermostat"};
		ArrayList<ConditionOrOperationViewData> conditionDataList = new ArrayList<>();
		
		for (int i = 0; i < typeList.length; i++) {
			ConditionOrOperationViewData conditionData = new ConditionOrOperationViewData(
					ConditionOrOperationViewData.TYPE_OPERATION,
					typeList[i],
					mainTextList[i],
					typeTextList[i]
			);
			
			conditionDataList.add(conditionData);
		}
		
		return conditionDataList;
	}
	
	private void conditionsRecyclerViewItemClicked(View view) {
		ConditionOrOperationView conditionView = (ConditionOrOperationView) view;
		Intent intent = new Intent();
		
		intent.setClass(this, EditConditionActivity.class);
		intent.putExtra(ConditionOrOperationViewData.ARG_CONDITION, conditionView.getConditionOrOperationType());
		
		startActivity(intent);
	}
	
	private void operationsRecyclerViewItemClicked(View view) {
		ConditionOrOperationView operationView = (ConditionOrOperationView) view;
		Intent intent = new Intent();
		
		intent.setClass(this, EditOperationActivity.class);
		intent.putExtra(ConditionOrOperationViewData.ARG_OPERATION, operationView.getConditionOrOperationType());
		
		startActivity(intent);
	}
	
	public void addConditionButtonClicked(View view) {
		startActivity(new Intent(this, EditConditionActivity.class));
	}
	
	public void addOperationButtonClicked(View view) {
		startActivity(new Intent(this, EditOperationActivity.class));
	}
	
	public void discardButtonClicked(View view) {
		//TODO data handling
		
		finish();
	}
	
	public void saveButtonClicked(View view) {
		//TODO data handling
		String taskId = realtimeDatabaseDataSource.addTask("New task", "Test", "TestCategory");
		realtimeDatabaseDataSource.addTaskOperation(taskId, 0, "Operation", "TestDevice");
		realtimeDatabaseDataSource.addTaskCondition(taskId, 0, "Condition", "TestDevice");
		
		Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show(); // TODO: Remove placeholder toast
		finish();
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