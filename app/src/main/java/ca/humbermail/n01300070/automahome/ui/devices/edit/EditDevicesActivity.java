package ca.humbermail.n01300070.automahome.ui.devices.edit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import ca.humbermail.n01300070.automahome.R;
import ca.humbermail.n01300070.automahome.data.model.DeviceOrTaskData;
import ca.humbermail.n01300070.automahome.ui.devices.control.ControlLightFragment;
import ca.humbermail.n01300070.automahome.ui.devices.control.ControlMovementSensorFragment;
import ca.humbermail.n01300070.automahome.ui.devices.control.ControlThermostatFragment;

public class EditDevicesActivity extends AppCompatActivity
{
    private Fragment fragment;
    private Button editDeviceButton;

    private String deviceType;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_devices);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        deviceType = getIntent().getExtras().getString(DeviceOrTaskData.ARG_DEVICE);

        switch(deviceType){
            case DeviceOrTaskData.DEVICE_LIGHTS:
                fragment = new EditLightFragment();
                break;
            case DeviceOrTaskData.DEVICE_MOVEMENT_SENSOR:
                fragment = new EditMovementSensorFragment();
                break;
            case DeviceOrTaskData.DEVICE_THERMOSTAT:
                fragment = new EditThermostatFragment();
                break;
            default:
                Toast.makeText(getApplicationContext(), "Error: failed to get device type", Toast.LENGTH_SHORT).show();
                finish();
                return;
        }
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_editDevice, fragment).commit();
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
}