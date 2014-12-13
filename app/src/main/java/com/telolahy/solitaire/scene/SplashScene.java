package com.telolahy.solitaire.scene;

import com.telolahy.solitaire.application.Constants;
import com.telolahy.solitaire.manager.ResourcesManager;
import com.telolahy.solitaire.manager.SceneManager;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.IEntityModifier;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.util.modifier.IModifier;

/**
 * Created by stephanohuguestelolahy on 11/15/14.
 */
public class SplashScene extends BaseScene {

    // ===========================================================
    // Constants
    // ===========================================================

    // ===========================================================
    // Fields
    // ===========================================================

    private Sprite mBackground;

    // ===========================================================
    // Constructors
    // ===========================================================

    // ===========================================================
    // Getter & Setter
    // ===========================================================

    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================

    @Override
    protected void onCreateScene(int... params) {

        setBackground(new Background(ResourcesManager.BACKGROUND_COLOR_OBJ));

        mBackground = new Sprite(Constants.SCREEN_WIDTH / 2, Constants.SCREEN_HEIGHT / 2, mResourcesManager.splashTexture.textureRegion, mVertexBufferObjectManager);
        attachChild(mBackground);

        IEntityModifier sequenceModifier = new AlphaModifier(2.f, 1.f, 1.f);
        sequenceModifier.addModifierListener(new IEntityModifier.IEntityModifierListener() {
            @Override
            public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {

            }

            @Override
            public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
                SceneManager.getInstance().createGameScene(1);
            }
        });
        mBackground.registerEntityModifier(sequenceModifier);
    }

    @Override
    protected void onDisposeScene() {

        mBackground.detachSelf();
    }

    @Override
    public void onBackKeyPressed() {

    }
}
