/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.weiresearch.entry;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;

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
    private JSONArray worksJson;
    
    public StarImpact() {
        
    }
    
    public StarImpact(long id, String name, int type, String worksJson) {
        super(id, name, type);
        try {
            this.worksJson = new JSONArray(worksJson);
        } catch (JSONException ex) {
            this.worksJson = null;
            System.out.println(this);
            Logger.getLogger(StarImpact.class.getName()).log(Level.SEVERE, null, ex);
        }
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

    public JSONArray getWorksJson() {
        return worksJson;
    }

    public void setWorksJson(JSONArray worksJson) {
        this.worksJson = worksJson;
    }

    @Override
    public String toString() {
        return super.toString();
    }
    
}
