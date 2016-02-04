package com.alibaba.lwp.draw.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.lwp.draw.data.DatasourceFactory;
import com.alibaba.lwp.draw.rule.PlayConfig;
import com.alibaba.lwp.draw.rule.Query;
import com.alibaba.lwp.draw.rule.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class PlayStartHandler extends Handler {
    static Logger LOG = LoggerFactory.getLogger("start");

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter writer = response.getWriter();
        Request r = new Request();
        Query query = r.build(request);
        String playId = query.getPlayId();
        StringBuilder sb = new StringBuilder();
        String title = null;
        long prizesTotal = 0;
        int totalTime = 0;
        String prizeName = null;
        Map<String, Object> result = new HashMap<String, Object>();
        if (!DatasourceFactory.playMap.containsKey(playId)) {
            result.put("result", "0");
            LOG.info("[PlayId is not exist] {} {}", playId, System.currentTimeMillis());
            writer.write(query.getCallback() + "(" + JSON.toJSONString(result) + ")");
            response.setStatus(200);
            writer.flush();
            return;
        }
        if (PlayConfig.isPlayStart()) {
            result.put("result", "0");
            LOG.info("[PlayStar starting] {} {}", playId, System.currentTimeMillis());
            writer.write(query.getCallback() + "(" + JSON.toJSONString(result) + ")");
            response.setStatus(200);
            writer.flush();
            return;
        }
        String playInfo = DatasourceFactory.playMap.get(playId);
        String[] arrs = null;
        arrs = playInfo.split(",");
        if (arrs.length < 4) {
            LOG.info("[ARR length is less 4] {} {}", playId, playInfo);
            return;
        }

        title = arrs[0];
        prizesTotal = Long.valueOf(arrs[1]);
        totalTime = Integer.valueOf(arrs[2]);
        prizeName = arrs[3];

        PlayConfig.setTitle(title);
        PlayConfig.setPrizeName(prizeName);
        PlayConfig.setTotalTime(totalTime);
        PlayConfig.setPrizeName(prizeName);

        int selected = 0;
        List<String> stringList = DatasourceFactory.luckyMap.get(playId);
        if (stringList != null) {
            selected = stringList.size();
        }
        long nowPrize = prizesTotal - selected;
        if (nowPrize <= 0) {
            result.put("result", "0");
            LOG.info("[PlayStart failure] {} {} {}  {} {} {} {}", playId, title, prizesTotal, totalTime, prizeName, nowPrize, System.currentTimeMillis());
        } else {
            PlayConfig.getRandom(nowPrize, totalTime);
            PlayConfig.setIsPlayStart(true);

            LOG.info("[PlayStart success] {} {} {}  {} {} {} {}", playId, title, prizesTotal, totalTime, prizeName, nowPrize, System.currentTimeMillis());
            PlayConfig.setPlayId(playId);
            sb.append(playId).append(",");
            sb.append("1");
            DatasourceFactory.writeFile(DatasourceFactory.statusFile, sb.toString());
            DatasourceFactory.statusMap.put(playId, "1");

            result.put("result", "1");
        }

        writer.write(query.getCallback() + "(" + JSON.toJSONString(result) + ")");
        response.setStatus(200);
        writer.flush();
        return;
    }
}
