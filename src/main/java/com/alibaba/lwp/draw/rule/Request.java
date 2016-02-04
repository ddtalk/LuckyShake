package com.alibaba.lwp.draw.rule;

import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;

public class Request {
    public Query build(HttpServletRequest request) {
        Query query = new Query();
        String uid = getParam(request, "uid", "null");
        String nick = getParam(request, "nick", "null");
        String avatar = getParam(request, "avatar", "null");
        String orgId = getParam(request, "orgId", "null");
        String title = getParam(request, "title", "null");
        String prizesTotal = getParam(request, "prizesTotal", "null");
        String totalTime = getParam(request, "totalTime", "null");
        String prizeName = getParam(request, "prizeName", "null");
        String callback = getParam(request, "callback", null);
        String playId = getParam(request, "playId", null);

        String prizeNum = getParam(request, "prizeNum", "null");
        if (prizeNum != null) {
            query.setPrizeNum(prizeNum);
        }
        query.setAvatar(avatar);
        query.setUid(uid);
        query.setNickName(nick);
        query.setPrizeNum(prizeNum);
        query.setOrgId(orgId);
        query.setTitle(title);
        query.setTotalTime(totalTime);
        query.setPrizesTotal(prizesTotal);
        query.setPrizeName(prizeName);
        query.setCallback(callback);
        query.setPlayId(playId);
        return query;
    }

    private String getParam(HttpServletRequest request, String name, String defValue) {
        String v = request.getParameter(name);
        if (v == null || StringUtils.isEmpty(v) || "null".equals(v)) {
            return defValue;
        }
        return v;
    }
}
