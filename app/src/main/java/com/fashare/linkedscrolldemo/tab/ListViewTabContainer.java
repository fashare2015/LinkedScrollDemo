package com.fashare.linkedscrolldemo.tab;

import android.content.Context;
import android.widget.AbsListView;
import android.widget.ListView;

import com.fashare.linkedscrolldemo.EventReceiver;
import com.fashare.linkedscrolldemo.base.BaseScrollableContainer;
import com.fashare.linkedscrolldemo.base.BaseViewGroupUtil;

/**
 * User: fashare(153614131@qq.com)
 * Date: 2016-10-15
 * Time: 22:05
 * <br/><br/>
 */
public class ListViewTabContainer extends BaseScrollableContainer<ListView> implements EventReceiver {
    public ListViewTabContainer(Context context, ListView listView) {
        super(context, listView);
    }

    @Override
    protected BaseViewGroupUtil<ListView> getViewUtil() {
        return new ListViewUtil(mViewGroup);
    }

    @Override
    protected void setOnScrollListener() {
        mViewGroup.setOnScrollListener(new ProxyOnScrollListener());

        mViewGroup.setOnItemClickListener((parent, view, position, id) ->
            mRealOnScrollListener.onClick(position)
        );
    }

    public class ProxyOnScrollListener implements AbsListView.OnScrollListener{
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            if(scrollState == SCROLL_STATE_IDLE) {              // 停止滑动
                mRealOnScrollListener.onScrollStop();
            }else if(scrollState == SCROLL_STATE_TOUCH_SCROLL)  // 按下拖动
                mRealOnScrollListener.onScrollStart();
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            mRealOnScrollListener.onScrolled();                 // 滑动
        }
    }
}