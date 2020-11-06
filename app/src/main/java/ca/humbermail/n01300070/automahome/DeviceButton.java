package ca.humbermail.n01300070.automahome;

import android.content.Context;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A custom component that displays info on a device and works like a button
 */
public class DeviceButton extends ConstraintLayout {
	
	// Device Button Constructor
	public DeviceButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater inflater = LayoutInflater.from(context);
		inflater.inflate(R.layout.device_button, this);
	}
	
}