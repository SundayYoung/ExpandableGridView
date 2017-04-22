package com.pinpaimei.expandgridview;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;

/**
 * Created by lhy.
 */
public abstract class ExpandableGridAdapter extends BaseExpandableListAdapter {

    public int mHorizontalSpacing;
    public int mVerticalSpacing;
    public int mNumColumns;
    public OnGridItemClickListener mListener;

    @Override
    public int getGroupCount() {
        return getGridGroupCount();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Deprecated
    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Deprecated
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Deprecated
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Deprecated
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        return getGridGroupView(groupPosition, isExpanded, convertView, parent);
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        GridAdapter gridAdapter;
        if (convertView == null) {
            convertView = new CustomGridView(parent.getContext());
            gridAdapter = new GridAdapter(this, groupPosition);
            convertView.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT,
                    AbsListView.LayoutParams.WRAP_CONTENT));
            convertView.setVerticalScrollBarEnabled(false);
            ((CustomGridView) convertView).setHorizontalSpacing(mHorizontalSpacing);
            ((CustomGridView) convertView).setVerticalSpacing(mVerticalSpacing);
            ((CustomGridView) convertView).setAdapter(gridAdapter);
            ((CustomGridView) convertView).setSelector(new ColorDrawable(Color.TRANSPARENT));
        } else {
            gridAdapter = (GridAdapter) ((CustomGridView) convertView).getAdapter();
            gridAdapter.setGridGroupPosition(groupPosition);
            gridAdapter.notifyDataSetChanged();
        }
        ((CustomGridView) convertView).setNumColumns(mNumColumns);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public abstract int getGridGroupCount();

    public abstract int getGridChildCount(int gridGroupPosition);

    public abstract Object getGridGroupData(int gridGroupPosition);

    public abstract Object getGridChildData(int gridGroupPosition, int gridChildPosition);

    public abstract View getGridGroupView(int gridGroupPosition, boolean isExpanded, View convertView, ViewGroup parent);

    public abstract View getGridChildView(int gridGroupPosition, int gridChildPosition, View convertView, ViewGroup parent);

    private class GridAdapter extends BaseAdapter {

        private ExpandableGridAdapter expandableGridAdapter;
        private int gridGroupPosition;

        GridAdapter(ExpandableGridAdapter expandableGridAdapter, int gridGroupPosition) {
            this.expandableGridAdapter = expandableGridAdapter;
            this.gridGroupPosition = gridGroupPosition;
        }

        void setGridGroupPosition(int gridGroupPosition) {
            this.gridGroupPosition = gridGroupPosition;
        }

        @Override
        public int getCount() {
            return expandableGridAdapter.getGridChildCount(gridGroupPosition);
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getGridChildView(gridGroupPosition, position, convertView, parent);
            convertView.setOnClickListener(new OnClickListenerImpl(position));
            return convertView;
        }

        class OnClickListenerImpl implements View.OnClickListener {

            private int gridChildPosition;

            OnClickListenerImpl(int gridChildPosition) {
                this.gridChildPosition = gridChildPosition;
            }

            @Override
            public void onClick(View v) {
                if (mListener != null)
                    mListener.onGridItemClick(v, gridGroupPosition, gridChildPosition);
            }
        }
    }
}
