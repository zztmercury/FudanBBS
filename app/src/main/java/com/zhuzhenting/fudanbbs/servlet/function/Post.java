package com.zhuzhenting.fudanbbs.servlet.function;

import com.zhuzhenting.fudanbbs.beans.ServerReport;
import com.zhuzhenting.fudanbbs.beans.fjjyy.BoaInfo;
import com.zhuzhenting.fudanbbs.beans.fjjyy.BrdInfo;
import com.zhuzhenting.fudanbbs.beans.fjjyy.PostInfo;
import com.zhuzhenting.fudanbbs.beans.fjjyy.RConInfo;
import com.zhuzhenting.fudanbbs.beans.fjjyy.TConInfo;
import com.zhuzhenting.fudanbbs.util.Config;
import com.zhuzhenting.fudanbbs.util.Support;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 帖子,版块相关操作
 * Created by snow on 15-12-14.
 */
public class Post {

    /**
     * TODO 主题贴被移除,使用回复贴格式:gid,reid,quote为空,isLast默认为true(待定) bid、title、content必填
     * 发表回复贴
     * 输入回复贴信息，其中bid、reid、content必填
     * 返回success=true/false
     * isReply:true-为回复贴
     */
    public static ServerReport send(RConInfo rCon, Boolean isReply) {
        String title;
        String quote;
        ServerReport report = new ServerReport();
        if (isReply) {
            Document rconDoc = Support.jsoupGet(Config.BASE_URL + Config.BID_URL + rCon.getBid() +
                    (isReply ? ("&f=" + rCon.getReid()) : ""));
//        System.out.println(rconDoc);
            Element post = rconDoc.getElementsByTag("po").first();
            //建议把title用作展示，可由用户修改
            String reTitle = post.getElementsByTag("title").first().text();
            title = reTitle.startsWith("Re:") ? "Re: " + reTitle.substring(4) : "Re: " + reTitle;
            //建议把quote用作展示，可由用户修改
            String reOwner = post.getElementsByTag("owner").first().text();
            quote = "【 在 " + reOwner + " 的大作中提到: 】\n";
            Elements ps = post.getElementsByTag("pa");
            Iterator<Element> psIter = ps.iterator();
            while (psIter.hasNext()) {
                Element eachPs = psIter.next();
                if (eachPs.attr("m").equals("t")) {
                    String p = "";
                    Elements pss = eachPs.getElementsByTag("p");
                    Iterator<Element> pssIter = pss.iterator();
                    while (pssIter.hasNext()) {
                        Element eachP = pssIter.next();
                        p += ": " + eachP.text() + "\n";
                    }
                    quote += p;
                }
            }
        } else {
            title = rCon.getTitle();
            quote = "";
        }
//        System.out.printf("%s\n%s\n%s\n", title, rCon.getContent(), quote);
        try {
            Connection.Response res = Jsoup.connect(Config.BASE_URL + "bbs/snd?bid=" + rCon.getBid()
                    + "&f=" + rCon.getReid() + "&utf8=1")
                    .data("title", title,
                            "sig", "1",
                            "text", rCon.getContent() + "\n" + quote)
                    .cookies(Support.getInstance().getCookie())
                    .method(Connection.Method.POST)
                    .execute();
            Document resDoc = res.parse();
            //To Do
            System.out.println(resDoc);
            report.setInfo("");
            report.setSuccess(true);
            return report;
        } catch (Exception e) {
            report.setInfo("");
            report.setSuccess(false);
            return report;
        }

    }

    /**
     * 查看分类讨论页面/版面目录页面
     * identifier=0 则 input 为分类讨论编号（[0-9AB]）
     * identifier=1 则 input 为版面目录名称
     * 返回该分类/目录下所有版面信息列表
     */
    public static List<BoaInfo> getBoaInfo(String input, int identifier) {
        Document boaDoc = null;
        if (identifier == 0) {
            boaDoc = Support.jsoupGet(Config.BASE_URL + Config.BOA_ID_URL + input);
        } else if (identifier == 1) {
            boaDoc = Support.jsoupGet(Config.BASE_URL + Config.BOA_NAME_URL + input);
        }
//        System.out.println(boaDoc);
        List<BoaInfo> retList = new ArrayList<>();
        Elements brds = boaDoc.getElementsByTag("brd");
        Iterator<Element> brdIter = brds.iterator();
        while (brdIter.hasNext()) {
            Element eachBrd = brdIter.next();
            BoaInfo b = new BoaInfo();
            b.setDir(eachBrd.attr("dir"));
            b.setTitle(eachBrd.attr("title"));
            b.setCate(b.getDir().equals("1") ? "[目录]" : eachBrd.attr("cate"));
            b.setDesc(eachBrd.attr("desc"));
            b.setBm(b.getDir().equals("1") ? "-" : eachBrd.attr("bm"));
            //read='0'表示没有未读贴，read='1'表示有未读贴
            b.setIsRead(eachBrd.attr("read").equals("0"));
            b.setCount(eachBrd.attr("count"));
            retList.add(b);
        }
        return retList;
    }

