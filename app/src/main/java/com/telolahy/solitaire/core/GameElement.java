package com.telolahy.solitaire.core;

import com.telolahy.solitaire.manager.ResourcesManager;

import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

/**
 * Created by stephanohuguestelolahy on 12/13/14.
 */
public class GameElement extends Sprite {

    private float mLastX;
    private float mLastY;
    private int mWeight;
    private Text mText;

    public GameElement(final float pX, final float pY, final ITextureRegion pTextureRegion, final VertexBufferObjectManager pVertexBufferObjectManager) {
        super(pX, pY, pTextureRegion, pVertexBufferObjectManager);
        mLastX = pX;
        mLastY = pY;

        mText = new Text(24, 24, ResourcesManager.getInstance().menuItemWhiteFont, "0123", ResourcesManager.getInstance().vertexBufferObjectManager);
        attachChild(mText);

        setWeight(1);
    }

    public float getLastX() {
        return mLastX;
    }

    public float getLastY() {
        return mLastY;
    }

    public int getWeight() {
        return mWeight;
    }

    public void setWeight(int value) {
        mWeight = value;
        mText.setText("" + value);
        setColor(ResourcesManager.TILE_COLORS[value % ResourcesManager.TILE_COLORS.length]);
    }

    public void setLastPosition(float lastX, float lastY) {
        mLastX = lastX;
        mLastY = lastY;
    }
}
