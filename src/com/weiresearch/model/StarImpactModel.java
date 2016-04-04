/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.weiresearch.model;

import com.weiresearch.data.BoxofficeConst;
import com.weiresearch.entry.StarImpact;
import com.weiresearch.tool.DataTool;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author GigaLiu
 */
public class StarImpactModel {

    private Map<Long, StarImpact> starMap;

    public Map<Long, StarImpact> getStarMap() {
        return starMap;
    }

    public void loadStarInfo(String starPath) {
        BufferedReader br;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(
                    starPath), "utf-8"));
            starMap = new HashMap<>();
            String lineStr;
            String[] values;
            String worksStr;
            int index;
            StarImpact si;
            long starId;
            while ((lineStr = br.readLine()) != null) {
                index = lineStr.indexOf(",\"[");
                if (index > 0) {
                    values = lineStr.substring(0, index).split(",");
                    worksStr = lineStr.substring(index + 2, lineStr.length() - 1);
                    if (values.length == 3) {
                        starId = Long.parseLong(values[1]);
                        si = new StarImpact(starId, values[2],
                                Integer.parseInt(values[0]), worksStr);
                        if (!starMap.containsKey(starId)) {
                            starMap.put(starId, si);
                        }
                    }
                }
            }
            br.close();

        } catch (IOException ex) {
            Logger.getLogger(DataTool.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * 计算影响力指数入口
     */
    public void compute() {
        StarImpact si;
        double impactIndex;
        for (Map.Entry<Long, StarImpact> entry : starMap.entrySet()) {
            si = entry.getValue();
            impactIndex = computeIndexByJson(si.getWorksJson());
            si.setImpactIndex(impactIndex);
        }
//        List<StarImpact> impactList = new ArrayList<>(starMap.values());
//        Collections.sort(impactList);
//        for (StarImpact impact : impactList) {
//            System.out.println(impact);
//        }
    }

    /**
     * 解析艺人作品json
     *
     * @param works
     * @return
     */
    public double computeIndexByJson(JSONArray works) {
        if (works == null) {
            return 0f;
        }

        Map<Integer, List<Integer>> boxofficeMap = new HashMap<>();
        int year;
        int boxoffice;
        JSONObject workJson;
        List<Integer> boxofficeList;
        for (int i = 0; i < works.length(); i++) {
            try {
                workJson = works.getJSONObject(i);
                year = optInt(workJson.getString("ReleaseYear"));
                boxoffice = optInt(workJson.getString("BoxOffice"));
                if (boxoffice > 0) {
                    if (!boxofficeMap.containsKey(year)) {
                        boxofficeList = new ArrayList<>();
                        boxofficeMap.put(year, boxofficeList);
                    }
                    boxofficeList = boxofficeMap.get(year);
                    boxofficeList.add(boxoffice);
                }
            } catch (JSONException ex) {
                Logger.getLogger(StarImpactModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return computeIndex(boxofficeMap);
    }

    private int optInt(String str) {
        try {
            int val = Integer.parseInt(str);
            return val;
        } catch (Exception ex) {
            return 0;
        }
    }
    
    public double getImpacIndex(long starId) {
        if (starMap.containsKey(starId)) {
            return starMap.get(starId).getImpactIndex();
        }
        return -1;
    }

    /**
     * 基于主创影视作品的影响力计算
     *
     * @param boxofficeMap
     * @return
     */
    public double computeIndex(Map<Integer, List<Integer>> boxofficeMap) {
        if (boxofficeMap == null || boxofficeMap.isEmpty()) {
            return 0f;
        }

        double impactIndex = 0.0f;
        for (Map.Entry<Integer, List<Integer>> entry : boxofficeMap.entrySet()) {
//            impactIndex += computeIndexByBoxRatio(entry.getKey(), entry.getValue());
//            impactIndex += computeIndexByCustomRatio(entry.getKey(), entry.getValue());
            impactIndex += computeIndexByAvgBox(entry.getKey(), entry.getValue());
        }
        return impactIndex;
    }

    /**
     * 基于2011-2015年票房比例，计算票房影响力
     *
     * @param year
     * @param boxofficeList
     * @return
     */
    private double computeIndexByBoxRatio(int year, List<Integer> boxofficeList) {
        if (year > 2010 && year < 2016) {
            int yearSum = 0;
            for (Integer box : boxofficeList) {
                yearSum += box;
            }
            return BoxofficeConst.BOXOFFICE_RATIO[year - 2011] * yearSum;
        }
        return 0f;
    }

    /**
     * 用户自定义参数，计算票房影响力
     *
     * @param year
     * @param boxofficeList
     * @return
     */
    private double computeIndexByCustomRatio(int year, List<Integer> boxofficeList) {
        int yearSum = 0;
        for (Integer box : boxofficeList) {
            yearSum += box;
        }
        switch (year) {
            case 2015:
            case 2014:
                return yearSum * 0.4;
            case 2013:
            case 2012:
                return yearSum * 0.3;
            case 2011:
                return yearSum * 0.25;
            default:
                return yearSum * 0.05;
        }
    }

    /**
     * 基于年度平均票房计算艺人影响力
     *
     * @param year
     * @param boxofficeList
     * @return
     */
    private double computeIndexByAvgBox(int year, List<Integer> boxofficeList) {
        if (year > 2010 && year < 2016) {
            int yearSum = 0;
            for (Integer box : boxofficeList) {
                yearSum += box;
            }
            return BoxofficeConst.BOXOFFICE_RATIO[year - 2011] * (yearSum / BoxofficeConst.YEAR_AVG_BOXOFFICE[year - 2011]);
        }
        return 0f;
    }

    public static void main(String[] args) {
        StarImpactModel model = new StarImpactModel();
        model.loadStarInfo("C:\\Users\\GigaLiu\\Desktop\\影视数据全\\EN DATA\\star_info_copy2.txt");
//        model.loadStarInfo("C:\\Users\\GigaLiu\\Desktop\\star_spider_2016.csv");
        model.compute();
    }
}
