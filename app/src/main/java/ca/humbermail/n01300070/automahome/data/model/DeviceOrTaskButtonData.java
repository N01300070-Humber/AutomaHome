package ca.humbermail.n01300070.automahome.data.model;

import android.graphics.drawable.Drawable;

public class DeviceOrTaskButtonData {
	public static final int TYPE_DEVICE = 0;
	public static final int TYPE_TASK = 1;
	
	public static final String ARG_DEVICE = "DeviceType";
	public static final String DEVICE_LIGHTS = "Lights";
	public static final String DEVICE_MOVEMENT_SENSOR = "Movement Sensor";
	public static final String DEVICE_THERMOSTAT = "Thermostat";
	
	private int type;
	private String deviceOrTaskId;
	private String deviceType;
	private String name;
	private String extraText;
	private boolean extraTextVisible;
	private String favoritesCategory;
	private Drawable icon;
	private String contentDescription;
	private int backgroundColour;
	
	public DeviceOrTaskButtonData(int type) {
		this(type, null, null, null, null, null, null);
	}
	
	public DeviceOrTaskButtonData(int type, String deviceOrTaskId, String name) {
		this(type, deviceOrTaskId, name, null, null, null, null);
	}
	
	public DeviceOrTaskButtonData(int type, String deviceOrTaskId, String name, String extraText) {
		this(type, deviceOrTaskId, name, extraText, null, null, null);
	}
	
	public DeviceOrTaskButtonData(int type, String deviceOrTaskId, String name, String extraText, String favoritesCategory) {
		this(type, deviceOrTaskId, name, extraText, favoritesCategory, null, null);
	}
	
	public DeviceOrTaskButtonData(int type, String deviceOrTaskId, String name, String extraText, String favoritesCategory, Drawable icon, String contentDescription) {
		this.type = type;
		this.deviceOrTaskId = deviceOrTaskId;
		this.name = name;
		this.extraText = extraText;
		this.extraTextVisible = isExtraTextNotEmpty();
		this.favoritesCategory = favoritesCategory;
		this.icon = icon;
		this.contentDescription = contentDescription;
	}
	
	public DeviceOrTaskButtonData(int type, String deviceOrTaskId, String name, String extraText, String favoritesCategory, Drawable icon, String contentDescription, int backgroundColour) {
		this(type, deviceOrTaskId, name, extraText, favoritesCategory, icon, contentDescription);
		this.extraTextVisible = isExtraTextNotEmpty();
		this.backgroundColour = backgroundColour;
	}
	
	public DeviceOrTaskButtonData(int type, String deviceOrTaskId, String name, String extraText, String favoritesCategory, Drawable icon, String contentDescription, int backgroundColour, boolean extraTextVisible) {
		this(type, deviceOrTaskId, name, extraText, favoritesCategory, icon, contentDescription);
		this.extraTextVisible = extraTextVisible;
		this.backgroundColour = backgroundColour;
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
	
	public String getFavoritesCategory() {
		return favoritesCategory;
	}
	
	public void setFavoritesCategory(String favoritesCategory) {
		this.favoritesCategory = favoritesCategory;
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
