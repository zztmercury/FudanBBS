package com.zhuzhenting.fudanbbs.beans.fjjyy;

import java.util.List;

/**
 * Created by 越 on 2015/12/10.
 */

/**
 * 版面信息
 * posts: 该页面所展示的贴子列表
 * bid: 版面ID
 * total: 该版面总共贴数
 * start: 当前页面首贴序号
 */
public class BrdInfo {
    private List<PostInfo> posts;
    private String bid;
    private int total, start;

    public BrdInfo() {
    }

    public List<PostInfo> getPosts() {
        return posts;
    }

    public void setPosts(List<PostInfo> posts) {
        this.posts = posts;
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }
}
