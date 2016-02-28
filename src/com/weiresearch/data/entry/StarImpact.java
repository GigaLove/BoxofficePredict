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
public class StarImpact extends Star {
    private int starRank;
    private int totalBoxoffice;
    private int mainWorkCount;
    private int highestGrossing;
    private int lowestGrossing;
    
    public StarImpact() {
        
    }
    
    public StarImpact(String name, int starRank, double impactIndex, int type,
            int totalBoxoffice, int mainWorkCount, int highestGrossing, int lowestGrossing) {
        super(name, impactIndex, type);
        this.starRank = starRank;
        this.totalBoxoffice = totalBoxoffice;
        this.mainWorkCount = mainWorkCount;
        this.highestGrossing = highestGrossing;
        this.lowestGrossing = lowestGrossing;
    }
    
    public int getStarRank() {
        return starRank;
    }

    public void setStarRank(int starRank) {
        this.starRank = starRank;
    }

    public int getTotalBoxoffice() {
        return totalBoxoffice;
    }

    public void setTotalBoxoffice(int totalBoxoffice) {
        this.totalBoxoffice = totalBoxoffice;
    }

    public int getMainWorkCount() {
        return mainWorkCount;
    }

    public void setMainWorkCount(int mainWorkCount) {
        this.mainWorkCount = mainWorkCount;
    }

    public int getHighestGrossing() {
        return highestGrossing;
    }

    public void setHighestGrossing(int highestGrossing) {
        this.highestGrossing = highestGrossing;
    }

    public int getLowestGrossing() {
        return lowestGrossing;
    }

    public void setLowestGrossing(int lowestGrossing) {
        this.lowestGrossing = lowestGrossing;
    }

}
