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

    public static final char NONE = ' ';
    public static final char EMPTY = 'o';
    public static final char PIECE = 'x';

    private static final int MAP_SIZE = 7;

    private int mSizeX;
    private int mSizeY;
    private char mElement[][];

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

    public boolean isValidCoordinate(Point point) {

        if (point.x < 0 || point.x > mSizeX - 1 || point.y < 0 || point.y > mSizeY - 1) {
            return false; // reached limit of he world
        } else {
            return true;
        }
    }

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

        if (lines.size() != MAP_SIZE) {
            return null;
        }

        GameMap gameMap = new GameMap(MAP_SIZE, MAP_SIZE);
        for (int y = 0; y < gameMap.getSizeY(); y++) {

            String line = lines.get(gameMap.getSizeY() - 1 - y);
            int lineLength = line.length();
            for (int x = 0; x < gameMap.getSizeX(); x++) {

                if (x < lineLength) {
                    gameMap.mElement[x][y] = line.charAt(x);
                } else {
                    gameMap.mElement[x][y] = NONE;
                }
            }
        }
        return gameMap;

    }

    private GameMap(int sizeX, int sizeY) {
        mSizeX = sizeX;
        mSizeY = sizeY;
        mElement = new char[sizeX][sizeY];
    }

    public Point computeMovement(Point source, Point destination) {

        if (getElement(destination) != EMPTY) {
            return null;
        }

        int dx = destination.x - source.x;
        int dy = destination.y - source.y;

        if (!(dx == 0 && Math.abs(dy) == 2) && !(dy == 0 && Math.abs(dx) == 2)) {
            return null;
        }

        Point inter = new Point(source.x + dx / 2, source.y + dy / 2);

        if (getElement(inter) != PIECE) {
            return null;
        }

        return inter;
    }

    public boolean isLevelCompleted() {
        return false;
    }

}
