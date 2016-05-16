/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.weiresearch.film.pojo;

import com.weiresearch.film.util.DecimalTool;
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
        predictInfo.setVideoChiefIndex(0, DecimalTool.format(predictInfo.getVideoChiefIndex()[0]));
        predictInfo.setVideoChiefIndex(1, DecimalTool.format(predictInfo.getVideoChiefIndex()[1]));
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
        private int randomForestRes;
        private int logisticRes;
        private int maxRes;

        public int getJ48Res() {
            return j48Res;
        }

        public void setJ48Res(int j48Res) {
            this.j48Res = j48Res;
        }

        public int getRandomForestRes() {
            return randomForestRes;
        }

        public void setRandomForestRes(int randomForestRes) {
            this.randomForestRes = randomForestRes;
        }

        public int getLogisticRes() {
            return logisticRes;
        }

        public void setLogisticRes(int logisticRes) {
            this.logisticRes = logisticRes;
        }

        public void computeMaxRes() {
//            this.maxRes = Math.max(Math.max(j48Res, randomForestRes), logisticRes);
            int[] predicts = new int[5];
            if (j48Res > 0) {
                predicts[j48Res]++;
            }
            if (logisticRes > 0) {
                predicts[logisticRes]++;
            }
            if (randomForestRes > 0) {
                predicts[randomForestRes]++;
            }

            int res = predicts.length - 1;
            for (int i = predicts.length - 2; i >= 0; i--) {
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
