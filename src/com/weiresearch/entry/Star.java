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
public class Star implements Comparable<Star> {

    private long id;
    private String name;
    private int type;
    private StarSocial starSocial;
    private double impactIndex = -1;

    public Star() {

    }

    public Star(String name) {
        this.name = name;
    }

    public Star(long id, String name, int type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public Star(String name, double impactIndex, int type) {
        this.name = name;
        this.impactIndex = impactIndex;
        this.type = type;
    }

    public Star(String name, int type, String weiboUrl, int fans) {
        this.name = name;
        this.type = type;
        this.starSocial = new StarSocial(weiboUrl, fans);
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

    public double getImpactIndex() {
        return impactIndex;
    }

    public void setImpactIndex(double impactIndex) {
        this.impactIndex = impactIndex;
    }
    
    public void setWeiboUrl(String url) {
        if (this.starSocial == null) {
            this.starSocial = new StarSocial(url);
        } else {
            this.starSocial.setWeiboUrl(url);
        }
    }
    
    public void setFans(int fans) {
        if (this.starSocial == null) {
            this.starSocial = new StarSocial(fans);
        } else {
            this.starSocial.setFans(fans);
        }
    }
    
    public int getFans() {
        if (this.starSocial != null) {
            return this.starSocial.getFans();
        }
        return 0;
    }

    @Override
    public String toString() {
        return "{" + id + "," + name + "," + type + "," + impactIndex + "}";
    }

    @Override
    public int compareTo(Star o) {
        if (this.type > o.type) {
            return 1;
        } else if (this.type < o.type) {
            return -1;
        } else {
            if (this.impactIndex > o.impactIndex) {
                return -1;
            } else if (this.impactIndex < o.impactIndex) {
                return 1;
            } else {
                return 0;
            }
        }
    }

}
