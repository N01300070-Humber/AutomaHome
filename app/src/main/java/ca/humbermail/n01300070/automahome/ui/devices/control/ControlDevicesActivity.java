package ca.humbermail.n01300070.automahome.ui.devices.control;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import ca.humbermail.n01300070.automahome.R;
import ca.humbermail.n01300070.automahome.components.DeviceOrTaskButtonView;
import ca.humbermail.n01300070.automahome.data.model.DeviceOrTaskButtonData;
import ca.humbermail.n01300070.automahome.ui.devices.edit.EditDevicesActivity;

public class ControlDevicesActivity extends AppCompatActivity
{
    private Fragment fragment;
    private Button editDeviceButton;
    public static String EXTRA_DEVICE_ID = "deviceId";

    private String deviceType;
    private String deviceId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_devices);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editDeviceButton = findViewById(R.id.button_editDevice);
        deviceId = getIntent().getExtras().getString(EXTRA_DEVICE_ID);
        deviceType = getIntent().getExtras().getString(DeviceOrTaskButtonData.ARG_DEVICE);

        if (deviceType == null) {
            Toast.makeText(getApplicationContext(), "Error: failed to get device type", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        switch(deviceType){
            case DeviceOrTaskButtonData.DEVICE_LIGHTS:
                fragment = new ControlLightFragment();
                break;
            case DeviceOrTaskButtonData.DEVICE_MOVEMENT_SENSOR:
                fragment = new ControlMovementSensorFragment();
                break;
            case DeviceOrTaskButtonData.DEVICE_THERMOSTAT:
                fragment = new ControlThermostatFragment();
                break;
            default:
                Toast.makeText(getApplicationContext(), "Error: device type " + deviceType + " is unknown", Toast.LENGTH_SHORT).show();
                finish();
                return;
        }
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_controlDevice, fragment).commit();
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
        intent.putExtra(DeviceOrTaskButtonData.ARG_DEVICE, deviceType);
        intent.putExtra(EXTRA_DEVICE_ID,deviceId);
        startActivity(intent);
    }
}