package ca.humbermail.n01300070.automahome.ui.tasks.operation;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ca.humbermail.n01300070.automahome.R;
import ca.humbermail.n01300070.automahome.components.ConditionOrOperationView;
import ca.humbermail.n01300070.automahome.components.ConditionOrOperationViewAdapter;
import ca.humbermail.n01300070.automahome.components.ListLinePadding;
import ca.humbermail.n01300070.automahome.data.model.ConditionOrOperationViewData;

public class ChooseOperationFragment extends Fragment {
	Context context;
	
	RecyclerView recyclerView;
	
	private ConditionOrOperationViewAdapter adapter;
	private ConditionOrOperationViewAdapter.OnItemClickListener onItemClickListener;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View root = inflater.inflate(R.layout.fragment_choose_operation, container, false);
		context = requireContext();
		
		recyclerView = root.findViewById(R.id.recyclerView_chooseOperation);
		
		onItemClickListener = new ConditionOrOperationViewAdapter.OnItemClickListener() {
			@Override
			public void onItemClick(View view, int position) {
				ConditionOrOperationView operationView = (ConditionOrOperationView) view;
				EditOperationActivity parentActivity = (EditOperationActivity) requireActivity();
				
				parentActivity.onOperationTypeSelected(operationView.getConditionOrOperationType());
			}
		};
		adapter = new ConditionOrOperationViewAdapter(context, getOperationsDataList(), onItemClickListener);
		
		recyclerView.setLayoutManager(new LinearLayoutManager(context));
		recyclerView.setAdapter(adapter);
		recyclerView.addItemDecoration(new ListLinePadding((int) getResources().getDimension(R.dimen.recycler_divider_space)));
		recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
		
		return root;
	}
	
	public ArrayList<ConditionOrOperationViewData> getOperationsDataList() {
		String[] operationTypeList = {ConditionOrOperationViewData.OPERATION_LIGHTS, ConditionOrOperationViewData.OPERATION_THERMOSTAT};
		String[] operationNameList = {getString(R.string.operation_lights), getString(R.string.operation_thermostat)};
		String[] operationDescriptionList = {getString(R.string.operation_lights_description), getString(R.string.operation_thermostat_description)};
		ArrayList<ConditionOrOperationViewData> operationsDataList = new ArrayList<>(operationTypeList.length);
		
		for (int i = 0; i < operationTypeList.length; i++) {
			ConditionOrOperationViewData conditionData = new ConditionOrOperationViewData(
					ConditionOrOperationViewData.TYPE_OPERATION,
					null,
					operationTypeList[i],
					operationNameList[i],
					operationDescriptionList[i],
					false
			);
			
			operationsDataList.add(conditionData);
		}
		
		return operationsDataList;
	}
}