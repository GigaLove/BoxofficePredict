/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.weiresearch.film.util;

/**
 *
 * @author GigaLiu
 */
public class MatrixTool {

    public static double[][] multiply(double[][] left, double[][] right) {
        if (left == null || right == null || left[0].length != right.length) {
            return null;
        }

        double result[][] = new double[left.length][right[0].length];
        double tmp;
        for (int i = 0; i < left.length; i++) {
            for (int j = 0; j < right[0].length; j++) {
                tmp = 0f;
                for (int k = 0; k < right.length; k++) {
                    // 矩阵AB中a_ij的值等于矩阵A的i行和矩阵B的j列的乘积之和
                    tmp += left[i][k] * right[k][j];
                }
                result[i][j] = tmp;
            }
        }
        return result;
    }

    public static double[][] vector2Matrix(double[] vector, int model) {
        double[][] res;
        if (model == 0) {
            res = new double[1][];
            res[0] = vector;
        } else {
            res = new double[vector.length][1];
            for (int i = 0; i < vector.length; i++) {
                res[i][0] = vector[i];
            }
        }
        return res;
    }
}
