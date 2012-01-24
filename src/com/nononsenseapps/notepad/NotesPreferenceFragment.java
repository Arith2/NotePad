package com.nononsenseapps.notepad;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.util.Log;

public class NotesPreferenceFragment extends PreferenceFragment implements
		OnSharedPreferenceChangeListener {
	public static final String KEY_THEME = "key_current_theme";
	public static final String KEY_SORT_ORDER = "key_sort_order";
	public static final String KEY_SORT_TYPE = "key_sort_type";
	
	public static final String THEME_DARK = "dark";
	public static final String THEME_LIGHT = "light";
	public static final String THEME_LIGHT_ICS_AB = "light_ab";

	private Preference prefSortOrder;
	private Preference prefSortType;
	private Preference prefTheme;

	public String SUMMARY_SORT_TYPE;
	public String SUMMARY_SORT_ORDER;
	public String SUMMARY_THEME;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Load the preferences from an XML resource
		addPreferencesFromResource(R.xml.main_preferences);

		SUMMARY_SORT_TYPE = getText(
				R.string.settings_summary_sort_type_alphabetic).toString();

		SUMMARY_SORT_ORDER = getText(R.string.settings_summary_sort_order_desc)
				.toString();
		
		SUMMARY_THEME = getText(R.string.settings_summary_theme_dark)
		.toString();

		prefSortOrder = getPreferenceScreen().findPreference(KEY_SORT_ORDER);
		prefSortType = getPreferenceScreen().findPreference(KEY_SORT_TYPE);
		prefTheme = getPreferenceScreen().findPreference(KEY_THEME);

		SharedPreferences sharedPrefs = getPreferenceScreen()
				.getSharedPreferences();
		// Set up a listener whenever a key changes
		sharedPrefs.registerOnSharedPreferenceChangeListener(this);

		// Set summaries
		setTypeSummary(sharedPrefs);
		setOrderSummary(sharedPrefs);
		setThemeSummary(sharedPrefs);

		// Set up navigation (adds nice arrow to icon)
		// ActionBar actionBar = getActionBar();
		// actionBar.setDisplayHomeAsUpEnabled(true);
	}

	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		if (KEY_THEME.equals(key)) {
			Log.d("settings", "Theme changed!");
			setThemeSummary(sharedPreferences);
		} else if (KEY_SORT_TYPE.equals(key)) {
			Log.d("settings", "Sort type changed!");
			setTypeSummary(sharedPreferences);
		} else if (KEY_SORT_ORDER.equals(key)) {
			Log.d("settings", "Sort order changed!");
			setOrderSummary(sharedPreferences);
		} else
			Log.d("settings", "Somethign changed!");
	}

	private void setOrderSummary(SharedPreferences sharedPreferences) {
		String value = sharedPreferences.getString(KEY_SORT_ORDER,
				NotePad.Notes.DEFAULT_SORT_ORDERING);
		String summary;
		if (NotePad.Notes.DEFAULT_SORT_ORDERING.equals(value))
			summary = getText(R.string.settings_summary_sort_order_desc)
					.toString();
		else
			summary = getText(R.string.settings_summary_sort_order_asc)
					.toString();
		SUMMARY_SORT_ORDER = summary;
		prefSortOrder.setSummary(summary);
	}

	private void setTypeSummary(SharedPreferences sharedPreferences) {
		String value = sharedPreferences.getString(KEY_SORT_TYPE,
				NotePad.Notes.DEFAULT_SORT_TYPE);
		String summary;
		if (NotePad.Notes.DEFAULT_SORT_TYPE.equals(value))
			summary = getText(R.string.settings_summary_sort_type_modified)
					.toString();
		else
			summary = getText(R.string.settings_summary_sort_type_alphabetic)
					.toString();
		SUMMARY_SORT_TYPE = summary;
		prefSortType.setSummary(summary);
	}
	
	private void setThemeSummary(SharedPreferences sharedPreferences) {
		// Dark theme is default
		String value = sharedPreferences.getString(KEY_THEME,
				THEME_DARK);
		String summary;
		if (THEME_DARK.equals(value))
			summary = getText(R.string.settings_summary_theme_dark)
					.toString();
		else if (THEME_LIGHT.equals(value))
			summary = getText(R.string.settings_summary_theme_light)
			.toString();
		else
			summary = getText(R.string.settings_summary_theme_light_dark_ab)
			.toString();
		SUMMARY_THEME = summary;
		prefTheme.setSummary(summary);
	}

	/*
	 * @Override public boolean onOptionsItemSelected(MenuItem item) { // Handle
	 * all of the possible menu actions. switch (item.getItemId()) { case
	 * android.R.id.home: Log.d("Settings", "Go back!"); finish(); break; }
	 * return super.onOptionsItemSelected(item); }
	 */
}