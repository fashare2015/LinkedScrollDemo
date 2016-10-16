package com.fashare.linkedscrolldemo;

import android.view.View;

/**
 * User: fashare(153614131@qq.com)
 * Date: 2016-10-16
 * Time: 06:43
 * <br/><br/>
 * 事件分发者
 */
public interface EventDispatcher {
    /**
     * 分发事件: fromView 中的 pos 被选中
     * @param pos
     * @param fromView
     */
    void dispatchItemSelectedEvent(int pos, View fromView);
}