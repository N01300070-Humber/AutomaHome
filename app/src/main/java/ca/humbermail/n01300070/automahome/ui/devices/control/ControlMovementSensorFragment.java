package ca.humbermail.n01300070.automahome.ui.devices.control;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import ca.humbermail.n01300070.automahome.R;

public class ControlMovementSensorFragment extends Fragment
{
    private RecyclerView DetectionLog;
    private Context context;

    public ControlMovementSensorFragment()
    {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_control_movement_sensor, container, false);
        context = getActivity().getApplicationContext();

        DetectionLog = root.findViewById(R.id.recyclerView_control_movementSensor);

        return root;
    }
}
