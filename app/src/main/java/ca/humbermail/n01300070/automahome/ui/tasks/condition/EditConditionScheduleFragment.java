package ca.humbermail.n01300070.automahome.ui.tasks.condition;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import ca.humbermail.n01300070.automahome.R;
import ca.humbermail.n01300070.automahome.data.RealtimeDatabaseDataSource;
import ca.humbermail.n01300070.automahome.utils.DateAndTime;
import ca.humbermail.n01300070.automahome.utils.NullHandler;

import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class EditConditionScheduleFragment extends Fragment {
	private static final String DATA_TIME_OF_DAY = "timeOfDay";
	private static final String KEY_HOUR_OF_DAY = "hourOfDay";
	private static final String KEY_MINUTE = "minute";
	private static final String DATA_DAYS_OF_WEEK = "daysOfWeek";
	private static final String KEY_MONDAY = "mon";
	private static final String KEY_TUESDAY = "tue";
	private static final String KEY_WEDNESDAY = "wed";
	private static final String KEY_THURSDAY = "thu";
	private static final String KEY_FRIDAY = "fri";
	private static final String KEY_SATURDAY = "sat";
	private static final String KEY_SUNDAY = "sun";
	
	private Context context;
	
	private EditConditionActivity editConditionActivity;
	private RealtimeDatabaseDataSource realtimeDatabaseDataSource;
	
	private TextInputLayout timeTextInputLayout;
	private TextInputEditText timeTextInputEditText;
	private MaterialButtonToggleGroup weekDaysToggleGroup;
	private MaterialButton sundayButton;
	private MaterialButton mondayButton;
	private MaterialButton tuesdayButton;
	private MaterialButton wednesdayButton;
	private MaterialButton thursdayButton;
	private MaterialButton fridayButton;
	private MaterialButton saturdayButton;
	
	private TimePickerDialog timePickerDialog;
	
	private String taskId;
	private String conditionId;
	private Map<String, Long> selectedTime = new HashMap<>();
	private Map<String, Boolean> selectedDays = new HashMap<>();
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		Log.d("EditCondScheduleFrag", "onCreateView called");
		
		// Inflate the layout for this fragment
		final View root = inflater.inflate(R.layout.fragment_edit_condition_schedule, container, false);
		context = requireContext();
		
		editConditionActivity = (EditConditionActivity) requireActivity();
		realtimeDatabaseDataSource = editConditionActivity.getRealtimeDatabaseDataSource();
		
		taskId = editConditionActivity.getSourceTaskId();
		conditionId = editConditionActivity.getConditionId();
		
		timeTextInputLayout = root.findViewById(R.id.textInputLayout_editConditionSchedule_time);
		timeTextInputEditText = root.findViewById(R.id.textInputEditText_editConditionSchedule_time);
		weekDaysToggleGroup = root.findViewById(R.id.buttonToggleGroup_weekDays);
		mondayButton = root.findViewById(R.id.toggleButton_monday);
		tuesdayButton = root.findViewById(R.id.toggleButton_tuesday);
		wednesdayButton = root.findViewById(R.id.toggleButton_wednesday);
		thursdayButton = root.findViewById(R.id.toggleButton_thursday);
		fridayButton = root.findViewById(R.id.toggleButton_friday);
		saturdayButton = root.findViewById(R.id.toggleButton_saturday);
		sundayButton = root.findViewById(R.id.toggleButton_sunday);
		
		timeTextInputEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View view, boolean b) {
				if (view.isFocused()) {
					Log.d("DatabaseDataSource", "timeTextInputEditText has gained focus");
					timeTextInputEditText.clearFocus();
					onTimeTextInputEditTextFocused();
				}
			}
		});
		mondayButton.addOnCheckedChangeListener(new MaterialButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(MaterialButton button, boolean isChecked) {
				Log.d("DatabaseDataSource", "mondayButton OnCheckedChangeListener called");
				selectedDays.put(KEY_MONDAY, isChecked);
				updateSelectedDays();
			}
		});
		tuesdayButton.addOnCheckedChangeListener(new MaterialButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(MaterialButton button, boolean isChecked) {
				Log.d("DatabaseDataSource", "tuesdayButton OnCheckedChangeListener called");
				selectedDays.put(KEY_TUESDAY, isChecked);
				updateSelectedDays();
			}
		});
		wednesdayButton.addOnCheckedChangeListener(new MaterialButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(MaterialButton button, boolean isChecked) {
				Log.d("DatabaseDataSource", "wednesdayButton OnCheckedChangeListener called");
				selectedDays.put(KEY_WEDNESDAY, isChecked);
				updateSelectedDays();
			}
		});
		thursdayButton.addOnCheckedChangeListener(new MaterialButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(MaterialButton button, boolean isChecked) {
				Log.d("DatabaseDataSource", "thursdayButton OnCheckedChangeListener called");
				selectedDays.put(KEY_THURSDAY, isChecked);
				updateSelectedDays();
			}
		});
		fridayButton.addOnCheckedChangeListener(new MaterialButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(MaterialButton button, boolean isChecked) {
				Log.d("DatabaseDataSource", "fridayButton OnCheckedChangeListener called");
				selectedDays.put(KEY_FRIDAY, isChecked);
				updateSelectedDays();
			}
		});
		saturdayButton.addOnCheckedChangeListener(new MaterialButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(MaterialButton button, boolean isChecked) {
				Log.d("DatabaseDataSource", "saturdayButton OnCheckedChangeListener called");
				selectedDays.put(KEY_SATURDAY, isChecked);
				updateSelectedDays();
			}
		});
		sundayButton.addOnCheckedChangeListener(new MaterialButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(MaterialButton button, boolean isChecked) {
				Log.d("DatabaseDataSource", "sundayButton OnCheckedChangeListener called");
				selectedDays.put(KEY_SUNDAY, isChecked);
				updateSelectedDays();
			}
		});
		
		return root;
	}
	
	@Override
	public void onStart() {
		Log.d("EditCondScheduleFrag", "onStart called");
		super.onStart();
		
		realtimeDatabaseDataSource.onTaskConditionDataValueChange(taskId, conditionId, DATA_TIME_OF_DAY).observe(this, new Observer<Object>() {
			@Override
			public void onChanged(Object o) {
				Log.d("DatabaseDataSource", "Detected change in selected time data");
				if (o != null) {
					selectedTime = (Map<String, Long>) o;
//					int hourOfDay = (int) time.getHourOfDay();
//					int minute = (int) time.getMinute();
					
					timeTextInputEditText.setText(DateAndTime.get12HourTime(
							Objects.requireNonNull(selectedTime.get(KEY_HOUR_OF_DAY)).intValue(),
							Objects.requireNonNull(selectedTime.get(KEY_MINUTE)).intValue()
					));
				}
			}
		});
		realtimeDatabaseDataSource.onTaskConditionDataValueChange(taskId, conditionId, DATA_DAYS_OF_WEEK).observe(this, new Observer<Object>() {
			@Override
			public void onChanged(Object o) {
				Log.d("DatabaseDataSource", "Detected change in selected days data");
				if (o != null) {
					selectedDays = (Map<String, Boolean>) o;
					
					mondayButton.setChecked(NullHandler.setDefaultIfNull(selectedDays.get(KEY_MONDAY), false));
					tuesdayButton.setChecked(NullHandler.setDefaultIfNull(selectedDays.get(KEY_TUESDAY), false));
					wednesdayButton.setChecked(NullHandler.setDefaultIfNull(selectedDays.get(KEY_WEDNESDAY), false));
					thursdayButton.setChecked(NullHandler.setDefaultIfNull(selectedDays.get(KEY_THURSDAY), false));
					fridayButton.setChecked(NullHandler.setDefaultIfNull(selectedDays.get(KEY_FRIDAY), false));
					saturdayButton.setChecked(NullHandler.setDefaultIfNull(selectedDays.get(KEY_SATURDAY), false));
					sundayButton.setChecked(NullHandler.setDefaultIfNull(selectedDays.get(KEY_SUNDAY), false));
				}
			}
		});
	}
	
	private void onTimeTextInputEditTextFocused() {
		Log.d("EditCondScheduleFrag", "onTimeTextInputEditTextFocused called");
		
		Calendar currentTime = Calendar.getInstance();
		
		timePickerDialog = new TimePickerDialog(context,
				new TimePickerDialog.OnTimeSetListener() {
					@Override
					public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
						timeTextInputEditText.setText(DateAndTime.get12HourTime(hourOfDay, minute));
						selectedTime.put(KEY_HOUR_OF_DAY, (long) hourOfDay);
						selectedTime.put(KEY_MINUTE, (long) minute);
						updateTime();
					}
				},
				currentTime.get(Calendar.HOUR_OF_DAY),
				currentTime.get(Calendar.MINUTE),
				false);
		
		timePickerDialog.setTitle(timeTextInputLayout.getHint());
		timePickerDialog.show();
	}
	
	private void updateTime() {
		Log.d("EditCondScheduleFrag", "updateTime called");
		
		realtimeDatabaseDataSource.setTaskConditionData(taskId, conditionId, DATA_TIME_OF_DAY, selectedTime);
	}
	
	private void updateSelectedDays() {
		Log.d("EditCondScheduleFrag", "updateSelectedDays called");
		
		realtimeDatabaseDataSource.setTaskConditionData(taskId, conditionId, DATA_DAYS_OF_WEEK, selectedDays);
	}
	
	@Override
	public void onStop() {
		Log.d("EditCondScheduleFrag", "onStop called");
		super.onStop();
		
		realtimeDatabaseDataSource.removeTaskConditionDataValueChangesListener(taskId, conditionId, DATA_TIME_OF_DAY);
		realtimeDatabaseDataSource.removeTaskConditionDataValueChangesListener(taskId, conditionId, DATA_DAYS_OF_WEEK);
	}
}