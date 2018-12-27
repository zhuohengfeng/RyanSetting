package com.ryan.setting;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.ryan.setting.utils.Constants;
import com.ryan.setting.view.ListItem;
import com.ryan.setting.view.SettingAdapter;
import com.ryan.setting.view.SettingListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "zhf123";
    

    private ArrayList<ListItem> mDataModelArrayList;
    //private int mCurrentIndex;

    private SettingAdapter mCustomAdapter;
    private SettingListView mCustomListView;

//    private IListViewReadyCallback mCustomListViewReadyCallback;
    private boolean mHasRecyclerViewBeenEstablished;

    private View mItemFrame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        initMainActivityListView();
    }



    private void initMainActivityListView() {
        mCustomListView = findViewById(R.id.list_activity_main);

        mDataModelArrayList = new ArrayList<>();
        mDataModelArrayList.add(new ListItem(R.drawable.icon_wifi_active, R.drawable.icon_wifi_inactive, getString(R.string.activity_main_title_wifi), "No Wi-Fi Connected"));
        mDataModelArrayList.add(new ListItem(R.drawable.icon_bluetooth_active, R.drawable.icon_bluetooth_inactive, getString(R.string.activity_main_title_bluetooth), ""));
        mDataModelArrayList.add(new ListItem(R.drawable.icon_brightness_active, R.drawable.icon_brightness_inactive, getString(R.string.activity_main_title_brightness), ""));
        mDataModelArrayList.add(new ListItem(R.drawable.icon_wakeup_active, R.drawable.icon_wakeup_inactive, getString(R.string.activity_main_title_wakeup), ""));
        mDataModelArrayList.add(new ListItem(R.drawable.icon_language_active, R.drawable.icon_language_inactive, getString(R.string.activity_main_title_language), ""));
        mDataModelArrayList.add(new ListItem(R.drawable.icon_ota_active, R.drawable.icon_ota_inactive, getString(R.string.activity_main_title_ota), ""));
        mDataModelArrayList.add(new ListItem(R.drawable.icon_about_active, R.drawable.icon_about_inactive, getString(R.string.activity_main_title_info), ""));

        mCustomListView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                onLayoutReady();
                mHasRecyclerViewBeenEstablished = true;
                mCustomListView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });

        mCustomAdapter = new SettingAdapter(this, mDataModelArrayList);
        mCustomListView.setAdapter(mCustomAdapter);


        mCustomListView.setDivider(null);
        mCustomListView.setCacheColorHint(Color.TRANSPARENT);
        mCustomListView.setOnItemClickListener(mBaseItemClickListener);
        mCustomListView.setOnItemSelectedListener(mBaseItemSelectedListener);

        mItemFrame = findViewById(R.id.img_item_frame);

    }

    /**
     * 设置选择框的位置
     */
    public void onLayoutReady() {

        ViewGroup.MarginLayoutParams margin = new ViewGroup.MarginLayoutParams(mItemFrame.getLayoutParams());
        margin.setMargins(mItemFrame.getLeft(), mItemFrame.getTop()+Constants.SHOW_HEADER_ITEMS * Constants.ITEM_HEIGHT, mItemFrame.getRight(), mItemFrame.getBottom());

        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(margin);
        mItemFrame.setLayoutParams(layoutParams);

    }


    private AdapterView.OnItemClickListener mBaseItemClickListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Toast.makeText(MainActivity.this, "selece pos="+position, Toast.LENGTH_SHORT).show();
//            if (mItemClickListener != null) {
//                mItemClickListener.onItemClick(parent, view, position - mCustomListView.getHeaderViewsCount(), id);
//            }
        }
    };

    private AdapterView.OnItemSelectedListener mBaseItemSelectedListener = new AdapterView.OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            Log.d(TAG, "zhfzhf123  onItemSelected=position="+position+", view.getTop()="+view.getTop());

            synchronized(this){
                mCustomListView.scrollTo(mCustomListView.getScrollX(), 0);
                if (view != null) {
                    //此处选中时调用ListView的移动动画，计算偏移值：通过选中View的top和第三项的高度来计算
                    Log.d(TAG, "position="+position+", view.getTop()="+view.getTop()+", getScrollY"+mCustomListView.getScrollY());
                    mCustomListView.smoothScrollBy(view.getTop() - Constants.SHOW_HEADER_ITEMS * Constants.ITEM_HEIGHT - mCustomListView.getScrollY(), 250);
                }
                mCustomAdapter.setSelected(position);
                mCustomAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };



}
