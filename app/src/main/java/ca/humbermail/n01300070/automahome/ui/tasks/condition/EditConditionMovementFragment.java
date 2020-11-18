package ca.humbermail.n01300070.automahome.ui.tasks.condition;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import java.util.ArrayList;

import ca.humbermail.n01300070.automahome.R;
import ca.humbermail.n01300070.automahome.components.FavoriteSelectView;

public class EditConditionMovementFragment extends Fragment {
    private Context context;
    
    private AutoCompleteTextView autoCompleteTextView;
    
    private ArrayAdapter<String> adapter;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_edit_condition_movement, container, false);
        context = getActivity().getApplicationContext();
    
        autoCompleteTextView = root.findViewById(R.id.autoCompleteText_conditionMovement_deviceSelect);
    
        adapter = new ArrayAdapter<String>(getContext(), R.layout.text_view_auto_complete_label, generateDeviceList());
        autoCompleteTextView.setAdapter(adapter);
        
        return root;
    }
    
    private ArrayList<String> generateDeviceList() {
        int numDevices = 6;
        ArrayList<String> categoryList = new ArrayList<>(numDevices);
        
        for (int i = 0; i < numDevices; i++) {
            categoryList.add("Movement Sensor " + (i + 1));
        }
        
        return categoryList;
    }
}