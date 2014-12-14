package com.telolahy.solitaire.manager;

import android.graphics.Color;

import com.telolahy.solitaire.application.MainActivity;
import com.telolahy.solitaire.core.SoundDescription;
import com.telolahy.solitaire.core.TextureDescription;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.ZoomCamera;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

/**
 * Created by stephanohuguestelolahy on 11/15/14.
 */
public class ResourcesManager {

    // ===========================================================
    // Constants
    // ===========================================================

    private static final ResourcesManager INSTANCE = new ResourcesManager();

    // ===========================================================
    // Fields
    // ===========================================================

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
    public Font menuItemWhiteFont;
    public Font menuItemGrayFont;
    public Font menuReplayFont;
    public Font menuHelpFont;

    public final SoundDescription menuItemClickedSound = new SoundDescription("mfx/item_click.ogg");
    public final SoundDescription gameElementMovedSound = new SoundDescription("mfx/piece_move.mp3");

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


    // ===========================================================
    // Constructors
    // ===========================================================

    // ===========================================================
    // Getter & Setter
    // ===========================================================

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

    public void loadGameResources() {

        loadGameTextures();
        loadGameFonts();
        loadGameMusics();
    }

    // ===========================================================
    // Private Methods
    // ===========================================================

    private void loadGameFonts() {

        menuTitleFont = FontFactory.createStrokeFromAsset(activity.getFontManager(), new BitmapTextureAtlas(activity.getTextureManager(), 256, 512, TextureOptions.BILINEAR_PREMULTIPLYALPHA), activity.getAssets(), "font/font.ttf", 96, true, Color.TRANSPARENT, 4, Color.rgb(115, 109, 101));
        menuTitleFont.load();

        menuItemFont = FontFactory.createStrokeFromAsset(activity.getFontManager(), new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA), activity.getAssets(), "font/font.ttf", 24, true, Color.rgb(230, 183, 121), 0, Color.TRANSPARENT);
        menuItemFont.load();

        menuReplayFont = FontFactory.createStrokeFromAsset(activity.getFontManager(), new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA), activity.getAssets(), "font/font.ttf", 24, true, Color.rgb(232, 185, 36), 0, Color.TRANSPARENT);
        menuReplayFont.load();

        menuLoadingFont = FontFactory.createStrokeFromAsset(activity.getFontManager(), new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA), activity.getAssets(), "font/font.ttf", 48, true, Color.rgb(115, 109, 101), 0, Color.TRANSPARENT);
        menuLoadingFont.load();

        menuItemWhiteFont = FontFactory.createStrokeFromAsset(activity.getFontManager(), new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA), activity.getAssets(), "font/font.ttf", 24, true, Color.WHITE, 0, Color.TRANSPARENT);
        menuItemWhiteFont.load();

        menuItemGrayFont = FontFactory.createStrokeFromAsset(activity.getFontManager(), new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA), activity.getAssets(), "font/font.ttf", 24, true, Color.rgb(182, 172, 169), 0, Color.TRANSPARENT);
        menuItemGrayFont.load();

        menuHelpFont = FontFactory.createStrokeFromAsset(activity.getFontManager(), new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA), activity.getAssets(), "font/font.ttf", 18, true, Color.rgb(182, 172, 169), 0, Color.TRANSPARENT);
        menuHelpFont.load();
    }

    private void loadGameMusics() {

        menuItemClickedSound.load(engine.getSoundManager(), activity);
        gameElementMovedSound.load(engine.getSoundManager(), activity);
    }

    private void loadGameTextures() {

        gameEmptyTexture.load(engine.getTextureManager(), activity);
    }

    // ===========================================================
    // Inner Classes/Interfaces
    // ===========================================================

}
