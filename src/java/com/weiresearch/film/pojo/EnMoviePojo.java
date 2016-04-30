/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.weiresearch.film.pojo;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author GigaLiu
 */
public class EnMoviePojo {

    private long id;
    private String name;
    /**
     * 0：爱情 1：动作 2：喜剧 3：剧情 4：科幻 5：魔幻 6：动画 7：惊悚 8：战争 9：纪实 10：歌舞
     */
    private int type;
    /**
     * 0：中国 1：美国 2：港台 3：其他
     */
    private int country;
    /**
     * 2011-2015
     */
    private int releaseYear;
    /**
     * 0：非节日档 1：暑期档 2：寒假档
     */
    private int period;
    private int is3D;
    private int isIMAX;
    
    private long dirId;
    private long starOneId;
    private long starTwoId;
    private double dirBoxIndex = -1;
    private double starOneBoxIndex = -1;
    private double starTwoBoxIndex = -1;
    
    /**
     * 主创指数
     */
    private List<Double> chiefIndexs;
    private double videoChiefIndex = -1;
    
    /**
     * 营销事件
     */
    private int marketCount;

    /**
     * 0：非系列电影 1：系列电影
     */
    private int isSeries;
    
    /**
     * 0：非ip电影 1：ip电影
     */
    private int isIp;
    /**
     * 0：千万以下 1：1-5千万 2：5千万-1亿 3：1亿-5亿 4：5亿-10亿 5:10亿以上
     */
    private int boxClass;

    public EnMoviePojo(long id, String name) {
        this.id = id;
        this.name = name;
        this.chiefIndexs = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getCountry() {
        return country;
    }

    public void setCountry(int country) {
        this.country = country;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public int getIs3D() {
        return is3D;
    }

    public void setIs3D(int is3D) {
        this.is3D = is3D;
    }

    public int getIsIMAX() {
        return isIMAX;
    }

    public void setIsIMAX(int isIMAX) {
        this.isIMAX = isIMAX;
    }

    public long getDirId() {
        return dirId;
    }

    public void setDirId(long dirId) {
        this.dirId = dirId;
    }

    public long getStarOneId() {
        return starOneId;
    }

    public void setStarOneId(long starOneId) {
        this.starOneId = starOneId;
    }

    public long getStarTwoId() {
        return starTwoId;
    }

    public void setStarTwoId(long starTwoId) {
        this.starTwoId = starTwoId;
    }

    public double getDirBoxIndex() {
        return dirBoxIndex;
    }

    public void setDirBoxIndex(double dirBoxIndex) {
        this.dirBoxIndex = dirBoxIndex;
    }

    public double getStarOneBoxIndex() {
        return starOneBoxIndex;
    }

    public void setStarOneBoxIndex(double starOneBoxIndex) {
        this.starOneBoxIndex = starOneBoxIndex;
    }

    public double getStarTwoBoxIndex() {
        return starTwoBoxIndex;
    }

    public void setStarTwoBoxIndex(double starTwoBoxIndex) {
        this.starTwoBoxIndex = starTwoBoxIndex;
    }

    public int getIsSeries() {
        return isSeries;
    }

    public void setIsSeries(int isSeries) {
        this.isSeries = isSeries;
    }

    public List<Double> getChiefIndexs() {
        return chiefIndexs;
    }
    
    public void addChiefIndex(double impactIndex) {
        this.chiefIndexs.add(impactIndex);
    }

    public void setChiefIndexs(List<Double> chiefIndexs) {
        this.chiefIndexs = chiefIndexs;
    }
    
    public double getVideoChiefIndex() {
        return videoChiefIndex;
    }

    public void setVideoChiefIndex(double videoChiefIndex) {
        this.videoChiefIndex = videoChiefIndex;
    }

    public int getIsIp() {
        return isIp;
    }

    public void setIsIp(int isIp) {
        this.isIp = isIp;
    }

    public int getMarketCount() {
        return marketCount;
    }

    public void setMarketCount(int marketCount) {
        this.marketCount = marketCount;
    }
    
    public int getBoxClass() {
        return boxClass;
    }

    public void setBoxClass(int boxClass) {
        this.boxClass = boxClass;
    }

    @Override
    public String toString() {
        return id + "," + type + "," + country + "," + releaseYear 
                + "," + period + "," + is3D + "," + isIMAX + "," + dirBoxIndex 
                + "," + starOneBoxIndex + "," + starTwoBoxIndex + "," + boxClass;
    }
    
}
