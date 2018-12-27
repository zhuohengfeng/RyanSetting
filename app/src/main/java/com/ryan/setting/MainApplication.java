package com.ryan.setting;

import android.app.Application;

import com.ryan.setting.utils.Constants;

public class MainApplication extends Application {

    private static MainApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        initApp();
    }

    private void initApp() {
        mInstance = this;

        Constants.ITEM_WIDTH = getResources()
                .getDimensionPixelSize(R.dimen.setting_item_width);
        Constants.ITEM_HEIGHT = getResources()
                .getDimensionPixelSize(R.dimen.setting_item_height);
    }

    public static MainApplication getInstance() {
        return mInstance;
    }
}
