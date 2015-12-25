package com.zhuzhenting.fudanbbs.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.zhuzhenting.fudanbbs.MyApplication;

/**
 * Created by zzt on 15-12-11.
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected MyApplication app;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = (MyApplication) getApplication();

    }

    abstract protected void bindViews();

    abstract protected void bindListeners();

    abstract protected void bindDatas();
}
