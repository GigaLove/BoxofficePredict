/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.weiresearch.data.entry;

/**
 *
 * @author GigaLiu
 */
public class MovieIns {
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
    
    private double directorImpactIndex;
    private double starImpactIndex;
    /**
     * 0：千万以下
     * 1：1-5千万
     * 2：5千万-1亿
     * 3：1亿-5亿
     * 4：5亿-10亿
     * 5:10亿以上
     */
    private int boxClass;
    
    public MovieIns(String name, String type) {
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

    public double getDirectorImpactIndex() {
        return directorImpactIndex;
    }

    public void setDirectorImpactIndex(double directorImpactIndex) {
        this.directorImpactIndex = directorImpactIndex;
    }

    public double getStarImpactIndex() {
        return starImpactIndex;
    }

    public void setStarImpactIndex(double starImpactIndex) {
        this.starImpactIndex = starImpactIndex;
    }

    public int getBoxClass() {
        return boxClass;
    }

    public void setBoxClass(int boxClass) {
        this.boxClass = boxClass;
    }

    @Override
    public String toString() {
        return name + "," + type + "," + country + "," + releaseTime + "," + 
                directorImpactIndex + "," + starImpactIndex + "," + boxClass;
    }
    
}
