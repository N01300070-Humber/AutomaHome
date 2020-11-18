package ca.humbermail.n01300070.automahome.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

import ca.humbermail.n01300070.automahome.R;

public class FavoriteSelectView extends ConstraintLayout {
	
	private MaterialButton button;
	private TextInputLayout categoryInputLayout;
	private AutoCompleteTextView categoryAutoCompleteText;
	
	private ArrayAdapter<String> adapter;
	private ArrayList<String> autoCompleteLabels;
	private boolean checked;
	
	public FavoriteSelectView(@NonNull Context context) {
		super(context);
		inflateViews(context);
	}
	
	public FavoriteSelectView(@NonNull Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		inflateViews(context);
		setAttributes(context, attrs);
	}
	
	public FavoriteSelectView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		inflateViews(context);
		setAttributes(context, attrs);
	}
	
	private void inflateViews(Context context) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.view_favorite_select, this);
		
		button = findViewById(R.id.button_favoriteSelect);
		categoryInputLayout = findViewById(R.id.textInputLayout_favoriteSelect);
		categoryAutoCompleteText = findViewById(R.id.autoCompleteText_favoriteSelect);
		
		button.addOnCheckedChangeListener(new MaterialButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(MaterialButton button, boolean isChecked) {
				setInputVisible(isChecked);
			}
		});
		setInputVisible(isChecked());
	}
	
	private void setAttributes(Context context, AttributeSet attrs) {
		TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.FavoriteSelectView);
		
		setChecked(attributes.getBoolean(R.styleable.FavoriteSelectView_android_state_checked, false));
		
		attributes.recycle();
	}
	
	public boolean isChecked() {
		return button.isChecked();
	}
	
	public void setChecked(boolean checked) {
		button.setChecked(checked);
	}
	
	public String getText() {
		return categoryAutoCompleteText.getText().toString();
	}
	
	public void setText(String text) {
		categoryAutoCompleteText.setText(text);
	}
	
	private void setInputVisible(boolean visible) {
		if (visible) {
			categoryInputLayout.setVisibility(VISIBLE);
		}
		else {
			categoryInputLayout.setVisibility(INVISIBLE);
		}
	}
	
	public void setAutoCompleteLabels(ArrayList<String> autoCompleteLabels) {
		adapter = new ArrayAdapter<String>(getContext(), R.layout.text_view_auto_complete_label, autoCompleteLabels);
		categoryAutoCompleteText.setAdapter(adapter);
	}
}
