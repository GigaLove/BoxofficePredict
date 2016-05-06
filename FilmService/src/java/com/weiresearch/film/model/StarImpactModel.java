/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.weiresearch.film.model;

import com.weiresearch.film.pojo.StarImpactPojo;
import com.weiresearch.film.pojo.StarYearRoleBoxPojo;
import com.weiresearch.film.util.BoxofficeConst;
import com.weiresearch.film.util.DataTool;
import com.weiresearch.film.util.MatrixTool;
import static com.weiresearch.film.util.MovieConst.ROLE_COUNT;
import static com.weiresearch.film.util.MovieConst.YEAR_SPAN;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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

    private Map<Long, StarImpactPojo> starMap;

    public Map<Long, StarImpactPojo> getStarMap() {
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
            StarImpactPojo si;
            long starId;
            while ((lineStr = br.readLine()) != null) {
                index = lineStr.indexOf(",\"[");
                if (index > 0) {
                    values = lineStr.substring(0, index).split(",");
                    worksStr = lineStr.substring(index + 2, lineStr.length() - 1);
                    if (values.length == 3) {
                        starId = Long.parseLong(values[1]);
                        si = new StarImpactPojo(starId, values[2],
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
        StarImpactPojo si;
        double impactIndex;
        for (Map.Entry<Long, StarImpactPojo> entry : starMap.entrySet()) {
            si = entry.getValue();
            impactIndex = computeIndexByJson(si.getWorksJson());
            si.setImpactIndex(impactIndex);
        }
        List<StarImpactPojo> impactList = new ArrayList<>(starMap.values());
        Collections.sort(impactList);
        for (StarImpactPojo impact : impactList) {
            System.out.println(impact);
        }
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
//            impactIndex += computeIndexByAvgBox(entry.getKey(), entry.getValue());
            impactIndex += computeIndexByAvgBoxRatio(entry.getKey(), entry.getValue());
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
            return BoxofficeConst.YEAR_BOXOFFICE_RATIO[year - 2011] * yearSum;
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
            return BoxofficeConst.YEAR_BOXOFFICE_RATIO[year - 2011] * (yearSum / BoxofficeConst.YEAR_AVG_BOXOFFICE[year - 2011]);
        }
        return 0f;
    }

    /**
     * 基于年度平均票房比率计算艺人影响力
     *
     * @param year
     * @param boxofficeList
     * @return
     */
    private double computeIndexByAvgBoxRatio(int year, List<Integer> boxofficeList) {
        if (year > 2010 && year < 2016) {
            int yearSum = 0;
            for (Integer box : boxofficeList) {
                yearSum += box;
            }
            return BoxofficeConst.YEAR_AVG_BOXOFFICE_RATIO[year - 2011] * yearSum;
        }
        return 0f;
    }

    public void writeStarImpact(String outputPath) {
        if (this.starMap != null) {
            List<StarImpactPojo> impactList = new ArrayList<>(starMap.values());
            Collections.sort(impactList);

            FileOutputStream fos = null;
            PrintWriter pw = null;
            try {
                fos = new FileOutputStream(outputPath);
                pw = new PrintWriter(fos);
                for (StarImpactPojo impact : impactList) {
                    pw.println(impact);
                }
                pw.flush();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(StarImpactModel.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (pw != null) {
                    pw.close();
                }
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException ex) {
                        Logger.getLogger(StarImpactModel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }

    public static double computeIndexByWorkMatrix(List<StarYearRoleBoxPojo> yearRoleBoxs, int minYear) {
        double[][] workMatrix = new double[YEAR_SPAN][ROLE_COUNT];
        for (StarYearRoleBoxPojo yrb : yearRoleBoxs) {
            int year = yrb.getYear();
            int role = yrb.getRole();
            workMatrix[year - minYear][role - 1] = yrb.getBoxoffice() / BoxofficeConst.HIGHEST_BOXOFFICE;
        }
        double[][] tmpRes = MatrixTool.multiply(MatrixTool.vector2Matrix(
                BoxofficeConst.YEAR_AVG_BOXOFFICE_RATIO, 0), workMatrix);
        double[][] res = MatrixTool.multiply(tmpRes, MatrixTool.vector2Matrix(
                BoxofficeConst.ROLE_WEIGHT, 1));
        return res[0][0];
    }

    public static void main(String[] args) {
        StarImpactModel model = new StarImpactModel();
        model.loadStarInfo("data/star_works_2.txt");
//        model.loadStarInfo("C:\\Users\\GigaLiu\\Desktop\\star_spider_2016.csv");
        model.compute();
        model.writeStarImpact("data/star_impact.txt");
    }
}
