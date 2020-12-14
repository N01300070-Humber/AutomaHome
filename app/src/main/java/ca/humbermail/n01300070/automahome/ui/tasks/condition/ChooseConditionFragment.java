package ca.humbermail.n01300070.automahome.ui.tasks.condition;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import ca.humbermail.n01300070.automahome.R;
import ca.humbermail.n01300070.automahome.components.ConditionOrOperationView;
import ca.humbermail.n01300070.automahome.components.ConditionOrOperationViewAdapter;
import ca.humbermail.n01300070.automahome.components.ListLinePadding;
import ca.humbermail.n01300070.automahome.data.model.ConditionOrOperationViewData;

public class ChooseConditionFragment extends Fragment {
	Context context;
	
	RecyclerView recyclerView;
	
	private ConditionOrOperationViewAdapter adapter;
	private View.OnClickListener onClickListener;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View root = inflater.inflate(R.layout.fragment_choose_condition, container, false);
		context = getActivity().getApplicationContext();
		
		recyclerView = root.findViewById(R.id.recyclerView_chooseCondition);
		
		onClickListener = new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				ConditionOrOperationView conditionView = (ConditionOrOperationView) view;
				EditConditionActivity parentActivity = (EditConditionActivity) getActivity();
				
				parentActivity.changeActiveFragment(conditionView.getConditionOrOperationType());
			}
		};
		adapter = new ConditionOrOperationViewAdapter(context, getConditionsDataList(), onClickListener);
		
		recyclerView.setLayoutManager(new LinearLayoutManager(context));
		recyclerView.setAdapter(adapter);
		recyclerView.addItemDecoration(new ListLinePadding((int) getResources().getDimension(R.dimen.recycler_divider_space)));
		recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
		
		return root;
	}
	
	public ArrayList<ConditionOrOperationViewData> getConditionsDataList() {
		String[] conditionTypeList = {ConditionOrOperationViewData.CONDITION_SCHEDULE, ConditionOrOperationViewData.CONDITION_TEMPERATURE, ConditionOrOperationViewData.CONDITION_MOVEMENT};
		String[] conditionNameList = {getString(R.string.condition_schedule), getString(R.string.condition_temperature), getString(R.string.condition_movement)};
		String[] conditionDescriptionList = {getString(R.string.condition_schedule_description), getString(R.string.condition_temperature_description), getString(R.string.condition_movement_description)};
		ArrayList<ConditionOrOperationViewData> conditionsDataList = new ArrayList<>(conditionTypeList.length);
		
		for (int i = 0; i < conditionTypeList.length; i++) {
			ConditionOrOperationViewData conditionData = new ConditionOrOperationViewData(
					ConditionOrOperationViewData.TYPE_CONDITION,
					null,
					conditionTypeList[i],
					conditionNameList[i],
					conditionDescriptionList[i],
					false
			);
			
			conditionsDataList.add(conditionData);
		}
		
		return conditionsDataList;
	}
}