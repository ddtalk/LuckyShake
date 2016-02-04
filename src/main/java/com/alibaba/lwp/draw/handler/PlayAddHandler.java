package com.alibaba.lwp.draw.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.lwp.draw.data.DatasourceFactory;
import com.alibaba.lwp.draw.rule.Query;
import com.alibaba.lwp.draw.rule.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class PlayAddHandler extends Handler {

    static Logger LOG = LoggerFactory.getLogger("play");

    static Logger logger = LoggerFactory.getLogger("debug");

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response) throws IOException {

        logger.info("ADD Access");

        PrintWriter writer = response.getWriter();
        Request r = new Request();
        Query query = r.build(request);
        Random rd = new Random();
        StringBuilder sb = new StringBuilder();
        StringBuilder playsb = new StringBuilder();
        StringBuilder status = new StringBuilder();
        sb.append(System.currentTimeMillis());
        sb.append(rd.nextInt(999999));
        String playId = sb.toString();
        Map<String, Object> result = new HashMap<String, Object>();

        String orgId = query.getOrgId();
        String title = query.getTitle();
        String prizesTotal = query.getPrizesTotal();
        String totalTime = query.getTotalTime();

        if (!totalTime.matches("[0-9]+") || !prizesTotal.matches("[0-9]+")) {
            result.put("result", "0");
            writer.write(query.getCallback() + "(" + JSON.toJSONString(result) + ")");
            response.setStatus(200);
            writer.flush();
            return;
        }
        String prizeName = query.getPrizeName();
        LOG.info("[AddPlay] {} {} {} {} {} {} {}", orgId, playId, title, prizesTotal, totalTime, prizeName,
                System.currentTimeMillis());
        playsb.append(playId).append(";");
        playsb.append(title).append(",");
        playsb.append(prizesTotal).append(",");
        playsb.append(totalTime).append(",");
        playsb.append(prizeName);
        String[] arr = playsb.toString().split(";");
        String info = arr[1];
        DatasourceFactory.playMap.put(playId, info);

        DatasourceFactory.writeFile(DatasourceFactory.playFile, playsb.toString());
        status.append(playId).append(",");
        status.append("0");
        DatasourceFactory.writeFile(DatasourceFactory.statusFile, status.toString());
        DatasourceFactory.statusMap.put(playId, "0");
        LOG.info("[write PlayFile] {} {}", sb.toString(), System.currentTimeMillis());
        result.put("result", "1");
        result.put("playId", playId);

        writer.write(query.getCallback() + "(" + JSON.toJSONString(result) + ")");
        response.setStatus(200);
        writer.flush();

        logger.info("ADD Access END");

    }

    public static void main(String[] args) {
        String srt = "1233442rrr";
        System.out.println(srt.matches("[0-9]+"));
    }
}
