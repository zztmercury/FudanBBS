package com.zhuzhenting.fudanbbs.beans.fjjyy;

import java.util.List;

/**
 * Created by 越 on 2015/12/7.
 */

/**
 * 同一主题所有贴子信息
 * rList: 回复贴列表
 * <p/>
 * 当前页面非主题帖首页时，mainCon中仅fid有数据
 */
public class TConInfo {
    private List<RConInfo> rList;

    public TConInfo() {
    }

    public List<RConInfo> getrList() {
        return rList;
    }

    public void setrList(List<RConInfo> rList) {
        this.rList = rList;
    }
}
