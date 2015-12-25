package com.zhuzhenting.fudanbbs.servlet.function;

import com.zhuzhenting.fudanbbs.beans.ServerReport;
import com.zhuzhenting.fudanbbs.util.Config;
import com.zhuzhenting.fudanbbs.util.Support;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * 登录与登出
 * Created by snow on 15-12-13.
 */
public class Log {
    public static ServerReport login(String username, String password) {
        /* 登陆过程 */
        Connection.Response res = null;
        Document resDoc = null;
        try {
            res = Jsoup.connect(Config.BASE_URL + Config.LOGIN_URL)
                    .data("id", username, "pw", password, "persistent", "on")
                    .method(Connection.Method.POST)
                    .execute();
            /* 判断是否登陆成功 */
            resDoc = res.parse();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        if (Config.ERROR_MSG.equals(resDoc.title())) {
            ServerReport report = new ServerReport();
            String errorMsg = resDoc.body().getElementsByTag("div").get(0).text();
            report.setInfo(errorMsg);
            report.setSuccess(false);
            return report;
        }

        /* 设置Cookie */
        ServerReport report = new ServerReport();
        Support.getInstance().setCookie(res.cookie(Config.COOKIE_KEY_NAME), username);
        report.setInfo(res.cookie(Config.COOKIE_KEY_NAME));
        report.setSuccess(true);
        return report;
    }

    public static ServerReport logout() {
        Document logoutDocStr = Support.jsoupGet(Config.BASE_URL + Config.LOGOUT_URL);
        if (logoutDocStr == null) {
            return null;
        }
        if (logoutDocStr.toString().contains("错误")) {
            ServerReport report = new ServerReport();
            String errorMsg = logoutDocStr.toString();
            report.setInfo(errorMsg);
            report.setSuccess(false);
            return report;
        }
        ServerReport report = new ServerReport();
        report.setInfo("");
        report.setSuccess(true);
        return report;
    }
}
