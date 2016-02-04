package com.alibaba.lwp.draw.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.lwp.draw.data.DatasourceFactory;
import com.alibaba.lwp.draw.rule.Query;
import com.alibaba.lwp.draw.rule.Request;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class ResetHandler extends Handler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> map = new HashMap<String, Object>();
        PrintWriter writer = response.getWriter();
        Request r = new Request();
        Query query = r.build(request);
        try {
            DatasourceFactory.reset();
            map.put("status", "1");
        } catch (Exception e) {
            map.put("status", "0");
        }
        writer.write(query.getCallback() + "(" + JSON.toJSONString(map) + ")");
        response.setStatus(200);
        writer.flush();
        return;
    }
}
