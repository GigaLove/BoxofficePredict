/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.weiresearch.film.pojo;

import java.text.DecimalFormat;

/**
 *
 * @author GigaLiu
 */
public class PredictResPojo {

    private static final DecimalFormat df = new java.text.DecimalFormat("##.0000");
    private EnMoviePojo predictInfo;
    private final PredictRes predictRes;
    private int errorCode;
    private String msg;

    public PredictResPojo() {
        this.predictRes = new PredictRes();
    }

    public EnMoviePojo getPredictInfo() {
        return predictInfo;
    }

    public void setPredictInfo(EnMoviePojo predictInfo) {
        this.predictInfo = predictInfo;
        double dirIndex = Double.valueOf(df.format(predictInfo.getVideoChiefIndex()[0]));
        double starIndex = Double.valueOf(df.format(predictInfo.getVideoChiefIndex()[1]));
        predictInfo.setVideoChiefIndex(0, dirIndex);
        predictInfo.setVideoChiefIndex(1, starIndex);
    }

    public PredictRes getPredictRes() {
        return predictRes;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public class PredictRes {

        private int j48Res;
        private int bayesRes;
        private int smoRes;
        private int maxRes;

        public int getJ48Res() {
            return j48Res;
        }

        public void setJ48Res(int j48Res) {
            this.j48Res = j48Res;
        }

        public int getBayesRes() {
            return bayesRes;
        }

        public void setBayesRes(int bayesRes) {
            this.bayesRes = bayesRes;
        }

        public int getSmoRes() {
            return smoRes;
        }

        public void setSmoRes(int smoRes) {
            this.smoRes = smoRes;
        }

        public void computeMaxRes() {
//            this.maxRes = Math.max(Math.max(j48Res, bayesRes), smoRes);
            int[] predicts = new int[5];
            if (j48Res > 0) {
                predicts[j48Res]++;
            }
            if (smoRes > 0) {
                predicts[smoRes]++;
            }
            if (bayesRes > 0) {
                predicts[bayesRes]++;
            }

            int res = 0;
            for (int i = 1; i < predicts.length; i++) {
                if (predicts[i] > predicts[res]) {
                    res = i;
                }
            }
            this.maxRes = res;
        }

        public int getMaxRes() {
            return maxRes;
        }

    }

}
