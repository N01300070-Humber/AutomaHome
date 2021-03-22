package ca.humbermail.n01300070.automahome.components;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.LinkedHashSet;

import ca.humbermail.n01300070.automahome.R;

public class NumberPickerView extends ConstraintLayout {
	private static final int TEXT_ALIGNMENT_LEFT = 0;
	private static final int TEXT_ALIGNMENT_CENTER = 1;
	private static final int TEXT_ALIGNMENT_RIGHT = 2;
	private static final int TEXT_ALIGNMENT_START = 3;
	private static final int TEXT_ALIGNMENT_END = 4;
	
	private final LinkedHashSet<OnNumberChangeListener> onNumberChangeListeners = new LinkedHashSet<>();
	
	private TextInputLayout numberInputLayout;
	private TextInputEditText numberEditText;
	private AppCompatImageButton increaseButton;
	private AppCompatImageButton decreaseButton;
	
	private float interval;
	
	
	public interface OnNumberChangeListener {
		void onNumberChanged(NumberPickerView numberPickerView, float number, boolean fromKeyboard);
	}
	
	
	public NumberPickerView(@NonNull Context context) {
		super(context);
		inflateViews(context);
		onCreate();
	}
	
	public NumberPickerView(@NonNull Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		inflateViews(context);
		setAttributes(context, attrs);
		onCreate();
	}
	
	public NumberPickerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		inflateViews(context);
		setAttributes(context, attrs);
		onCreate();
	}
	
	private void inflateViews(Context context) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.view_number_picker, this);
		
		numberInputLayout = findViewById(R.id.textInputLayout_number);
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
		setNumberAlignment(attributes.getInteger(R.styleable.NumberPickerView_numberAlignment, 0));
		setInterval(attributes.getFloat(R.styleable.NumberPickerView_numberInterval, 1));
		setHint(attributes.getString(R.styleable.NumberPickerView_android_hint));
		setPrefixText(attributes.getString(R.styleable.NumberPickerView_prefixText));
		setSuffixText(attributes.getString(R.styleable.NumberPickerView_suffixText));
		setTextAppearance(attributes.getResourceId(
				R.styleable.NumberPickerView_android_textAppearance,
				R.style.TextAppearance_MaterialComponents_Body1));
		
		attributes.recycle();
	}
	
	private void onCreate() {
		numberEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_DONE) {
					runOnNumberChangeListeners(true);
					return true;
				}
				return false;
			}
		});
	}
	
	
	public float getNumber() {
		return Float.parseFloat(numberEditText.getText().toString());
	}
	
	public void setNumber(float number) {
		numberEditText.setText(String.format("%s", number));
	}
	
	public int getNumberGravity() {
		return numberEditText.getGravity();
	}
	
	public void setNumberGravity(int gravity) {
		numberEditText.setGravity(gravity);
	}
	
	@SuppressLint("RtlHardcoded")
	public void setNumberAlignment(int alignment) {
		switch (alignment) {
			case TEXT_ALIGNMENT_LEFT:
				setNumberGravity(Gravity.LEFT);
				break;
			case TEXT_ALIGNMENT_CENTER:
				setNumberGravity(Gravity.CENTER_HORIZONTAL);
				break;
			case TEXT_ALIGNMENT_RIGHT:
				setNumberGravity(Gravity.RIGHT);
				break;
			case TEXT_ALIGNMENT_START:
				setNumberGravity(Gravity.START);
				break;
			case TEXT_ALIGNMENT_END:
				setNumberGravity(Gravity.END);
				break;
		}
	}
	
	public float getInterval() {
		return interval;
	}
	
	public void setInterval(float interval) {
		this.interval = interval;
	}
	
	public String getHint() {
		return (String) numberInputLayout.getHint();
	}
	
	public void setHint(String hint) {
		numberInputLayout.setHint(hint);
	}
	
	public String getPrefixText() {
		return numberInputLayout.getPrefixText().toString();
	}
	
	public void setPrefixText(String text) {
		numberInputLayout.setPrefixText(text);
	}
	
	public String getSuffixText() {
		return numberInputLayout.getSuffixText().toString();
	}
	
	public void setSuffixText(String text) {
		numberInputLayout.setSuffixText(text);
	}
	
	public void setTextAppearance(int textAppearance) {
		numberEditText.setTextAppearance(textAppearance);
		numberInputLayout.setPrefixTextAppearance(textAppearance);
		numberInputLayout.setSuffixTextAppearance(textAppearance);
	}
	
	
	public void increaseNumber(float interval) {
		setNumber(getNumber() + interval);
		runOnNumberChangeListeners(false);
	}
	
	public void decreaseNumber(float interval) {
		setNumber(getNumber() - interval);
		runOnNumberChangeListeners(false);
	}
	
	
	public void addOnNumberChangeListener(OnNumberChangeListener listener) {
		onNumberChangeListeners.add(listener);
	}
	
	public void removeOnNumberChangeListener(OnNumberChangeListener listener) {
		onNumberChangeListeners.remove(listener);
	}
	
	public void clearOnNumberChangeListeners() {
		onNumberChangeListeners.clear();
	}
	
	private void runOnNumberChangeListeners(boolean fromKeyboard) {
		for (OnNumberChangeListener listener : onNumberChangeListeners) {
			listener.onNumberChanged(this, this.getNumber(), fromKeyboard);
		}
	}
}
