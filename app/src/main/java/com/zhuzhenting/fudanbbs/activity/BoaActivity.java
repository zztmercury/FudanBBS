package com.zhuzhenting.fudanbbs.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.zhuzhenting.fudanbbs.R;
import com.zhuzhenting.fudanbbs.adapter.BoaAdapter;
import com.zhuzhenting.fudanbbs.beans.fjjyy.BoaInfo;
import com.zhuzhenting.fudanbbs.servlet.Servlet;
import com.zhuzhenting.fudanbbs.widget.DividerItemDecoration;

import java.util.List;

/**
 * Created by zzt on 15-12-23.
 */
public class BoaActivity extends BaseActivity {
    private RecyclerView recyclerView;
    private BoaAdapter mAdapter;
    private boolean isDir;
    private String input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_recycler);

        bindViews();
        bindDatas();
        bindListeners();

        loadData();
    }

    @Override
    protected void bindViews() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, R.drawable.default_divider));
        mAdapter = new BoaAdapter(this);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void bindListeners() {

    }

    @Override
    protected void bindDatas() {
        isDir = getIntent().getBooleanExtra("dir", false);
        input = getIntent().getStringExtra("input");
        String title = getIntent().getStringExtra("title");
        setTitle(title);
    }

    private void loadData() {
        new AsyncTask<Void, Void, List<BoaInfo>>() {
            @Override
            protected List<BoaInfo> doInBackground(Void... params) {
                if (isDir)
                    return Servlet.getLayoutInfo(input);
                else
                    return Servlet.getClassifyInfo(input);
            }

            @Override
            protected void onPostExecute(List<BoaInfo> boaInfos) {
                mAdapter.clear();
                mAdapter.addAll(boaInfos);
                mAdapter.sort();
                mAdapter.notifyDataSetChanged();
            }
        }.execute();
    }
}
