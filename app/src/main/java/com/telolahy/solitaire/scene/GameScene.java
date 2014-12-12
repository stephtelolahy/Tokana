package com.telolahy.solitaire.scene;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Point;

import com.telolahy.solitaire.R;
import com.telolahy.solitaire.application.Constants;
import com.telolahy.solitaire.core.GameMap;
import com.telolahy.solitaire.manager.GameManager;
import com.telolahy.solitaire.manager.SceneManager;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.TextMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.adt.align.HorizontalAlign;
import org.andengine.util.adt.color.Color;

/**
 * Created by stephanohuguestelolahy on 11/16/14.
 */
public class GameScene extends BaseScene {

    static class GameElement extends Sprite {

        public float lastX;
        public float lastY;

        GameElement(final float pX, final float pY, final ITextureRegion pTextureRegion, final VertexBufferObjectManager pVertexBufferObjectManager) {
            super(pX, pY, pTextureRegion, pVertexBufferObjectManager);
            lastX = pX;
            lastY = pY;
        }
    }

    private static final int MENU_ITEM_PLAY = 1;
    private static final int MENU_ITEM_SHARE = 2;
    private static final int MENU_ITEM_SOUND = 3;

    private HUD mHUD;
    private Text mTitle;
    private MenuScene mMenuScene;

    private GameMap mGame;
    private GameElement[][] mElements;
    private GameElement mCurrentTouchElement;

    private int mX0;
    private int mY0;
    private int mBlockSize;

    private int mLevel;
    private int mMoves;

    private Text mScoreText;
    private Text mBestText;

    public GameScene(int... params) {
        super(params);
    }

    @Override
    protected void onCreateScene(int... params) {

        mLevel = 1;
        createBackground();
        createHUD();
        createMenu();
        loadLevel();
    }

    @Override
    protected void onDisposeScene() {

        mCamera.setHUD(null);
        mHUD.detachSelf();
        mMenuScene.dispose();
    }

    @Override
    public void onBackKeyPressed() {

        displayExitDialog();
    }

    private void createBackground() {

        setBackground(new SpriteBackground(new Sprite(Constants.SCREEN_WIDTH / 2, Constants.SCREEN_HEIGHT / 2, mResourcesManager.gameBackground.textureRegion, mVertexBufferObjectManager)));
    }

