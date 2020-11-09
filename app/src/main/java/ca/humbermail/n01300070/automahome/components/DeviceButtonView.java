package ca.humbermail.n01300070.automahome.components;

import android.content.Context;
import android.content.res.TypedArray;

import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.solver.state.Dimension;

import ca.humbermail.n01300070.automahome.R;

/**
 * A custom component that displays info on a device and works like a button
 */
public class DeviceButtonView extends CardView {
	
	private ImageView iconImageView;
	private TextView nameTextView;
	private TextView extraTextView;
	
	private CharSequence name;
	private CharSequence extraText;
	private Drawable icon;
	private CharSequence contentDescription;
	private float cornerRadius;
	private int backgroundColour;
	
	// Device Button Constructors
	public DeviceButtonView(Context context) {
		super(context);
		inflateViews(context);
	}
	
	public DeviceButtonView(Context context, AttributeSet attrs) {
		super(context, attrs);
		getAttributes(context, attrs);
		inflateViews(context);
		setAttributes();
	}
	
	public DeviceButtonView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		getAttributes(context, attrs);
		inflateViews(context);
		setAttributes();
	}
	
	/**
	 * Inflate views in DeviceButton
	 * @param context current context of DeviceButton
	 */
	private void inflateViews(Context context) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.view_device_button, this);
		
		iconImageView = findViewById(R.id.imageView_device_type);
		nameTextView = findViewById(R.id.textView_device_name);
		extraTextView = findViewById(R.id.textView_location);
	}
	
	private void getAttributes(Context context, AttributeSet attrs) {
		TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.DeviceButtonView);
		
		name = attributes.getText(R.styleable.DeviceButtonView_android_text);
		cornerRadius = attributes.getDimension(R.styleable.DeviceButtonView_cardCornerRadius,
				getResources().getDimension(R.dimen.device_button_corner_radius));
		icon = attributes.getDrawable(R.styleable.DeviceButtonView_android_src);
		contentDescription = attributes.getString(R.styleable.DeviceButtonView_contentDescription);
		backgroundColour = attributes.getColor(R.styleable.DeviceButtonView_backgroundTint,
				getResources().getColor(R.color.design_default_color_secondary, context.getTheme()));
		
		attributes.recycle();
	}
	
	private void setAttributes() {
		setName(name);
		setIcon(icon);
		setContentDescription(contentDescription);
		setCornerRadius(cornerRadius);
		setBackgroundColour(backgroundColour);
	}
	
	public String getName() {
		return (String) nameTextView.getText();
	}
	
	public void setName(CharSequence name) {
		nameTextView.setText(name);
	}
	
	public void setName(String name) {
		nameTextView.setText(name);
	}
	
	public String getExtraText() {
		return (String) extraTextView.getText();
	}
	
	public void setExtraText(CharSequence name) {
		extraTextView.setText(name);
	}
	
	public void setExtraText(String name) {
		extraTextView.setText(name);
	}
	
	public boolean isExtraTextVisible() {
		return extraTextView.getVisibility() == View.VISIBLE;
	}
	
	public void setExtraTextVisible(boolean visible) {
		if (extraTextView.getVisibility() == View.VISIBLE) {
			extraTextView.setVisibility(View.GONE);
		}
		else {
			extraTextView.setVisibility(View.VISIBLE);
		}
	}
	
	public Drawable getIcon() {
		return iconImageView.getDrawable();
	}
	
	public void setIcon(Drawable icon) {
		iconImageView.setImageDrawable(icon);
	}
	
	public void setCornerRadius(float cornerRadius) {
		this.setRadius(cornerRadius);
	}
	
	public CharSequence getContentDesc() {
		return this.getContentDescription();
	}
	
	public void setContentDesc(CharSequence contentDescription) {
		this.setContentDescription(contentDescription);
	}
	
	public void setContentDesc(String contentDescription) {
		this.setContentDescription(contentDescription);
	}
	
	public float getCornerRadius() {
		return this.getRadius();
	}
	
	public int getBackgroundColour() {
		return this.getBackgroundColour();
	}
	
	public void setBackgroundColour(int colour) {
		this.setCardBackgroundColor(colour);
	}
	
	/*public CharSequence getText() {
		return text;
	}
	
	public void setText(CharSequence text) {
		this.text = text;
	}
	
	public boolean isShowLocation() {
		return showLocation;
	}
	
	public void setShowLocation(boolean showLocation) {
		this.showLocation = showLocation;
	}
	
	public int getBackgroundTint() {
		return backgroundTint;
	}
	
	public void setBackgroundTint(int backgroundTint) {
		this.backgroundTint = backgroundTint;
		invalidate();
		requestLayout();
	}*/
	
	//Save and Restore instance state
	private static String STATE_NAME;
	
	
}