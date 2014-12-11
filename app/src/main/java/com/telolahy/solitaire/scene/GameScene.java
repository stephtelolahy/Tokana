package com.telolahy.solitaire.scene;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Point;

import com.telolahy.solitaire.R;
import com.telolahy.solitaire.application.Constants;
import com.telolahy.solitaire.core.GameMap;
import com.telolahy.solitaire.manager.SceneManager;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.scene.background.Background;
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
        public float startX;
        public float startY;

        GameElement(final float pX, final float pY, final ITextureRegion pTextureRegion, final VertexBufferObjectManager pVertexBufferObjectManager) {
            super(pX, pY, pTextureRegion, pVertexBufferObjectManager);
        }
    }

    private GameMap mGame;
    private GameElement[][] mElements;

    private int mX0;
    private int mY0;
    private int mBlockSize;

    private int mLevel;
    private int mMoves;

    private Text mMovesText;


    public GameScene(int... params) {
        super(params);
    }

    @Override
    protected void onCreateScene(int... params) {

        mLevel = 1;
        createBackground();
        createHUD();
        loadLevel();
    }

    @Override
    protected void onDisposeScene() {

        mCamera.setHUD(null);
    }

    @Override
    public void onBackKeyPressed() {

        exitGame(false);
    }

    private void createHUD() {

        HUD gameHUD = new HUD();

        Text levelText = new Text(Constants.SCREEN_WIDTH / 2, Constants.SCREEN_HEIGHT - 40, mResourcesManager.menuItemFont, "Level " + mLevel, new TextOptions(HorizontalAlign.CENTER), mVertexBufferObjectManager);
        gameHUD.attachChild(levelText);

        mMovesText = new Text(Constants.SCREEN_WIDTH / 2, 40, mResourcesManager.menuItemFont, "Moves: 0123", new TextOptions(HorizontalAlign.CENTER), mVertexBufferObjectManager);
        gameHUD.attachChild(mMovesText);

        mCamera.setHUD(gameHUD);
    }

    private void createBackground() {

        setBackground(new Background(new Color(66f / 256f, 183f / 256f, 190f / 256f)));
//        setBackground(new SpriteBackground(new Sprite(Constants.SCREEN_WIDTH / 2, Constants.SCREEN_HEIGHT/2, mResourcesManager.gameBackground.textureRegion, mVertexBufferObjectManager)));
    }

    private void loadLevel() {

        String levelFile = "level/level" + mLevel + ".txt";
        mGame = GameMap.createGame(levelFile, mActivity);

        if (mGame == null) {
            displayErrorLoadingLevel(levelFile);
            return;
        }

        updateMoves(0);

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
                        attachChild(new Sprite(posX, posY, mResourcesManager.gameEmptyTexture.textureRegion, mVertexBufferObjectManager));
                        break;

                    case GameMap.PIECE:
                        attachChild(new Sprite(posX, posY, mResourcesManager.gameEmptyTexture.textureRegion, mVertexBufferObjectManager));

                        GameElement piece = new GameElement(posX, posY, mResourcesManager.gamePieceTexture.textureRegion, mVertexBufferObjectManager) {
                            @Override
                            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {

                                if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {
                                    this.startX = this.getX();
                                    this.startY = this.getY();
                                }

                                this.setPosition(pSceneTouchEvent.getX(), pSceneTouchEvent.getY());


                                if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP) {
                                    checkMove(this);
                                }
                                return true;
                            }
                        };
                        mElements[x][y] = piece;
                        attachChild(piece);
                        registerTouchArea(piece);
                        break;

                    default:
                        break;
                }
            }
        }

        setTouchAreaBindingOnActionDownEnabled(true);
    }

    private void updateMoves(final int i) {

        mMoves = i;
        mMovesText.setText("Moves " + i);
    }

    private void checkMove(GameElement piece) {

        int sourceX = Math.round((piece.startX - mX0)) / mBlockSize;
        int sourceY = Math.round((piece.startY - mY0)) / mBlockSize;

        int targetX = Math.round((piece.getX() - mX0)) / mBlockSize;
        int targetY = Math.round((piece.getY() - mY0)) / mBlockSize;

        Point inter = mGame.computeMovement(new Point(sourceX, sourceY), new Point(targetX, targetY));

        if (inter != null) {
            int posX = mX0 + targetX * mBlockSize + mBlockSize / 2;
            int posY = mY0 + targetY * mBlockSize + mBlockSize / 2;
            piece.setPosition(posX, posY);

            mResourcesManager.menuItemClickedSound.play();

            mGame.setElement(new Point(sourceX, sourceY), GameMap.EMPTY);
            mGame.setElement(new Point(targetX, targetY), GameMap.PIECE);
            mGame.setElement(inter, GameMap.EMPTY);

            mElements[sourceX][sourceY] = null;
            mElements[targetX][targetY] = piece;
            mElements[inter.x][inter.y].detachSelf();
            mElements[inter.x][inter.y] = null;

            updateMoves(mMoves + 1);

        } else {
            piece.setPosition(piece.startX, piece.startY);
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
                        exitGame(false);
                    }
                });
                ad.show();
            }
        });
    }

    private void exitGame(boolean completed) {

        SceneManager.getInstance().loadMenuScene();
    }
}
