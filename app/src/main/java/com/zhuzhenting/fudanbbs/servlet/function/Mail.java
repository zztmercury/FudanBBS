package com.zhuzhenting.fudanbbs.servlet.function;

import com.zhuzhenting.fudanbbs.beans.ServerReport;
import com.zhuzhenting.fudanbbs.beans.js.MailInfo;
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
 * 邮件相关操作
 * Created by snow on 15-12-14.
 */
public class Mail {
    public static List<MailInfo> getNewMailInfo() {
        /* 链接‘阅览新信’页面 */
        Document newMailDoc = Support.jsoupGet(Config.BASE_URL + Config.NEWMAIL_URL);

        /* 生成List */
        List<MailInfo> retList = new ArrayList<>();
        Elements newMails = newMailDoc.getElementsByTag("mail");
        Iterator<Element> mailIter = newMails.iterator();
        while (mailIter.hasNext()) {
            Element eachNewMail = mailIter.next();
            MailInfo e = new MailInfo();
            String formattedDate = eachNewMail.attr("date").replace('T', ' ');
            // System.out.println(formattedDate);
            e.setDate(Support.Str2TS(formattedDate));
            e.setFrom(eachNewMail.attr("from"));
            e.setId(eachNewMail.attr("name"));
            e.setTitle(eachNewMail.text());
            e.setCount(Integer.parseInt(eachNewMail.attr("n")));
            e.setStatus(eachNewMail.attr("m"));
            retList.add(e);
        }
        return retList;
    }

    public static List<MailInfo> getAllMailInfo() {
        /* 链接‘所有信件’页面 */
        Document allMailDoc = Support.jsoupGet(Config.BASE_URL + Config.ALLMAIL_URL);

        /* 生成List */
        List<MailInfo> retList = new ArrayList<>();
        Elements allMails = allMailDoc.getElementsByTag("mail");
        Iterator<Element> mailIter = allMails.iterator();
        Element bbsmailInfo = allMailDoc.getElementsByTag("bbsmail").get(0);
        /* 前三个分别是总邮件数，每页最多显示条数，本页开始邮件编号,最后那个用来赋值邮件编号的 */
        int total = Integer.parseInt(bbsmailInfo.attr("total")),
                dpage = Integer.parseInt(bbsmailInfo.attr("dpage")),
                start = total - dpage + 1,
                count = total;
        while (mailIter.hasNext()) {
            Element eachAllMail = mailIter.next();
            MailInfo e = new MailInfo();
            String formattedDate = eachAllMail.attr("date").replace('T', ' ');
            // System.out.println(formattedDate);
            e.setDate(Support.Str2TS(formattedDate));
            e.setFrom(eachAllMail.attr("from"));
            e.setId(eachAllMail.attr("name"));
            e.setTitle(eachAllMail.text());
            e.setStatus(eachAllMail.attr("m"));
            e.setCount(count--);
            retList.add(e);
        }

        /* 邮件可能不止一页 */
        while (start > 0) {
            if (start < dpage) {
                Map<String, String> data = new HashMap<>();
                data.put("start", "1");
                data.put("page", String.valueOf(start - 1));
                allMailDoc = Support.jsoupGet(Config.BASE_URL + Config.ALLMAIL_URL, data);
                allMails = allMailDoc.getElementsByTag("mail");
                mailIter = allMails.iterator();
            } else {
                Map<String, String> data = new HashMap<>();
                data.put("start", String.valueOf(start - dpage));
                data.put("page", String.valueOf(dpage));
                allMailDoc = Support.jsoupGet(Config.BASE_URL + Config.ALLMAIL_URL, data);
                allMails = allMailDoc.getElementsByTag("mail");
                mailIter = allMails.iterator();
            }
            while (mailIter.hasNext()) {
                Element eachAllMail = mailIter.next();
                MailInfo e = new MailInfo();
                String formattedDate = eachAllMail.attr("date").replace('T', ' ');
                // System.out.println(formattedDate);
                e.setDate(Support.Str2TS(formattedDate));
                e.setFrom(eachAllMail.attr("from"));
                e.setId(eachAllMail.attr("name"));
                e.setTitle(eachAllMail.text());
                e.setStatus(eachAllMail.attr("m"));
                e.setCount(count--);
                retList.add(e);
            }
            start -= dpage;
        }
        return retList;
    }

