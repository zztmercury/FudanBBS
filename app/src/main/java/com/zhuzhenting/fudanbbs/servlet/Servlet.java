package com.zhuzhenting.fudanbbs.servlet;

import com.zhuzhenting.fudanbbs.beans.ServerReport;
import com.zhuzhenting.fudanbbs.beans.UQueryResult;
import com.zhuzhenting.fudanbbs.beans.fjjyy.BoaInfo;
import com.zhuzhenting.fudanbbs.beans.fjjyy.BrdInfo;
import com.zhuzhenting.fudanbbs.beans.fjjyy.PostInfo;
import com.zhuzhenting.fudanbbs.beans.fjjyy.RConInfo;
import com.zhuzhenting.fudanbbs.beans.fjjyy.TConInfo;
import com.zhuzhenting.fudanbbs.beans.js.FavSec;
import com.zhuzhenting.fudanbbs.beans.js.FriendInfo;
import com.zhuzhenting.fudanbbs.beans.js.MailInfo;
import com.zhuzhenting.fudanbbs.beans.js.SelfInfo;
import com.zhuzhenting.fudanbbs.beans.js.TopInfo;
import com.zhuzhenting.fudanbbs.servlet.function.Fav;
import com.zhuzhenting.fudanbbs.servlet.function.Find;
import com.zhuzhenting.fudanbbs.servlet.function.Friend;
import com.zhuzhenting.fudanbbs.servlet.function.Log;
import com.zhuzhenting.fudanbbs.servlet.function.Mail;
import com.zhuzhenting.fudanbbs.servlet.function.Post;
import com.zhuzhenting.fudanbbs.servlet.function.TopTen;
import com.zhuzhenting.fudanbbs.servlet.function.ViewSelfInfo;

import java.util.List;


/**
 * Created by snow on 15-12-13.
 */
public class Servlet {
    //js
    public static ServerReport login(String username, String password) {
        return Log.login(username, password);
    }

    public static ServerReport logout() {
        return Log.logout();
    }

    public static List<TopInfo> getTopTenInfo() {
        return TopTen.getTopTenInfo();
    }

    public static SelfInfo viewSelf() {
        return ViewSelfInfo.viewSelf();
    }

    public static ServerReport editInfo(String nickName, int year, int month, int day, String gender,
                                        SelfInfo selfInfo) {
        return ViewSelfInfo.editInfo(nickName, year, month, day, gender, selfInfo);
    }

    public static ServerReport editSig(String content, SelfInfo selfInfo) {
        return ViewSelfInfo.editSig(content, selfInfo);
    }

    public static ServerReport editPlan(String content, SelfInfo selfInfo) {
        return ViewSelfInfo.editPlan(content, selfInfo);
    }

    public static ServerReport changePassword(String oldPwd, String newPwd, String confirmPwd) {
        return ViewSelfInfo.changePassword(oldPwd, newPwd, confirmPwd);
    }

    public static ServerReport addFriend(String id, String desc, List<FriendInfo> oldList) {
        return Friend.addFriend(id, desc, oldList);
    }

    public static ServerReport deleteFriend(String friendId, List<FriendInfo> oldList) {
        return Friend.deleteFriend(friendId, oldList);
    }

    public static List<FriendInfo> viewFriend() {
        return Friend.viewFriend();
    }

    public static List<MailInfo> getNewMailInfo() {
        return Mail.getNewMailInfo();
    }

    public static List<MailInfo> getAllMailInfo() {
        return Mail.getAllMailInfo();
    }

    public static ServerReport viewMail(MailInfo mailInfo) {
        return Mail.viewMail(mailInfo);
    }

    public static ServerReport sendMail(String to, String title, boolean sentToMyself, String content) {
        return Mail.sendMail(to, title, sentToMyself, content);
    }

    public static ServerReport replyMail(MailInfo mailInfo, boolean sentToMyself, String content) {
        return Mail.replyMail(mailInfo, sentToMyself, content);
    }

    public static ServerReport deleteMail(MailInfo mailInfo) {
        return Mail.deleteMail(mailInfo);
    }

    //fjjyy
    public static ServerReport sendPost(RConInfo rCon) {
        return Post.send(rCon, false);
    }

    public static ServerReport sendReply(RConInfo rCon) {
        return Post.send(rCon, true);
    }

    public static List<BoaInfo> getClassifyInfo(String input) {
        return Post.getBoaInfo(input, 0);
    }

    public static List<BoaInfo> getLayoutInfo(String input) {
        return Post.getBoaInfo(input, 1);
    }

    public static BrdInfo getBrdInfo(String input, int identifier, int start, int mode) {
        return Post.getBrdInfo(input, identifier, start, mode);
    }

    public static RConInfo getRConInfo(String bid, String fid) {
        return Post.getRConInfo(bid, fid);
    }

    public static TConInfo getTConInfo(String board, String gid, String fid, String a) {
        return Post.getTConInfo(board, gid, fid, a);
    }

    public static ServerReport postDelete(String bid, String fid) {
        return Post.postDelete(bid, fid);
    }

    //jyt
    public static UQueryResult findUser(String u) {
        return Find.findUser(u);
    }

    public static List<PostInfo> findThread(String bid, String t1, String t2, String t3,
                                            String user, String limit, String mark, String nore) {
        return Find.findThread(bid, t1, t2, t3, user, limit, mark, nore);
    }

    public static ServerReport addFav(String bid) {
        return Fav.addFav(bid);
    }

    public static ServerReport delFav(String bid, List<FavSec> oldList) {
        return Fav.delFav(bid, oldList);
    }

    public static List<FavSec> getFavSec() {
        return Fav.getFavSec();
    }
}
