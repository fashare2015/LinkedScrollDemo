package com.fashare.linkedscrolldemo.content;

import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.fashare.linkedscrolldemo.base.BaseViewGroupUtil;

/**
 * User: fashare(153614131@qq.com)
 * Date: 2016-10-16
 * Time: 07:03
 * <br/><br/>
 */
public class RecyclerViewUtil extends BaseViewGroupUtil<RecyclerView> {
    private final LinearLayoutManager mLinearLayoutManager;

    public RecyclerViewUtil(RecyclerView recyclerView) {
        super(recyclerView);
        mLinearLayoutManager = (LinearLayoutManager)mViewGroup.getLayoutManager();
    }

    @Override
    public void updateEdges(){
        beginPos = mLinearLayoutManager.findFirstVisibleItemPosition();
        endPos = mLinearLayoutManager.findLastVisibleItemPosition();
    }

    /**
     * Android RecyclerView滚动定位
     * 仅用于 item 高度固定
     * 来自 http://blog.csdn.net/tyzlmjj/article/details/49227601
     * @param pos
     */
    @Override
    public void smoothScrollTo(int pos) {
        updateEdges();
        final int itemHeight = mViewGroup.getChildAt(0).getHeight();
        final int beginTop = mViewGroup.getChildAt(0).getTop();
        mViewGroup.smoothScrollBy(0, beginTop + itemHeight * (pos-beginPos)); // 仅用于 item 高度固定
    }

    /** 始终选中 beginPos
     * @param pos
     * @return
     */
    public int updatePosOnScrolled(int pos) {
        updateEdges();
        setViewSelected(beginPos);
        return beginPos;
    }

    @Override
    protected void setViewSelected(View view, boolean isSelected){
        view.setBackgroundColor(isSelected? Color.WHITE: Color.GRAY);
    }
}