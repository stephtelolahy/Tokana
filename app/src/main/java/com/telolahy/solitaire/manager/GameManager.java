package com.telolahy.solitaire.manager;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by stephanohuguestelolahy on 11/25/14.
 */
public class GameManager {

    // ===========================================================
    // Constants
    // ===========================================================

    private static final GameManager INSTANCE = new GameManager();

    private static final String PREFS_NAME = "preferences";
    private static final String MUSIC_PREFS_KEY = "music";
    private static final String MAX_LEVEL_PREFS_KEY = "maxLevel";

    // ===========================================================
    // Fields
    // ===========================================================

    // ===========================================================
    // Constructors
    // ===========================================================

    // ===========================================================
    // Getter & Setter
    // ===========================================================

    // ===========================================================
    // Methods from SuperClass
    // ===========================================================

    // ===========================================================
    // Methods for Interfaces
    // ===========================================================

    // ===========================================================
    // Methods from Interfaces
    // ===========================================================

    // ===========================================================
    // Public Methods
    // ===========================================================

    public static GameManager getInstance() {
        return INSTANCE;
    }

    public boolean isSoundEnabled() {
        return preferences().getBoolean(MUSIC_PREFS_KEY, true);
    }

    public void setSoundEnabled(boolean value) {
        SharedPreferences.Editor edit = preferences().edit();
        edit.putBoolean(MUSIC_PREFS_KEY, value);
        edit.commit();
    }

    public int maxScore() {
        return preferences().getInt(MAX_LEVEL_PREFS_KEY, 0);
    }

    public void saveMaxScore(int value) {

        SharedPreferences.Editor edit = preferences().edit();
        edit.putInt(MAX_LEVEL_PREFS_KEY, value);
        edit.commit();
    }


    // ===========================================================
    // Private Methods
    // ===========================================================

    private SharedPreferences preferences() {

        return ResourcesManager.getInstance().activity.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    // ===========================================================
    // Inner Classes/Interfaces
    // ===========================================================


}
