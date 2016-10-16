package com.fashare.linkedscrolldemo.content;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.fashare.linkedscrolldemo.EventReceiver;
import com.fashare.linkedscrolldemo.base.BaseScrollableContainer;
import com.fashare.linkedscrolldemo.base.BaseViewGroupUtil;

/**
 * User: fashare(153614131@qq.com)
 * Date: 2016-10-15
 * Time: 22:41
 * <br/><br/>
 */
public class RecyclerViewContentContainer extends BaseScrollableContainer<RecyclerView> implements EventReceiver {
    public RecyclerViewContentContainer(Context context, RecyclerView recyclerView) {
        super(context, recyclerView);
    }

    @Override
    protected BaseViewGroupUtil<RecyclerView> getViewUtil() {
        return new RecyclerViewUtil(mViewGroup);
    }

    @Override
    protected void setOnScrollListener() {
        mViewGroup.addOnScrollListener(new ProxyOnScrollListener());
    }

    private class ProxyOnScrollListener extends RecyclerView.OnScrollListener {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            if(newState == RecyclerView.SCROLL_STATE_IDLE) {            // 停止滑动
                mRealOnScrollListener.onScrollStop();
            }else if(newState == RecyclerView.SCROLL_STATE_DRAGGING){   // 按下拖动
                mRealOnScrollListener.onScrollStart();
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) { // 滑动
            mRealOnScrollListener.onScrolled();
        }
    }
}