    private void createHUD() {

        mHUD = new HUD();
        mCamera.setHUD(mHUD);

        mTitle = new Text(64, Constants.SCREEN_HEIGHT - 50, mResourcesManager.menuTitleFont, mResourcesManager.activity.getResources().getString(R.string.app_name), new TextOptions(HorizontalAlign.LEFT), mVertexBufferObjectManager) {
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

        mScoreText = new Text(Constants.SCREEN_WIDTH / 4, 40, mResourcesManager.menuItemFont, "Score0123456789", new TextOptions(HorizontalAlign.CENTER), mVertexBufferObjectManager);
        mHUD.attachChild(mScoreText);

        mBestText = new Text(Constants.SCREEN_WIDTH * 3 / 4, 40, mResourcesManager.menuItemFont, "Best0123456789", new TextOptions(HorizontalAlign.CENTER), mVertexBufferObjectManager);
        mHUD.attachChild(mBestText);
    }

    private void createMenu() {

        mMenuScene = new MenuScene(mCamera);

        TextMenuItem playTextMenuItem = new TextMenuItem(MENU_ITEM_PLAY, mResourcesManager.menuItemFont, mActivity.getResources().getString(R.string.replay), mVertexBufferObjectManager);
        IMenuItem playMenuItem = new ScaleMenuItemDecorator(playTextMenuItem, 1.2f, 1);
        mMenuScene.addMenuItem(playMenuItem);

        TextMenuItem shareTextMenuItem = new TextMenuItem(MENU_ITEM_SHARE, mResourcesManager.menuItemFont, mActivity.getResources().getString(R.string.share), mVertexBufferObjectManager);
        IMenuItem shareMenuItem = new ScaleMenuItemDecorator(shareTextMenuItem, 1.2f, 1);
        mMenuScene.addMenuItem(shareMenuItem);

        String text = GameManager.getInstance().isMusicEnabled() ? mActivity.getResources().getString(R.string.music_on) : mActivity.getResources().getString(R.string.music_off);
        final TextMenuItem soundTextMenuItem = new TextMenuItem(MENU_ITEM_SOUND, mResourcesManager.menuItemFont, text, mVertexBufferObjectManager);
        IMenuItem soundMenuItem = new ScaleMenuItemDecorator(soundTextMenuItem, 1.2f, 1);
        mMenuScene.addMenuItem(soundMenuItem);

        mMenuScene.buildAnimations();
        mMenuScene.setBackgroundEnabled(false);

        playMenuItem.setPosition(Constants.SCREEN_WIDTH / 6, Constants.SCREEN_HEIGHT - 100);
        shareMenuItem.setPosition(Constants.SCREEN_WIDTH / 2, Constants.SCREEN_HEIGHT - 100);
        soundMenuItem.setPosition(Constants.SCREEN_WIDTH * 5 / 6, Constants.SCREEN_HEIGHT - 100);

        mMenuScene.setOnMenuItemClickListener(new MenuScene.IOnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem, float pMenuItemLocalX, float pMenuItemLocalY) {

                if (GameManager.getInstance().isMusicEnabled())
                    mResourcesManager.menuItemClickedSound.play();

                switch (pMenuItem.getID()) {
                    case MENU_ITEM_PLAY:
                        SceneManager.getInstance().reloadGameScene();
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
        setChildScene(mMenuScene);

    }


    private void loadLevel() {

        String levelFile = "level/level" + mLevel + ".txt";
        mGame = GameMap.createGame(levelFile, mActivity);

        if (mGame == null) {
            displayErrorLoadingLevel(levelFile);
            return;
        }

        final int sizeX = mGame.getSizeX();
        final int sizeY = mGame.getSizeY();

        mBlockSize = (int) mResourcesManager.gameEmptyTexture.textureRegion.getWidth();
        int worldWidth = mBlockSize * sizeX;
        int worldHeight = mBlockSize * sizeY;

        mX0 = (Constants.SCREEN_WIDTH - worldWidth) / 2;
        mY0 = (Constants.SCREEN_HEIGHT - worldHeight) / 2;

        mElements = new GameElement[sizeX][sizeY];

        for (int y = 0; y < sizeY; y++) {
            for (int x = 0; x < sizeX; x++) {

                int posX = mX0 + x * mBlockSize + mBlockSize / 2;
                int posY = mY0 + y * mBlockSize + mBlockSize / 2;

                switch (mGame.getElement(new Point(x, y))) {

                    case GameMap.EMPTY:
                        mElements[x][y] = null;
                        attachChild(new Sprite(posX, posY, mResourcesManager.gameEmptyTexture.textureRegion, mVertexBufferObjectManager));
                        break;

                    case GameMap.PIECE:
                        attachChild(new Sprite(posX, posY, mResourcesManager.gameEmptyTexture.textureRegion, mVertexBufferObjectManager));

                        GameElement piece = new GameElement(posX, posY, mResourcesManager.gamePieceTexture.textureRegion, mVertexBufferObjectManager) {
                            @Override
                            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {


                                if (mCurrentTouchElement == null) {
                                    mCurrentTouchElement = this;
                                }

                                if (mCurrentTouchElement != this) {
                                    return false;
                                }

                                this.setPosition(pSceneTouchEvent.getX(), pSceneTouchEvent.getY());

                                if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP
                                        || pSceneTouchEvent.getAction() == TouchEvent.ACTION_CANCEL
                                        || pSceneTouchEvent.getAction() == TouchEvent.ACTION_OUTSIDE) {
                                    checkMove(this);

                                    mCurrentTouchElement = null;
                                }

                                return true;
                            }
                        };
                        mElements[x][y] = piece;
                        attachChild(piece);
                        registerTouchArea(piece);
                        break;

                    default:
                        mElements[x][y] = null;
                        break;
                }
            }

            updateMoves(0);
        }

        setTouchAreaBindingOnActionDownEnabled(true);
    }

    private void updateMoves(final int i) {

        mMoves = i;

        int score = i;
        mScoreText.setText("Score  " + score);

        int savedBest = GameManager.getInstance().maxScore();
        int best = Math.max(score, savedBest);
        mBestText.setText("Best  " + best);

        if (best > savedBest) {
            GameManager.getInstance().saveMaxScore(best);
        }
    }

    private void checkMove(GameElement piece) {

        int sourceX = Math.round((piece.lastX - mX0)) / mBlockSize;
        int sourceY = Math.round((piece.lastY - mY0)) / mBlockSize;

        int targetX = Math.round((piece.getX() - mX0)) / mBlockSize;
        int targetY = Math.round((piece.getY() - mY0)) / mBlockSize;

        Point inter = mGame.computeMovement(new Point(sourceX, sourceY), new Point(targetX, targetY));

        if (inter != null) {

            int posX = mX0 + targetX * mBlockSize + mBlockSize / 2;
            int posY = mY0 + targetY * mBlockSize + mBlockSize / 2;
            piece.setPosition(posX, posY);
            piece.lastX = posX;
            piece.lastY = posY;

            GameElement interElement = mElements[inter.x][inter.y];
            detachChild(interElement);
            unregisterTouchArea(interElement);

            if (interElement.hasParent()) throw new IllegalArgumentException("expected no parent");

            mGame.setElement(new Point(sourceX, sourceY), GameMap.EMPTY);
            mGame.setElement(new Point(targetX, targetY), GameMap.PIECE);
            mGame.setElement(inter, GameMap.EMPTY);

            mElements[sourceX][sourceY] = null;
            mElements[targetX][targetY] = piece;
            mElements[inter.x][inter.y] = null;

            updateMoves(mMoves + 1);

            if (GameManager.getInstance().isMusicEnabled())
                mResourcesManager.menuItemClickedSound.play();

        } else {
            piece.setPosition(piece.lastX, piece.lastY);
        }
    }

    private void displayErrorLoadingLevel(final String levelFile) {

        mActivity.runOnUiThread(new Runnable() {
            public void run() {

                String title = mActivity.getResources().getString(R.string.error_loading_level);
                String message = mActivity.getResources().getString(R.string.cannot_load_level) + ": " + levelFile;
                String positiveText = mActivity.getResources().getString(R.string.close);
                AlertDialog.Builder ad = new AlertDialog.Builder(mActivity);
                ad.setTitle(title);
                ad.setMessage(message);
                ad.setPositiveButton(positiveText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        System.exit(0);
                    }
                });
                ad.show();
            }
        });
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
}
