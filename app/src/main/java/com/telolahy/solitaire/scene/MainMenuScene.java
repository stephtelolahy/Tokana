package com.telolahy.solitaire.scene;

import android.app.AlertDialog;
import android.content.DialogInterface;

import com.telolahy.solitaire.R;
import com.telolahy.solitaire.application.Constants;
import com.telolahy.solitaire.manager.GameManager;
import com.telolahy.solitaire.manager.SceneManager;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.TextMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.adt.align.HorizontalAlign;
import org.andengine.util.adt.color.Color;

/**
 * Created by stephanohuguestelolahy on 11/16/14.
 */
public class MainMenuScene extends BaseScene {

    // ===========================================================
    // Constants
    // ===========================================================

    private static final int MENU_ITEM_PLAY = 1;
    private static final int MENU_ITEM_SHARE = 2;
    private static final int MENU_ITEM_SOUND = 3;

    // ===========================================================
    // Fields
    // ===========================================================

    private HUD mHUD;
    private Text mTitle;
    private MenuScene mHomeMenuScene;

    // ===========================================================
    // Constructors
    // ===========================================================

    public MainMenuScene(int... params) {
        super(params);
    }

    // ===========================================================
    // Getter & Setter
    // ===========================================================

    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================


    @Override
    protected void onCreateScene(int... params) {

        createBackground();
        createHomeMenuChildScene();
        createHUD();
    }


    @Override
    protected void onDisposeScene() {

        mCamera.setHUD(null);
        mHUD.detachSelf();
        mHomeMenuScene.dispose();
    }

    @Override
    public void onBackKeyPressed() {

        displayExitDialog();
    }

    // ===========================================================
    // Methods
    // ===========================================================

    private void createBackground() {

        setBackground(new Background(new Color(66f / 256f, 183f / 256f, 190f / 256f)));
    }


    private void createHUD() {

        mHUD = new HUD();
        mCamera.setHUD(mHUD);

        mTitle = new Text(Constants.SCREEN_WIDTH / 2, Constants.SCREEN_HEIGHT * 3 / 4, mResourcesManager.menuTitleFont, mResourcesManager.activity.getResources().getString(R.string.app_name), new TextOptions(HorizontalAlign.CENTER), mVertexBufferObjectManager) {
            @Override
            public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {

                if (pSceneTouchEvent.isActionDown()) {
                    if (GameManager.getInstance().isMusicEnabled())
                        mResourcesManager.menuItemClickedSound.play();
                    SceneManager.getInstance().loadCreditsScene();
                }
                return true;
            }
        };

        mHUD.attachChild(mTitle);
        registerTouchArea(mTitle);
    }

    private void createHomeMenuChildScene() {

        mHomeMenuScene = new MenuScene(mCamera);

        TextMenuItem playTextMenuItem = new TextMenuItem(MENU_ITEM_PLAY, mResourcesManager.menuItemFont, mActivity.getResources().getString(R.string.play), mVertexBufferObjectManager);
        IMenuItem playMenuItem = new ScaleMenuItemDecorator(playTextMenuItem, 1.2f, 1);
        mHomeMenuScene.addMenuItem(playMenuItem);

        TextMenuItem shareTextMenuItem = new TextMenuItem(MENU_ITEM_SHARE, mResourcesManager.menuItemFont, mActivity.getResources().getString(R.string.share), mVertexBufferObjectManager);
        IMenuItem shareMenuItem = new ScaleMenuItemDecorator(shareTextMenuItem, 1.2f, 1);
        mHomeMenuScene.addMenuItem(shareMenuItem);

        String text = GameManager.getInstance().isMusicEnabled() ? mActivity.getResources().getString(R.string.music_on) : mActivity.getResources().getString(R.string.music_off);
        final TextMenuItem soundTextMenuItem = new TextMenuItem(MENU_ITEM_SOUND, mResourcesManager.menuItemFont, text, mVertexBufferObjectManager);
        IMenuItem soundMenuItem = new ScaleMenuItemDecorator(soundTextMenuItem, 1.2f, 1);
        mHomeMenuScene.addMenuItem(soundMenuItem);

        mHomeMenuScene.buildAnimations();
        mHomeMenuScene.setBackgroundEnabled(false);

        mHomeMenuScene.setOnMenuItemClickListener(new MenuScene.IOnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem, float pMenuItemLocalX, float pMenuItemLocalY) {

                if (GameManager.getInstance().isMusicEnabled())
                    mResourcesManager.menuItemClickedSound.play();

                switch (pMenuItem.getID()) {
                    case MENU_ITEM_PLAY:
                        SceneManager.getInstance().createGameScene(1);
                        return true;
                    case MENU_ITEM_SOUND:
                        boolean soundEnabled = GameManager.getInstance().isMusicEnabled();
                        soundEnabled = !soundEnabled;
                        String text = soundEnabled ? mActivity.getResources().getString(R.string.music_on) : mActivity.getResources().getString(R.string.music_off);
                        soundTextMenuItem.setText(text);
                        GameManager.getInstance().setMusicEnabled(soundEnabled);
                        return true;
                    default:
                        return false;
                }
            }
        });
        setChildScene(mHomeMenuScene);
    }

    private void displayExitDialog() {

        mActivity.runOnUiThread(new Runnable() {
            public void run() {

                AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
                builder.setTitle(mResourcesManager.activity.getResources().getString(R.string.exit));
                builder.setMessage(mResourcesManager.activity.getResources().getString(R.string.exit_message));
                builder.setPositiveButton((mResourcesManager.activity.getResources().getString(R.string.yes)), new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        System.exit(0);
                    }
                });
                builder.setNegativeButton(mResourcesManager.activity.getResources().getString(R.string.no), null);
                builder.setCancelable(false);
                builder.show();
            }
        });
    }

    // ===========================================================
    // Implemented interfaces
    // ===========================================================


}
