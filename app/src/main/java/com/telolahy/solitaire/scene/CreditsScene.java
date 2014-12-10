package com.telolahy.solitaire.scene;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.telolahy.solitaire.R;
import com.telolahy.solitaire.application.Constants;
import com.telolahy.solitaire.manager.SceneManager;

import org.andengine.entity.text.Text;

import java.util.Calendar;

/**
 * Created by stephanohuguestelolahy on 12/10/14.
 */
public class CreditsScene extends BaseScene {

    @Override
    protected void onCreateScene(int... params) {

        attachChild(new Text(Constants.SCREEN_WIDTH / 2, 300, mResourcesManager.menuCreditsFontGray, mActivity.getResources().getString(R.string.game_developer), mVertexBufferObjectManager));
        attachChild(new Text(Constants.SCREEN_WIDTH / 2, 260, mResourcesManager.menuCreditsFontWhite, mActivity.getResources().getString(R.string.game_developer_value), mVertexBufferObjectManager));

        attachChild(new Text(Constants.SCREEN_WIDTH / 2, 200, mResourcesManager.menuCreditsFontGray, mActivity.getResources().getString(R.string.game_engine), mVertexBufferObjectManager));
        attachChild(new Text(Constants.SCREEN_WIDTH / 2, 160, mResourcesManager.menuCreditsFontWhite, mActivity.getResources().getString(R.string.game_engine_value), mVertexBufferObjectManager));

        try {
            PackageInfo pInfo = mActivity.getPackageManager().getPackageInfo(mActivity.getPackageName(), 0);
            String versionName = pInfo.versionName;
            String versionDescription = mActivity.getResources().getString(R.string.app_name) + " version " + versionName;
            attachChild(new Text(Constants.SCREEN_WIDTH / 2, 100, mResourcesManager.menuCreditsFontGray, versionDescription, mVertexBufferObjectManager));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        String gameStudioDescription = mActivity.getResources().getString(R.string.game_studio) + year;
        attachChild(new Text(Constants.SCREEN_WIDTH / 2, 60, mResourcesManager.menuCreditsFontGray, gameStudioDescription, mVertexBufferObjectManager));

    }

    @Override
    protected void onDisposeScene() {

    }

    @Override
    public void onBackKeyPressed() {

        SceneManager.getInstance().loadMenuScene();
    }
}
