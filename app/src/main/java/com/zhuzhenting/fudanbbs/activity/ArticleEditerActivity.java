package com.zhuzhenting.fudanbbs.activity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.zhuzhenting.fudanbbs.R;
import com.zhuzhenting.fudanbbs.beans.ServerReport;
import com.zhuzhenting.fudanbbs.beans.fjjyy.RConInfo;
import com.zhuzhenting.fudanbbs.servlet.Servlet;

/**
 * Created by zzt on 15-12-25.
 */
public class ArticleEditerActivity extends BaseActivity {
    private EditText etTitle, etContent;
    private String bid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_editer);

        bindViews();
        bindDatas();
        bindListeners();
    }

    @Override
    protected void bindViews() {
        etTitle = (EditText) findViewById(R.id.et_title);
        etContent = (EditText) findViewById(R.id.et_content);
    }

    @Override
    protected void bindListeners() {
        findViewById(R.id.btn_post).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validInput()) {
                    post();
                }
            }
        });
    }

    @Override
    protected void bindDatas() {
        bid = getIntent().getStringExtra("bid");
    }

    private boolean validInput() {
        if (TextUtils.isEmpty(etTitle.getText())) {
            etTitle.setError("标题不能为空");
            return false;
        }
        if (TextUtils.isEmpty(etContent.getText())) {
            etContent.setError("内容不能为空");
            return false;
        }
        return true;
    }

    private void post() {
        final RConInfo info = new RConInfo();
        info.setBid(bid);
        info.setTitle(etTitle.getText().toString());
        info.setContent(etContent.getText().toString());
        info.setReid("");
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage(getString(R.string.sending));
        pd.show();
        pd.setCancelable(false);
        new AsyncTask<Void, Void, ServerReport>() {
            @Override
            protected ServerReport doInBackground(Void... params) {
                return Servlet.sendPost(info);
            }

            @Override
            protected void onPostExecute(ServerReport serverReport) {
                pd.dismiss();
                if (serverReport.isSuccess()) {
                    Toast.makeText(getApplicationContext(), R.string.sending_success, Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), serverReport.getInfo(), Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
    }
}
