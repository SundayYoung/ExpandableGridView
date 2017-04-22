package com.pinpaimei.expandgridview;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListAdapter;

/**
 * Created by lhy.
 */
public class ExpandableGridView extends ExpandableListView implements ExpandableListView.OnGroupClickListener {

    private OnGridItemClickListener mOnGridItemListener;
    private boolean mIsOverwriteOnMeasure;
    private boolean mIsGroupClickable;
    private int mHorizontalSpacing;
    private int mVerticalSpacing;
    private int mNumColumns;

    public ExpandableGridView(Context context) {
        super(context);
        init(context, null);
    }

    public ExpandableGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ExpandableGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ExpandableGridView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        setOnGroupClickListener(this);
        if (attrs == null)
            return;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ExpandableGridView);
        mIsOverwriteOnMeasure = typedArray.getBoolean(R.styleable.ExpandableGridView_overwrite_measure, false);
        mIsGroupClickable = typedArray.getBoolean(R.styleable.ExpandableGridView_group_clickable, true);
        mHorizontalSpacing = typedArray.getDimensionPixelOffset(R.styleable.ExpandableGridView_horizontal_spacing, 0);
        mVerticalSpacing = typedArray.getDimensionPixelOffset(R.styleable.ExpandableGridView_vertical_spacing, 0);
        mNumColumns = typedArray.getInteger(R.styleable.ExpandableGridView_columns_number, 0);
        typedArray.recycle();
    }

    @Deprecated
    @Override
    public void setAdapter(ListAdapter adapter) {
        throw new RuntimeException(
                "For ExpandableGridView, use setExpandableGridAdapter(ExpandableGridAdapter) instead of " +
                        "setAdapter(ListAdapter)");
    }

    @Deprecated
    @Override
    public void setAdapter(ExpandableListAdapter adapter) {
        throw new RuntimeException(
                "For ExpandableGridView, use setExpandableGridAdapter(ExpandableGridAdapter) instead of " +
                        "setAdapter(ExpandableListAdapter)");
    }

    public void setExpandableGridAdapter(ExpandableGridAdapter adapter) {
        super.setAdapter(adapter);
        getExpandableGridAdapter().mHorizontalSpacing = mHorizontalSpacing;
        getExpandableGridAdapter().mVerticalSpacing = mVerticalSpacing;
        getExpandableGridAdapter().mNumColumns = mNumColumns;
        getExpandableGridAdapter().mListener = mOnGridItemListener;
    }

    @Deprecated
    @Override
    public ListAdapter getAdapter() {
        return super.getAdapter();
        /*throw new RuntimeException(
                "For ExpandableGridView, use getExpandableGridAdapter() instead of getAdapter()");*/
    }

    @Deprecated
    @Override
    public ExpandableListAdapter getExpandableListAdapter() {
        throw new RuntimeException(
                "For ExpandableGridView, use getExpandableGridAdapter() instead of getExpandableListAdapter()");
    }

    public ExpandableGridAdapter getExpandableGridAdapter() {
        return (ExpandableGridAdapter) super.getExpandableListAdapter();
    }


    public void setHorizontalSpacing(int horizontalSpacing) {
        this.mHorizontalSpacing = horizontalSpacing;
        if (getExpandableGridAdapter() != null)
            getExpandableGridAdapter().mHorizontalSpacing = horizontalSpacing;
    }

    public void setVerticalSpacing(int verticalSpacing) {
        this.mVerticalSpacing = verticalSpacing;
        if (getExpandableGridAdapter() != null)
            getExpandableGridAdapter().mVerticalSpacing = verticalSpacing;
    }

    public void setmNumColumns(int columns) {
        this.mNumColumns = columns;
        if (getExpandableGridAdapter() != null)
            getExpandableGridAdapter().mNumColumns = columns;
    }

    public int getVerticalSpacing() {
        return mVerticalSpacing;
    }

    public int getHorizontalSpacing() {
        return mHorizontalSpacing;
    }

    public boolean isOverwriteOnMeasure() {
        return mIsOverwriteOnMeasure;
    }

    public void setOverwriteOnMeasure(boolean overwriteOnMeasure) {
        mIsOverwriteOnMeasure = overwriteOnMeasure;
    }

    public boolean isGroupClickable() {
        return mIsGroupClickable;
    }

    public void setGroupClickable(boolean groupClickable) {
        mIsGroupClickable = groupClickable;
    }

    public void setOnGridItemClickListener(OnGridItemClickListener listener) {
        this.mOnGridItemListener = listener;
        if (getExpandableGridAdapter() != null)
            getExpandableGridAdapter().mListener = listener;
    }

    public OnGridItemClickListener getOnGridItemClickListener() {
        return mOnGridItemListener;
    }

    public void expandAll() {
        expandAll(false);
    }

    public void expandAll(boolean animate) {
        if (getExpandableGridAdapter() == null) {
            return;
        }
        for (int groupPosition = 0, count = getExpandableGridAdapter().getGroupCount(); groupPosition < count; groupPosition++)
            expandGroup(groupPosition, animate);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = heightMeasureSpec;
        if (mIsOverwriteOnMeasure)
            expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                    MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

    @Override
    public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
        return !mIsGroupClickable;
    }
}
