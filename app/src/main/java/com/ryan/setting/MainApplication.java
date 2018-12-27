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

        Constants.ITEM_ENABLE_PADDING_LEFT = getResources()
                .getDimensionPixelSize(R.dimen.setting_item_enable_padding);
        Constants.ITEM_DISABLE_PADDING_LEFT = getResources()
                .getDimensionPixelSize(R.dimen.setting_item_disable_padding);

        Constants.SETTING_ITEM_WIDTH = getResources()
                .getDimensionPixelSize(R.dimen.setting_item_width);
        Constants.SETTING_ITEM_HEIGHT = getResources()
                .getDimensionPixelSize(R.dimen.setting_item_height);
    }

    public static MainApplication getInstance() {
        return mInstance;
    }
}
