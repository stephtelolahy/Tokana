package com.telolahy.solitaire.manager;

import android.content.Context;
import android.graphics.Color;

import com.telolahy.solitaire.application.MainActivity;

import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.Engine;
import org.andengine.engine.camera.ZoomCamera;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.bitmap.AssetBitmapTexture;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.debug.Debug;

import java.io.IOException;

/**
 * Created by stephanohuguestelolahy on 11/15/14.
 */
public class ResourcesManager {

    public static class TextureDescription {

        protected final String textureFile;
        protected ITexture texture;
        public ITextureRegion textureRegion;

        public TextureDescription(String file) {
            textureFile = file;
        }

        public void load(TextureManager textureManager, Context context) {
            try {
                texture = new AssetBitmapTexture(textureManager, context.getAssets(), textureFile, TextureOptions.BILINEAR);
                textureRegion = TextureRegionFactory.extractFromTexture(texture);
                texture.load();
            } catch (IOException e) {
                texture = null;
                textureRegion = null;
                Debug.e(e);
            }
        }

        public void unload() {
            if (texture != null) {
                texture.unload();
            }
            textureRegion = null;
        }
    }

    private static final ResourcesManager INSTANCE = new ResourcesManager();

    public Engine engine;
    public MainActivity activity;
    public ZoomCamera camera;
    public VertexBufferObjectManager vertexBufferObjectManager;

    public Font menuItemFont;
    public Font menuTitleFont;

    public Sound menuItemClickedSound;

    // splash resources
    public final TextureDescription splashTexture = new TextureDescription("gfx/splash/creative_games_logo.png");


    //---------------------------------------------
    // GETTERS AND SETTERS
    //---------------------------------------------

    public static ResourcesManager getInstance() {
        return INSTANCE;
    }

    public static void prepareManager(Engine engine, MainActivity activity, ZoomCamera camera, VertexBufferObjectManager vertexBufferObjectManager) {

        getInstance().engine = engine;
        getInstance().activity = activity;
        getInstance().camera = camera;
        getInstance().vertexBufferObjectManager = vertexBufferObjectManager;
    }

    public void loadSplashResources() {

        splashTexture.load(engine.getTextureManager(), activity);
    }

    public void unloadSplashResources() {

        splashTexture.unload();
    }

    public void loadMenuResources() {

        loadMenuGraphics();
        loadMenuFonts();
        loadMenuMusics();
    }

    private void loadMenuGraphics() {

    }

    private void loadMenuFonts() {

        final ITexture menuTitleFontTexture = new BitmapTextureAtlas(activity.getTextureManager(), 256, 512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        menuTitleFont = FontFactory.createStrokeFromAsset(activity.getFontManager(), menuTitleFontTexture, activity.getAssets(), "font/font.ttf", 96, true, Color.WHITE, 2, Color.BLACK);
        menuTitleFont.load();

        final ITexture menuItemFontTexture = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        menuItemFont = FontFactory.createStrokeFromAsset(activity.getFontManager(), menuItemFontTexture, activity.getAssets(), "font/font.ttf", 48, true, Color.BLACK, 0, Color.BLACK);
        menuItemFont.load();
    }

    private void loadMenuMusics() {

        try {
            menuItemClickedSound = SoundFactory.createSoundFromAsset(engine.getSoundManager(), activity, "mfx/item_click.ogg");
        } catch (final IOException e) {
            Debug.e(e);
        }
    }

    public void unloadMenuTextures() {

    }

    public void loadMenuTextures() {

        loadMenuGraphics();
    }


    public void loadGameResources() {

        loadGameGraphics();
    }

    private void loadGameGraphics() {

    }

    public void unloadGameTextures() {

    }
}