    /**
     * 查看版面
     * identifier=0 则 input 为版面名称
     * identifier=1 则 input 为版面ID
     * start:该页面首贴序号
     * mode=0 则为传统模式
     * mode=1 则为主题模式
     * 返回版面信息
     */
    public static BrdInfo getBrdInfo(String input, int identifier, int start, int mode) {
        String brd_start_url = "";
        if (start > 0)  //start <= 0时进入默认页面
            brd_start_url = "&start=" + start;
        Document brdDoc = Support.jsoupGet(Config.BASE_URL +
                (mode == 0 ? Config.MOD_TRA_URL : Config.MOD_THEME_URL) + //模式:传统/主题
                (identifier == 0 ? "?board=" : "?bid=") + input + //输入为版面名称(0) 输入为版面Id(1)
                brd_start_url);
//        System.out.println(brdDoc);
        BrdInfo retBoard = new BrdInfo();
        List<PostInfo> postList = new ArrayList<>();

        Elements posts = brdDoc.getElementsByTag("po");
        Iterator<Element> postIter = posts.iterator();
        Element brd = brdDoc.getElementsByTag("brd").first();

        retBoard.setBid(brd.attr("bid"));
        retBoard.setStart(Integer.parseInt(brd.attr("start")));
        retBoard.setTotal(Integer.parseInt(brd.attr("total")));

        while (postIter.hasNext()) {
            Element eachPost = postIter.next();
            PostInfo b = new PostInfo();
            b.setNore(!eachPost.attr("nore").equals("1"));
            b.setM(eachPost.attr("m"));
            b.setOwner(eachPost.attr("owner"));
            b.setTime(eachPost.attr("time"));
            b.setId(eachPost.attr("id"));
            b.setTitle(eachPost.text());
            b.setIsSticky(eachPost.attr("sticky").equals("1"));
            postList.add(b);
        }
        retBoard.setPosts(postList);

        return retBoard;
    }

    /**
     * 查看回复贴/主题贴
     * bid:版面ID     fid:回复贴ID
     * 返回回复贴/主题贴信息
     */
    public static RConInfo getRConInfo(String bid, String fid) {
        Document rconDoc = Support.jsoupGet(Config.BASE_URL + Config.BID_URL + bid +
                "&f=" + fid);
//        System.out.println(rconDoc);
        Element post = rconDoc.getElementsByTag("po").first();
        RConInfo m = new RConInfo();
        m.setFid(post.attr("fid"));
        m.setReid(post.attr("reid"));
        m.setGid(post.attr("gid"));
        m.setIsLast("1".equals(post.attr("tlast")));
        m.setOwner(post.getElementsByTag("owner").first().text());
        m.setNick(post.getElementsByTag("nick").first().text());
        m.setBoard(post.getElementsByTag("board").first().text());
        m.setBid(rconDoc.getElementsByTag("bbscon").first().attr("bid"));
        m.setTitle(post.getElementsByTag("title").first().text());
        m.setDate(post.getElementsByTag("date").first().text());

        Elements ps = post.getElementsByTag("pa");
        Iterator<Element> psIter = ps.iterator();
        String pt = "";
        while (psIter.hasNext()) {
            Element eachPs = psIter.next();
            if (eachPs.attr("m").equals("t")) {
                Elements pss = eachPs.getElementsByTag("p");
                Iterator<Element> pssIter = pss.iterator();
                while (pssIter.hasNext()) {
                    Element eachP = pssIter.next();
                    pt += eachP.getElementsByTag("a");
                    pt += eachP.text() + "\n";
                }
            } else if (eachPs.attr("m").equals("q")) {
                String p = "";
                Elements pss = eachPs.getElementsByTag("p");
                Iterator<Element> pssIter = pss.iterator();
                while (pssIter.hasNext()) {
                    Element eachP = pssIter.next();
                    p += eachP.getElementsByTag("a");
                    p += eachP.text() + "\n";
                }
                m.setQuote(p);
            }
        }
        m.setContent(pt);
        //m.setContent(ps.attr("m"));
        return m;
    }

