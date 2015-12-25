package com.zhuzhenting.fudanbbs.beans;

//记录搜索用户结果的类
public class UQueryResult {
    private String id, login, lastlogin, perf, post, hp, level, repeat, contrib, rank, horo;
    //id 不明 上次登录 表现值 文章数 生命力 不明 不明 贡献 贡献百分比 星座
    private String gender, ip, nick, ident, smd, logout;
    //性别 IP 昵称 身份 个人说明 离站
    private int success;

    public UQueryResult() {
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int s) {
        this.success = s;
    }


    public String getId() {
        return id;
    }

    public void setId(String s) {
        this.id = s;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String s) {
        this.login = s;
    }

    public String getLastlogin() {
        return lastlogin;
    }

    public void setLastlogin(String s) {
        this.lastlogin = s;
    }

    public String getPerf() {
        return perf;
    }

    public void setPerf(String s) {
        this.perf = s;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String s) {
        this.post = s;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String s) {
        this.level = s;
    }

    public String getHp() {
        return hp;
    }

    public void setHp(String s) {
        this.hp = s;
    }

    public String getRepeat() {
        return repeat;
    }

    public void setRepeat(String s) {
        this.repeat = s;
    }

    public String getContrib() {
        return contrib;
    }

    public void setContrib(String s) {
        this.contrib = s;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String s) {
        this.rank = s;
    }

    public String getHoro() {
        return horo;
    }

    public void setHoro(String s) {
        this.horo = s;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String s) {
        this.gender = s;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String s) {
        this.ip = s;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String s) {
        this.nick = s;
    }

    public String getIdent() {
        return ident;
    }

    public void setIdent(String s) {
        this.ident = s;
    }

    public String getSmd() {
        return smd;
    }

    public void setSmd(String s) {
        this.smd = s;
    }

    public String getLogout() {
        return logout;
    }

    public void setLogout(String s) {
        this.logout = s;
    }
}
