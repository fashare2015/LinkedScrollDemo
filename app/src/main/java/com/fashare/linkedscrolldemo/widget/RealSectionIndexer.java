package com.fashare.linkedscrolldemo.widget;

import android.widget.SectionIndexer;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import java.util.List;

/**
 * User: fashare(153614131@qq.com)
 * Date: 2016-10-16
 * Time: 00:17
 * <br/><br/>
 * SectionIndexer 的具体实现类, 作为被代理对象
 */
public class RealSectionIndexer implements SectionIndexer {
    private List<Integer> mObjects;

    public RealSectionIndexer(List<Integer> objects) {
        mObjects = objects;
    }

    @Override
    public Integer[] getSections() {
        if(mObjects == null)
            return new Integer[0];

        return Stream.of(mObjects)
                .groupBy(obj -> getSectionForPosition(obj))   // section 作为 key 分组
                .map(entry -> entry.getKey())   // 获取所有的 key, 也就是 section
                .sorted()
                .collect(Collectors.toList())
                .toArray(new Integer[0]);
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        return sectionIndex*5;
    }

    @Override
    public int getSectionForPosition(int position) {
        return position/5;
    }
}