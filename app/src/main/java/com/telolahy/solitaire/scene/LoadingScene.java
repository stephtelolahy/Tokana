package com.telolahy.solitaire.scene;

import com.telolahy.solitaire.R;
import com.telolahy.solitaire.application.Constants;

import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;

/**
 * Created by stephanohuguestelolahy on 11/16/14.
 */
public class LoadingScene extends BaseScene {

    // ===========================================================
    // Constants
    // ===========================================================

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
    // Methods for/from SuperClass/Interfaces
    // ===========================================================

    @Override
    protected void onCreateScene(int... params) {

        setBackground(new SpriteBackground(new Sprite(Constants.SCREEN_WIDTH / 2, Constants.SCREEN_HEIGHT / 2, mResourcesManager.gameBackground.textureRegion, mVertexBufferObjectManager)));
        String text = mActivity.getResources().getString(R.string.loading);
        attachChild(new Text(Constants.SCREEN_WIDTH / 2, Constants.SCREEN_HEIGHT / 2, mResourcesManager.menuLoadingFont, text, mVertexBufferObjectManager));
    }

    @Override
    protected void onDisposeScene() {

    }

    @Override
    public void onBackKeyPressed() {

    }

    // ===========================================================
    // Methods
    // ===========================================================
}
