package com.fashare.linkedscrolldemo.base;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;

import com.annimon.stream.Stream;

/**
 * User: fashare(153614131@qq.com)
 * Date: 2016-10-16
 * Time: 05:49
 * <br/><br/>
 * viewgroup 工具基类
 */
public abstract class BaseViewGroupUtil<VG extends ViewGroup> {
    protected VG mViewGroup;
    public int beginPos;
    public int endPos;

    public BaseViewGroupUtil(VG mViewGroup) {
        this.mViewGroup = mViewGroup;
    }

    /**
     * 更新边界 beginPos, endPos
     */
    public abstract void updateEdges();

    /**
     * 滑动到 pos
     * @param pos
     */
    public abstract void smoothScrollTo(int pos);

    /**
     * 选中 position
     * @param position
     */
    public void setViewSelected(int position) {
//        Log.d("setViewSelected: ", "cur: " + position);
        if(!isVisiblePos(position))
            return ;
        Stream.of(mViewGroup.getTouchables())     // child 必须是 touchable !!!
                .forEach(view -> setViewSelected(view, false)); // 清空状态
        setViewSelected(position, true);
    }

    public void setViewSelected(int position, boolean isSelected){
        if (getViewByPosition(position) != null)
            setViewSelected(getViewByPosition(position), isSelected);
    }

    protected void setViewSelected(View view, boolean isSelected){
        view.setBackgroundColor(isSelected? Color.WHITE: Color.LTGRAY);
    }

    /**
     * pos 的项是否可见
     * @param pos
     * @return
     */
    public boolean isVisiblePos(int pos) {
        updateEdges();
        return pos>=beginPos && pos<=endPos;
    }

    /**
     * 获取 position 处的 view
     * @param pos
     * @return
     */
    private View getViewByPosition(int pos) {
        updateEdges();
        return mViewGroup.getChildAt(pos - beginPos);
    }

    /**
     * 返回新的 newPos, 以更新 mCurPosition
     * 注意边界处理
     * @param pos
     * @return
     */
    public abstract int updatePosOnScrolled(int pos);
}