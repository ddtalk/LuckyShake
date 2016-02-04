package com.alibaba.lwp.draw.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public abstract class Handler {
    public abstract void handle(HttpServletRequest request, HttpServletResponse response) throws IOException;

}