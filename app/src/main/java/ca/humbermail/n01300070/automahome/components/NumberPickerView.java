package ca.humbermail.n01300070.automahome.components;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.textfield.TextInputEditText;

import ca.humbermail.n01300070.automahome.R;

public class NumberPickerView extends ConstraintLayout {
	
	TextView prefixTextView;
	TextView suffixTextView;
	TextInputEditText numEditText;
	AppCompatImageButton increaseButton;
	AppCompatImageButton decreaseButton;
	
	public NumberPickerView(@NonNull Context context) {
		super(context);
		inflateViews(context);
	}
	
	public NumberPickerView(@NonNull Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		inflateViews(context);
		setAttributes(context, attrs);
	}
	
	public NumberPickerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		inflateViews(context);
		setAttributes(context, attrs);
	}
	
	private void inflateViews(Context context) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.view_number_picker, this);
		
		prefixTextView = findViewById(R.id.textView_prefix);
		suffixTextView = findViewById(R.id.textView_suffix);
		numEditText = findViewById(R.id.textInputEditText_number);
		increaseButton = findViewById(R.id.imageView_increaseNumber);
		decreaseButton = findViewById(R.id.imageView_decreaseNumber);
	}
	
	private void setAttributes(Context context, AttributeSet attrs) {
	
	}
}
