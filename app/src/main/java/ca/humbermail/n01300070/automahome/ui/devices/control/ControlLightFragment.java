package ca.humbermail.n01300070.automahome.ui.devices.control;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import java.util.Map;

import top.defaults.colorpicker.ColorPickerPopup;

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

    // text view variable to set the color for GFG text
    private TextView currentLightColor;

    // two buttons to open color picker dialog and one to
    // set the color for currentColor text
    private Button mPickColorButton, mSetColorButton;

    // view box to preview the selected color
    private View mColorPreview;

    // this is the default color of the preview box
    private int mDefaultColor;

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
        //temperatureSeekBar = root.findViewById(R.id.seekBar_controlLight_temp);
        //nightLightSwitch = root.findViewById(R.id.switch_control_light_nightLight);

        //register for currentLightColor with appropriate value
        currentLightColor = root.findViewById(R.id.textView_currentColor_light);

        //register two of the buttons with their appropriate IDs
        mPickColorButton = root.findViewById(R.id.button_pick_color_button);
        mSetColorButton = root.findViewById(R.id.button_set_color_button);

        // and also register the view which shows the
        // preview of the color chosen by the user
        mColorPreview = root.findViewById(R.id.preview_selected_color);

        // set the default color to 0 as it is black
        mDefaultColor = 0;

        setOnColourChangeListener();
        
        return root;
    }
    
    private void setOnColourChangeListener() {
        Log.d("ControlLight", "setOnColourChangeListener called");
        
        realtimeDatabaseDataSource.onDeviceDataValueChange(deviceId, null, true).observe(getViewLifecycleOwner(), new Observer<Object>() {
            @Override
            public void onChanged(Object object) {
                int intensityRed;
                int intensityGreen;
                int intensityBlue;
                
                if (object instanceof Map) {
                    Map<String, Object> deviceData = (Map<String, Object>) object;
                    intensityRed = (int) deviceData.get(DeviceDataPaths.INTENSITY_RED_LED);
                    intensityGreen = (int) deviceData.get(DeviceDataPaths.INTENSITY_GREEN_LED);
                    intensityBlue = (int) deviceData.get(DeviceDataPaths.INTENSITY_BLUE_LED);
                } else {
                    Log.d("ControlLight", "Device data values object is null");
                    intensityRed = 0;
                    intensityGreen = 0;
                    intensityBlue = 0;
                }

                // TODO: Set colour wheel to reflect intensity values
                //handling the Pick Color Button to open Color Picker Dialog
                mPickColorButton.setOnClickListener(
                        new View.OnClickListener(){
                            @Override
                            public void onClick (final View v){
                                new ColorPickerPopup.Builder(context ControlLightFragment.this).initial(
                                        Color.RED) // set initial color of the Color Picker Dialog
                                        .okTitle("Choose") // this is top right ChooseButton
                                        .cancelTitle ("Cancel") // this is top left CancelButton
                                        .showIndicator (true) // this is small box which shows the chosen
                                        // color by user at the buttom of the cancelButton
                                        .showValue (true) // this is the value which shows
                                        // the selected color hex color
                                        .build ()
                                        .show(
                                                v,
                                                new ColorPickerPopup.ColorPickerObserver() {
                                                    @Override
                                                    public void
                                                    onColorPicked(int color) {
                                                        // set the color
                                                        // which is returned
                                                        // by the color
                                                        // picker
                                                        mDefaultColor = color;

                                                        // now as soon as
                                                        // the dialog closes
                                                        // set the preview
                                                        // box to returned
                                                        // color
                                                        mColorPreview.setBackgroundColor(mDefaultColor);
                                                    }
                                                });
                            }
                        });

                // handling the Set Color button to set the selected
                // color for the GFG text.
                mSetColorButton.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // now change the value of the currentLight text as well.
                                currentLightColor.setTextColor(mDefaultColor);
                            }
                        });

            }
        });
    }
}