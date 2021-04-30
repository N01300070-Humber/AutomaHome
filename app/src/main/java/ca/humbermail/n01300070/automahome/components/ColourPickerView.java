package ca.humbermail.n01300070.automahome.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.LinkedHashSet;

import ca.humbermail.n01300070.automahome.R;
import top.defaults.colorpicker.ColorObserver;
import top.defaults.colorpicker.ColorPickerView;

public class ColourPickerView extends ConstraintLayout {
	
	private final LinkedHashSet<ColourPickerView.OnColourChangeListener> onColourChangeListeners = new LinkedHashSet<>();
	
	private View colourPreview;
	private TextInputLayout textInputLayoutColourHex;
	private TextInputEditText inputEditTextColourHex;
	private ColorPickerView colourWheel;
	
	
	public interface OnColourChangeListener {
		void onColourChanged(ColourPickerView colourPickerView, int colour, boolean fromTextBox, boolean fromUser, boolean shouldPropagate);
	}
	
	
	public ColourPickerView(@NonNull Context context) {
		super(context);
		inflateViews(context);
		onCreate();
	}
	
	public ColourPickerView(@NonNull Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		inflateViews(context);
		setAttributes(context, attrs);
		onCreate();
	}
	
	public ColourPickerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		inflateViews(context);
		setAttributes(context, attrs);
		onCreate();
	}
	
	private void inflateViews(Context context) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.view_colour_picker, this);
		
		colourPreview = findViewById(R.id.colourPreview_colourPickerView);
		textInputLayoutColourHex = findViewById(R.id.textInputLayout_colourHex_colourPickerView);
		inputEditTextColourHex = findViewById(R.id.inputEditText_colourHex_colourPickerView);
		colourWheel = findViewById(R.id.colourPicker_colourPickerView);
	}
	
	private void setAttributes(Context context, AttributeSet attrs) {
		TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.ColourPickerView);
		
		setColour(attributes.getColor(R.styleable.ColourPickerView_selectedColour, Color.WHITE));
		
		attributes.recycle();
	}
	
	private void onCreate() {
		colourWheel.subscribe(new ColorObserver() {
			@Override
			public void onColor(int colour, boolean fromUser, boolean shouldPropagate) {
				inputEditTextColourHex.setText(String.format("%06X", (0xFFFFFF & colour)));
				
				if (fromUser) {
					colourPreview.setBackground(new ColorDrawable(colour));
				}
				
				runOnNumberChangeListeners(colour, false, fromUser, shouldPropagate);
			}
		});
		
		inputEditTextColourHex.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_DONE) {
					int colour;
					StringBuilder hexString;
					
					hexString = new StringBuilder(textView.getText().toString());
					if (hexString.length() < 6) {
						while (hexString.length() < 6) {
							hexString.append('0');
						}
						textView.setText(hexString.toString());
					}
					hexString.insert(0, '#');
					colour = Color.parseColor(hexString.toString());
					
					colourPreview.setBackground(new ColorDrawable(colour));
					colourWheel.setInitialColor(colour);
					
					runOnNumberChangeListeners(colour, true, true, true);
					
					return true;
				}
				return false;
			}
		});
	}
	
	
	public void setColour(int colour) {
		colourPreview.setBackground(new ColorDrawable(colour));
		colourWheel.setInitialColor(colour);
	}
	
	public int getColour() {
		return colourWheel.getColor();
	}
	
	
	public void addOnNumberChangeListener(ColourPickerView.OnColourChangeListener listener) {
		onColourChangeListeners.add(listener);
	}
	
	public void removeOnNumberChangeListener(ColourPickerView.OnColourChangeListener listener) {
		onColourChangeListeners.remove(listener);
	}
	
	public void clearOnNumberChangeListeners() {
		onColourChangeListeners.clear();
	}
	
	private void runOnNumberChangeListeners(int colour, boolean fromTextBox, boolean fromUser, boolean shouldPropagate) {
		for (ColourPickerView.OnColourChangeListener listener : onColourChangeListeners) {
			listener.onColourChanged(this, colour, fromTextBox, fromUser, shouldPropagate);
		}
	}
}
