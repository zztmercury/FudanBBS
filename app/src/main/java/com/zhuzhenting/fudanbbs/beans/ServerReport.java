package com.zhuzhenting.fudanbbs.beans;

/**
 * Created by Jimmy on 15/10/26.
 */
public class ServerReport {
    /* 是否成功 */
    private boolean success;
    /* 返回信息 */
    private String info;

    public ServerReport() {
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "ServerReport{" +
                "success=" + success +
                ", info='" + info + '\'' +
                '}';
    }
}
