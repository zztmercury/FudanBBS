package com.zhuzhenting.fudanbbs.servlet.function;

import com.zhuzhenting.fudanbbs.beans.js.TopInfo;
import com.zhuzhenting.fudanbbs.util.Config;
import com.zhuzhenting.fudanbbs.util.Support;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 十大
 * Created by Jimmy on 15/12/12.
 */
public class TopTen {

    public static List<TopInfo> getTopTenInfo() {
        /* 链接Top10页面 */
        Document topTenDoc = Support.jsoupGet(Config.BASE_URL + Config.TOP_TEN_URL);

        /* 生成List */
        List<TopInfo> retList = new ArrayList<>();
        Elements tops = topTenDoc.getElementsByTag("top");
        Iterator<Element> topIter = tops.iterator();
        while (topIter.hasNext()) {
            Element eachTop = topIter.next();
            TopInfo e = new TopInfo();
            e.setBoard(eachTop.attr("board"));
            e.setCount(eachTop.attr("count"));
            e.setGid(eachTop.attr("gid"));
            e.setOwner(eachTop.attr("owner"));
            e.setTitle(eachTop.text());
            retList.add(e);
        }
        return retList;
    }
}
