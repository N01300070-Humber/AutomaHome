package ca.humbermail.n01300070.automahome.ui.tasks.operation;

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

public class EditOperationActivity extends AppCompatActivity {
	
	private Button saveButton;
	private Button discardButton;
	
	private String operationType;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_operation);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		operationType = getIntent().getExtras().getString(ConditionOrOperationData.ARG_OPERATION);
		
		saveButton = findViewById(R.id.button_editOperation_save);
		discardButton = findViewById(R.id.button_editOperation_discard);
		
		Fragment fragment;
		switch (operationType) {
			case ConditionOrOperationData.OPERATION_LIGHTS:
				fragment = new EditOperationLightsFragment();
				break;
			case ConditionOrOperationData.OPERATION_THERMOSTAT:
				fragment = new EditOperationThermostatFragment();
				break;
			default:
				fragment = new ChooseOperationFragment();
				saveButton.setVisibility(View.GONE);
				discardButton.setVisibility(View.GONE);
		}
		changeActiveFragment(fragment);
	}
	
	private void changeActiveFragment(Fragment fragment) {
		getSupportFragmentManager().beginTransaction().add(R.id.fragment_editOperation, fragment).commit();
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