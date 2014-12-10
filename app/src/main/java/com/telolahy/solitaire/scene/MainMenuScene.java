package com.telolahy.solitaire.scene;

import android.app.AlertDialog;
import android.content.DialogInterface;

import com.telolahy.solitaire.R;
import com.telolahy.solitaire.application.Constants;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.TextMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.util.adt.align.HorizontalAlign;

/**
 * Created by stephanohuguestelolahy on 11/16/14.
 */
public class MainMenuScene extends BaseScene {

    // ===========================================================
    // Constants
    // ===========================================================

    private static final int MENU_ITEM_PLAY = 1;
    private static final int MENU_ITEM_OPTIONS = 2;

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

        createHomeMenuChildScene();
        createHUD();
        setupTouchGesture();
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

    private void setupTouchGesture() {

        this.setTouchAreaBindingOnActionDownEnabled(true);
        this.setTouchAreaBindingOnActionMoveEnabled(true);
        this.setOnSceneTouchListenerBindingOnActionDownEnabled(true);
    }


    private void createHUD() {

        mHUD = new HUD();

        mTitle = new Text(Constants.SCREEN_WIDTH / 2, Constants.SCREEN_HEIGHT * 3 / 4, mResourcesManager.menuTitleFont, "abcdefghijklmnopqrstuvwxyz", new TextOptions(HorizontalAlign.CENTER), mVertexBufferObjectManager);
        mTitle.setText(mResourcesManager.activity.getResources().getString(R.string.app_name));
        mHUD.attachChild(mTitle);
        mCamera.setHUD(mHUD);
    }

    private void createHomeMenuChildScene() {

        mHomeMenuScene = new MenuScene(mCamera);

        TextMenuItem playTextMenuItem = new TextMenuItem(MENU_ITEM_PLAY, mResourcesManager.menuItemFont, mActivity.getResources().getString(R.string.play), mVertexBufferObjectManager);
        IMenuItem playMenuItem = new ScaleMenuItemDecorator(playTextMenuItem, 1.2f, 1);
        TextMenuItem optionsTextMenuItem = new TextMenuItem(MENU_ITEM_OPTIONS, mResourcesManager.menuItemFont, mActivity.getResources().getString(R.string.options), mVertexBufferObjectManager);
        IMenuItem helpMenuItem = new ScaleMenuItemDecorator(optionsTextMenuItem, 1.2f, 1);
        mHomeMenuScene.addMenuItem(playMenuItem);
        mHomeMenuScene.addMenuItem(helpMenuItem);

        mHomeMenuScene.buildAnimations();
        mHomeMenuScene.setBackgroundEnabled(false);

        mHomeMenuScene.setOnMenuItemClickListener(new MenuScene.IOnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem, float pMenuItemLocalX, float pMenuItemLocalY) {

                mResourcesManager.menuItemClickedSound.play();
                switch (pMenuItem.getID()) {
                    case MENU_ITEM_PLAY:
                        return true;
                    case MENU_ITEM_OPTIONS:
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
