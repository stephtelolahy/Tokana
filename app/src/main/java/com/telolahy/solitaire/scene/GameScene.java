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

import java.util.ArrayList;

/**
 * Created by stephanohuguestelolahy on 11/16/14.
 */
public class GameScene extends BaseScene {


    static class GameElement extends Sprite
    {
        public int locationX;
        public int locationY;

        GameElement(final float pX, final float pY, final ITextureRegion pTextureRegion, final VertexBufferObjectManager pVertexBufferObjectManager, final int pLocationX, final int pLocationY)
        {
            super(pX, pY, pTextureRegion,pVertexBufferObjectManager);
            this.locationX = pLocationX;
            this.locationY = pLocationY;
        }
    }

    GameMap mGame;
    ArrayList<GameElement> mPieces;
    ArrayList<GameElement> mPlaces;

    public GameScene(int... params) {
        super(params);
    }

    @Override
    protected void onCreateScene(int... params) {

        createBackground();
        loadLevel(1);
        createHUD();
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

        Text levelText = new Text(Constants.SCREEN_WIDTH / 2, Constants.SCREEN_HEIGHT - 40, mResourcesManager.menuItemFont, "Level 0123", new TextOptions(HorizontalAlign.CENTER), mVertexBufferObjectManager);
        gameHUD.attachChild(levelText);

        Text movesText = new Text(Constants.SCREEN_WIDTH / 2, 40, mResourcesManager.menuItemFont, "Moves: 0123", new TextOptions(HorizontalAlign.CENTER), mVertexBufferObjectManager);
        gameHUD.attachChild(movesText);

        mCamera.setHUD(gameHUD);
    }

    private void createBackground() {

        setBackground(new Background(new Color(66f / 256f, 183f / 256f, 190f / 256f)));
//        setBackground(new SpriteBackground(new Sprite(Constants.SCREEN_WIDTH / 2, Constants.SCREEN_HEIGHT/2, mResourcesManager.gameBackground.textureRegion, mVertexBufferObjectManager)));
    }

    private void loadLevel(int level) {

        String levelFile = "level/level" + level + ".txt";
        mGame = GameMap.createGame(levelFile, mActivity);

        if (mGame == null) {
            displayErrorLoadingLevel(levelFile);
            return;
        }

        final int blocSize = (int) mResourcesManager.gameEmptyTexture.textureRegion.getWidth();
        int worldWidth = blocSize * mGame.getSizeX();
        int worldHeight = blocSize * mGame.getSizeY();

        final int x0 = (Constants.SCREEN_WIDTH - worldWidth) / 2;
        final int y0 = (Constants.SCREEN_HEIGHT - worldHeight) / 2;

        mPlaces = new ArrayList<GameElement>();
        mPieces = new ArrayList<GameElement>();

        for (int y = 0; y < mGame.getSizeY(); y++) {
            for (int x = 0; x < mGame.getSizeX(); x++) {

                int posX = x0 + x * blocSize + blocSize / 2;
                int posY = y0 + y * blocSize + blocSize / 2;

                switch (mGame.getElement(new Point(x, y))) {

                    case GameMap.EMPTY:
                        GameElement place = new GameElement(posX, posY, mResourcesManager.gameEmptyTexture.textureRegion, mVertexBufferObjectManager, x, y);
                        mPlaces.add(place);
                        attachChild(place);
                        break;

                    case GameMap.PIECE:
                        GameElement placeFull = new GameElement(posX, posY, mResourcesManager.gameEmptyTexture.textureRegion, mVertexBufferObjectManager, x, y);
                        mPlaces.add(placeFull);
                        attachChild(placeFull);

                        GameElement piece = new GameElement(posX, posY, mResourcesManager.gamePieceTexture.textureRegion, mVertexBufferObjectManager, x, y) {
                            @Override
                            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {

                                this.setPosition(pSceneTouchEvent.getX(), pSceneTouchEvent.getY());

                                if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP) {
                                    checkForCollisionsWithPlaces(this);
                                }
                                return true;
                            }
                        };
                        mPieces.add(piece);
                        break;

                    default:
                        break;
                }
            }
        }

        for(GameElement piece: mPieces)
        {
            attachChild(piece);
            registerTouchArea(piece);
        }
        setTouchAreaBindingOnActionDownEnabled(true);
    }

    private void checkForCollisionsWithPlaces(GameElement piece)
    {
        for(GameElement place: mPlaces)
        {
            if (piece.collidesWith(place))
            {
                piece.setPosition(place.getX(), place.getY());
                mResourcesManager.menuItemClickedSound.play();
                break;
            }
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
