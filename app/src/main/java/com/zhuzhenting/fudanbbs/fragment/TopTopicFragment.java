package com.zhuzhenting.fudanbbs.fragment;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhuzhenting.fudanbbs.MyApplication;
import com.zhuzhenting.fudanbbs.R;
import com.zhuzhenting.fudanbbs.adapter.TopTopicAdapter;
import com.zhuzhenting.fudanbbs.beans.js.TopInfo;
import com.zhuzhenting.fudanbbs.servlet.Servlet;
import com.zhuzhenting.fudanbbs.widget.DividerItemDecoration;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TopTopicFragment extends Fragment {
    private RecyclerView list;
    private TopTopicAdapter adapter;
    private MyApplication app;

    public TopTopicFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        app = (MyApplication) getContext().getApplicationContext();
        View rootView = inflater.inflate(R.layout.layout_recycler, container, false);

        list = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        adapter = new TopTopicAdapter(getContext());
        list.addItemDecoration(new DividerItemDecoration(getContext(), R.drawable.default_divider));
        list.setAdapter(adapter);
        list.setLayoutManager(new LinearLayoutManager(getContext()));

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {
        new AsyncTask<Object, Void, List<TopInfo>>() {

            @Override
            protected List<TopInfo> doInBackground(Object... params) {
                return Servlet.getTopTenInfo();
            }

            @Override
            protected void onPostExecute(List<TopInfo> topInfos) {
                adapter.clear();
                adapter.addAll(topInfos);
                adapter.notifyDataSetChanged();
            }
        }.execute();
    }
}
