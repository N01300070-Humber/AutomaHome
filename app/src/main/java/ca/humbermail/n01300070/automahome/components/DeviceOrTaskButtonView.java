package ca.humbermail.n01300070.automahome.components;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;

import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import ca.humbermail.n01300070.automahome.R;

/**
 * A custom component that displays info on a device and works like a button
 */
public class DeviceOrTaskButtonView extends CardView {
	
	private ImageView iconImageView;
	private TextView nameTextView;
	private TextView extraTextView;
	
	private int type = -1;
	private String deviceOrTaskId = null;
	private String deviceType;
	private String favoritesCategory;
	private CharSequence name;
	private CharSequence extraText;
	private Drawable icon;
	private CharSequence contentDescription;
	private float cornerRadius;
	private int backgroundColour;
	
	// Constructors
	public DeviceOrTaskButtonView(Context context) {
		super(context);
		inflateViews(context);
	}
	
	public DeviceOrTaskButtonView(Context context, AttributeSet attrs) {
		super(context, attrs);
		getAttributes(context, attrs);
		inflateViews(context);
		setAttributes();
	}
	
	public DeviceOrTaskButtonView(Context context, AttributeSet attrs, int defStyle) {
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
		inflater.inflate(R.layout.view_device_or_task_button, this);
		
		iconImageView = findViewById(R.id.imageView_type);
		nameTextView = findViewById(R.id.textView_name);
		extraTextView = findViewById(R.id.textView_extra);
	}
	
	private void getAttributes(Context context, AttributeSet attrs) {
		TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.DeviceOrTaskButtonView);
		
		name = attributes.getText(R.styleable.DeviceOrTaskButtonView_android_text);
		cornerRadius = attributes.getDimension(R.styleable.DeviceOrTaskButtonView_cardCornerRadius,
				getResources().getDimension(R.dimen.device_or_task_button_corner_radius));
		icon = attributes.getDrawable(R.styleable.DeviceOrTaskButtonView_android_src);
		contentDescription = attributes.getString(R.styleable.DeviceOrTaskButtonView_contentDescription);
		backgroundColour = attributes.getColor(R.styleable.DeviceOrTaskButtonView_backgroundTint,
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
	
	public int getType() {
		return type;
	}
	
	public void setType(int type) {
		this.type = type;
	}
	
	public String getDeviceOrTaskId() {
		return deviceOrTaskId;
	}
	
	public void setDeviceOrTaskId(String deviceOrTaskId) {
		this.deviceOrTaskId = deviceOrTaskId;
	}
	
	public String getDeviceType() {
		return deviceType;
	}
	
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	
	public String getFavoritesCategory() {
		return favoritesCategory;
	}
	
	public void setFavoritesCategory(String favoritesCategory) {
		this.favoritesCategory = favoritesCategory;
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
		if (visible) {
			extraTextView.setVisibility(View.VISIBLE);
		}
		else {
			extraTextView.setVisibility(View.GONE);
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
	
	public CharSequence getIconContentDesc() {
		return iconImageView.getContentDescription();
	}
	
	public void setIconContentDesc(CharSequence contentDescription) {
		iconImageView.setContentDescription(contentDescription);
	}
	
	public void setIconContentDesc(String contentDescription) {
		iconImageView.setContentDescription(contentDescription);
	}
	
	public float getCornerRadius() {
		return this.getRadius();
	}
	
	public int getBackgroundColour() {
		return this.getBackgroundColour();
	}
	
	public void setBackgroundColour(int colour) {
		this.setBackgroundTintList(ColorStateList.valueOf(colour));
	}
}