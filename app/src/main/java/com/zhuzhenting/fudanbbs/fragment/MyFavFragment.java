package com.zhuzhenting.fudanbbs.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhuzhenting.fudanbbs.R;
import com.zhuzhenting.fudanbbs.adapter.MyFavAdapter;
import com.zhuzhenting.fudanbbs.beans.js.FavSec;
import com.zhuzhenting.fudanbbs.servlet.Servlet;
import com.zhuzhenting.fudanbbs.widget.DividerItemDecoration;

import java.util.List;

/**
 * Created by zzt on 15-12-25.
 */
public class MyFavFragment extends Fragment {
    private RecyclerView recyclerView;
    private MyFavAdapter mAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.layout_recycler,container,false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), R.drawable.default_divider));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        mAdapter = new MyFavAdapter(getContext());
        recyclerView.setAdapter(mAdapter);

        loadData();

        return rootView;
    }

    private void loadData() {
        new AsyncTask<Void, Void, List<FavSec>>() {
            @Override
            protected List<FavSec> doInBackground(Void... params) {
                return Servlet.getFavSec();
            }

            @Override
            protected void onPostExecute(List<FavSec> favSecs) {
                mAdapter.clear();
                mAdapter.addAll(favSecs);
                mAdapter.notifyDataSetChanged();
            }
        }.execute();
    }
}
