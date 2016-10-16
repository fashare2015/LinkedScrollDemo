package com.fashare.linkedscrolldemo.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SectionIndexer;

import com.fashare.linkedscrolldemo.EventDispatcher;
import com.fashare.linkedscrolldemo.base.BaseScrollableContainer;


/**
 * User: fashare(153614131@qq.com)
 * Date: 2016-10-15
 * Time: 20:37
 * <br/><br/>
 */
public class LinkedLayout extends LinearLayout implements EventDispatcher {
    private static final int MEASURE_BY_WEIGHT = 0;
    private static final float WEIGHT_TAB = 1;
    private static final float WEIGHT_CONTENT = 3;

    private Context mContext;
    private BaseScrollableContainer mTabContainer;
    private BaseScrollableContainer mContentContainer;
    private SectionIndexer mSectionIndexer; // 代理

    public void setContainers(BaseScrollableContainer tabContainer, BaseScrollableContainer contentContainer) {
        mTabContainer = tabContainer;
        mContentContainer = contentContainer;
        mTabContainer.setEventDispatcher(this);
        mContentContainer.setEventDispatcher(this);

        // 设置 LayoutParams
        mTabContainer.mViewGroup.setLayoutParams(new LinearLayout.LayoutParams(
                MEASURE_BY_WEIGHT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                WEIGHT_TAB
        ));

        mContentContainer.mViewGroup.setLayoutParams(new LinearLayout.LayoutParams(
                MEASURE_BY_WEIGHT,
                ViewGroup.LayoutParams.MATCH_PARENT,
                WEIGHT_CONTENT
        ));

        this.addView(mTabContainer.mViewGroup);
        this.addView(mContentContainer.mViewGroup);
        this.setOrientation(HORIZONTAL);
    }

    public void setSectionIndexer(SectionIndexer sectionIndexer) {
        mSectionIndexer = sectionIndexer;
    }

    public LinkedLayout(Context context) {
        super(context);
        initView();
    }

    public LinkedLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public LinkedLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        mContext = getContext();
    }

    @Override
    public void dispatchItemSelectedEvent(int pos, View fromView) {
        if(mSectionIndexer == null){
            throw new NullPointerException("you should set mSectionIndexer first!!");
        }
        int convertPos;
        Log.d("dispatchItemSelected", (fromView == mContentContainer.mViewGroup) + ", " + (fromView == mTabContainer.mViewGroup));
        if (fromView == mContentContainer.mViewGroup) { // 来自 content, 转发给 tab

            convertPos = mSectionIndexer.getSectionForPosition(pos);
            Log.d("dispatchItemSelected", pos + ", " + convertPos);
            mTabContainer.selectItem(convertPos);
        } else {                    // 来自 tab, 转发给 content
            convertPos = mSectionIndexer.getPositionForSection(pos);
            mContentContainer.selectItem(convertPos);
        }
    }
}