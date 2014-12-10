package com.telolahy.solitaire.manager;

import android.content.Context;
import android.content.SharedPreferences;

import com.telolahy.solitaire.application.Constants;

import java.io.IOException;

/**
 * Created by stephanohuguestelolahy on 11/25/14.
 */
public class GameManager {

    private static final GameManager INSTANCE = new GameManager();

    private static final String PREFS_NAME = "preferences";
    private static final String LEVEL_PREFS_KEY = "level";
    private static final String MUSIC_PREFS_KEY = "music";

    public static GameManager getInstance() {
        return INSTANCE;
    }

    public int maxLevelReached() {

        if (Constants.DEBUG) {
            return Constants.TOTAL_LEVELS_COUNT + 1;
        }

        return preferences().getInt(LEVEL_PREFS_KEY, 1);
    }

    public void setLevelCompleted(int level) {

        int nextLevel = level + 1;
        if (nextLevel > maxLevelReached()) {
            SharedPreferences.Editor edit = preferences().edit();
            edit.putInt(LEVEL_PREFS_KEY, nextLevel);
            edit.commit();
        }
    }

    public boolean isMusicEnabled() {
        return preferences().getBoolean(MUSIC_PREFS_KEY, true);
    }

    public void setMusicEnabled(boolean value) {
        SharedPreferences.Editor edit = preferences().edit();
        edit.putBoolean(MUSIC_PREFS_KEY, value);
        edit.commit();
    }

    private SharedPreferences preferences() {

        return ResourcesManager.getInstance().activity.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public int countLevelsAssetFiles(Context context) {

        try {
            String[] list = context.getAssets().list("level");
            return list.length;
        } catch (IOException e) {
            return 0;
        }
    }

}
