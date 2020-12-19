package ca.humbermail.n01300070.automahome.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import ca.humbermail.n01300070.automahome.R;
import ca.humbermail.n01300070.automahome.data.LoginDataSource;
import ca.humbermail.n01300070.automahome.data.PreferenceKeys;
import ca.humbermail.n01300070.automahome.data.RealtimeDatabaseDataSource;

public class CustomActivity extends AppCompatActivity {
	public static final int THEME_SYSTEM_DEFAULT = 0;
	public static final int THEME_LIGHT = 1;
	public static final int THEME_DARK = 2;
	
	LoginDataSource loginDataSource;
	RealtimeDatabaseDataSource realtimeDatabaseDataSource;
	SharedPreferences settings;
	
	Integer themeOverride = null;
	
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		settings = getSharedPreferences(PreferenceKeys.KEY_SETTINGS, Context.MODE_PRIVATE);
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public Resources.Theme getTheme() {
		Resources.Theme theme = super.getTheme();
		
		if (themeOverride == null) {
			switch (getThemeSelection()) {
				case THEME_SYSTEM_DEFAULT:
					break;
				case THEME_LIGHT:
					theme.applyStyle(R.style.Theme_AutomaHome_Light, true);
					break;
				case THEME_DARK:
					theme.applyStyle(R.style.Theme_AutomaHome_Dark, true);
					break;
			}
		} else {
			theme.applyStyle(themeOverride, true);
		}
		
		return theme;
	}
	
	public void setCurrentTheme(int themeSelection) {
		switch (themeSelection) {
			case THEME_SYSTEM_DEFAULT:
				setTheme(R.style.Theme_AutomaHome);
				break;
			case THEME_LIGHT:
				setTheme(R.style.Theme_AutomaHome_Light);
				break;
			case THEME_DARK:
				setTheme(R.style.Theme_AutomaHome_Dark);
				break;
		}
	}
	
	public int getThemeSelection() {
		return settings.getInt(PreferenceKeys.KEY_SETTINGS_THEME, 0);
	}
	
	public void overrideTheme(@StyleRes Integer resId) {
		themeOverride = resId;
	}
	
	public LoginDataSource getLoginDataSource() {
		return loginDataSource;
	}
	
	public void setLoginDataSource(LoginDataSource loginDataSource) {
		this.loginDataSource = loginDataSource;
	}
	
	public RealtimeDatabaseDataSource getRealtimeDatabaseDataSource() {
		return realtimeDatabaseDataSource;
	}
	
	public void setRealtimeDatabaseDataSource(RealtimeDatabaseDataSource realtimeDatabaseDataSource) {
		this.realtimeDatabaseDataSource = realtimeDatabaseDataSource;
	}
	
	public void removeAllListeners() {
		if (loginDataSource != null) {
			loginDataSource.removeAllListeners();
		}
	}
	
	@Override
	public void finish() {
		removeAllListeners();
		super.finish();
	}
}
