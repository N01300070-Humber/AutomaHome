package ca.humbermail.n01300070.automahome.ui.devices.control;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import ca.humbermail.n01300070.automahome.R;

public class ControlThermostatFragment extends Fragment {

    private TextView temp;
    private TextView humid;
    private Context context;

    public ControlThermostatFragment()
    {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_control_thermostat, container, false);
        context = getActivity().getApplicationContext();

        temp = root.findViewById(R.id.textView_control_thermostat_temperature);
        humid = root.findViewById(R.id.textView_control_thermostat_humidity);

        //TODO replace hardcoded strings for actual readings
        temp.setText("23°C");
        humid.setText("47.5%");

        return root;
    }
}
