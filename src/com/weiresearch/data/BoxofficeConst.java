/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.weiresearch.data;

/**
 *
 * @author GigaLiu
 */
public class BoxofficeConst {
    
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
    public static final float[] BOXOFFICE_RATIO;
    static {
        BOXOFFICE_RATIO = new float[YEAR_BOXOFFICE.length];
        int sum = 0;
        for (int val : YEAR_BOXOFFICE) {
            sum += val;
        }
        for (int i = 0; i < BOXOFFICE_RATIO.length; i++) {
            BOXOFFICE_RATIO[i] = (float) (YEAR_BOXOFFICE[i]) / sum;
        }
    }
    /**
     * 2011-2015历年平均票房
     */
    public static final float[] YEAR_AVG_BOXOFFICE;
    static {
        YEAR_AVG_BOXOFFICE = new float[YEAR_FILMS.length];
        for (int i = 0; i < YEAR_AVG_BOXOFFICE.length; i++) {
            YEAR_AVG_BOXOFFICE[i] = (float)(YEAR_BOXOFFICE[i]) / YEAR_FILMS[i];
        }
    }
    
}
