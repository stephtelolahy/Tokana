package com.telolahy.solitaire.scene;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Point;

import com.telolahy.solitaire.R;
import com.telolahy.solitaire.application.Constants;
import com.telolahy.solitaire.core.GameMap;
import com.telolahy.solitaire.manager.SceneManager;

import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.util.adt.color.Color;

import java.util.ArrayList;

/**
 * Created by stephanohuguestelolahy on 11/16/14.
 */
public class GameScene extends BaseScene {

    GameMap mGame;
    ArrayList<Sprite> mPieces;

    public GameScene(int... params) {
        super(params);
    }

    @Override
    protected void onCreateScene(int... params) {

        createBackground();
        loadLevel(1);

    }

    @Override
    protected void onDisposeScene() {

    }

    @Override
    public void onBackKeyPressed() {

        exitGame(false);
    }

    private void createBackground() {

        setBackground(new Background(new Color(66f / 256f, 183f / 256f, 190f / 256f)));
    }

    private void loadLevel(int level) {

        String levelFile = "level/level" + level + ".txt";
        mGame = GameMap.createGame(levelFile, mActivity);

        if (mGame == null) {
            displayErrorLoadingLevel(levelFile);
            return;
        }

        final int blocSize = (int) mResourcesManager.gameEmptyTexture.textureRegion.getWidth() + 4;
        int worldWidth = blocSize * mGame.getSizeX();
        int worldHeight = blocSize * mGame.getSizeY();

        final int x0 = (Constants.SCREEN_WIDTH - worldWidth) / 2;
        final int y0 = (Constants.SCREEN_HEIGHT - worldHeight) / 2;

        mPieces = new ArrayList<Sprite>();

        for (int y = 0; y < mGame.getSizeY(); y++) {
            for (int x = 0; x < mGame.getSizeX(); x++) {

                int posX = x0 + x * blocSize + blocSize / 2;
                int posY = y0 + y * blocSize + blocSize / 2;

                switch (mGame.getElement(new Point(x, y))) {

                    case GameMap.EMPTY:
                        attachChild(new Sprite(posX, posY, mResourcesManager.gameEmptyTexture.textureRegion, mVertexBufferObjectManager));
                        break;

                    case GameMap.PIECE:
                        attachChild(new Sprite(posX, posY, mResourcesManager.gamePieceTexture.textureRegion, mVertexBufferObjectManager));
                        break;

                    default:
                        break;
                }
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
