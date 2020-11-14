package ca.humbermail.n01300070.automahome.data.model;

import android.graphics.drawable.Drawable;

public class DeviceOrTaskData {
	public static final int TYPE_DEVICE = 0;
	public static final int TYPE_TASK = 1;
	
	private int type;
	private String name;
	private String extraText;
	private boolean extraTextVisible;
	private Drawable icon;
	private String contentDescription;
	private int backgroundColour;
	
	public DeviceOrTaskData(int type) {
		this(type, null, null, null, null);
	}
	
	public DeviceOrTaskData(int type, String name, String extraText) {
		this(type, name, extraText, null, null);
	}
	
	public DeviceOrTaskData(int type, String name, String extraText, Drawable icon, String contentDescription) {
		this.type = type;
		this.name = name;
		this.extraText = extraText;
		this.extraTextVisible = isExtraTextNotEmpty();
		this.icon = icon;
		this.contentDescription = contentDescription;
	}
	
	public DeviceOrTaskData(int type, String name, String extraText, Drawable icon, String contentDescription, int backgroundColour) {
		this(type, name, extraText, icon, contentDescription);
		this.extraTextVisible = isExtraTextNotEmpty();
		this.backgroundColour = backgroundColour;
	}
	
	public DeviceOrTaskData(int type, String name, String extraText, Drawable icon, String contentDescription, int backgroundColour, boolean extraTextVisible) {
		this(type, name, extraText, icon, contentDescription);
		this.extraTextVisible = extraTextVisible;
		this.backgroundColour = backgroundColour;
	}
	
	public int getType() {
		return type;
	}
	
	public void setType(int type) {
		this.type = type;
	}
	
	public DeviceOrTaskData(int type, String name) {
		this(type, name, null);
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getExtraText() {
		return extraText;
	}
	
	public void setExtraText(String extraText) {
		this.extraText = extraText;
	}
	
	public boolean isExtraTextVisible() {
		return extraTextVisible;
	}
	
	public void setExtraTextVisible(boolean extraTextVisible) {
		this.extraTextVisible = extraTextVisible;
	}
	
	public Drawable getIcon() {
		return icon;
	}
	
	public void setIcon(Drawable icon) {
		this.icon = icon;
	}
	
	public void setIcon(Drawable icon, String contentDescription) {
		this.icon = icon;
		setContentDescription(contentDescription);
	}
	
	public String getContentDescription() {
		return contentDescription;
	}
	
	public void setContentDescription(String contentDescription) {
		this.contentDescription = contentDescription;
	}
	
	public int getBackgroundColour() {
		return backgroundColour;
	}
	
	public void setBackgroundColour(int backgroundColour) {
		this.backgroundColour = backgroundColour;
	}
	
	public boolean isExtraTextNotEmpty() {
		return extraText != null && !extraText.isEmpty();
	}
}
