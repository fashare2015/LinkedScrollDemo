package com.fashare.linkedscrolldemo.widget;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;

import java.util.List;

/**
 * User: fashare(153614131@qq.com)
 * Date: 2016-10-15
 * Time: 23:29
 * <br/><br/>
 */
public class SimpleArrayAdapter<T>
        extends RecyclerView.Adapter<SimpleArrayAdapter.ViewHolder>
        implements SectionIndexer{

    private final Context mContext;
    private final @LayoutRes int mResource;
    private List<T> mObjects;
    private SectionIndexer mRealSectionIndexer; // 代理

    public SimpleArrayAdapter(@NonNull Context context, @NonNull List<T> objects, SectionIndexer realSectionIndexer) {
        mContext = context;
        mResource = android.R.layout.simple_list_item_1;
        mObjects = objects;
        mRealSectionIndexer = realSectionIndexer;
    }

    @Override
    public SimpleArrayAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(View.inflate(mContext, mResource, null));
    }

    @Override
    public void onBindViewHolder(SimpleArrayAdapter.ViewHolder holder, int position) {
        holder.textView.setText(mObjects.get(position).toString());
        holder.textView.setOnClickListener(view -> new Object());   // enable ViewGroup.getTouchables()
    }

    @Override
    public int getItemCount() {
        return mObjects==null? 0: mObjects.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView;
            textView.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            ));
        }
    }

    // 代理
    @Override
    public Integer[] getSections() {
        return (Integer[]) mRealSectionIndexer.getSections();
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        return mRealSectionIndexer.getPositionForSection(sectionIndex);
    }

    @Override
    public int getSectionForPosition(int position) {
        return mRealSectionIndexer.getSectionForPosition(position);
    }
}