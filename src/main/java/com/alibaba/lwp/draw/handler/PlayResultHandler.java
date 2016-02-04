package com.alibaba.lwp.draw.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.lwp.draw.data.DatasourceFactory;
import com.alibaba.lwp.draw.rule.Query;
import com.alibaba.lwp.draw.rule.Request;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class PlayResultHandler extends Handler {
    static Logger LOG = LoggerFactory.getLogger("result");

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter writer = response.getWriter();
        Request r = new Request();
        Query query = r.build(request);

        String playId = query.getPlayId();
        Map<String, Object> map = new HashMap<String, Object>();
        List<Map<String, Object>> luckyDog = new ArrayList<Map<String, Object>>();
        String status = DatasourceFactory.statusMap.get(playId);
        if (Strings.isNullOrEmpty(status)) {
            map.put("status", "0");
            writer.write(query.getCallback() + "(" + JSON.toJSONString(map) + ")");
            response.setStatus(200);
            writer.flush();
            return;
        }
        String line = DatasourceFactory.playMap.get(playId);
        String[] arrs = line.split(",");
        String title = arrs[0];
        String prizesTotal = arrs[1];
        List<String> luckList = DatasourceFactory.luckyMap.get(playId);
        if (luckList != null) {
            if (luckList.size() > 0) {
                Iterator<String> iter = luckList.iterator();
                while (iter.hasNext()) {
                    String luckLine = iter.next();
                    String uid = "null";
                    String nick = "null";
                    String avatar = "null";
                    String luckTime = "null";
                    String[] arr = luckLine.split(",");
                    Map<String, Object> info = new HashMap<String, Object>();
                    for (int i = 0; i < arr.length; i++) {
                        if (i == 0) {
                            uid = arr[0];
                        } else if (i == 1) {
                            nick = arr[1];
                        } else if (i == 2) {
                            avatar = arr[2];
                        } else if (i == 3) {
                            luckTime = arr[3];
                        }
                    }
                    info.put("uid", uid);
                    info.put("nick", nick);
                    info.put("avatar", avatar);
                    info.put("luckTime", luckTime);
                    luckyDog.add(info);
                }
            }
        }
        map.put("status", status);
        map.put("title", title);
        map.put("prizesTotal", prizesTotal);
        if (luckyDog.size() > 0) {
            map.put("luckyDog", luckyDog);
        }
        LOG.info("[PlayResult luckyDog] {} {} ", JSON.toJSONString(map), System.currentTimeMillis());
        writer.write(query.getCallback() + "(" + JSON.toJSONString(map) + ")");
        response.setStatus(200);
        writer.flush();
        return;
    }

}
