package com.telolahy.solitaire.manager;

import com.telolahy.solitaire.scene.BaseScene;
import com.telolahy.solitaire.scene.CreditsScene;
import com.telolahy.solitaire.scene.GameScene;
import com.telolahy.solitaire.scene.LoadingScene;
import com.telolahy.solitaire.scene.SplashScene;
import com.telolahy.utils.AppRater;

import org.andengine.engine.Engine;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.ui.IGameInterface;

/**
 * Created by stephanohuguestelolahy on 11/15/14.
 */
public class SceneManager {


    // ===========================================================
    // Constants
    // ===========================================================

    private static final SceneManager INSTANCE = new SceneManager();

    // ===========================================================
    // Fields
    // ===========================================================

    private Engine mEngine = ResourcesManager.getInstance().engine;
    private BaseScene mCurrentScene;
    private BaseScene mSplashScene;
    private BaseScene mLoadingScene;
    private BaseScene mCreditsScene;
    private BaseScene mGameScene;

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

    public static SceneManager getInstance() {
        return INSTANCE;
    }

    public BaseScene getCurrentScene() {

        return mCurrentScene;
    }

    public void createSplashScene(IGameInterface.OnCreateSceneCallback pOnCreateSceneCallback) {

        ResourcesManager.getInstance().loadSplashResources();
        mSplashScene = new SplashScene();
        mCurrentScene = mSplashScene;
        pOnCreateSceneCallback.onCreateSceneFinished(mSplashScene);
    }

    public void createGameScene(final int level) {

        ResourcesManager.getInstance().loadGameResources();
        mGameScene = new GameScene();
        mCreditsScene = new CreditsScene();
        mLoadingScene = new LoadingScene();
        setScene(mGameScene);
        disposeSplashScene();
        AppRater.checkAppLaunched();
    }

    public void loadCreditsScene() {

        setScene(mCreditsScene);
    }

    public void loadGameScene() {

        if (mCurrentScene == mCreditsScene) {
            setScene(mGameScene);
        }
    }

    public void reloadGameScene() {

        setScene(mLoadingScene);
        mGameScene.disposeScene();
        mGameScene = null;
        mEngine.registerUpdateHandler(new TimerHandler(1f, new ITimerCallback() {
            public void onTimePassed(final TimerHandler pTimerHandler) {
                mEngine.unregisterUpdateHandler(pTimerHandler);
                mGameScene = new GameScene();
                setScene(mGameScene);
            }
        }));
    }

    // ===========================================================
    // Private Methods
    // ===========================================================

    private void setScene(BaseScene scene) {

        mEngine.setScene(scene);
        mCurrentScene = scene;
    }

    private void disposeSplashScene() {

        mSplashScene.disposeScene();
        mSplashScene = null;
        ResourcesManager.getInstance().unloadSplashResources();
    }

    // ===========================================================
    // Inner Classes/Interfaces
    // ===========================================================

}
