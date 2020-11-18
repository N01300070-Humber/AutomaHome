package ca.humbermail.n01300070.automahome.ui.devices.edit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ca.humbermail.n01300070.automahome.R;
import ca.humbermail.n01300070.automahome.components.FavoriteSelectView;
import ca.humbermail.n01300070.automahome.data.model.DeviceOrTaskData;

public class EditDevicesActivity extends AppCompatActivity
{
    private Fragment fragment;
    private FavoriteSelectView favoriteSelectView;
    private Spinner roomSpinner;
    private Spinner roomSpinner2;
    private TextView roomLocationHeader;
    private TextView roomLocationHeader2;

    private String deviceType;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_devices);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        roomSpinner = findViewById(R.id.spinner_editDevice);
        roomSpinner2 = findViewById(R.id.spinner_editDevice_2);
        roomLocationHeader = findViewById(R.id.textView_deviceLocation_editDevice);
        roomLocationHeader2 = findViewById(R.id.textView_editDevice_deviceLocation2);
        favoriteSelectView = findViewById(R.id.favoriteSelectView_editDevice);

        deviceType = getIntent().getExtras().getString(DeviceOrTaskData.ARG_DEVICE);
        favoriteSelectView.setAutoCompleteLabels(generateCategoryList());

        switch(deviceType){
            case DeviceOrTaskData.DEVICE_LIGHTS:
                fragment = new EditLightFragment();
                break;
            case DeviceOrTaskData.DEVICE_MOVEMENT_SENSOR:
                roomLocationHeader.setText(getString(R.string.side_device_location, "A"));
                roomLocationHeader2.setText(getString(R.string.side_device_location, "B"));
                roomLocationHeader2.setVisibility(View.VISIBLE);
                roomSpinner2.setVisibility(View.VISIBLE);
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

    public void discardButtonClicked(View view) {
        //TODO data handling
        setResult(Activity.RESULT_CANCELED);
        finish();
    }

    public void saveButtonClicked(View view) {
        //TODO data handling
        Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show(); // TODO: Remove placeholder toast
        setResult(Activity.RESULT_OK);
        finish();
    }
    
    private ArrayList<String> generateCategoryList() {
        int numCategories = 6;
        ArrayList<String> categoryList = new ArrayList<>(numCategories);
        
        for (int i = 0; i < numCategories; i++) {
            categoryList.add("Category " + (i + 1));
        }
        
        return categoryList;
    }
    
    /**
     * Handles back button onClick event
     * @param item non-null MenuItem
     * @return Boolean
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        setResult(Activity.RESULT_CANCELED);
        finish();
        return true;
    }
}