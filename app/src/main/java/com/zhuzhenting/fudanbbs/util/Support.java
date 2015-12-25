package com.zhuzhenting.fudanbbs.util;

import com.zhuzhenting.fudanbbs.MyApplication;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by snow on 15-12-13.
 */
public class Support {
    private static Support support = new Support();
    private Map<String, String> currentCookies = new HashMap<>();
    private MyApplication app;

    private Support() {

    }

    public static Support getInstance() {
        return support;
    }

    /* String转Timestamp */
    public static Timestamp Str2TS(String tsStr) {
        Timestamp ts = Timestamp.valueOf(tsStr);
        return ts;
    }

    /* Timestamp转String */
    public static String TS2Str(Timestamp ts) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String tsStr = sdf.format(ts);
        return tsStr;
    }

    public static Document jsoupGet(String url) {
        try {
            return Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.21 (KHTML, like Gecko) Chrome/19.0.1042.0 Safari/535.21")
                    .cookies(Support.getInstance().getCookie())
                    .get();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Document jsoupGet(String url, Map data) {
        try {
            return Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.21 (KHTML, like Gecko) Chrome/19.0.1042.0 Safari/535.21")
                    .data(data)
                    .cookies(Support.getInstance().getCookie())
                    .get();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Document jsoupPost(String url, Map data) {
        try {
            return Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_1) AppleWebKit/601.2.7 (KHTML, like Gecko) Version/9.0.1 Safari/601.2.7")
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .data(data)
                    .cookies(Support.getInstance().getCookie())
                    .post();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    public Map<String, String> setCookie(String currentKey, String currentUser) {
        currentCookies.put(Config.COOKIE_KEY_NAME, currentKey);
        currentCookies.put(Config.COOKIE_USER_NAME, currentUser);
        app.cacheStringParam(Config.COOKIE_KEY_NAME, currentKey);
        app.cacheStringParam(Config.COOKIE_USER_NAME, currentUser);
        return currentCookies;
    }

    public Map<String, String> getCookie() {
        if (currentCookies.isEmpty() && app.getCachedStringParam(Config.COOKIE_KEY_NAME) != null && app.getCachedStringParam(Config.COOKIE_USER_NAME) != null) {
            currentCookies.put(Config.COOKIE_KEY_NAME, app.getCachedStringParam(Config.COOKIE_KEY_NAME));
            currentCookies.put(Config.COOKIE_USER_NAME, app.getCachedStringParam(Config.COOKIE_USER_NAME));
        }
        return currentCookies;
    }

    public void setApp(MyApplication app) {
        this.app = app;
    }
}
