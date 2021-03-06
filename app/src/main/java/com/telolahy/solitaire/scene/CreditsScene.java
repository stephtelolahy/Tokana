package com.telolahy.solitaire.scene;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.telolahy.solitaire.R;
import com.telolahy.solitaire.application.Constants;
import com.telolahy.solitaire.manager.SceneManager;

import org.andengine.entity.scene.background.Background;
import org.andengine.entity.text.Text;
import org.andengine.util.adt.color.Color;

import java.util.Calendar;

/**
 * Created by stephanohuguestelolahy on 12/10/14.
 */
public class CreditsScene extends BaseScene {

    // ===========================================================
    // Constants
    // ===========================================================

    // ===========================================================
    // Fields
    // ===========================================================

    // ===========================================================
    // Constructors
    // ===========================================================

    // ===========================================================
    // Getter & Setter
    // ===========================================================

    // ===========================================================
    // Methods from SuperClass
    // ===========================================================

    @Override
    protected void onCreateScene(int... params) {

        setBackground(new Background(new Color(231f / 256f, 231f / 256f, 231f / 256f)));

        attachChild(new Text(Constants.SCREEN_WIDTH / 2, 200, mResourcesManager.menuItemGrayFont.font, mActivity.getResources().getString(R.string.game_developer), mVertexBufferObjectManager));
        attachChild(new Text(Constants.SCREEN_WIDTH / 2, 160, mResourcesManager.menuItemFont.font, mActivity.getResources().getString(R.string.game_developer_value), mVertexBufferObjectManager));

        try {
            PackageInfo pInfo = mActivity.getPackageManager().getPackageInfo(mActivity.getPackageName(), 0);
            String versionName = pInfo.versionName;
            String versionDescription = mActivity.getResources().getString(R.string.app_name) + " version " + versionName;
            attachChild(new Text(Constants.SCREEN_WIDTH / 2, 100, mResourcesManager.menuItemGrayFont.font, versionDescription, mVertexBufferObjectManager));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        String gameStudioDescription = mActivity.getResources().getString(R.string.game_studio) + year;
        attachChild(new Text(Constants.SCREEN_WIDTH / 2, 60, mResourcesManager.menuItemGrayFont.font, gameStudioDescription, mVertexBufferObjectManager));

    }

    @Override
    protected void onDisposeScene() {

    }

    @Override
    public void onBackKeyPressed() {

        SceneManager.getInstance().loadGameScene();
    }

    // ===========================================================
    // Methods for Interfaces
    // ===========================================================

    // ===========================================================
    // Methods from Interfaces
    // ===========================================================

    // ===========================================================
    // Public Methods
    // ===========================================================

    // ===========================================================
    // Private Methods
    // ===========================================================

    // ===========================================================
    // Inner Classes/Interfaces
    // ===========================================================

}
