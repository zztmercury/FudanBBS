package com.zhuzhenting.fudanbbs.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.zhuzhenting.fudanbbs.R;
import com.zhuzhenting.fudanbbs.beans.ServerReport;
import com.zhuzhenting.fudanbbs.servlet.Servlet;

public class LoginActivity extends BaseActivity {
    private static final String ERROR_MSG = "发生错误";

    private EditText editAccount;
    private EditText editPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        bindViews();
        bindDatas();
        bindListeners();
    }

    @Override
    protected void bindViews() {
        editAccount = (EditText) findViewById(R.id.account);
        editPassword = (EditText) findViewById(R.id.password);
    }

    @Override
    protected void bindListeners() {
        findViewById(R.id.btn_log_in).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    @Override
    protected void bindDatas() {

    }

    private void login() {
        if (validInput()) {
            final String account = editAccount.getText().toString().trim();
            final String password = editPassword.getText().toString().trim();
            /* 登陆过程 */
            new AsyncTask<Void, Void, ServerReport>() {
                @Override
                protected ServerReport doInBackground(Void... params) {
                    return Servlet.login(account, password);
                }

                @Override
                protected void onPostExecute(ServerReport serverReport) {
                    if (serverReport == null) {
                        Toast.makeText(getApplicationContext(), "服务器返回异常，请稍后再尝试", Toast.LENGTH_SHORT).show();
                    } else if (!serverReport.isSuccess()) {
                        Toast.makeText(getApplicationContext(), serverReport.getInfo(), Toast.LENGTH_SHORT).show();
                    } else {
                        app.cacheAccount(account);
                        app.cachePassword(password);
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    }
                }
            }.execute();
        }
    }

    private boolean validInput() {
        if (TextUtils.isEmpty(editAccount.getText())) {
            editAccount.setError("账号不能为空");
            return false;
        }
        if (TextUtils.isEmpty(editPassword.getText())) {
            editPassword.setError("密码不能为空");
            return false;
        }
        return true;
    }
}
