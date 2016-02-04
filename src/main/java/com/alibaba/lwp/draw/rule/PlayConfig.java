package com.alibaba.lwp.draw.rule;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class PlayConfig {
    public static Map<String, Object> playMap = new ConcurrentHashMap<String, Object>();
    static long totalTime;
    static long prizeTotal;
    static boolean isPlayStart = false;
    static List<Long> selected = new ArrayList<Long>();
    static String title;
    static String status;
    static String playId;
    static String prizeName;

    public static long getPrizeTotal() {
        return prizeTotal;
    }

    public static long getTotalTime() {
        return totalTime;
    }

    public static void setPrizeTotal(long prizeTotal) {
        PlayConfig.prizeTotal = prizeTotal;
    }

    public static void setTotalTime(long totalTime) {
        PlayConfig.totalTime = totalTime;
    }

    public static boolean isPlayStart() {
        return isPlayStart;
    }

    public static void setIsPlayStart(boolean isPlayStart) {
        PlayConfig.isPlayStart = isPlayStart;
    }

    public static void getRandom(long prizeTotal, int max) {
        Random r = new Random();
        for (int i = 0; i < prizeTotal; i++) {
            long random = r.nextInt(max);
            selected.add(System.currentTimeMillis() + random);
        }
        Collections.sort(selected);
        setSelected(selected);
    }

    public static List<Long> getSelected() {
        return selected;
    }

    public static void main(String[] args) {
        getRandom(10, 120);
        Iterator<Long> it = selected.iterator();
        while (it.hasNext()) {
            System.out.println("TIME:" + System.currentTimeMillis());
            System.out.println(it.next());
            it.remove();
            System.out.println(selected.size());
        }
    }

    public static String getTitle() {
        return title;
    }

    public static void setSelected(List<Long> selected) {
        PlayConfig.selected = selected;
    }

    public static void setTitle(String title) {
        PlayConfig.title = title;
    }

    public static void setStatus(String status) {
        PlayConfig.status = status;
    }

    public static Map<String, Object> getPlayMap() {
        return playMap;
    }

    public static String getPlayId() {
        return playId;
    }

    public static void setPlayId(String playId) {
        PlayConfig.playId = playId;
    }

    public static String getPrizeName() {
        return prizeName;
    }

    public static void setPrizeName(String prizeName) {
        PlayConfig.prizeName = prizeName;
    }
}
