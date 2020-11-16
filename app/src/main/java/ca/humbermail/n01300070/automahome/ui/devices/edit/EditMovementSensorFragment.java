package ca.humbermail.n01300070.automahome.ui.devices.edit;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;


import androidx.fragment.app.Fragment;

import ca.humbermail.n01300070.automahome.R;

public class EditMovementSensorFragment extends Fragment {
    private SeekBar sideA;
    private SeekBar sideB;
    private Button calibrate;

    private Context context;

    public EditMovementSensorFragment()
    {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_edit_movement_sensor, container, false);
        context = getActivity().getApplicationContext();

        sideA = root.findViewById(R.id.seekBar_edit_movementSensor_tolerance1);
        sideB = root.findViewById(R.id.seekBar_edit_movementSensor_tolerance2);
        calibrate = root.findViewById(R.id.button_edit_movementSensor_calibrate);

        return root;
    }
}