    //TODO 需要进一步解析返回值里的content
    public static ServerReport viewMail(MailInfo mailInfo) {
        /* 链接‘信件内容’页面 */
        Map<String, String> data = new HashMap<>();
        data.put("f", mailInfo.getId());
        data.put("n", String.valueOf(mailInfo.getCount()));
        Document viewMailDoc = Support.jsoupGet(Config.BASE_URL + Config.VIEWMAIL_URL, data);

        /* 返回服务端信息 */
        if (Config.ERROR_MSG.equals(viewMailDoc.title())) {
            ServerReport report = new ServerReport();
            String errorMsg = viewMailDoc.body().getElementsByTag("div").get(0).text();
            report.setInfo(errorMsg);
            report.setSuccess(false);
            return report;
        }
        ServerReport report = new ServerReport();
        report.setInfo("");
        report.setSuccess(true);
        mailInfo.setContent(viewMailDoc.getElementsByTag("mail").get(0).text());
        return report;
    }

    public static ServerReport sendMail(String to, String title, boolean sentToMyself, String content) {
        /* 设置相关发送信息 */
        Map<String, String> data = new HashMap<>();
        data.put("ref", "/bbs/mail");
        data.put("recv", to);
        data.put("title", title);
        data.put("backup", sentToMyself ? "checked" : "");
        data.put("text", content);

        /* 发送邮件 */
        Document sendMailDoc = Support.jsoupPost(Config.BASE_URL + Config.SENDMAIL_URL, data);

        /* 返回服务端信息 */
        if (Config.ERROR_MSG.equals(sendMailDoc.title())) {
            ServerReport report = new ServerReport();
            String errorMsg = sendMailDoc.body().getElementsByTag("div").get(0).text();
            report.setInfo(errorMsg);
            report.setSuccess(false);
            return report;
        }
        ServerReport report = new ServerReport();
        report.setInfo("");
        report.setSuccess(true);
        return report;
        // System.out.println(sendMailDoc.toString());
    }

    public static ServerReport replyMail(MailInfo mailInfo, boolean sentToMyself, String content) {
        /* 设置发送信息 */
        Map<String, String> data = new HashMap<>();
        data.put("ref", "/bbs/mailcon?f=" + mailInfo.getId() + "&n=" + mailInfo.getCount());
        data.put("recv", mailInfo.getFrom());
        data.put("title", "Re: " + mailInfo.getTitle());
        data.put("backup", sentToMyself ? "checked" : "");
        data.put("text", content);

        /* 回复邮件 */
        Document replyMailDoc = Support.jsoupPost(Config.BASE_URL + Config.SENDMAIL_URL, data);

        /* 返回服务端信息 */
        if (Config.ERROR_MSG.equals(replyMailDoc.title())) {
            ServerReport report = new ServerReport();
            String errorMsg = replyMailDoc.body().getElementsByTag("div").get(0).text();
            report.setInfo(errorMsg);
            report.setSuccess(false);
            return report;
        }
        ServerReport report = new ServerReport();
        report.setInfo("");
        report.setSuccess(true);
        return report;
    }

    public static ServerReport deleteMail(MailInfo mailInfo) {
        Map<String, String> data = new HashMap<>();
        data.put("f", mailInfo.getId());

        /* 删除邮件 */
        Document deleteMailDoc = Support.jsoupGet(Config.BASE_URL + Config.DELETEMAIL_URL, data);

        /* 返回服务端信息 */
        if (Config.ERROR_MSG.equals(deleteMailDoc.title())) {
            ServerReport report = new ServerReport();
            String errorMsg = deleteMailDoc.body().getElementsByTag("div").get(0).text();
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
