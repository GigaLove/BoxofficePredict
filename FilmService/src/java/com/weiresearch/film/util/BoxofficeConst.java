/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.weiresearch.film.util;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author GigaLiu
 */
public class BoxofficeConst {

    public static final int HIGHEST_BOXOFFICE = 20000;
    
    public static final Map<Integer, Integer> YEAR_HIGHEST_BOXOFFICE;
    static {
        YEAR_HIGHEST_BOXOFFICE = new HashMap<>();
        YEAR_HIGHEST_BOXOFFICE.put(2016, 339059);
        YEAR_HIGHEST_BOXOFFICE.put(2015, 243800);
        YEAR_HIGHEST_BOXOFFICE.put(2014, 197900);
        YEAR_HIGHEST_BOXOFFICE.put(2013, 124699);
        YEAR_HIGHEST_BOXOFFICE.put(2012, 126801);
        YEAR_HIGHEST_BOXOFFICE.put(2011, 107157);
        YEAR_HIGHEST_BOXOFFICE.put(2010, 138200);
        YEAR_HIGHEST_BOXOFFICE.put(2009, 45227);
        YEAR_HIGHEST_BOXOFFICE.put(2008, 32500);
    }

    /**
     * 2011-2015历年上映影片数量
     */
    public static final int[] YEAR_FILMS = {228, 289, 305, 323, 358};
    /**
     * 2011-2015年度票房总额，亿为单位
     */
    public static final int[] YEAR_BOXOFFICE = {131, 171, 218, 296, 441};
    /**
     * 2011-2015票房占比
     */
    public static final double[] YEAR_BOXOFFICE_RATIO;

    static {
        YEAR_BOXOFFICE_RATIO = new double[YEAR_BOXOFFICE.length];
        int sum = 0;
        for (int val : YEAR_BOXOFFICE) {
            sum += val;
        }
        for (int i = 0; i < YEAR_BOXOFFICE_RATIO.length; i++) {
            YEAR_BOXOFFICE_RATIO[i] = (float) (YEAR_BOXOFFICE[i]) / sum;
        }
    }
    /**
     * 2011-2015历年平均票房
     */
    public static final double[] YEAR_AVG_BOXOFFICE;
    /**
     * 2011-2015历年平均票房比例
     */
    public static final double[] YEAR_AVG_BOXOFFICE_RATIO;

    static {
        YEAR_AVG_BOXOFFICE = new double[YEAR_FILMS.length];
        float sum = 0;
        for (int i = 0; i < YEAR_AVG_BOXOFFICE.length; i++) {
            YEAR_AVG_BOXOFFICE[i] = (float) (YEAR_BOXOFFICE[i]) / YEAR_FILMS[i];
            sum += YEAR_AVG_BOXOFFICE[i];
        }

        YEAR_AVG_BOXOFFICE_RATIO = new double[YEAR_AVG_BOXOFFICE.length];
        for (int i = 0; i < YEAR_AVG_BOXOFFICE.length; i++) {
            YEAR_AVG_BOXOFFICE_RATIO[i] = (float) (YEAR_AVG_BOXOFFICE[i]) / sum;
        }
    }

    /**
     * 导演、主演、编剧、制片权重
     */
    public static final double[] ROLE_WEIGHT = {0.8, 1, 0.2, 0.1};

}
