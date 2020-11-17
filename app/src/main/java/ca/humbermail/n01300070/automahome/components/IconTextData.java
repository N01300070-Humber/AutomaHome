package ca.humbermail.n01300070.automahome.components;

import android.graphics.drawable.Drawable;

public class IconTextData {
	
	private String text;
	private int textAppearance;
	private Drawable icon;
	private int iconTint;
	private String iconContentDescription;
	boolean iconVisible;
	
	public IconTextData() {
		this(null, 0, null, 0, null, false);
	}
	
	public IconTextData(String text) {
		this(text, 0, null, 0, null, false);
	}
	
	public IconTextData(String text, int textAppearance) {
		this(text, textAppearance, null, 0, null, false);
	}
	
	public IconTextData(String text, int textAppearance, Drawable icon) {
		this(text, textAppearance, icon, 0, null, true);
	}
	
	public IconTextData(String text, int textAppearance, Drawable icon, int iconTint) {
		this(text, textAppearance, icon, iconTint, null, true);
	}
	
	public IconTextData(String text, int textAppearance, Drawable icon, int iconTint, String iconContentDescription) {
		this(text, textAppearance, icon, iconTint, iconContentDescription, true);
	}
	
	public IconTextData(String text, int textAppearance, Drawable icon, int iconTint, String iconContentDescription, boolean iconVisible) {
		this.text = text;
		this.textAppearance = textAppearance;
		this.icon = icon;
		this.iconTint = iconTint;
		this.iconContentDescription = iconContentDescription;
		this.iconVisible = iconVisible;
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public int getTextAppearance() {
		return textAppearance;
	}
	
	public void setTextAppearance(int textAppearance) {
		this.textAppearance = textAppearance;
	}
	
	public Drawable getIcon() {
		return icon;
	}
	
	public void setIcon(Drawable icon) {
		this.icon = icon;
	}
	
	public int getIconTint() {
		return iconTint;
	}
	
	public void setIconTint(int iconTint) {
		this.iconTint = iconTint;
	}
	
	public String getIconContentDescription() {
		return iconContentDescription;
	}
	
	public void setIconContentDescription(String iconContentDescription) {
		this.iconContentDescription = iconContentDescription;
	}
	
	public boolean isIconVisible() {
		return iconVisible;
	}
	
	public void setIconVisible(boolean iconVisible) {
		this.iconVisible = iconVisible;
	}
}
