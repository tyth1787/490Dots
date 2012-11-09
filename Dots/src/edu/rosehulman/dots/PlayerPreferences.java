package edu.rosehulman.dots;

import android.os.Bundle;
import android.preference.PreferenceActivity;


public class PlayerPreferences extends PreferenceActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.player_preferences);
	}
}
