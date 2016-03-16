package com.zhuzhenting.fudanbbs.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.zhuzhenting.fudanbbs.R;
import com.zhuzhenting.fudanbbs.adapter.PostAdapter;
import com.zhuzhenting.fudanbbs.beans.ServerReport;
import com.zhuzhenting.fudanbbs.beans.fjjyy.BrdInfo;
import com.zhuzhenting.fudanbbs.beans.fjjyy.PostInfo;
import com.zhuzhenting.fudanbbs.servlet.Servlet;
import com.zhuzhenting.fudanbbs.widget.DividerItemDecoration;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Created by zzt on 15-12-24.
 */
public class PostActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView recyclerView;
    private PostAdapter mAdapter;
    private String input;
    private int startIndex;
    private String bid;
    private String TAG = "PostActivity";
    private RecyclerView.LayoutManager mLayoutManager;
    private boolean isLoadingMore = false;
    private SwipeRefreshLayout refreshView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.refresh_layout_recycler);

        bindViews();
        bindDatas();
        bindListeners();

        loadData();
    }

    @Override
    protected void bindViews() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, R.drawable.default_divider));
        recyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new PostAdapter(this);
        recyclerView.setAdapter(mAdapter);
        refreshView = (SwipeRefreshLayout) findViewById(R.id.refresh_view);
        refreshView.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        refreshView.setOnRefreshListener(this);
    }

    @Override
    protected void bindListeners() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItem = ((LinearLayoutManager) mLayoutManager).findLastVisibleItemPosition();
                int totalItemCount = mLayoutManager.getItemCount();
                //lastVisibleItem >= totalItemCount - 4 表示剩下4个item自动加载，各位自由选择
                // dy>0 表示向下滑动
                if (lastVisibleItem >= totalItemCount - 4 && dy > 0) {
                    if (isLoadingMore) {
                        Log.d(TAG, "ignore manually update!");
                    } else {
                        isLoadingMore = true;
                        loadData();
                    }
                }
            }
        });
    }

    @Override
    protected void bindDatas() {
        input = getIntent().getStringExtra("input");
        startIndex = -1;
        String title = getIntent().getStringExtra("title");
        setTitle(title);
    }

    private void loadData() {
        new AsyncTask<Void, Void, BrdInfo>() {
            @Override
            protected BrdInfo doInBackground(Void... params) {
                if (startIndex <= 0) {
                    return Servlet.getBrdInfo(input, 0, startIndex, 1);
                }
                return Servlet.getBrdInfo(bid, 1, startIndex, 1);
            }

            @Override
            protected void onPostExecute(BrdInfo brdInfo) {
                isLoadingMore = false;
                List<PostInfo> postInfos = brdInfo.getPosts();
                Collections.reverse(postInfos);
                if (startIndex <= 0)
                    mAdapter.clear();
                else {
                    Iterator<PostInfo> iterator = postInfos.iterator();
                    while (iterator.hasNext()) {
                        PostInfo postInfo = iterator.next();
                        if (postInfo.isSticky())
                            iterator.remove();
                    }
                }
                startIndex = brdInfo.getStart() - 20;
                bid = brdInfo.getBid();
                mAdapter.setBid(bid);
                mAdapter.addAll(postInfos);
                mAdapter.notifyDataSetChanged();
            }
        }.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.board, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_collect:
                addFav();
                break;
            case R.id.action_post:
                Intent i = new Intent(this, ArticleEditerActivity.class);
                i.putExtra("bid", bid);
                startActivity(i);
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void addFav() {
        new AsyncTask<Void, Void, ServerReport>() {
            @Override
            protected ServerReport doInBackground(Void... params) {
                return Servlet.addFav(bid);
            }

            @Override
            protected void onPostExecute(ServerReport serverReport) {
                if (serverReport.isSuccess()) {
                    Toast.makeText(getApplicationContext(), "收藏成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), serverReport.getInfo(), Toast.LENGTH_SHORT).show();
                }
                super.onPostExecute(serverReport);
            }
        }.execute();
    }

    @Override
    public void onRefresh() {
        startIndex = -1;
        loadData();
    }
}
