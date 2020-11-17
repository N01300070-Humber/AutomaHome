package ca.humbermail.n01300070.automahome.ui.manageHome;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ca.humbermail.n01300070.automahome.R;

public class AddNetworkActivity extends AppCompatActivity {
    
    EditText networkEditText;
    Button addButton;
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_network);
    }
    
    /**
     * onClick event handler for AddNetworkButton
     * @param view Source view
     */
    public void addNetworkBtn_Clicked(View view){
        addNetwork();
    }
    
    public void addNetwork() {
        // TODO: Add network to Wi-Fi Detection list of current home if not already in use by another home
        Toast.makeText(getApplicationContext(), "Network Added", Toast.LENGTH_SHORT).show();
        finish();
    }
}