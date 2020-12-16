package ca.humbermail.n01300070.automahome.ui.tasks.condition;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.Objects;

import ca.humbermail.n01300070.automahome.R;
import ca.humbermail.n01300070.automahome.data.RealtimeDatabaseDataSource;
import ca.humbermail.n01300070.automahome.data.model.ConditionOrOperationViewData;
import ca.humbermail.n01300070.automahome.ui.CustomActivity;
import ca.humbermail.n01300070.automahome.ui.tasks.EditTaskActivity;

public class EditConditionActivity extends CustomActivity {
	
	private Button saveButton;
	private Button discardButton;
	
	private Fragment fragment;
	
	private RealtimeDatabaseDataSource realtimeDatabaseDataSource;
	private String taskId;
	private String conditionId;
	private String conditionType;
	private int position;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_condition);
		Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
		
		setRealtimeDatabaseDataSource(new RealtimeDatabaseDataSource());
		realtimeDatabaseDataSource = getRealtimeDatabaseDataSource();
		
		Bundle bundle = getIntent().getExtras();
		taskId = bundle.getString(EditTaskActivity.EXTRA_TASK_ID);
		conditionId = bundle.getString(EditTaskActivity.EXTRA_CONDITION_ID);
		conditionType = bundle.getString(ConditionOrOperationViewData.EXTRA_CONDITION_TYPE);
		if (conditionType == null) {
			conditionType = "";
		}
		position = bundle.getInt(EditTaskActivity.EXTRA_POSITION);
		
		saveButton = findViewById(R.id.button_editCondition_save);
		discardButton = findViewById(R.id.button_editCondition_delete);
		
		setActiveFragment(conditionType);
	}
	
	public void onConditionTypeSelected(String conditionType) {
		getSupportFragmentManager().beginTransaction().remove(fragment).commit();
		saveButton.setVisibility(View.VISIBLE);
		discardButton.setVisibility(View.VISIBLE);
		
		this.conditionType = conditionType;
		this.conditionId = realtimeDatabaseDataSource.addTaskCondition(taskId, position, conditionType, "");
		
		setActiveFragment(conditionType);
	}
	
	private void setActiveFragment(String conditionType) {
		switch (conditionType) {
			case ConditionOrOperationViewData.CONDITION_SCHEDULE:
				fragment = new EditConditionScheduleFragment();
				break;
			case ConditionOrOperationViewData.CONDITION_MOVEMENT:
				fragment = new EditConditionMovementFragment();
				break;
			case ConditionOrOperationViewData.CONDITION_TEMPERATURE:
				fragment = new EditConditionTemperatureFragment();
				break;
			default:
				fragment = new ChooseConditionFragment();
				saveButton.setVisibility(View.GONE);
				discardButton.setVisibility(View.GONE);
		}
		getSupportFragmentManager().beginTransaction().add(R.id.fragment_editCondition, fragment).commit();
	}
	
	public void discardButtonClicked(View view) {
		//TODO data handling

		finish();
	}
	
	public void saveButtonClicked(View view) {
		//TODO data handling
		Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show(); // TODO: Remove placeholder toast
		System.out.println(fragment);
		if(fragment instanceof EditConditionTemperatureFragment) {
			((EditConditionTemperatureFragment) fragment).saveTemp();
		}

		finish();
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
	
	public String getSourceTaskId() {
		return this.taskId;
	}
	
	public String getConditionId() {
		return this.conditionId;
	}
}