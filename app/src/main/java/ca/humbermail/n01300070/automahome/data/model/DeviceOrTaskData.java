package ca.humbermail.n01300070.automahome.data.model;

import android.graphics.drawable.Drawable;

public class DeviceOrTaskData {
	public static final int TYPE_DEVICE = 0;
	public static final int TYPE_TASK = 1;
	
	private int type;
	private String name;
	private String extraText;
	private Drawable icon;
	private int backgroundColour;
	
	public DeviceOrTaskData() {
		this(-1, null, null);
		this.type = -1;
		this.name = null;
		this.extraText = null;
	}
	
	public DeviceOrTaskData(int type, String name, String extraText) {
		this.type = type;
		this.name = name;
		this.extraText = extraText;
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
	
	public Drawable getIcon() {
		return icon;
	}
	
	public void setIcon(Drawable icon) {
		this.icon = icon;
	}
	
	public int getBackgroundColour() {
		return backgroundColour;
	}
	
	public void setBackgroundColour(int backgroundColour) {
		this.backgroundColour = backgroundColour;
	}
}
