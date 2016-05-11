/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.weiresearch.film.pojo;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author GigaLiu
 */
public class PredictResPojo {

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
    }

}
