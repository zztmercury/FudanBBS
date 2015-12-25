package com.zhuzhenting.fudanbbs.beans.fjjyy;

/**
 * Created by 越 on 2015/12/11.
 */

/**
 * 回复贴信息
 * fid: 回复贴ID
 * reid: 被回复贴ID
 * gid: 所属主题贴ID
 * owner: 作者
 * nick: 作者昵称
 * board: 所属版面名称
 * bid: 所属版面ID
 * title: 回复贴标题（以Re:开头）
 * date: 发表时间
 * content: 贴子内容
 * quote: 引用内容
 * isLast: 是否为该主题下最后一贴
 */
public class RConInfo {
    private String fid, reid, gid, owner, nick, board, bid, title, date, content, quote;
    private boolean isLast;

    public RConInfo() {
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getReid() {
        return reid;
    }

    public void setReid(String reid) {
        this.reid = reid;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getBoard() {
        return board;
    }

    public void setBoard(String board) {
        this.board = board;
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public boolean isLast() {
        return isLast;
    }

    public void setIsLast(boolean isLast) {
        this.isLast = isLast;
    }
}
