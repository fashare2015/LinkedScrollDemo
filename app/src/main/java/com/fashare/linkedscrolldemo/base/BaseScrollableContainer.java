package com.fashare.linkedscrolldemo.base;

import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;

import com.fashare.linkedscrolldemo.EventDispatcher;
import com.fashare.linkedscrolldemo.EventReceiver;

/**
 * User: fashare(153614131@qq.com)
 * Date: 2016-10-15
 * Time: 21:39
 * <br/><br/>
 * 基类, 可滚动的页面
 */
public abstract class BaseScrollableContainer<VG extends ViewGroup>
        implements EventReceiver {
    protected Context mContext;
    public VG mViewGroup;

    protected RealOnScrollListener mRealOnScrollListener;
    private EventDispatcher mEventDispatcher;

    public void setEventDispatcher(EventDispatcher eventDispatcher) {
        mEventDispatcher = eventDispatcher;
    }

    protected void dispatchItemSelectedEvent(int curPosition){
        if(mEventDispatcher != null)
            mEventDispatcher.dispatchItemSelectedEvent(curPosition, mViewGroup);
    }

    public BaseScrollableContainer(Context context, VG viewGroup) {
        mContext = context;
        mViewGroup = viewGroup;
        mRealOnScrollListener = new RealOnScrollListener(getViewUtil());
        setOnScrollListener();
    }

    protected abstract BaseViewGroupUtil<VG> getViewUtil();

    protected abstract void setOnScrollListener();

    @Override
    public void selectItem(int newPos) {
        mRealOnScrollListener.selectItem(newPos);
    }

    // OnScrollListener: 代理模式
    /**
     * User: fashare(153614131@qq.com)
     * Date: 2016-10-16
     * Time: 16:47
     * <br/><br/>
     */
    public class RealOnScrollListener implements OnScrollListener {
        public boolean isTouching = false;  // 处于触摸状态
        private int mCurPosition = 0;       // 当前选中项
        private BaseViewGroupUtil<VG> mViewUtil;

        public RealOnScrollListener(BaseViewGroupUtil<VG> viewUtil) {
            mViewUtil = viewUtil;
        }

        public void selectItem(int position){
            mCurPosition = position;
            Log.d("setitem", position + "");
            // 来自另一边的联动事件
            mViewUtil.smoothScrollTo(position);
//            if(mViewUtil.isVisiblePos(position))    // curSection 可见时, 不滚动
                mViewUtil.setViewSelected(position);
        }

        @Override
        public void onClick(int position) {
            isTouching = true;
            mViewUtil.setViewSelected(mCurPosition = position);
            dispatchItemSelectedEvent(position);
            isTouching = false;
        }

        @Override
        public void onScrollStart() {
            isTouching = true;
        }

        @Override
        public void onScrollStop() {
            isTouching = false;
            mViewUtil.smoothScrollTo(mCurPosition);    // 调正位置
            mViewUtil.setViewSelected(mCurPosition);
        }

        @Override
        public void onScrolled() {
            mCurPosition = mViewUtil.updatePosOnScrolled(mCurPosition);
            if(isTouching)          // 来自用户, 通知 对方 联动
                onScrolledByUser();
            else                    // 来自对方, 被动滑动不响应
                onScrolledByInvoked();
        }

        @Override
        public void onScrolledByUser() {
            dispatchItemSelectedEvent(mCurPosition);    // 来自用户, 通知 对方 联动
        }

        @Override
        public void onScrolledByInvoked() {     // 被动滑动不响应
            // do nothing
        }
    }

    /**
     * Created by apple on 16-10-16.
     * 滑动事件 代理接口
     */
    public interface OnScrollListener {
        // tab 点击事件
        void onClick(int position);

        // 滑动开始
        void onScrollStart();

        // 滑动结束
        void onScrollStop();

        // 触发 onScrolled()
        void onScrolled();

        // 用户手动滑, 触发的 onScrolled()
        void onScrolledByUser();

        // 程序调用 scrollTo(), 触发的 onScrolled()
        void onScrolledByInvoked();
    }
}