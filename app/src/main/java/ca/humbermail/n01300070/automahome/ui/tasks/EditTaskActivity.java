package ca.humbermail.n01300070.automahome.ui.tasks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ca.humbermail.n01300070.automahome.R;
import ca.humbermail.n01300070.automahome.components.ConditionOrOperationView;
import ca.humbermail.n01300070.automahome.components.ConditionOrOperationViewAdapter;
import ca.humbermail.n01300070.automahome.components.FavoriteSelectView;
import ca.humbermail.n01300070.automahome.components.ListLinePadding;
import ca.humbermail.n01300070.automahome.components.NonScrollingLinerLayoutManager;
import ca.humbermail.n01300070.automahome.data.model.ConditionOrOperationData;
import ca.humbermail.n01300070.automahome.ui.tasks.condition.EditConditionActivity;
import ca.humbermail.n01300070.automahome.ui.tasks.operation.EditOperationActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class EditTaskActivity extends AppCompatActivity {
	
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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_task);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		addConditionButton = findViewById(R.id.button_addCondition);
        addOperationButton = findViewById(R.id.button_addOperation);
        saveButton = findViewById(R.id.button_editTask_save);
        discardButton = findViewById(R.id.button_editTask_discard);
        favoriteSelectView = findViewById(R.id.favoriteSelectView_editTask);
        conditionsRecyclerView = findViewById(R.id.recyclerView_conditions);
        operationsRecyclerView = findViewById(R.id.recyclerView_operations);
        
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
        conditionsAdapter = new ConditionOrOperationViewAdapter(getApplicationContext(), generateConditionList(), conditionsOnClickListener);
		operationsAdapter = new ConditionOrOperationViewAdapter(getApplicationContext(), generateOperationList(), operationsOnClickListener);
		
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
	
	private ArrayList<String> generateCategoryList() {
		int numCategories = 6;
		ArrayList<String> categoryList = new ArrayList<>(numCategories);
		
		for (int i = 0; i < numCategories; i++) {
			categoryList.add("Category " + (i + 1));
		}
		
		return categoryList;
	}
	
	// TODO: Replace placeholder data generator function with one that gets real data
	private ArrayList<ConditionOrOperationData> generateConditionList() {
		String[] typeList = {ConditionOrOperationData.CONDITION_SCHEDULE, ConditionOrOperationData.CONDITION_TEMPERATURE, ConditionOrOperationData.CONDITION_MOVEMENT};
		String[] mainTextList = {"10:30 on Week Days", "Temperature equal to 23°C", "Moving Toward Room 3"};
		String[] typeTextList = {"Schedule", "Temperature", "Movement"};
		ArrayList<ConditionOrOperationData> operationDataList = new ArrayList<>();
		
		for (int i = 0; i < typeList.length; i++) {
			ConditionOrOperationData operationData = new ConditionOrOperationData(
					ConditionOrOperationData.TYPE_OPERATION,
					typeList[i],
					mainTextList[i],
					typeTextList[i]
			);
			
			operationDataList.add(operationData);
		}
		
		return operationDataList;
	}
	
	// TODO: Replace placeholder data generator function with one that gets real data
	private ArrayList<ConditionOrOperationData> generateOperationList() {
		String[] typeList = {ConditionOrOperationData.OPERATION_LIGHTS, ConditionOrOperationData.OPERATION_THERMOSTAT};
		String[] mainTextList = {"Set Brightness to 70%, Temp to 30%", "Set target temp to 28°C"};
		String[] typeTextList = {"Lights", "Thermostat"};
		ArrayList<ConditionOrOperationData> conditionDataList = new ArrayList<>();
		
		for (int i = 0; i < typeList.length; i++) {
			ConditionOrOperationData conditionData = new ConditionOrOperationData(
					ConditionOrOperationData.TYPE_CONDITION,
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
		intent.putExtra(ConditionOrOperationData.ARG_CONDITION, conditionView.getConditionOrOperationType());
		
		startActivity(intent);
    }
    
    private void operationsRecyclerViewItemClicked(View view) {
		ConditionOrOperationView operationView = (ConditionOrOperationView) view;
		Intent intent = new Intent();
		
		intent.setClass(this, EditOperationActivity.class);
		intent.putExtra(ConditionOrOperationData.ARG_OPERATION, operationView.getConditionOrOperationType());
		
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
		setResult(Activity.RESULT_CANCELED);
		finish();
	}
	
	public void saveButtonClicked(View view) {
		//TODO data handling
		Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show(); // TODO: Remove placeholder toast
		setResult(Activity.RESULT_OK);
		finish();
	}
	
	/**
	 * Handles back button onClick event
	 * @param item non-null MenuItem
	 * @return Boolean
	 */
	@Override
	public boolean onOptionsItemSelected(@NonNull MenuItem item) {
		setResult(Activity.RESULT_CANCELED);
		finish();
		return true;
	}
}