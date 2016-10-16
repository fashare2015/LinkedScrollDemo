package com.fashare.linkedscrolldemo.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SectionIndexer;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.fashare.linkedscrolldemo.R;
import com.fashare.linkedscrolldemo.base.BaseScrollableContainer;
import com.fashare.linkedscrolldemo.tab.ListViewTabContainer;
import com.fashare.linkedscrolldemo.content.RecyclerViewContentContainer;
import com.fashare.linkedscrolldemo.widget.SimpleArrayAdapter;
import com.fashare.linkedscrolldemo.widget.RealSectionIndexer;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.linked_layout)
    LinkedLayout mLinkedLayout;

    private BaseScrollableContainer mTabContainer;      // 左边的 Tab 页
    private BaseScrollableContainer mContentContainer;  // 右边的 content 页

    List<Integer> mData = Stream.iterate(0, item -> item+1)
            .limit(500)
            .collect(Collectors.toList());

    private SectionIndexer mSectionIndexer = new RealSectionIndexer(mData);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initTabContainer();
        initContentContainer();
        initLinkedLayout();
    }

    private void initTabContainer() {
        ListView mListView = new ListView(this);
        mListView.setAdapter(new ArrayAdapter<>(this, R.layout.item_common,
                Arrays.asList(mSectionIndexer.getSections())
        ));

        mTabContainer = new ListViewTabContainer(this, mListView);
    }

    private void initContentContainer() {
        RecyclerView mRecyclerView = new RecyclerView(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new SimpleArrayAdapter<>(this, mData, mSectionIndexer));

        mContentContainer = new RecyclerViewContentContainer(this, mRecyclerView);
    }

    private void initLinkedLayout() {
        mLinkedLayout.setContainers(mTabContainer, mContentContainer);
        mLinkedLayout.setSectionIndexer(mSectionIndexer);
    }
}
