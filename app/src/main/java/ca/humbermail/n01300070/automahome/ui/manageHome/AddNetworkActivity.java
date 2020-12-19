package ca.humbermail.n01300070.automahome.ui.manageHome;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import ca.humbermail.n01300070.automahome.R;
import ca.humbermail.n01300070.automahome.ui.CustomActivity;

public class AddNetworkActivity extends CustomActivity {
	
	EditText networkEditText;
	Button addNetworkBySSIDButton;
	Button addCurrentNetworkButton;
	
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_network);
		
		networkEditText = findViewById(R.id.textInputEditText_ssid);
		addNetworkBySSIDButton = findViewById(R.id.button_addNetworkFromSSID);
		addCurrentNetworkButton = findViewById(R.id.button_addNetworkFromCurrent);
		
		networkEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
				addNetworkBySSID();
				return true;
			}
		});
	}
	
	/**
	 * onClick event handler for addNetworkBySSIDButton
	 * @param view Source view
	 */
	public void addNetworkBySSIDButton_Clicked(View view) {
		addNetworkBySSID();
	}
	
	/**
	 * onCLick event handler for addCurrentNetworkButton
	 * @param view Source view
	 */
	public void addCurrentNetworkButton_Clicked(View view) {
		// TODO: Add currently connected network to Wi-Fi Detection list of current home if not already in use by another home
		Toast.makeText(getApplicationContext(), "Current network added", Toast.LENGTH_SHORT).show();
		finish();
	}
	
	public void addNetworkBySSID() {
		String ssid = networkEditText.getText().toString();
		
		// TODO: Add network from provided SSID to Wi-Fi Detection list of current home if not already in use by another home
		Toast.makeText(getApplicationContext(), "Network " + ssid + " added", Toast.LENGTH_SHORT).show();
		finish();
	}
}