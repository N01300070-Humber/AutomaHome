package ca.humbermail.n01300070.automahome.ui.tasks;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.ArrayList;
import java.util.List;

import ca.humbermail.n01300070.automahome.R;
import ca.humbermail.n01300070.automahome.data.model.Condition;
import ca.humbermail.n01300070.automahome.data.model.ConditionOrOperationViewData;
import ca.humbermail.n01300070.automahome.data.model.Operation;

public class EditTaskViewModel extends AndroidViewModel {
	
	public EditTaskViewModel(@NonNull Application application) {
		super(application);
	}
	
	// TODO: Replace placeholder data generator function with one that gets real data
	public ArrayList<String> generateCategoryList() {
		int numCategories = 6;
		ArrayList<String> categoryList = new ArrayList<>(numCategories);
		
		for (int i = 0; i < numCategories; i++) {
			categoryList.add("Category " + (i + 1));
		}
		
		return categoryList;
	}
	
	public ArrayList<ConditionOrOperationViewData> getConditionViewDataList(List<Condition> conditions) {
		int listSize = conditions.size();
		boolean dragHandleVisible = listSize > 1;
		ArrayList<ConditionOrOperationViewData> conditionViewDataList = new ArrayList<>(listSize);
		
		for (Condition condition : conditions) {
			ConditionOrOperationViewData conditionViewData = new ConditionOrOperationViewData(
					ConditionOrOperationViewData.TYPE_CONDITION,
					condition.getId(),
					condition.getType()
			);
			
			switch (condition.getType()) {
				case ConditionOrOperationViewData.CONDITION_SCHEDULE:
					conditionViewData.setMainText(getApplication().getString(R.string.condition_schedule_settings,
							getSimpleTimeString(),
							getSelectedDaysString()));
					conditionViewData.setTypeText(getApplication().getString(R.string.schedule));
					break;
				case ConditionOrOperationViewData.CONDITION_TEMPERATURE:
					conditionViewData.setMainText(getApplication().getString(R.string.condition_temperature_settings, "logicStatement", 23, "C")); // TODO: Replace hardcoded number and strings with data from database
					conditionViewData.setTypeText(getApplication().getString(R.string.temperature));
					break;
				case ConditionOrOperationViewData.CONDITION_MOVEMENT:
					conditionViewData.setMainText(getApplication().getString(R.string.condition_movement_settings, "destinationRoom", "sourceRoom")); // TODO: Replace hardcoded strings with data from database
					conditionViewData.setTypeText(getApplication().getString(R.string.movement));
					break;
				default:
					conditionViewData.setMainText(getApplication().getString(R.string.condition_unknown_type));
					conditionViewData.setTypeText(condition.getType());
			}
			conditionViewData.setDragHandleVisible(dragHandleVisible);
			
			conditionViewDataList.add(conditionViewData);
		}
		
		return conditionViewDataList;
	}
	
	public ArrayList<ConditionOrOperationViewData> getOperationViewDataList(List<Operation> operations) {
		int listSize = operations.size();
		boolean dragHandleVisible = listSize > 1;
		ArrayList<ConditionOrOperationViewData> operationViewDataList = new ArrayList<>(listSize);
		
		for (Operation operation : operations) {
			ConditionOrOperationViewData conditionViewData = new ConditionOrOperationViewData(
					ConditionOrOperationViewData.TYPE_CONDITION,
					operation.getId(),
					operation.getType()
			);
			
			switch (operation.getType()) {
				case ConditionOrOperationViewData.OPERATION_LIGHTS:
					conditionViewData.setMainText(getApplication().getString(R.string.operation_lights_settings, "10%", "20%")); // TODO: Replace hardcoded numbers with data from database
					conditionViewData.setTypeText(getApplication().getString(R.string.lights));
					break;
				case ConditionOrOperationViewData.OPERATION_THERMOSTAT:
					conditionViewData.setMainText(getApplication().getString(R.string.operation_thermostat_settings, 23, "C")); // TODO: Replace hardcoded number and string with data from database
					conditionViewData.setTypeText(getApplication().getString(R.string.thermostat));
					break;
				default:
					conditionViewData.setMainText(getApplication().getString(R.string.operation_unknown_type));
					conditionViewData.setTypeText(operation.getType());
			}
			conditionViewData.setDragHandleVisible(dragHandleVisible);
			
			operationViewDataList.add(conditionViewData);
		}
		
		return operationViewDataList;
	}
	
	private String getSimpleTimeString() {
		return "timeOfDay"; // TODO: Replace hardcoded string with data from database
	}
	
	private String getSelectedDaysString() {
		return "selectedDays"; // TODO: Replace hardcoded string with data from database
	}
}
