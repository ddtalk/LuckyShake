package com.alibaba.lwp.draw.rule;

public class Query {
    private String uid;
    private String nickName;
    private String callback;
    private String prizeId;
    private String prizeNum;
    private boolean isStart;
    private boolean isStop;
    private String avatar;
    private String orgId;
    private String title;
    private String prizesTotal;
    private String totalTime;
    private String prizeName;
    private String playId;


    public String getNickName() {
        return nickName;
    }

    public String getUid() {
        return uid;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getCallback() {
        return callback;
    }

    public void setCallback(String callback) {
        this.callback = callback;
    }

    public String getPrizeId() {
        return prizeId;
    }

    public String getPrizeNum() {
        return prizeNum;
    }

    public void setPrizeId(String prizeId) {
        this.prizeId = prizeId;
    }

    public void setPrizeNum(String prizeNum) {
        this.prizeNum = prizeNum;
    }

    public boolean isStart() {
        return isStart;
    }

    public boolean isStop() {
        return isStop;
    }

    public void setIsStart(boolean isStart) {
        this.isStart = isStart;
    }

    public void setIsStop(boolean isStop) {
        this.isStop = isStop;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPrizesTotal() {
        return prizesTotal;
    }

    public String getTotalTime() {
        return totalTime;
    }

    public String getPrizeName() {
        return prizeName;
    }

    public String getOrgId() {
        return orgId;
    }

    public String getTitle() {
        return title;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public void setPrizeName(String prizeName) {
        this.prizeName = prizeName;
    }

    public void setPrizesTotal(String prizesTotal) {
        this.prizesTotal = prizesTotal;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }

    public String getPlayId() {
        return playId;
    }

    public void setPlayId(String playId) {
        this.playId = playId;
    }
}
