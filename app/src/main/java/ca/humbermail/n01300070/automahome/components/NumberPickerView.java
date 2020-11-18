package ca.humbermail.n01300070.automahome.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
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
	TextInputEditText numberEditText;
	AppCompatImageButton increaseButton;
	AppCompatImageButton decreaseButton;
	
	private float interval;
	
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
		numberEditText = findViewById(R.id.textInputEditText_number);
		increaseButton = findViewById(R.id.imageView_increaseNumber);
		decreaseButton = findViewById(R.id.imageView_decreaseNumber);
		
		increaseButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				increaseNumber(interval);
			}
		});
		decreaseButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				decreaseNumber(interval);
			}
		});
	}
	
	private void setAttributes(Context context, AttributeSet attrs) {
		TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.NumberPickerView);
		
		setNumber(attributes.getFloat(R.styleable.NumberPickerView_initialNumber, 0));
		setInterval(attributes.getFloat(R.styleable.NumberPickerView_increaseDecreaseInterval, 1));
		setHint(attributes.getString(R.styleable.NumberPickerView_android_hint));
		setPrefixText(attributes.getString(R.styleable.NumberPickerView_prefixText));
		setSuffixText(attributes.getString(R.styleable.NumberPickerView_suffixText));
		setTextAppearance(attributes.getResourceId(
				R.styleable.NumberPickerView_android_textAppearance,
				R.style.TextAppearance_MaterialComponents_Body1 ));
		
		attributes.recycle();
	}
	
	public float getNumber() {
		return Float.parseFloat(numberEditText.getText().toString());
	}
	
	public void setNumber(float number) {
		numberEditText.setText(String.format("%s", number));
	}
	
	public float getInterval() {
		return interval;
	}
	
	public void setInterval(float interval) {
		this.interval = interval;
	}
	
	public String getHint() {
		return (String) numberEditText.getHint();
	}
	
	public void setHint(String hint) {
		numberEditText.setHint(hint);
	}
	
	public String getPrefixText() {
		return prefixTextView.getText().toString();
	}
	
	public void setPrefixText(String text) {
		prefixTextView.setText(text);
	}
	
	public String getSuffixText() {
		return suffixTextView.getText().toString();
	}
	
	public void setSuffixText(String text) {
		suffixTextView.setText(text);
	}
	
	public void setTextAppearance(int textAppearance) {
		prefixTextView.setTextAppearance(textAppearance);
		numberEditText.setTextAppearance(textAppearance);
		suffixTextView.setTextAppearance(textAppearance);
	}
	
	
	public void increaseNumber(float interval) {
		setNumber(getNumber() + interval);
	}
	
	public void decreaseNumber(float interval) {
		setNumber(getNumber() - interval);
	}
}
