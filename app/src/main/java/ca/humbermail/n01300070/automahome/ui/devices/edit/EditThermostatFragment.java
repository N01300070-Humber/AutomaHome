package ca.humbermail.n01300070.automahome.ui.devices.edit;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.fragment.app.Fragment;

import ca.humbermail.n01300070.automahome.R;

public class EditThermostatFragment extends Fragment {

    private TextView currentTemp;
    private TextView targetTemp;
    private ToggleButton heat;
    private ToggleButton cool;
    private Button up;
    private Button down;
    private Button set;
    private Context context;

    public EditThermostatFragment()
    {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_edit_thermostat, container, false);
        context = getActivity().getApplicationContext();

        currentTemp = root.findViewById(R.id.textView_thermoTempSensor_currentTemp);
        targetTemp = root.findViewById(R.id.textView_thermoTempSensor_targetTemp);

        heat = root.findViewById(R.id.toggleButton_thermoTempSensor_heat);
        cool = root.findViewById(R.id.toggleButton_thermoTempSensor_cool);

        up = root.findViewById(R.id.Button_thermoTempSensor_upArrow);
        down = root.findViewById(R.id.Button_thermoTempSensor_downArrow);
        set = root.findViewById(R.id.Button_thermoTempSensor_setButton);

        return root;
    }

}
