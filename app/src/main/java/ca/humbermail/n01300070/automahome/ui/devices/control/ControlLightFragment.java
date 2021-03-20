package ca.humbermail.n01300070.automahome.ui.devices.control;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.Switch;

import java.util.Map;

import ca.humbermail.n01300070.automahome.R;
import ca.humbermail.n01300070.automahome.data.DeviceDataPaths;
import ca.humbermail.n01300070.automahome.data.RealtimeDatabaseDataSource;


public class ControlLightFragment extends Fragment
{
    private Context context;
    private ControlDevicesActivity controlDevicesActivity;
    private String deviceId;

    private SeekBar brightnessSeekBar;
    private SeekBar temperatureSeekBar;
    private Switch nightLightSwitch;

    private RealtimeDatabaseDataSource realtimeDatabaseDataSource;

    public ControlLightFragment()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_control_light, container, false);
        context = getActivity().getApplicationContext();
        controlDevicesActivity = (ControlDevicesActivity) getActivity();
        
        deviceId = controlDevicesActivity.getDeviceId();
        
        realtimeDatabaseDataSource = controlDevicesActivity.getRealtimeDatabaseDataSource();

        brightnessSeekBar = root.findViewById(R.id.seekBar_controlLight_brightness);
        temperatureSeekBar = root.findViewById(R.id.seekBar_controlLight_temp);
        nightLightSwitch = root.findViewById(R.id.switch_control_light_nightLight);

        return root;
    }
}