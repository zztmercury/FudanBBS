package com.zhuzhenting.fudanbbs.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.zhuzhenting.fudanbbs.R;
import com.zhuzhenting.fudanbbs.adapter.RConAdapter;
import com.zhuzhenting.fudanbbs.beans.fjjyy.TConInfo;
import com.zhuzhenting.fudanbbs.servlet.Servlet;
import com.zhuzhenting.fudanbbs.widget.DividerItemDecoration;

/**
 * Created by zzt on 15-12-23.
 */
public class RConActivity extends BaseActivity {
    private RecyclerView recyclerView;
    private RConAdapter mAdapter;
    private String gid, bid;

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
        recyclerView.addItemDecoration(new DividerItemDecoration(this, R.drawable.default_divider));
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mAdapter = new RConAdapter(this);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void bindListeners() {

    }

    @Override
    protected void bindDatas() {
        gid = getIntent().getStringExtra("gid");
        bid = getIntent().getStringExtra("bid");
    }

    private void loadData() {
        new AsyncTask<Void, Void, TConInfo>() {
            @Override
            protected TConInfo doInBackground(Void... params) {
                return Servlet.getTConInfo(bid, gid, "0", "");
            }

            @Override
            protected void onPostExecute(TConInfo tConInfo) {
                mAdapter.clear();
                mAdapter.addAll(tConInfo.getrList());
                mAdapter.notifyDataSetChanged();
            }
        }.execute();
    }
}
