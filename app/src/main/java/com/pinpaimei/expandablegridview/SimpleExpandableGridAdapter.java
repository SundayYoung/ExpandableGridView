package com.pinpaimei.expandablegridview;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pinpaimei.expandgridview.ExpandableGridAdapter;

import java.util.List;

/**
 * Created by lhy
 */
public class SimpleExpandableGridAdapter extends ExpandableGridAdapter {

    protected Context mContext;
    private List<GroupRequest> mGroupDataList;

    public SimpleExpandableGridAdapter(Context context) {
        if (context == null) {
            return;
        }
        mContext = context;
    }

    public SimpleExpandableGridAdapter(Context context, List<GroupRequest> datas) {
        this(context);
        this.mGroupDataList = datas;
    }

    public void setDatas(List<GroupRequest> list) {
        this.mGroupDataList = list;
    }

    @Override
    public int getGridGroupCount() {
        return mGroupDataList == null ? 0 : mGroupDataList.size();
    }

    @Override
    public int getGridChildCount(int gridGroupPosition) {
        List<ChildItem> children = mGroupDataList.get(gridGroupPosition).childList;
        return children == null ? 0 : children.size();
    }

    @Override
    public View getGridGroupView(int gridGroupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder groupViewHolder;
        if (convertView == null) {
            groupViewHolder = new GroupViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_item, parent, false);
            groupViewHolder.mTitleTv = (TextView) convertView.findViewById(R.id.text);
            convertView.setTag(groupViewHolder);
        } else {
            groupViewHolder = (GroupViewHolder) convertView.getTag();
        }
        GroupRequest groupData = getGridGroupData(gridGroupPosition);
        if (groupData == null) {
            return convertView;
        }

        groupViewHolder.mTitleTv.setText(groupData.groupName);

        return convertView;
    }

    @Override
    public View getGridChildView(int gridGroupPosition, int gridChildPosition, View convertView, ViewGroup parent) {
        ChildViewHolder childViewHolder;
        if (convertView == null) {
            childViewHolder = new ChildViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_item, parent, false);
            childViewHolder.mTitleTv = (TextView) convertView.findViewById(R.id.text);
            convertView.setTag(childViewHolder);
        } else {
            childViewHolder = (ChildViewHolder) convertView.getTag();
        }
        ChildItem childItem = getGridChildData(gridGroupPosition, gridChildPosition);
        if (childItem == null) {
            return convertView;
        }

        childViewHolder.mTitleTv.setText(childItem.childName);

        return convertView;
    }

    @Override
    public GroupRequest getGridGroupData(int gridGroupPosition) {
        return mGroupDataList == null || gridGroupPosition < 0 || gridGroupPosition >= mGroupDataList.size() ? null : mGroupDataList.get(gridGroupPosition);
    }

    @Override
    public ChildItem getGridChildData(int gridGroupPosition, int gridChildPosition) {
        GroupRequest request = getGridGroupData(gridGroupPosition);
        return request == null || request.childList == null ? null : request.childList.get(gridChildPosition);
    }

    private static class GroupViewHolder {
        TextView mTitleTv;
    }

    private static class ChildViewHolder {
        TextView mTitleTv;
    }
}
