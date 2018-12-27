package com.ryan.setting;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.ryan.setting.utils.Constants;
import com.ryan.setting.ui.ItemData;
import com.ryan.setting.ui.CustomAdapter;
import com.ryan.setting.ui.CustomListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "zhf123";

    private ArrayList<ItemData> mItemDataList;

    private CustomAdapter mCustomAdapter;
    private CustomListView mCustomListView;

    private View mItemBorder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        initData();
        initView();
    }

    /**
     * 定义设置的入口
     */
    private void initData() {
        mItemDataList = new ArrayList<>();
        mItemDataList.add(new ItemData(R.drawable.icon_wifi_active, R.drawable.icon_wifi_inactive, getString(R.string.activity_main_title_wifi), "No Wi-Fi Connected"));
        mItemDataList.add(new ItemData(R.drawable.icon_bluetooth_active, R.drawable.icon_bluetooth_inactive, getString(R.string.activity_main_title_bluetooth), ""));
        mItemDataList.add(new ItemData(R.drawable.icon_brightness_active, R.drawable.icon_brightness_inactive, getString(R.string.activity_main_title_brightness), ""));
        mItemDataList.add(new ItemData(R.drawable.icon_wakeup_active, R.drawable.icon_wakeup_inactive, getString(R.string.activity_main_title_wakeup), ""));
        mItemDataList.add(new ItemData(R.drawable.icon_language_active, R.drawable.icon_language_inactive, getString(R.string.activity_main_title_language), ""));
        mItemDataList.add(new ItemData(R.drawable.icon_ota_active, R.drawable.icon_ota_inactive, getString(R.string.activity_main_title_ota), ""));
        mItemDataList.add(new ItemData(R.drawable.icon_about_active, R.drawable.icon_about_inactive, getString(R.string.activity_main_title_info), ""));

        Constants.SETTING_HEAD_COUNT = 2;
        Constants.SETTING_ITEM_COUNT = mItemDataList.size();
    }

    /**
     * 初始化View
     */
    private void initView() {
        // 初始化ListView
        mCustomListView = findViewById(R.id.list_activity_main);
        mCustomListView.setDivider(null);
        mCustomListView.setCacheColorHint(Color.TRANSPARENT);
        mCustomListView.setOnItemClickListener(mItemClickListener);
        mCustomListView.setOnItemSelectedListener(mItemSelectedListener);
        mCustomListView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                onLayoutReady();
                mCustomListView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });

        mCustomAdapter = new CustomAdapter(this, mItemDataList);
        mCustomListView.setAdapter(mCustomAdapter);

        mItemBorder = findViewById(R.id.img_item_box);
    }

    /**
     * 设置选择框的位置
     */
    public void onLayoutReady() {
        ViewGroup.MarginLayoutParams margin =
                new ViewGroup.MarginLayoutParams(mItemBorder.getLayoutParams());
        margin.setMargins(mItemBorder.getLeft(),
                mItemBorder.getTop()+Constants.SETTING_HEAD_COUNT * Constants.SETTING_ITEM_HEIGHT,
                mItemBorder.getRight(),
                mItemBorder.getBottom());
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(margin);
        mItemBorder.setLayoutParams(layoutParams);
    }


    private AdapterView.OnItemClickListener mItemClickListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Toast.makeText(MainActivity.this, "selece pos="+position, Toast.LENGTH_SHORT).show();

        }
    };

    private AdapterView.OnItemSelectedListener mItemSelectedListener = new AdapterView.OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            Log.d(TAG, "zhfzhf123  onItemSelected=position="+position+", ui.getTop()="+view.getTop());

            synchronized(this){
                mCustomListView.scrollTo(mCustomListView.getScrollX(), 0);
                if (view != null) {
                    //此处选中时调用ListView的移动动画，计算偏移值：通过选中View的top和第三项的高度来计算
                    Log.d(TAG, "position="+position+", ui.getTop()="+view.getTop()+", getScrollY"+mCustomListView.getScrollY());
                    mCustomListView.smoothScrollBy(view.getTop() - Constants.SETTING_HEAD_COUNT * Constants.SETTING_ITEM_HEIGHT - mCustomListView.getScrollY(), 250);
                }
                mCustomAdapter.setSelected(position);
                mCustomAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {}
    };



}
