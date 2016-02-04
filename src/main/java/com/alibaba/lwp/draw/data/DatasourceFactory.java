package com.alibaba.lwp.draw.data;

import com.alibaba.lwp.draw.rule.PlayConfig;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatasourceFactory {
    public static String luckyFile = "data/lucky.txt";
    public static String playFile = "data/play.txt";
    public static String statusFile = "data/status.txt";
    public static Map<String, String> playMap = new HashMap<String, String>();
    public static Map<String, String> statusMap = new HashMap<String, String>();
    public static Map<String, List<String>> luckyMap = new HashMap<String, List<String>>();
    public static List<String> lucklist = new ArrayList<String>();

    static {
        try {
            readLuckFile();
            readPlayFile();
            readStatusFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void init() {

    }

    public static void readLuckFile() throws IOException {
        File file = new File(luckyFile);
        if (!file.exists()) {
            try {
                file.createNewFile();
                return;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        FileReader fr = new FileReader(luckyFile);
        BufferedReader br = new BufferedReader(fr);
        String line = "";
        String[] arrs = null;
        while ((line = br.readLine()) != null) {
            arrs = line.split(";");
            String playId = arrs[0];
            String info = arrs[1];
            String[] arr = info.split(",");
            String uid = arr[0];
            lucklist.add(uid);
            if (luckyMap.containsKey(playId)) {
                List<String> uidList = luckyMap.get(info);
                uidList.add(info);
                luckyMap.put(playId, uidList);
            } else {
                List<String> uidList = new ArrayList<String>();
                uidList.add(info);
                luckyMap.put(playId, uidList);
            }
        }
        br.close();
        fr.close();
    }

    public static void readStatusFile() throws IOException {
        File file = new File(statusFile);
        if (!file.exists()) {
            try {
                file.createNewFile();
                return;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        FileReader fr = new FileReader(statusFile);
        BufferedReader br = new BufferedReader(fr);
        String line = "";
        String[] arrs = null;
        while ((line = br.readLine()) != null) {
            arrs = line.split(",");
            String playId = arrs[0];
            String status = arrs[1];
            statusMap.put(playId, status);
        }
        br.close();
        fr.close();
    }

    public static void readPlayFile() throws IOException {
        File file = new File(playFile);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        FileReader fr = new FileReader(playFile);
        BufferedReader br = new BufferedReader(fr);
        String line = "";
        String[] arrs = null;
        while ((line = br.readLine()) != null) {
            arrs = line.split(";");
            if (arrs.length < 2) {
                return;
            }
            String playId = arrs[0];
            String playInfo = arrs[1];
            playMap.put(playId, playInfo);
        }
        br.close();
        fr.close();
    }

    public static void writeFile(final String pathName, final String content) throws IOException {
        File file = new File(pathName);
        FileWriter fw = new FileWriter(file, true);
        fw.write(new String("a".getBytes(), "UTF-8"));
        fw.write(content + "\n");
        fw.close();
    }

    public static Map<String, List<String>> getLuckyList() {
        return luckyMap;
    }

    public static Map<String, String> getPlayList() {
        return playMap;
    }

    public static void setPlayList(Map<String, String> playMap) {
        DatasourceFactory.playMap = playMap;
    }

    public static void setLuckyList(Map<String, List<String>> luckyList) {
        DatasourceFactory.luckyMap = luckyList;
    }

    public static void main(String[] args) {
        System.out.println("hello");
    }

    public static void reset() throws IOException {
        PlayConfig.setIsPlayStart(false);
        PlayConfig.getSelected().clear();
        playMap.clear();
        luckyMap.clear();
        statusMap.clear();
        lucklist.clear();
        resetFile(luckyFile);
        resetFile(playFile);
        resetFile(statusFile);
    }

    public static void resetFile(String path) throws IOException {
        File f = new File(path);
        FileWriter fw = new FileWriter(f);
        fw.write("");
        fw.close();
    }

}