    /**
     * 查看该主题下所有贴子
     * board:版面名称   gid:主题帖ID
     * 输入 fid=本页最后一贴的id & a=n 则到达后一页 返回TConInfo.MainConInfo.fid为主题贴ID,
     * TConInfo.List<RConInfo>为回复贴List
     * 输入 fid=本页第一贴的id & a=p 则到达前一页 返回值同上
     * 输入 fid=0 (a随意) 则跳转到该主题贴首页 返回TConInfo.MainConInfo为主题贴, TConInfo.List<RConInfo>为回复贴List
     */
    public static TConInfo getTConInfo(String board, String gid, String fid, String a) {
        String theme_url = "";
        try {
            int bid = Integer.parseInt(board);
            theme_url = Config.BID_URL + bid;
        } catch (Exception e) {
            theme_url = Config.BOARD_URL + board;
        }
        if ("0".equals(fid)) {
            theme_url += "&f=" + gid;
        } else {
            theme_url += "&g=" + gid + "&f=" + fid + "&a=" + a;
        }

        Document TConDoc = Support.jsoupGet(Config.BASE_URL + theme_url);
//        System.out.println(TConDoc);
        TConInfo result = new TConInfo();
        List<RConInfo> retList = new ArrayList<>();
        Elements posts = TConDoc.getElementsByTag("po");
        Iterator<Element> postIter = posts.iterator();

        //回复
        while (postIter.hasNext()) {
            Element eachPost = postIter.next();
            RConInfo m = new RConInfo();
            m.setFid(eachPost.attr("fid"));
            m.setReid(eachPost.attr("reid"));
            m.setGid(eachPost.attr("gid"));
            m.setIsLast("1".equals(eachPost.attr("tlast")));
            m.setOwner(eachPost.getElementsByTag("owner").first().text());
            m.setNick(eachPost.getElementsByTag("nick").first().text());
            m.setBoard(eachPost.getElementsByTag("board").first().text());
            m.setTitle(eachPost.getElementsByTag("title").first().text());
            m.setDate(eachPost.getElementsByTag("date").first().text());

            Elements psT = eachPost.getElementsByTag("pa");
            Iterator<Element> psIter = psT.iterator();
            String pt = "";
            while (psIter.hasNext()) {
                Element eachPs = psIter.next();
                if (eachPs.attr("m").equals("t")) {
                    Elements pssT = eachPs.getElementsByTag("p");
                    Iterator<Element> pssTIter = pssT.iterator();
                    while (pssTIter.hasNext()) {
                        Element eachP = pssTIter.next();
                        pt += eachP.getElementsByTag("a");
                        pt += eachP.text() + "\n";
                    }
                } else if (eachPs.attr("m").equals("q")) {
                    String pp = "";
                    Elements pssT = eachPs.getElementsByTag("p");
                    Iterator<Element> pssTIter = pssT.iterator();
                    while (pssTIter.hasNext()) {
                        Element eachP = pssTIter.next();
                        pp += eachP.getElementsByTag("a");
                        pp += eachP.text() + "\n";
                    }
                    m.setQuote(pp);
                }

            }
            m.setContent(pt);
            retList.add(m);
        }
        result.setrList(retList);
        return result;

    }

    //删除帖子
    public static ServerReport postDelete(String bid, String fid) {
        ServerReport report = new ServerReport();
        Support.jsoupGet(Config.BASE_URL + Config.BID_URL + bid + "&f=" + fid);

        try {
            //del?bid=205&f=3041001245159457162
            Connection.Response res = Jsoup.connect(Config.BASE_URL + Config.DEL_URL + bid + "&f=" + fid)
                    .cookies(Support.getInstance().getCookie())
                    .method(Connection.Method.POST)
                    .execute();
            Document resDoc = res.parse();
            //ToDo
            System.out.println(resDoc);
            report.setInfo("");
            report.setSuccess(true);
            return report;
        } catch (Exception e) {
            report.setInfo("删除出错");
            report.setSuccess(false);
            return report;
        }
    }
}
