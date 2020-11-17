package ca.humbermail.n01300070.automahome.components;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import ca.humbermail.n01300070.automahome.R;

public class IconTextView extends LinearLayout {
	private TextView textView;
	private ImageView iconImageView;
	
	public IconTextView(Context context) {
		super(context);
		inflateViews(context);
	}
	
	public IconTextView(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		inflateViews(context);
		setAttributes(context, attrs);
	}
	
	public IconTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		inflateViews(context);
		setAttributes(context, attrs);
	}
	
	private void inflateViews(Context context) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.view_icon_text, this);
		
		textView = findViewById(R.id.textView_iconTextView);
		iconImageView = findViewById(R.id.imageView_iconTextView_icon);
	}
	
	private void setAttributes(Context context, AttributeSet attrs) {
		TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.IconTextView);
		
		setText(attributes.getText(R.styleable.IconTextView_android_text));
		setTextAppearance(attributes.getResourceId(
				R.styleable.IconTextView_android_textAppearance,
				R.style.TextAppearance_MaterialComponents_Body1 ));
		setIcon(attributes.getDrawable(R.styleable.IconTextView_android_icon));
		setIconTint(attributes.getColor(
				R.styleable.IconTextView_android_iconTint,
				getResources().getColor(R.color.design_default_color_primary, context.getTheme()) ));
		
		attributes.recycle();
	}
	
	public CharSequence getText() {
		return textView.getText();
	}
	
	public void setText(CharSequence text) {
		textView.setText(text);
	}
	
	public void setTextAppearance(int colour) {
		 textView.setTextAppearance(colour);
	}
	
	public Drawable getIcon() {
		return iconImageView.getDrawable();
	}
	
	public void setIcon(Drawable icon) {
		iconImageView.setImageDrawable(icon);
	}
	
	public void setIconTint(int colour) {
		iconImageView.setImageTintList(ColorStateList.valueOf(colour));
	}
	
	public void setIconVisible(boolean visible) {
		if (visible) {
			iconImageView.setVisibility(VISIBLE);
		}
		else {
			iconImageView.setVisibility(GONE);
		}
	}
	
	public CharSequence getIconContentDescription() {
		return iconImageView.getContentDescription();
	}
	
	public void setIconContentDescription(CharSequence contentDescription) {
		iconImageView.setContentDescription(contentDescription);
	}
}
