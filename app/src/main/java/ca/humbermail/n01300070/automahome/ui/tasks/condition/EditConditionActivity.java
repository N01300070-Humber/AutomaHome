package ca.humbermail.n01300070.automahome.ui.tasks.condition;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import ca.humbermail.n01300070.automahome.R;
import ca.humbermail.n01300070.automahome.data.model.ConditionOrOperationData;
import ca.humbermail.n01300070.automahome.data.model.DeviceOrTaskData;

public class EditConditionActivity extends AppCompatActivity {
	
	private Button saveButton;
	private Button discardButton;
	
	private String conditionType;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_condition);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		conditionType = getIntent().getExtras().getString(ConditionOrOperationData.ARG_CONDITION);
		
		saveButton = findViewById(R.id.button_editCondition_save);
		discardButton = findViewById(R.id.button_editCondition_discard);
		
		Fragment fragment;
		switch (conditionType) {
			case ConditionOrOperationData.CONDITION_SCHEDULE:
				fragment = new EditConditionScheduleFragment();
				break;
			case ConditionOrOperationData.CONDITION_MOVEMENT:
				fragment = new EditConditionMovementFragment();
				break;
			case ConditionOrOperationData.CONDITION_TEMPERATURE:
				fragment = new EditConditionTemperatureFragment();
				break;
			default:
				fragment = new ChooseConditionFragment();
				saveButton.setVisibility(View.GONE);
				discardButton.setVisibility(View.GONE);
		}
		changeActiveFragment(fragment);
	}
	
	private void changeActiveFragment(Fragment fragment) {
		getSupportFragmentManager().beginTransaction().add(R.id.fragment_editCondition, fragment).commit();
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