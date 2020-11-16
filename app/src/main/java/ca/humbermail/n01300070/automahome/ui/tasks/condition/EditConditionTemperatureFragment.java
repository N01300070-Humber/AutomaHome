package ca.humbermail.n01300070.automahome.ui.tasks.condition;

import androidx.fragment.app.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ca.humbermail.n01300070.automahome.R;

public class EditConditionTemperatureFragment extends Fragment {
    private Context context;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_edit_condition_temperature, container, false);
        context = getActivity().getApplicationContext();
        
        return root;
    }
}