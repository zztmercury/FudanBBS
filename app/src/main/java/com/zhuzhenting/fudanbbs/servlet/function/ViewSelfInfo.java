package com.zhuzhenting.fudanbbs.servlet.function;

import com.zhuzhenting.fudanbbs.beans.ServerReport;
import com.zhuzhenting.fudanbbs.beans.js.SelfInfo;
import com.zhuzhenting.fudanbbs.util.Config;
import com.zhuzhenting.fudanbbs.util.Support;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.HashMap;
import java.util.Map;

/**
 * 个人信息相关操作
 * Created by snow on 15-12-13.
 */
public class ViewSelfInfo {

    public static SelfInfo viewSelf() {
        /* 存储个人信息 */
        Document getSelfInfoDoc = Support.jsoupGet(Config.BASE_URL + Config.INFO_URL);
        Element tempEle = getSelfInfoDoc.getElementsByTag("bbsinfo").get(0);

        SelfInfo selfInfo = new SelfInfo();
        selfInfo.setPosts(Integer.parseInt(tempEle.attr("post")));
        selfInfo.setLoginTimes(Integer.parseInt(tempEle.attr("login")));
        selfInfo.setStayTime(Integer.parseInt(tempEle.attr("stay")));
        String tempStr = tempEle.attr("since").replace("T", " ");
        selfInfo.setCreateTime(Support.Str2TS(tempStr));
        selfInfo.setLatestIP(tempEle.attr("host"));
        selfInfo.setYear(Integer.parseInt(tempEle.attr("year")));
        selfInfo.setMonth(Integer.parseInt(tempEle.attr("month")));
        selfInfo.setDay(Integer.parseInt(tempEle.attr("day")));
        selfInfo.setGender("M".equals(tempEle.attr("gender")) ? "男" : "女");
        tempStr = tempEle.attr("last").replace("T", " ");
        selfInfo.setRecentLoginTime(Support.Str2TS(tempStr));
        selfInfo.setNickname(tempEle.getElementsByTag("nick").get(0).text());

        /* 存储签名档、说明档 */
        tempEle = Support.jsoupGet(Config.BASE_URL + Config.PLAN_URL);
        selfInfo.setPlan(tempEle.getElementsByTag("text").get(0).text());
        tempEle = Support.jsoupGet(Config.BASE_URL + Config.SIG_URL);
        selfInfo.setSignature(tempEle.getElementsByTag("text").get(0).text());

        return selfInfo;
    }

    public static ServerReport editInfo(String nickName, int year, int month, int day,
                                        String gender, SelfInfo selfInfo) {
        /* 设置个人信息 */
        Map<String, String> data = new HashMap<>();
        data.put("nick", nickName);
        data.put("year", "" + year);
        data.put("month", "" + month);
        data.put("day", "" + day);
        data.put("gender", "男".equals(gender) ? "M" : "F");

        /* 发送更改请求 */
        Document editInfoDoc = Support.jsoupPost(Config.BASE_URL + Config.INFO_URL + "?type=1", data);

        System.out.println(editInfoDoc);

        /* 返回服务端信息 */
        String editInfoStr = editInfoDoc.toString();
        if (editInfoStr.indexOf("错误") >= 0) {
            int startPos = editInfoStr.indexOf("错误");
            ServerReport report = new ServerReport();
            String errorMsg = editInfoDoc.toString().substring(startPos, editInfoStr.indexOf('<', startPos));
            report.setInfo(errorMsg);
            report.setSuccess(false);
            return report;
        }
        selfInfo.setNickname(nickName);
        selfInfo.setYear(year);
        selfInfo.setMonth(month);
        selfInfo.setDay(day);
        selfInfo.setGender(gender);
        ServerReport report = new ServerReport();
        report.setInfo("");
        report.setSuccess(true);
        return report;
    }

    public static ServerReport editSig(String content, SelfInfo selfInfo) {
        /* 设置签名档 */
        Map<String, String> data = new HashMap<>();
        data.put("text", content);

        /* 发送更改请求 */
        Document editSigDoc = Support.jsoupPost(Config.BASE_URL + Config.SIG_URL, data);
        /* 返回服务端信息 */
        if (Config.ERROR_MSG.equals(editSigDoc.title())) {
            ServerReport report = new ServerReport();
            String errorMsg = editSigDoc.body().getElementsByTag("div").get(0).text();
            report.setInfo(errorMsg);
            report.setSuccess(false);
            return report;
        }

        selfInfo.setSignature(content);
        ServerReport report = new ServerReport();
        report.setInfo("");
        report.setSuccess(true);
        return report;
    }

    //TODO 有问题 中文支持
    public static ServerReport editPlan(String content, SelfInfo selfInfo) {
        /* 设置说明档 */
        Map<String, String> data = new HashMap<>();
        data.put("text", content);

        /* 发送更改请求 */
        Document editPlanDoc = Support.jsoupPost(Config.BASE_URL + Config.PLAN_URL, data);

        /* 返回服务端信息 */
        if (Config.ERROR_MSG.equals(editPlanDoc.title())) {
            ServerReport report = new ServerReport();
            String errorMsg = editPlanDoc.body().getElementsByTag("div").get(0).text();
            report.setInfo(errorMsg);
            report.setSuccess(false);
            return report;
        }
        selfInfo.setPlan(content);
        ServerReport report = new ServerReport();
        report.setInfo("");
        report.setSuccess(true);
        return report;
    }

    public static ServerReport changePassword(String oldPwd, String newPwd, String confirmPwd) {
        /* 设置待发送数据 */
        Map<String, String> data = new HashMap<>();
        data.put("pw1", oldPwd);
        data.put("pw2", newPwd);
        data.put("pw3", confirmPwd);

        /* 发送更改请求 */
        Document editPwdDoc = Support.jsoupPost(Config.BASE_URL + Config.PWD_URL, data);

        /* 返回服务端信息 */
        if (Config.ERROR_MSG.equals(editPwdDoc.title())) {
            ServerReport report = new ServerReport();
            String errorMsg = editPwdDoc.body().getElementsByTag("div").get(0).text();
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
