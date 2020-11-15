package ca.humbermail.n01300070.automahome.ui.devices.control;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import ca.humbermail.n01300070.automahome.R;
import ca.humbermail.n01300070.automahome.data.model.DeviceOrTaskData;
import ca.humbermail.n01300070.automahome.ui.devices.edit.EditDevicesActivity;

public class ControlDevicesActivity extends AppCompatActivity
{
    private Fragment fragment;
    private Button editDeviceButton;

    private String deviceType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_devices);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editDeviceButton = findViewById(R.id.button_editDevice);

        deviceType = getIntent().getExtras().getString(DeviceOrTaskData.ARG_DEVICE);

        switch(deviceType){
            case DeviceOrTaskData.DEVICE_LIGHTS:
                fragment = new DevicesControlLightFragment();
                getSupportFragmentManager().beginTransaction().add(R.id.fragment_controlDevice, fragment).commit();
                break;
            case DeviceOrTaskData.DEVICE_MOVEMENT_SENSOR:
                //fragment =
        }
    }


    /**
     * Handles back button onClick event
     * @param item non-null MenuItem
     * @return Boolean
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        finish();
        return true;
    }


    public void buttonEditDeviceClicked(View view) {
        Intent intent = new Intent(getApplicationContext(), EditDevicesActivity.class);
        intent.putExtra(DeviceOrTaskData.ARG_DEVICE, deviceType);
        startActivity(intent);
    }
}