package ca.humbermail.n01300070.automahome.ui.tasks.condition;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import ca.humbermail.n01300070.automahome.R;

import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;

public class EditConditionScheduleFragment extends Fragment {
	private Context context;
	
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
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		final View root = inflater.inflate(R.layout.fragment_edit_condition_schedule, container, false);
		context = getActivity().getApplicationContext();
		
		timeTextInputLayout = root.findViewById(R.id.textInputLayout_editConditionSchedule_time);
		timeTextInputEditText = root.findViewById(R.id.textInputEditText_editConditionSchedule_time);
		weekDaysToggleGroup = root.findViewById(R.id.buttonToggleGroup_weekDays);
		sundayButton = root.findViewById(R.id.toggleButton_sunday);
		mondayButton = root.findViewById(R.id.toggleButton_monday);
		tuesdayButton = root.findViewById(R.id.toggleButton_tuesday);
		wednesdayButton = root.findViewById(R.id.toggleButton_wednesday);
		thursdayButton = root.findViewById(R.id.toggleButton_thursday);
		fridayButton = root.findViewById(R.id.toggleButton_friday);
		saturdayButton = root.findViewById(R.id.toggleButton_saturday);
		
		timeTextInputEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View view, boolean b) {
				if (view.isFocused()) {
					timeTextInputEditText.clearFocus();
					
					Calendar currentTime = Calendar.getInstance();
					
					timePickerDialog = new TimePickerDialog(root.getContext(),
							new TimePickerDialog.OnTimeSetListener() {
								@Override
								public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
									String period12Hour;
									int hour;
									Calendar selectedTime = Calendar.getInstance();
									
									selectedTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
									selectedTime.set(Calendar.MINUTE, minute);
									
									if (selectedTime.get(Calendar.AM_PM) == Calendar.AM) {
										period12Hour = "am";
									}
									else {
										period12Hour = "pm";
									}
									
									hour = hourOfDay % 12;
									if (hour == 0) {
										hour = 12;
									}
									
									timeTextInputEditText.setText(String.format("%d:%02d %s", hour, minute, period12Hour));
								}
							},
							currentTime.get(Calendar.HOUR_OF_DAY), currentTime.get(Calendar.MINUTE), false);
					timePickerDialog.setTitle(timeTextInputLayout.getHint());
					timePickerDialog.show();
				}
			}
		});
		
		
		return root;
	}
}