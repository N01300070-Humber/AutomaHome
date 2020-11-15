package ca.humbermail.n01300070.automahome.ui.devices.control;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import ca.humbermail.n01300070.automahome.R;

public class ControlDevicesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_devices);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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