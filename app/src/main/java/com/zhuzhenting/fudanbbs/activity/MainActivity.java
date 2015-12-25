package com.zhuzhenting.fudanbbs.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.zhuzhenting.fudanbbs.MyApplication;
import com.zhuzhenting.fudanbbs.R;
import com.zhuzhenting.fudanbbs.beans.ServerReport;
import com.zhuzhenting.fudanbbs.constant.UrlConfig;
import com.zhuzhenting.fudanbbs.fragment.HomeFragment;
import com.zhuzhenting.fudanbbs.fragment.MyFavFragment;
import com.zhuzhenting.fudanbbs.fragment.TopTopicFragment;
import com.zhuzhenting.fudanbbs.servlet.Servlet;
import com.zhuzhenting.fudanbbs.util.Support;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.Map;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Context context = this;
    private TextView tvNickname;
    private MyApplication app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        app = (MyApplication) getApplication();
        checkLoginStat();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setCheckedItem(R.id.nav_top_10);

        View header = LayoutInflater.from(this).inflate(R.layout.nav_header_main, navigationView, false);
        tvNickname = (TextView) header.findViewById(R.id.text_nickname);

        tvNickname.setText(app.getCachedAccount());

        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(R.string.top_10);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_root, new TopTopicFragment(), "TopTopicFragment");
        ft.commit();

        navigationView.setNavigationItemSelectedListener(this);
    }

    private void checkLoginStat() {
        final Map<String, String> cookies;
        cookies = Support.getInstance().getCookie();
        if (cookies.isEmpty()) {
            startActivity(new Intent(context, LoginActivity.class));
            finish();
            return;
        }
        new AsyncTask<Void, Void, Connection.Response>() {
            @Override
            protected Connection.Response doInBackground(Void... params) {
                try {
                    return Jsoup.connect(UrlConfig.BBS_ADDRESS + UrlConfig.ACTION_CHECK_COOKIES).cookies(cookies)
                            .ignoreContentType(true).execute();
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(Connection.Response response) {
                if (response == null)
                    return;
                if (response.body().contains("10007")) {
                    Toast.makeText(getApplicationContext(), "cookie过期，请重新登录", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(context, LoginActivity.class));
                    finish();
                }
                //else {
                //tvNickname.setText(app.getCachedAccount());
                //}
            }
        }.execute();
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentTransaction ft;

        switch (id) {
            case R.id.nav_home_page:
                if (getSupportActionBar() != null)
                    getSupportActionBar().setTitle(R.string.home_page);
                ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_root, new HomeFragment(), "HomeFragment");
                ft.commit();
                break;
            case R.id.nav_top_10:
                if (getSupportActionBar() != null)
                    getSupportActionBar().setTitle(R.string.top_10);
                ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_root, new TopTopicFragment(), "TopTopicFragment");
                ft.commit();
                break;
            case R.id.nav_my_collection:
                if (getSupportActionBar() != null)
                    getSupportActionBar().setTitle(R.string.my_collection);
                ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_root, new MyFavFragment(), "MyFavFragment");
                ft.commit();
                break;
            case R.id.action_logout:
                new AsyncTask<Void, Void, ServerReport>() {
                    @Override
                    protected ServerReport doInBackground(Void... params) {
                        return Servlet.logout();
                    }

                    @Override
                    protected void onPostExecute(ServerReport serverReport) {
                        if (serverReport.isSuccess()) {
                            app.cacheAccount(null);
                            app.cachePassword(null);
                            Support.getInstance().getCookie().clear();
                            startActivity(new Intent(context, LoginActivity.class));
                            finish();
                        }
                    }
                }.execute();
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
