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
import java.util.concurrent.locks.ReentrantLock;

public class LuckHandler extends Handler {
    static Logger LOG = LoggerFactory.getLogger("lucky");
    private final ReentrantLock pendding = new ReentrantLock();

    @Override
    public void handle(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
        String playId = PlayConfig.getPlayId();
        PrintWriter writer = response.getWriter();
        Map<String, Object> result = new HashMap<String, Object>();

        Request r = new Request();
        Query query = r.build(request);
        String uid = query.getUid();
        String nick = query.getNickName();
        String avatar = query.getAvatar();

        if (!PlayConfig.isPlayStart()) {
            result.put("result", "0");
            LOG.info("[Play not start] {} {} {}  {}", uid, nick, avatar, System.currentTimeMillis());
            response.setStatus(200);
            writer.write(query.getCallback() + "(" + JSON.toJSONString(result) + ")");
            writer.flush();
            return;
        } else if (DatasourceFactory.lucklist.contains(uid)) {
            result.put("result", "1");
            LOG.info("[continue for you] {} {} {}  {}", uid, nick, avatar, System.currentTimeMillis());
            response.setStatus(200);
            writer.write(query.getCallback() + "(" + JSON.toJSONString(result) + ")");
            writer.flush();
            return;
        } else {
            try {
                pendding.lock();
                final long luckyTime = System.currentTimeMillis();
                StringBuilder sb = new StringBuilder();
                if (PlayConfig.getSelected().size() >= 1) {
                    long sequence = 0;
                    Iterator<Long> it = PlayConfig.getSelected().iterator();
                    while (it.hasNext()) {
                        sequence = it.next();
                        break;
                    }
                    LOG.info("[sequence] {}", sequence);
                    if (luckyTime >= sequence) {
                        sb.append(playId).append(";");
                        sb.append(uid).append(",");
                        sb.append(nick).append(",");
                        sb.append(avatar).append(",");
                        sb.append(System.currentTimeMillis());
                        DatasourceFactory.writeFile(DatasourceFactory.luckyFile, sb.toString());
                        List<String> list = DatasourceFactory.luckyMap.get(playId);
                        String[] arr = sb.toString().split(";");
                        if (list == null) {
                            list = new ArrayList<String>();
                        }
                        list.add(arr[1]);
                        DatasourceFactory.luckyMap.put(playId, list);
                        result.put("result", "2");
                        result.put("prize", PlayConfig.getPrizeName());
                        LOG.info("[congradulation to you] {} {} {}  {}", uid, nick, avatar, System.currentTimeMillis());
                        it.remove();
                        LOG.info("PlayConfig.getSelected().size() {}", PlayConfig.getSelected().size());
                        if (PlayConfig.getSelected().size() == 0) {
                            PlayConfig.setIsPlayStart(false);
                            DatasourceFactory.statusMap.put(playId, "2");
                            DatasourceFactory.writeFile(DatasourceFactory.statusFile, playId + "," + "2");
                        }
                        DatasourceFactory.lucklist.add(uid);
                    } else {
                        result.put("result", "1");
                        LOG.info("[continue for you] {} {} {}  {}", uid, nick, avatar, System.currentTimeMillis());
                    }
                } else {
                    result.put("result", "1");
                    LOG.info("[continue for you] {} {} {}  {}", uid, nick, avatar, System.currentTimeMillis());
                }
            } finally {
                pendding.unlock();
            }

            response.setStatus(200);
            writer.write(query.getCallback() + "(" + JSON.toJSONString(result) + ")");
            writer.flush();
            return;
        }

    }
}

