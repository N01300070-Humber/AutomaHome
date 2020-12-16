package ca.humbermail.n01300070.automahome.ui.tasks.operation;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;

import java.util.Objects;

import ca.humbermail.n01300070.automahome.R;
import ca.humbermail.n01300070.automahome.data.RealtimeDatabaseDataSource;
import ca.humbermail.n01300070.automahome.data.model.ConditionOrOperationViewData;
import ca.humbermail.n01300070.automahome.ui.CustomActivity;
import ca.humbermail.n01300070.automahome.ui.tasks.EditTaskActivity;

public class EditOperationActivity extends CustomActivity {
	
	private Button saveButton;
	private Button discardButton;
	
	private Fragment fragment;
	
	private RealtimeDatabaseDataSource realtimeDatabaseDataSource;
	private String taskId;
	private String operationId;
	private String operationType;
	private int position;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_operation);
		Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
		
		setRealtimeDatabaseDataSource(new RealtimeDatabaseDataSource());
		realtimeDatabaseDataSource = getRealtimeDatabaseDataSource();
		
		Bundle bundle = getIntent().getExtras();
		taskId = bundle.getString(EditTaskActivity.EXTRA_TASK_ID);
		operationId = bundle.getString(EditTaskActivity.EXTRA_CONDITION_ID);
		operationType = bundle.getString(ConditionOrOperationViewData.EXTRA_CONDITION_TYPE);
		if (operationType == null) {
			operationType = "";
		}
		position = bundle.getInt(EditTaskActivity.EXTRA_POSITION);
		
		saveButton = findViewById(R.id.button_editOperation_save);
		discardButton = findViewById(R.id.button_editOperation_delete);
		
		setActiveFragment(operationType);
	}
	
	public void onOperationTypeSelected(String operationType) {
		getSupportFragmentManager().beginTransaction().remove(fragment).commit();
		saveButton.setVisibility(View.VISIBLE);
		discardButton.setVisibility(View.VISIBLE);
		
		this.operationType = operationType;
		this.operationId = realtimeDatabaseDataSource.addTaskOperation(taskId, position, operationType, "");
		
		setActiveFragment(operationType);
	}
	
	private void setActiveFragment(String operationType) {
		switch (operationType) {
			case ConditionOrOperationViewData.OPERATION_LIGHTS:
				fragment = new EditOperationLightsFragment();
				break;
			case ConditionOrOperationViewData.OPERATION_THERMOSTAT:
				fragment = new EditOperationThermostatFragment();
				break;
			default:
				fragment = new ChooseOperationFragment();
				saveButton.setVisibility(View.GONE);
				discardButton.setVisibility(View.GONE);
		}
		getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainer_editOperation, fragment).commit();
	}
	
	public void discardButtonClicked(View view) {
		realtimeDatabaseDataSource.removeTaskOperation(taskId, operationId);
		
		finish();
	}
	
	public void saveButtonClicked(View view) {
		//TODO data handling

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
}