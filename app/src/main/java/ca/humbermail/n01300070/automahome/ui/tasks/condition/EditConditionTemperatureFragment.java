package ca.humbermail.n01300070.automahome.ui.tasks.condition;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import ca.humbermail.n01300070.automahome.R;
import ca.humbermail.n01300070.automahome.components.NumberPickerView;

public class EditConditionTemperatureFragment extends Fragment {
    private Context context;
    
    NumberPickerView temperatureNumberPickerView;
    MaterialButton logicLessThanButton;
    MaterialButton logicEqualToButton;
    MaterialButton logicMoreThanButton;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_edit_condition_temperature, container, false);
        context = getActivity().getApplicationContext();
        
        temperatureNumberPickerView = root.findViewById(R.id.numberPickerView_editConditionTemperature);
        logicLessThanButton = root.findViewById(R.id.toggleButton_lessThan);
        logicEqualToButton = root.findViewById(R.id.toggleButton_equal);
        logicMoreThanButton = root.findViewById(R.id.toggleButton_moreThan);
    
        temperatureNumberPickerView.setNumber(32); // TODO: Replace hardcoded number with saved temperature or default (convert if user preference is fahrenheit)
        temperatureNumberPickerView.setSuffixText(getString(R.string.degrees_celsius)); // TODO: Replace getString(resource) with unit preference from user settings
        
        return root;
    }
}