package com.zhuzhenting.fudanbbs.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhuzhenting.fudanbbs.R;
import com.zhuzhenting.fudanbbs.adapter.HomeAdapter;
import com.zhuzhenting.fudanbbs.widget.DividerItemDecoration;

/**
 * Created by zzt on 15-12-20.
 */
public class HomeFragment extends Fragment {
    public HomeAdapter adapter;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.layout_recycler, container, false);


        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), R.drawable.default_divider));
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        adapter = new HomeAdapter(getContext());
        recyclerView.setAdapter(adapter);
        //adapter.notifyDataSetChanged();
        return rootView;
    }

}
