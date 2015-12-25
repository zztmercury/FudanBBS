package com.zhuzhenting.fudanbbs.beans.js;

/**
 * Created by Jimmy on 15/11/2.
 */
public class TopInfo {
    /* 板块，发布人，投票数，帖子ID，帖子标题 */
    private String board, owner, count, gid, title;

    public TopInfo() {

    }

    public String getBoard() {
        return board;
    }

    public void setBoard(String board) {
        this.board = board;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
