/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.weiresearch.entry;

/**
 *
 * @author GigaLiu
 */
public class MovieInfo {
    private long id;
    private String name;
    private String type;
    
    /**
     * 0：中国
     * 1：美国
     * 2：港台
     * 3：其他
     */
    private int country;
    
    /**
     * 0：2013
     * 1：2014
     * 2：2015
     * 3：2016
     */
    private int releaseTime;
    
    private double dirBoxImpactIndex;
    private double dirSocialImpactIndex;
    private double starBoxImpactIndex;
    private double starSocialImpactIndex;
    
    /**
     * 0：非系列电影
     * 1：系列电影
     */
    private int isSeries;
    private int trailerViews;
    private int trailerPos;
    private int trailerNeg;
    /**
     * 0：千万以下
     * 1：1-5千万
     * 2：5千万-1亿
     * 3：1亿-5亿
     * 4：5亿-10亿
     * 5:10亿以上
     */
    private int boxClass;
    
    public MovieInfo(String name, String type) {
        this.name = name;
        this.type = type;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCountry() {
        return country;
    }

    public void setCountry(int country) {
        this.country = country;
    }

    public int getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(int releaseTime) {
        this.releaseTime = releaseTime;
    }

    public double getDirBoxImpactIndex() {
        return dirBoxImpactIndex;
    }

    public void setDirBoxImpactIndex(double dirBoxImpactIndex) {
        this.dirBoxImpactIndex = dirBoxImpactIndex;
    }

    public double getStarBoxImpactIndex() {
        return starBoxImpactIndex;
    }

    public void setStarBoxImpactIndex(double starBoxImpactIndex) {
        this.starBoxImpactIndex = starBoxImpactIndex;
    }

    public int getIsSeries() {
        return isSeries;
    }

    public void setIsSeries(int isSeries) {
        this.isSeries = isSeries;
    }

    public double getDirSocialImpactIndex() {
        return dirSocialImpactIndex;
    }

    public void setDirSocialImpactIndex(double dirSocialImpactIndex) {
        this.dirSocialImpactIndex = dirSocialImpactIndex;
    }

    public double getStarSocialImpactIndex() {
        return starSocialImpactIndex;
    }

    public void setStarSocialImpactIndex(double starSocialImpactIndex) {
        this.starSocialImpactIndex = starSocialImpactIndex;
    }

    public int getBoxClass() {
        return boxClass;
    }

    public void setBoxClass(int boxClass) {
        this.boxClass = boxClass;
    }

    public int getTrailerViews() {
        return trailerViews;
    }

    public void setTrailerViews(int trailerViews) {
        this.trailerViews = trailerViews;
    }

    public int getTrailerPos() {
        return trailerPos;
    }

    public void setTrailerPos(int trailerPos) {
        this.trailerPos = trailerPos;
    }

    public int getTrailerNeg() {
        return trailerNeg;
    }

    public void setTrailerNeg(int trailerNeg) {
        this.trailerNeg = trailerNeg;
    }
    
    @Override
    public String toString() {
//        return name + "," + type + "," + country + "," + releaseTime + "," + 
//                dirBoxImpactIndex + "," + starBoxImpactIndex + ","  + 
//                dirSocialImpactIndex + "," + starSocialImpactIndex + "," + isSeries + 
//                "," + boxClass;
//        return name + "," + type + "," + country + "," + releaseTime + "," + 
//                dirBoxImpactIndex + "," + starBoxImpactIndex + "," + boxClass;
        return name + "," + type + "," + country + "," + releaseTime + "," + 
                dirBoxImpactIndex + "," + starBoxImpactIndex + "," + 
                trailerViews + "," + trailerPos + "," + trailerNeg + "," + boxClass;
    }
    
}
