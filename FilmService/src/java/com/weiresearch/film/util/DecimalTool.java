/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.weiresearch.film.util;

import java.text.DecimalFormat;

/**
 *
 * @author GigaLiu
 */
public class DecimalTool {

    private static final DecimalFormat df = new java.text.DecimalFormat("##.0000");

    public static double format(double value) {
        return Double.valueOf(df.format(value));
    }
}
