package com.ryan.setting.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.ryan.setting.utils.Constants;

public class CustomListView extends ListView {
    private static final String TAG = "zhf123";

    private Context mContext;
    private CustomAdapter mAdapter;

    public CustomListView(Context context) {
        super(context);
        initDate(context);
        initView();
    }

    public CustomListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initDate(context);
        initView();
    }

    public CustomListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initDate(context);
        initView();
    }

    private void initDate(Context context) {
        mContext = context;
    }

    private void initView() {
        for (int i = 0; i < Constants.SETTING_HEAD_COUNT; i++) {
            View headerView = new View(mContext);
            LayoutParams headerLp = new LayoutParams(Constants.SETTING_ITEM_WIDTH, Constants.SETTING_ITEM_HEIGHT);
            headerView.setLayoutParams(headerLp);
            addHeaderView(headerView, null, false);
        }

        for (int i = 0; i < Constants.SETTING_ITEM_COUNT - Constants.SETTING_HEAD_COUNT - 1; i++) {
            View footerView = new View(mContext);
            LayoutParams footerLp = new LayoutParams(Constants.SETTING_ITEM_WIDTH, Constants.SETTING_ITEM_HEIGHT);
            footerView.setLayoutParams(footerLp);
            addFooterView(footerView, null, false);
        }
        setPadding(0, 0, 0, 0);
    }


    @Override
    public void setAdapter(ListAdapter adapter) {
        super.setAdapter(adapter);
        mAdapter = (CustomAdapter)adapter;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        int action = event.getAction();
        int keyCode = event.getKeyCode();
        View view = this.getSelectedView();
        int pos = mAdapter.getSelected();
        int count = mAdapter.getCount();

        if (/*action == KeyEvent.ACTION_DOWN && */view != null) {
            Log.d(TAG, "zhfzhf123 dispatchKeyEvent(): , pos="+pos+", count="+count+", keyCode="+keyCode);
            if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN || keyCode == KeyEvent.KEYCODE_DPAD_UP) {
                int top = view.getTop();
                //Log.d(TAG, "zhfzhf123 dispatchKeyEvent(): top="+top+", pos="+pos+", count="+count);
                if ((Constants.SETTING_HEAD_COUNT * Constants.SETTING_ITEM_HEIGHT) != top) {
                    return false;
                }
                if (pos <= 0
                        && keyCode == KeyEvent.KEYCODE_DPAD_UP) {
                    return false;
                }
                if (pos >= count - 1
                        && keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
                    return false;
                }
            }
        }
        return super.dispatchKeyEvent(event);
    }

}
