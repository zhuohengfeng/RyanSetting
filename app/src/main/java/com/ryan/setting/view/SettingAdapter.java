package com.ryan.setting.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ryan.setting.MainActivity;
import com.ryan.setting.R;
import com.ryan.setting.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class SettingAdapter extends BaseAdapter {

    private List<ListItem> mDatas = new ArrayList<ListItem>();
    private Context mContext;
    private int mSelectedPos = 0;

    public SettingAdapter(Context context) {
        mContext = context;
    }

    public SettingAdapter(Context context, List<ListItem> datas) {
        mContext = context;
        clear();
        if (datas != null) {
            mDatas.addAll(datas);
        }
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public ListItem getItem(int position) {
        int size = mDatas.size();
        if (position >= 0 && position < size) {
            return mDatas.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void clear() {
        mDatas.clear();
    }

    public void add(ListItem data) {
        mDatas.add(data);
    }

    public void addAll(List<ListItem> datas) {
        if (datas != null) {
            mDatas.addAll(datas);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = ((MainActivity)mContext).getLayoutInflater().inflate(R.layout.activity_main_row, null);
            AbsListView.LayoutParams param = new AbsListView.LayoutParams(Constants.ITEM_WIDTH, Constants.ITEM_HEIGHT);
            convertView.setLayoutParams(param);

            holder.mRelativeLayout = convertView.findViewById(R.id.ll_activity_main_row);
            holder.mImageViewLogo = convertView.findViewById(R.id.iv_activity_main_row_logo);
            holder.mTextViewTitle = convertView.findViewById(R.id.tv_activity_main_row_title);
            holder.mTextViewContent = convertView.findViewById(R.id.tv_activity_main_row_content);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ListItem item = mDatas.get(position);
        //

        holder.mTextViewTitle.setText(item.getTitle());
        holder.mTextViewContent.setText(item.getContent());

        int enableTextColor = mContext.getResources().getColor(R.color.enable_text, null);
        int disableTextColor = mContext.getResources().getColor(R.color.disable_text, null);
        if (mSelectedPos == position) {
            holder.mImageViewLogo.setImageResource(item.getIcon(true));
            holder.mRelativeLayout.setPadding(Constants.INT_ACTIVITY_MAIN_ROW_HIGHLIGHT, 0, 0, 0);
            holder.mTextViewTitle.setTextColor(enableTextColor);
            holder.mTextViewTitle.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            holder.mTextViewContent.setTextColor(enableTextColor);
            holder.mTextViewContent.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        }
        else {
            holder.mImageViewLogo.setImageResource(item.getIcon(false));
            holder.mRelativeLayout.setPadding(Constants.INT_ACTIVITY_MAIN_ROW_DE_HIGHLIGHT, 0, 0, 0);
            holder.mTextViewTitle.setTextColor(disableTextColor);
            holder.mTextViewTitle.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            holder.mTextViewContent.setTextColor(disableTextColor);
            holder.mTextViewContent.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        }

        return convertView;
    }

    public void setSelected(int position) {
        mSelectedPos = position - Constants.SHOW_HEADER_ITEMS;
    }

    public int getSelected() {
        return mSelectedPos;
    }

    static final class ViewHolder {
        ImageView mImageViewLogo;
        TextView mTextViewTitle;
        TextView mTextViewContent;
        RelativeLayout mRelativeLayout;
    }

}

