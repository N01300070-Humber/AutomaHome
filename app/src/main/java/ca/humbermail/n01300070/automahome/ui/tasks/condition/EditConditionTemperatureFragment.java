package ca.humbermail.n01300070.automahome.ui.tasks.condition;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import ca.humbermail.n01300070.automahome.R;

public class EditConditionTemperatureFragment extends Fragment {
    private Context context;
    
    TextInputEditText temperatureEditText;
    TextView temperatureUnitTextView;
    AppCompatImageView increaseTemperatureButton;
    AppCompatImageView decreaseTemperatureButton;
    MaterialButton logicLessThanButton;
    MaterialButton logicEqualToButton;
    MaterialButton logicMoreThanButton;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_edit_condition_temperature, container, false);
        context = getActivity().getApplicationContext();
        
        temperatureEditText = root.findViewById(R.id.textInputEditText_temperature);
        temperatureUnitTextView = root.findViewById(R.id.textView_editConditionTemperature_unit);
        increaseTemperatureButton = root.findViewById(R.id.imageView_editConditionTemperature_increase);
        decreaseTemperatureButton = root.findViewById(R.id.imageView_editConditionTemperature_decrease);
        logicLessThanButton = root.findViewById(R.id.toggleButton_lessThan);
        logicEqualToButton = root.findViewById(R.id.toggleButton_equal);
        logicMoreThanButton = root.findViewById(R.id.toggleButton_moreThan);
        
        temperatureEditText.setText("32"); // TODO: Replace hardcoded text with saved temperature or default (convert if user preference is fahrenheit)
        temperatureUnitTextView.setText(getString(R.string.degrees_celsius)); // TODO: Replace getString(resource) with unit preference from user settings
        
        return root;
    }
}