package com.fashare.linkedscrolldemo;

/**
 * User: fashare(153614131@qq.com)
 * Date: 2016-10-16
 * Time: 06:43
 * <br/><br/>
 * 事件接受者
 */
public interface EventReceiver {
    /**
     * 收到事件: 立即选中 newPos
     * @param newPos
     */
    void selectItem(int newPos);
}