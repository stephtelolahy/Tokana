package com.telolahy.solitaire.scene;

import com.telolahy.solitaire.R;
import com.telolahy.solitaire.application.Constants;
import com.telolahy.solitaire.manager.ResourcesManager;

import org.andengine.entity.scene.background.Background;
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

        setBackground(new Background(ResourcesManager.BACKGROUND_COLOR_OBJ));
        String text = mActivity.getResources().getString(R.string.loading);
        attachChild(new Text(Constants.SCREEN_WIDTH / 2, Constants.SCREEN_HEIGHT / 2, mResourcesManager.menuLoadingFont.font, text, mVertexBufferObjectManager));
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
