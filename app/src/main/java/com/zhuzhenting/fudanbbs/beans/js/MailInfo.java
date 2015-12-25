package com.zhuzhenting.fudanbbs.beans.js;

import java.sql.Timestamp;

/**
 * Created by Jimmy on 15/12/8.
 */
public class MailInfo {
    /* 寄件人ID */
    private String from;
    /* 邮件ID */
    private String id;
    /* 发送时间 */
    private Timestamp date;
    /* 邮件标题 */
    private String title;
    /* 该邮件是信箱中的第几封 */
    private int count;
    /* 邮件内容 */
    private String content;
    /* 邮件状态 */
    private String status;

    public MailInfo() {

    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
