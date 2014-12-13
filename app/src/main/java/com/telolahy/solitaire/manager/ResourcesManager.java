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
import org.andengine.opengl.texture.region.ITiledTextureRegion;
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

    public static class TiledTextureDescription {

        protected final String textureFile;
        protected ITexture texture;
        public ITiledTextureRegion textureRegion;
        protected final int columns;
        protected final int rows;

        public TiledTextureDescription(String file, int col, int row) {
            textureFile = file;
            columns = col;
            rows = row;
        }

        public void load(TextureManager textureManager, Context context) {
            try {
                texture = new AssetBitmapTexture(textureManager, context.getAssets(), textureFile, TextureOptions.BILINEAR);
                textureRegion = TextureRegionFactory.extractTiledFromTexture(texture, columns, rows);
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


    // splash resources
    public final TextureDescription splashTexture = new TextureDescription("gfx/splash/logo.png");

    // menu resources
    public Font menuItemFont;
    public Font menuTitleFont;
    public Font menuLoadingFont;
    public Font menuCreditsWhiteFont;
    public Font menuCreditsGrayFont;

    public Sound menuItemClickedSound;

    // game resources
    public final TextureDescription gameEmptyTexture = new TextureDescription("gfx/game/empty.png");

    public static final org.andengine.util.adt.color.Color BACKGROUND_COLOR_OBJ = new org.andengine.util.adt.color.Color(231f / 256f, 231f / 256f, 231f / 256f);
    public static final int EMPTY_COLOR = Color.rgb(210, 204, 195);
    public static final int TILE_COLORS[] = {
            Color.rgb(234, 227, 217),
            Color.rgb(233, 223, 199),
            Color.rgb(225, 175, 123),
            Color.rgb(220, 148, 102),
            Color.rgb(214, 122, 96),
            Color.rgb(209, 92, 62),
            Color.rgb(228, 206, 119),
            Color.rgb(227, 203, 104),
            Color.rgb(226, 199, 90),
            Color.rgb(225, 196, 76),

            Color.rgb(225, 192, 65),
            Color.rgb(195, 168, 204),
            Color.rgb(178, 137, 190),
            Color.rgb(160, 106, 175),
            Color.rgb(129, 60, 143),
            Color.rgb(104, 48, 114),
            Color.rgb(136, 140, 216),
            Color.rgb(119, 123, 212),
            Color.rgb(102, 107, 208),
            Color.rgb(85, 91, 204),

            Color.rgb(68, 73, 199),
            Color.rgb(142, 142, 142),
            Color.rgb(108, 108, 108),
            Color.rgb(86, 86, 86),
            Color.rgb(75, 75, 75),
            Color.rgb(64, 64, 64),
            Color.rgb(52, 52, 52),
            Color.rgb(42, 42, 42),
            Color.rgb(32, 32, 32),
            Color.rgb(16, 16, 16)

//            Color.rgb(, , ),
//            Color.rgb(, , ),
//            Color.rgb(, , ),
//            Color.rgb(, , ),
//            Color.rgb(, , ),
//            Color.rgb(, , ),
    };


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

    private void loadGameFonts() {

        menuTitleFont = FontFactory.createStrokeFromAsset(activity.getFontManager(), new BitmapTextureAtlas(activity.getTextureManager(), 256, 512, TextureOptions.BILINEAR_PREMULTIPLYALPHA), activity.getAssets(), "font/font.ttf", 96, true, Color.TRANSPARENT, 4, Color.rgb(115, 109, 101));
        menuTitleFont.load();

        menuItemFont = FontFactory.createStrokeFromAsset(activity.getFontManager(), new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA), activity.getAssets(), "font/font.ttf", 24, true, Color.rgb(230, 183, 121), 0, Color.TRANSPARENT);
        menuItemFont.load();

        menuLoadingFont = FontFactory.createStrokeFromAsset(activity.getFontManager(), new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA), activity.getAssets(), "font/font.ttf", 48, true, Color.rgb(115, 109, 101), 0, Color.TRANSPARENT);
        menuLoadingFont.load();

        menuCreditsWhiteFont = FontFactory.createStrokeFromAsset(activity.getFontManager(), new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA), activity.getAssets(), "font/font.ttf", 24, true, Color.WHITE, 0, Color.TRANSPARENT);
        menuCreditsWhiteFont.load();

        menuCreditsGrayFont = FontFactory.createStrokeFromAsset(activity.getFontManager(), new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA), activity.getAssets(), "font/font.ttf", 24, true, Color.rgb(182, 172, 169), 0, Color.TRANSPARENT);
        menuCreditsGrayFont.load();
    }

    private void loadGameMusics() {

        try {
            menuItemClickedSound = SoundFactory.createSoundFromAsset(engine.getSoundManager(), activity, "mfx/item_click.ogg");
        } catch (final IOException e) {
            Debug.e(e);
        }
    }


    public void loadGameResources() {

        loadGameTextures();
        loadGameFonts();
        loadGameMusics();
    }

    private void loadGameTextures() {

        gameEmptyTexture.load(engine.getTextureManager(), activity);
    }

    public void unloadGameTextures() {

        gameEmptyTexture.unload();
    }
}
