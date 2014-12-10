package com.telolahy.solitaire.scene;


import com.telolahy.solitaire.manager.SceneManager;

import org.andengine.entity.scene.background.Background;
import org.andengine.util.adt.color.Color;

/**
 * Created by stephanohuguestelolahy on 11/16/14.
 */
public class GameScene extends BaseScene {

    public GameScene(int... params) {
        super(params);
    }

    @Override
    protected void onCreateScene(int... params) {

        createBackground();

    }

    @Override
    protected void onDisposeScene() {

    }

    @Override
    public void onBackKeyPressed() {

        SceneManager.getInstance().loadMenuScene();
    }

    private void createBackground() {

        setBackground(new Background(new Color(66f / 256f, 183f / 256f, 190f / 256f)));
    }
}
