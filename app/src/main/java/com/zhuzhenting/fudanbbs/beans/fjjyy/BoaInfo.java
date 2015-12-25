package com.zhuzhenting.fudanbbs.beans.fjjyy;

/**
 * Created by 越 on 2015/12/5.
 */

/**
 * 分类讨论/版面目录信息
 * dir: =1表示是目录；=0表示版面
 * title: 讨论区名称，目录的title前后有中括号
 * cate: 类别，目录的cate为"[目录]"
 * desc: 中文描述
 * bm: 版主，多个版主用" "分隔
 * count: 文章数
 * isRead: =true表示该版面所有文章已读；=false表示该版面有未读文章
 */
public class BoaInfo {
    private String dir, title, cate, desc, bm, count;
    private boolean isRead;

    public BoaInfo() {
    }

    public boolean isRead() {
        return isRead;
    }

    public void setIsRead(boolean isRead) {
        this.isRead = isRead;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCate() {
        return cate;
    }

    public void setCate(String cate) {
        this.cate = cate;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getBm() {
        return bm;
    }

    public void setBm(String bm) {
        this.bm = bm;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
