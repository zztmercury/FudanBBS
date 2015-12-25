package com.zhuzhenting.fudanbbs.fragment;


import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhuzhenting.fudanbbs.MyApplication;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class BaseFragment extends Fragment {
    MyApplication application = (MyApplication) getContext().getApplicationContext();

    public BaseFragment() {
        // Required empty public constructor
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState, @LayoutRes int resource) {
        View rootView = inflater.inflate(resource, container, false);

        bindViews(rootView);
        bindDatas();
        bindListeners();

        return rootView;
    }

    protected abstract void bindViews(View rootView);

    protected abstract void bindDatas();

    protected abstract void bindListeners();

}
