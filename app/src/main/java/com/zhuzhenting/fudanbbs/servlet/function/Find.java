package com.zhuzhenting.fudanbbs.servlet.function;

import com.zhuzhenting.fudanbbs.beans.UQueryResult;
import com.zhuzhenting.fudanbbs.beans.fjjyy.PostInfo;
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
 * 搜索相关操作
 * Created by snow on 15-12-14.
 */
public class Find {
    public static UQueryResult findUser(String u) {
        Map<String, String> data = new HashMap<String, String>();
        data.put("u", u);

        Document result = Support.jsoupGet(Config.BASE_URL + Config.FIND_URL, data);

        System.out.println(result);

        Elements e = result.select("bbsqry");
        UQueryResult r = new UQueryResult();
        if (!e.hasAttr("login")) {//没有这个用户
            r.setSuccess(0);
            return r;
        } else {
            e = result.select("ident");
            r.setIdent(e.text());
            e = result.select("nick");
            r.setNick(e.text());
            e = result.select("ip");
            r.setIp(e.text());
            e = result.select("smd");
            r.setSmd(e.text());
            e = result.select("logout");
            r.setLogout(e.text());
            e = result.select("bbsqry");
            r.setId(e.attr("id"));
            r.setLogin(e.attr("login"));
            r.setLastlogin(e.attr("lastlogin"));
            r.setPerf(e.attr("perf"));
            r.setPost(e.attr("post"));
            r.setHp(e.attr("hp"));
            r.setLevel(e.attr("level"));
            r.setRepeat(e.attr("repeat"));
            r.setContrib(e.attr("contrib"));
            r.setRank(e.attr("rank"));
            r.setHoro(e.attr("horo"));
            r.setGender(e.attr("gender"));
            r.setSuccess(1);
            return r;
        }
    }

    public static List<PostInfo> findThread(String bid, String t1, String t2, String t3,
                                            String user, String limit, String mark, String nore) {
        Map<String, String> data = new HashMap<String, String>();
        data.put("bid", bid);
        data.put("t1", t1);
        data.put("t2", t2);
        data.put("t3", t3);
        data.put("user", user);
        data.put("limit", limit);
        data.put("mark", mark);
        data.put("nore", nore);
        //limit:7-30 但是其实是可以超过30的  mark/nore:checked="on"

        Document result = Support.jsoupGet(Config.BASE_URL + Config.SEARCH_URL, data);

        System.out.println(result);

        List<PostInfo> retList = new ArrayList<PostInfo>();
        //获取各条信息
        Elements ps = result.getElementsByTag("po");
        Iterator<Element> pIter = ps.iterator();
        while (pIter.hasNext()) {
            Element each = pIter.next();
            PostInfo e = new PostInfo();
            e.setM(each.attr("M"));
            String tn = each.attr("nore");
            if (tn == "0") e.setNore(true);
            if (tn == "1") e.setNore(false);//是在下输了
            e.setTime(each.attr("time"));
            e.setId(each.attr("id"));
            e.setTitle(each.text());
            retList.add(e);//存入list里
        }
        return retList;
    }
}
