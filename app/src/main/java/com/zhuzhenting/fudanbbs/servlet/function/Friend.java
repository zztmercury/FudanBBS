package com.zhuzhenting.fudanbbs.servlet.function;

import com.zhuzhenting.fudanbbs.beans.ServerReport;
import com.zhuzhenting.fudanbbs.beans.js.FriendInfo;
import com.zhuzhenting.fudanbbs.util.Config;
import com.zhuzhenting.fudanbbs.util.Support;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 好友相关操作
 * Created by snow on 15-12-13.
 */
public class Friend {
    public static ServerReport addFriend(String id, String desc, List<FriendInfo> oldList) {
        /* 添加好友信息 */
        Map<String, String> data = new HashMap<>();
        data.put("id", id);
        data.put("desc", desc);

        /* 发送请求 */
        Document addFriendDoc = Support.jsoupGet(Config.BASE_URL + Config.ADDFRIEND_URL, data);

        /* 返回服务端信息 */
        if (Config.ERROR_MSG.equals(addFriendDoc.title())) {
            ServerReport report = new ServerReport();
            String errorMsg = addFriendDoc.body().getElementsByTag("div").get(0).text();
            report.setInfo(errorMsg);
            report.setSuccess(false);
            return report;
        }

        /* 查看是否添加上了该好友 */
        Elements eles = addFriendDoc.getElementsByTag("ov");
        for (int i = 0; i < eles.size(); i++) {
            String eachId = eles.get(i).attr("id"), eachDesc = eles.get(i).text();
            if (eachId.equals(id) && eachDesc.equals(desc)) {
                FriendInfo friend = new FriendInfo();
                friend.setId(id);
                friend.setDesc(desc);
                oldList.add(i, friend);
                ServerReport report = new ServerReport();
                report.setInfo("");
                report.setSuccess(true);
                return report;
            }
        }
        /* 添加失败 */
        ServerReport report = new ServerReport();
        report.setInfo("好友添加失败");
        report.setSuccess(false);
        return report;
    }

    public static ServerReport deleteFriend(String id, List<FriendInfo> oldList) {
        /* 删除好友信息 */
        Map<String, String> data = new HashMap<>();
        data.put("u", id);

        /* 发送请求 */
        Document delFriendDoc = Support.jsoupGet(Config.BASE_URL + Config.DELFRIEND_URL, data);

        /* 返回服务端信息 */
        if (Config.ERROR_MSG.equals(delFriendDoc.title())) {
            ServerReport report = new ServerReport();
            String errorMsg = delFriendDoc.body().getElementsByTag("div").get(0).text();
            report.setInfo(errorMsg);
            report.setSuccess(false);
            return report;
        }

        /* 查看是否删除掉了该好友 */
        boolean deleted = true;
        Elements eles = delFriendDoc.getElementsByTag("ov");
        for (int i = 0; i < eles.size(); i++) {
            String eachId = eles.get(i).attr("id");
            if (eachId.equals(id)) {
                deleted = false;
                break;
            }
        }

        /* 删除成功 */
        if (deleted) {
            for (int i = 0; i < oldList.size(); i++)
                if (oldList.get(i).getId().equals(id)) {
                    oldList.remove(i);
                    break;
                }
            ServerReport report = new ServerReport();
            report.setInfo("");
            report.setSuccess(true);
            return report;
        }
        /* 删除失败 */
        else {
            ServerReport report = new ServerReport();
            report.setInfo("好友删除失败");
            report.setSuccess(false);
            return report;
        }
    }

    public static List<FriendInfo> viewFriend() {
        /* 查询好友 */
        Document viewFriendDoc = Support.jsoupGet(Config.BASE_URL + Config.ALLFRIEND_URL);

        /* 好友情况 */
        List<FriendInfo> friendList = new ArrayList<>();

        for (Element ele : viewFriendDoc.getElementsByTag("ov")) {
            FriendInfo friend = new FriendInfo();
            friend.setDesc(ele.text());
            friend.setId(ele.attr("id"));
            friendList.add(friend);
        }
        return friendList;
    }

}
