package com.zhuzhenting.fudanbbs.beans.fjjyy;

/**
 * Created by 越 on 2015/12/7.
 * 具体版面中的贴子列表
 */

/**
 * 版面所展示的贴子信息
 * m: 标记（大写字母或"+"表示未读，小写字母或" "表示已读）
 * owner: 作者
 * time: 时间
 * id: 贴子id
 * title: 贴子标题
 * nore: 是否不允许回复（=1可回复设false，=0不可回复设true）
 * isSticky: 是否置顶
 */
public class PostInfo {
    private String m, owner, time, id, title;
    private boolean isSticky, nore;

    public PostInfo() {
    }

    public boolean isNore() {
        return nore;
    }

    public void setNore(boolean nore) {
        this.nore = nore;
    }

    public boolean isSticky() {
        return isSticky;

    }

    public void setIsSticky(boolean isSticky) {
        this.isSticky = isSticky;
    }

    public String getM() {
        return m;
    }

    public void setM(String m) {
        this.m = m;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
