package ca.humbermail.n01300070.automahome.ui.tasks.condition;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import ca.humbermail.n01300070.automahome.R;
import ca.humbermail.n01300070.automahome.data.model.ConditionOrOperationViewData;

public class EditConditionActivity extends AppCompatActivity {
	
	private Button saveButton;
	private Button discardButton;
	
	private Fragment fragment;
	
	private String conditionType;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_condition);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		Bundle arguments = getIntent().getExtras();
		if (arguments == null) {
			conditionType = "";
		}
		else {
			conditionType = arguments.getString(ConditionOrOperationViewData.ARG_CONDITION);
		}
		
		saveButton = findViewById(R.id.button_editCondition_save);
		discardButton = findViewById(R.id.button_editCondition_discard);
		
		setActiveFragment(conditionType);
	}
	
	public void changeActiveFragment(String conditionType) {
		getSupportFragmentManager().beginTransaction().remove(fragment).commit();
		saveButton.setVisibility(View.VISIBLE);
		discardButton.setVisibility(View.VISIBLE);
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