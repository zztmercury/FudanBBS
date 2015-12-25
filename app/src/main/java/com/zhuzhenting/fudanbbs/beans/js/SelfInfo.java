package com.zhuzhenting.fudanbbs.beans.js;

import java.sql.Timestamp;

/**
 * Created by Jimmy on 15/12/11.
 */
public class SelfInfo {
    /* 昵称 */
    private String nickname;
    /* 出生年、月、日 */
    private int year, month, day;
    /* 性别:“男” or “女” */
    private String gender;
    /* 登陆次数 */
    private int loginTimes;
    /* 发表文章数 */
    private int posts;
    /* 累计上站时间（分钟） */
    private int stayTime;
    /* 建号时间 */
    private Timestamp createTime;
    /* 最近一次登陆的时间 */
    private Timestamp recentLoginTime;
    /* 最近一次登陆的IP */
    private String latestIP;
    /* 说明档 */
    private String plan;
    /* 签名档 */
    private String signature;

    public SelfInfo() {

    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getLoginTimes() {
        return loginTimes;
    }

    public void setLoginTimes(int loginTimes) {
        this.loginTimes = loginTimes;
    }

    public int getPosts() {
        return posts;
    }

    public void setPosts(int posts) {
        this.posts = posts;
    }

    public int getStayTime() {
        return stayTime;
    }

    public void setStayTime(int stayTime) {
        this.stayTime = stayTime;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getRecentLoginTime() {
        return recentLoginTime;
    }

    public void setRecentLoginTime(Timestamp recentLoginTime) {
        this.recentLoginTime = recentLoginTime;
    }

    public String getLatestIP() {
        return latestIP;
    }

    public void setLatestIP(String latestIP) {
        this.latestIP = latestIP;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

}
