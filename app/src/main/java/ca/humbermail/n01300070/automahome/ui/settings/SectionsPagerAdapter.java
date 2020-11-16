package ca.humbermail.n01300070.automahome.ui.settings;

import android.accounts.Account;
import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import ca.humbermail.n01300070.automahome.R;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {
	
	@StringRes
	private static final int[] TAB_TITLES = new int[]{R.string.tab_settings_general, R.string.tab_settings_account, R.string.tab_settings_notification};
	private final Context mContext;
	
	public SectionsPagerAdapter(Context context, FragmentManager fm) {
		super(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
		mContext = context;
	}
	
	@Override
	public Fragment getItem(int position) {
		// getItem is called to instantiate the fragment for the given page.
		// Return a PlaceholderFragment (defined as a static inner class below).
		switch (position) {
			case 0:
				return GeneralSettingsFragment.newInstance();
			case 1:
				return AccountSettingsFragment.newInstance();
			case 2:
				return NotificationSettingsFragment.newInstance();
		}
		return null;
	}
	
	@Nullable
	@Override
	public CharSequence getPageTitle(int position) {
		return mContext.getResources().getString(TAB_TITLES[position]);
	}
	
	@Override
	public int getCount() {
		// Show 3 total pages.
		return 3;
	}
}