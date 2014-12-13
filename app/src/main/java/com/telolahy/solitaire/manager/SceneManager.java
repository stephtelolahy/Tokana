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

    private static final SceneManager INSTANCE = new SceneManager();

    private Engine mEngine = ResourcesManager.getInstance().engine;
    private BaseScene mCurrentScene;
    private BaseScene mSplashScene;
    private BaseScene mLoadingScene;
    private BaseScene mCreditsScene;
    private BaseScene mGameScene;

    public static SceneManager getInstance() {
        return INSTANCE;
    }

    private void setScene(BaseScene scene) {

        mEngine.setScene(scene);
        mCurrentScene = scene;
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

    private void disposeSplashScene() {

        mSplashScene.disposeScene();
        mSplashScene = null;
        ResourcesManager.getInstance().unloadSplashResources();
    }

    public void createGameScene(final int level) {

        ResourcesManager.getInstance().loadGameResources();
        mGameScene = new GameScene(level);
        mCreditsScene = new CreditsScene();
        mLoadingScene = new LoadingScene();
        setScene(mGameScene);
        disposeSplashScene();
        AppRater.app_launched(ResourcesManager.getInstance().activity);
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
                mGameScene = new GameScene(1);
                setScene(mGameScene);
            }
        }));
    }
}
