package com.telolahy.utils.resources;

import android.content.Context;

import com.telolahy.solitaire.manager.GameManager;

import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.audio.sound.SoundManager;
import org.andengine.util.debug.Debug;

import java.io.IOException;

/**
 * Created by stephanohuguestelolahy on 12/13/14.
 */
public class SoundDescription {

    private final String mSoundFile;
    private Sound mSound;

    public SoundDescription(String file) {
        mSoundFile = file;
    }

    public void load(SoundManager manager, Context context) {

        try {
            mSound = SoundFactory.createSoundFromAsset(manager, context, mSoundFile);
        } catch (final IOException e) {
            mSound = null;
            Debug.e(e);
        }
    }

    public void play() {
        if (mSound != null && GameManager.getInstance().isSoundEnabled()) {
            mSound.play();
        }
    }
}
