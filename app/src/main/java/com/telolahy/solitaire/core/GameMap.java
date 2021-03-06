package com.telolahy.solitaire.core;

import android.content.Context;
import android.graphics.Point;

import org.andengine.util.debug.Debug;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by stephanohuguestelolahy on 12/10/14.
 */
public class GameMap {

    // ===========================================================
    // Constants
    // ===========================================================

    public static final char NONE = ' ';
    public static final char EMPTY = 'o';
    public static final char PIECE = 'x';

    public static final Point UP = new Point(0, 1);
    public static final Point DOWN = new Point(0, -1);
    public static final Point LEFT = new Point(-1, 0);
    public static final Point RIGHT = new Point(1, 0);

    // ===========================================================
    // Fields
    // ===========================================================

    private final int mSizeX;
    private final int mSizeY;
    private final char mElement[][];

    // ===========================================================
    // Constructors
    // ===========================================================

    private GameMap(int sizeX, int sizeY) {
        mSizeX = sizeX;
        mSizeY = sizeY;
        mElement = new char[sizeX][sizeY];
    }

    // ===========================================================
    // Getter & Setter
    // ===========================================================

    public int getSizeX() {
        return mSizeX;
    }

    public int getSizeY() {
        return mSizeY;
    }

    public char getElement(Point position) {

        if (isValidCoordinate(position)) {
            return mElement[position.x][position.y];
        } else {
            return NONE;
        }
    }

    public void setElement(Point position, char element) {

        if (isValidCoordinate(position)) {
            mElement[position.x][position.y] = element;
        }
    }

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

    public static GameMap createGame(String file, Context context) {

        ArrayList<String> lines = new ArrayList<String>();
        InputStream inputStream = null;
        try {
            inputStream = context.getAssets().open(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            inputStream.close();

        } catch (IOException e) {
            Debug.e(e);
            return null;
        }

        final int maxY = lines.size();
        final int maxX = maxY;
        GameMap gameMap = new GameMap(maxX, maxY);
        for (int y = 0; y < maxY; y++) {
            String line = lines.get(maxY - 1 - y);
            int lineLength = line.length();
            for (int x = 0; x < maxX; x++) {
                if (x < lineLength) {
                    gameMap.mElement[x][y] = line.charAt(x);
                } else {
                    gameMap.mElement[x][y] = NONE;
                }
            }
        }
        return gameMap;
    }

    public boolean canMovePieceToDirection(Point source, Point direction) {

        Point intermediate = new Point(source.x + direction.x, source.y + direction.y);

        if (!isValidCoordinate(intermediate)) {
            return false;
        }

        if (getElement(intermediate) != PIECE) {
            return false;
        }

        Point destination = new Point(source.x + 2 * direction.x, source.y + 2 * direction.y);

        if (!isValidCoordinate(destination)) {
            return false;
        }

        if (getElement(destination) != EMPTY) {
            return false;
        }

        return true;
    }

    public boolean isLevelCompleted() {

        int nbPiece = 0;
        for (int y = 0; y < mSizeY; y++) {
            for (int x = 0; x < mSizeX; x++) {
                if (getElement(new Point(x, y)) == PIECE) {
                    nbPiece++;
                }
            }
        }
        return nbPiece == 1;
    }

    public boolean isGameOver() {

        for (int y = 0; y < mSizeY; y++) {
            for (int x = 0; x < mSizeX; x++) {
                if (canMovePiece(new Point(x, y))) {
                    return false;
                }
            }
        }
        return true;
    }

    // ===========================================================
    // Private Methods
    // ===========================================================

    private boolean isValidCoordinate(Point point) {

        if (point.x < 0 || point.x > mSizeX - 1 || point.y < 0 || point.y > mSizeY - 1) {
            return false;   // reached limit of he world
        }

        if (mElement[point.x][point.y] == NONE) {
            return false;   // not a place
        }

        return true;
    }

    private boolean canMovePiece(Point source) {

        if (getElement(source) != PIECE) {
            return false;
        }

        if (canMovePieceToDirection(source, UP)) {
            return true;
        }

        if (canMovePieceToDirection(source, DOWN)) {
            return true;
        }

        if (canMovePieceToDirection(source, LEFT)) {
            return true;
        }

        if (canMovePieceToDirection(source, RIGHT)) {
            return true;
        }

        return false;
    }

    // ===========================================================
    // Inner Classes/Interfaces
    // ===========================================================

}
