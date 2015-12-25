package com.zhuzhenting.fudanbbs.servlet.function;

import com.zhuzhenting.fudanbbs.beans.ServerReport;
import com.zhuzhenting.fudanbbs.beans.js.FavSec;
import com.zhuzhenting.fudanbbs.util.Config;
import com.zhuzhenting.fudanbbs.util.Support;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * 收藏相关操作
 * Created by snow on 15-12-13.
 */
public class Fav {

    public static List<FavSec> getFavSec() {
        /** 取得收藏板块信息*/
        Document favDoc = Support.jsoupGet(Config.BASE_URL + Config.FAV_URL);
        System.out.println(favDoc);//输出取得的页面格式

        /* 生成List */
        List<FavSec> retList = new ArrayList<FavSec>();
        //获取各条信息
        Elements favs = favDoc.getElementsByTag("brd");
        Iterator<Element> favIter = favs.iterator();
        while (favIter.hasNext()) {
            Element each = favIter.next();
            FavSec e = new FavSec();
            e.setBid(each.attr("bid"));
            e.setName(each.attr("brd"));
            e.setDesc(each.text());
            retList.add(e);//存入list里
        }
        return retList;
    }

    public static ServerReport addFav(String bid) {
        Map<String, String> data = new HashMap<String, String>();
        data.put("bid", bid);

         /* 发送请求 */
        Document addFavDoc = Support.jsoupGet(Config.BASE_URL + Config.ADD_FAV_URL, data);

         /* 返回服务端信息 */
        if (Config.ERROR_MSG.equals(addFavDoc.title())) {
            ServerReport report = new ServerReport();
            String errorMsg = addFavDoc.body().getElementsByTag("div").get(0).text();
            report.setInfo(errorMsg);
            report.setSuccess(false);
            return report;
        }

         /* 查看是否添加上了该版块*/
        Document favDoc = Support.jsoupGet(Config.BASE_URL + Config.FAV_URL);
        Elements eles = favDoc.getElementsByTag("brd");
        for (int i = 0; i < eles.size(); i++) {
            String eachId = eles.get(i).attr("bid");
            if (eachId.equals(bid)) {
                FavSec f = new FavSec();
                f.setBid(bid);
                ServerReport report = new ServerReport();
                report.setInfo("");
                report.setSuccess(true);
                return report;
            }
        }

        ServerReport report = new ServerReport();
        report.setInfo("收藏失败");
        report.setSuccess(false);
        return report;
    }

    public static ServerReport delFav(String bid, List<FavSec> oldList) {
        //请自己判断要删除的bid是不是已经收藏了！
        Map<String, String> data = new HashMap<String, String>();
        data.put("bid", bid);

         /* 发送请求 */
        Document favDoc = Support.jsoupGet(Config.BASE_URL + Config.DEL_FAV_URL, data);

         /* 返回服务端信息 */
        if (Config.ERROR_MSG.equals(favDoc.title())) {
            ServerReport report = new ServerReport();
            String errorMsg = favDoc.body().getElementsByTag("div").get(0).text();
            report.setInfo(errorMsg);
            report.setSuccess(false);
            return report;
        }

         /* 查看是否删除了了该版块*/
        Elements eles = favDoc.getElementsByTag("brd");
        for (int i = 0; i < eles.size(); i++) {
            String eachId = eles.get(i).attr("bid");
            if (eachId.equals(bid)) {
                ServerReport report = new ServerReport();
                report.setInfo("删除失败");
                report.setSuccess(false);
                return report;
            }
        }

        ServerReport report = new ServerReport();
        report.setInfo("");
        report.setSuccess(true);
        return report;
    }
}
