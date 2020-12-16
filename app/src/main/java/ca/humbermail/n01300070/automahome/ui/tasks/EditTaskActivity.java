package ca.humbermail.n01300070.automahome.ui.tasks;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
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
	public static final String EXTRA_TASK_CATEGORY = "category";
	public static final String EXTRA_CONDITION_ID = "conditionId";
	public static final String EXTRA_OPERATION_ID = "operationId";
	public static final String EXTRA_POSITION = "position";
	
	private static final String DEFAULT_NAME = "Untitled Task";
	
	
	private EditTaskViewModel editTaskViewModel;
	
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
	private ConditionOrOperationViewAdapter.OnItemClickListener conditionsOnItemClickListener;
	private ConditionOrOperationViewAdapter.OnItemClickListener operationsOnItemClickListener;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		editTaskViewModel = new ViewModelProvider(this).get(EditTaskViewModel.class);
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
			
			String category = bundle.getString(EXTRA_TASK_CATEGORY);
			if (category != null && !category.isEmpty()) {
				favoriteSelectView.setChecked(true);
				favoriteSelectView.setText(category);
			}
		}
		
		nameEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView textView, int keyCode, KeyEvent keyEvent) {
				if (keyCode == EditorInfo.IME_ACTION_DONE) {
					realtimeDatabaseDataSource.updateTaskName(taskId,
							Objects.requireNonNull(textView.getText()).toString());
					textView.clearFocus();
					((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
							.hideSoftInputFromWindow(textView.getWindowToken(),
									InputMethodManager.HIDE_NOT_ALWAYS);
					return true;
				}
				return false;
			}
		});
		conditionsOnItemClickListener = new ConditionOrOperationViewAdapter.OnItemClickListener() {
			@Override
			public void onItemClick(View view, int position) {
				Toast.makeText(getApplicationContext(), "Position: " + position, Toast.LENGTH_SHORT).show(); // Remove debugging toast
				conditionsRecyclerViewItemClicked(view, position);
			}
		};
		operationsOnItemClickListener = new ConditionOrOperationViewAdapter.OnItemClickListener() {
			@Override
			public void onItemClick(View view, int position) {
				Toast.makeText(getApplicationContext(), "Position: " + position, Toast.LENGTH_SHORT).show(); // Remove debugging toast
				operationsRecyclerViewItemClicked(view, position);
			}
		};
		favoriteSelectView.setOnCheckedChangeListener(new MaterialButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(MaterialButton button, boolean isChecked) {
				if (isChecked) {
					realtimeDatabaseDataSource.updateTaskCategory(taskId, favoriteSelectView.getText());
				} else {
					realtimeDatabaseDataSource.updateTaskCategory(taskId, "");
				}
			}
		});
		favoriteSelectView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				saveFavoriteCategory();
			}
		});
		favoriteSelectView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView textView, int keyCode, KeyEvent keyEvent) {
				if (keyCode == EditorInfo.IME_ACTION_DONE) {
					realtimeDatabaseDataSource.updateTaskCategory(taskId,
							Objects.requireNonNull(textView.getText()).toString());
					textView.clearFocus();
					((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
							.hideSoftInputFromWindow(textView.getWindowToken(),
									InputMethodManager.HIDE_NOT_ALWAYS);
					return true;
				}
				return false;
			}
		});
		
		favoriteSelectView.setAutoCompleteLabels(editTaskViewModel.generateCategoryList());
		conditionsAdapter = new ConditionOrOperationViewAdapter(this, conditionsOnItemClickListener);
		operationsAdapter = new ConditionOrOperationViewAdapter(this, operationsOnItemClickListener);
		
		//Conditions Recycler
		conditionsRecyclerView.setLayoutManager(new NonScrollingLinerLayoutManager(getApplicationContext()));
		conditionsRecyclerView.setAdapter(conditionsAdapter);
		conditionsRecyclerView.addItemDecoration(new ListLinePadding((int) getResources().getDimension(R.dimen.recycler_divider_space)));
		conditionsRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
		
		//Operations Recycler
		operationsRecyclerView.setLayoutManager(new NonScrollingLinerLayoutManager(getApplicationContext()));
		operationsRecyclerView.setAdapter(operationsAdapter);
		operationsRecyclerView.addItemDecoration(new ListLinePadding((int) getResources().getDimension(R.dimen.recycler_divider_space)));
		operationsRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		
		realtimeDatabaseDataSource.onTaskConditionValuesChange(taskId)
				.observe(this, new Observer<List<Condition>>() {
					@Override
					public void onChanged(List<Condition> conditions) {
						conditionsAdapter.setDataList(editTaskViewModel.getConditionViewDataList(conditions));
					}
				});
		realtimeDatabaseDataSource.onTaskOperationValuesChange(taskId)
				.observe(this, new Observer<List<Operation>>() {
					@Override
					public void onChanged(List<Operation> operations) {
						operationsAdapter.setDataList(editTaskViewModel.getOperationViewDataList(operations));
					}
				});
	}
	
	private void conditionsRecyclerViewItemClicked(View view, int position) {
		ConditionOrOperationView conditionView = (ConditionOrOperationView) view;
		Intent intent = new Intent(this, EditConditionActivity.class);
		
		intent.putExtra(EXTRA_TASK_ID, taskId);
		intent.putExtra(EXTRA_CONDITION_ID, conditionView.getConditionOrOperationId());
		intent.putExtra(ConditionOrOperationViewData.EXTRA_CONDITION_TYPE, conditionView.getConditionOrOperationType());
		intent.putExtra(EXTRA_POSITION, position);
		
		startActivity(intent);
	}
	
	private void operationsRecyclerViewItemClicked(View view, int position) {
		ConditionOrOperationView operationView = (ConditionOrOperationView) view;
		Intent intent = new Intent(this, EditOperationActivity.class);
		
		intent.putExtra(EXTRA_TASK_ID, taskId);
		intent.putExtra(EXTRA_OPERATION_ID, operationView.getConditionOrOperationId());
		intent.putExtra(ConditionOrOperationViewData.EXTRA_OPERATION_TYPE, operationView.getConditionOrOperationType());
		intent.putExtra(EXTRA_POSITION, position);
		
		startActivity(intent);
	}
	
	public void addConditionButtonClicked(View view) {
		Intent intent = new Intent(this, EditConditionActivity.class);
		
		intent.putExtra(EXTRA_TASK_ID, taskId);
		intent.putExtra(EXTRA_POSITION, conditionsAdapter.getItemCount());
		
		startActivity(intent);
	}
	
	public void addOperationButtonClicked(View view) {
		Intent intent = new Intent(this, EditOperationActivity.class);
		
		intent.putExtra(EXTRA_TASK_ID, taskId);
		intent.putExtra(EXTRA_POSITION, operationsAdapter.getItemCount());
		
		startActivity(intent);
	}
	
	public void deleteButtonClicked(View view) {
		realtimeDatabaseDataSource.removeTask(taskId);
		finish();
	}
	
	public void saveButtonClicked(View view) {
		saveName();
		saveFavoriteCategory();
		finish();
	}
	
	private void saveName() {
		realtimeDatabaseDataSource.updateTaskName(taskId, Objects.requireNonNull(nameEditText.getText()).toString());
	}
	
	private void saveFavoriteCategory() {
		if (favoriteSelectView.isChecked()) {
			realtimeDatabaseDataSource.updateTaskCategory(taskId, favoriteSelectView.getText());
		} else {
			realtimeDatabaseDataSource.updateTaskCategory(taskId, "");
		}
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