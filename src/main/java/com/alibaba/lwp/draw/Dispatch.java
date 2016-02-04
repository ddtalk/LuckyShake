package com.alibaba.lwp.draw;

import com.alibaba.fastjson.JSON;
import com.alibaba.lwp.draw.handler.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Dispatch {

    static Logger logger = LoggerFactory.getLogger("debug");

    Map<String, Handler> handlerMap;

    public Dispatch() {
        this.handlerMap = new HashMap<String, Handler>();
        handlerMap.put("/result", new PlayResultHandler());
        handlerMap.put("/shake", new LuckHandler());
        handlerMap.put("/start", new PlayStartHandler());
        handlerMap.put("/add", new PlayAddHandler());
        handlerMap.put("/reset", new ResetHandler());
        handlerMap.put("/getConfig", new GetConfigHandler());

        logger.info("HandlerMap is : " + JSON.toJSONString(handlerMap));
    }

    public boolean dispatch(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Handler handler = handlerMap.get(request.getRequestURI());
        logger.info("url is " + request.getRequestURI() + " handler is " + JSON.toJSONString(handler));
        if (handler == null) {
            return true;
        }
        handler.handle(request, response);
        return false;
    }
}
