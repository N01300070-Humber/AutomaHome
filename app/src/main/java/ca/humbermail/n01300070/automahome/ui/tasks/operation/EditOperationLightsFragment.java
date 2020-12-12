package ca.humbermail.n01300070.automahome.ui.tasks.operation;

import androidx.fragment.app.Fragment;

import ca.humbermail.n01300070.automahome.R;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import java.util.ArrayList;

public class EditOperationLightsFragment extends Fragment {
    private Context context;

    
    private AutoCompleteTextView autoCompleteTextView;
    
    private ArrayAdapter<String> adapter;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_edit_operation_lights, container, false);
        context = getActivity().getApplicationContext();
    
        autoCompleteTextView = root.findViewById(R.id.autoCompleteText_controlLight_deviceSelect);
    
        adapter = new ArrayAdapter<String>(getContext(), R.layout.text_view_auto_complete_label, generateDeviceList());
        autoCompleteTextView.setAdapter(adapter);
        
        return root;
    }
    
    private ArrayList<String> generateDeviceList() {
        int numDevices = 6;
        ArrayList<String> categoryList = new ArrayList<>(numDevices);
        
        for (int i = 0; i < numDevices; i++) {
            categoryList.add("Light " + (i + 1));
        }
        
        return categoryList;
    }
}