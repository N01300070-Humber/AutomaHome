package ca.humbermail.n01300070.automahome.ui.tasks;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import ca.humbermail.n01300070.automahome.R;
import ca.humbermail.n01300070.automahome.components.ConditionOrOperationView;
import ca.humbermail.n01300070.automahome.components.ConditionOrOperationViewAdapter;
import ca.humbermail.n01300070.automahome.components.FavoriteSelectView;
import ca.humbermail.n01300070.automahome.components.ListLinePadding;
import ca.humbermail.n01300070.automahome.components.NonScrollingLinerLayoutManager;
import ca.humbermail.n01300070.automahome.data.model.ConditionOrOperationViewData;
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

    ArrayList<ConditionOrOperationViewData> getConditionsFromResults = new ArrayList<>();
	ArrayList<ConditionOrOperationViewData> getOperationsFromResults = new ArrayList<>();
	final int ADD_CONDITION = 0;
	final int ADD_OPERATION = 1;
	
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

	@Override
	protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == ADD_CONDITION) {
			if (resultCode == Activity.RESULT_OK) {
				ConditionOrOperationViewData newObject = data.getParcelableExtra("New Condition");
				getConditionsFromResults.add(newObject);
			}
		}

		if (requestCode == ADD_OPERATION) {
			if (resultCode == Activity.RESULT_OK){
				ConditionOrOperationViewData newObject = data.getParcelableExtra("New Operation");
				getOperationsFromResults.add(newObject);
			}
		}
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
	private ArrayList<ConditionOrOperationViewData> generateConditionList() {
		String[] typeList = {ConditionOrOperationViewData.CONDITION_SCHEDULE, ConditionOrOperationViewData.CONDITION_TEMPERATURE};
		String[] mainTextList = {"10:30 on Week Days", "Temperature equal to 23°C"};
		String[] typeTextList = {"Schedule", "Temperature"};
		ArrayList<ConditionOrOperationViewData> operationDataList = new ArrayList<>();
		if (!getConditionsFromResults.isEmpty()) {
			for (ConditionOrOperationViewData operationData: getConditionsFromResults) {
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
		startActivityForResult(new Intent(this, EditConditionActivity.class),ADD_CONDITION);
	}
	
	public void addOperationButtonClicked(View view) {
		startActivityForResult(new Intent(this, EditOperationActivity.class),ADD_OPERATION);
	}
	
	public void discardButtonClicked(View view) {
		//TODO data handling
		setResult(Activity.RESULT_CANCELED);
		finish();
	}
	
	public void saveButtonClicked(View view) {
		//TODO data handling
		Toast.makeText(getApplicationContext(), "Saved 1", Toast.LENGTH_SHORT).show(); // TODO: Remove placeholder toast
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