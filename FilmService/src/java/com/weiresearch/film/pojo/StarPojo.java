/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.weiresearch.film.pojo;

/**
 *
 * @author GigaLiu
 */
public class StarPojo implements Comparable<StarPojo> {

    private long id;
    private String name;
    private String nameEn;
    private int role;
    private StarSocialPojo starSocial;
    private double impactIndex = -1;

    public StarPojo() {

    }

    public StarPojo(String name) {
        this.name = name;
    }

    public StarPojo(long id, String name, int role) {
        this.id = id;
        this.name = name;
        this.role = role;
    }

    public StarPojo(long id, String name, String nameEn, int role) {
        this.id = id;
        this.name = name;
        this.nameEn = nameEn;
        this.role = role;
    }

    public StarPojo(String name, double impactIndex, int role) {
        this.name = name;
        this.impactIndex = impactIndex;
        this.role = role;
    }

    public StarPojo(String name, int type, String weiboUrl, int fans) {
        this.name = name;
        this.role = type;
        this.starSocial = new StarSocialPojo(weiboUrl, fans);
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

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public double getImpactIndex() {
        return impactIndex;
    }

    public void setImpactIndex(double impactIndex) {
        this.impactIndex = impactIndex;
    }

    public void setWeiboUrl(String url) {
        if (this.starSocial == null) {
            this.starSocial = new StarSocialPojo(url);
        } else {
            this.starSocial.setWeiboUrl(url);
        }
    }

    public void setFans(int fans) {
        if (this.starSocial == null) {
            this.starSocial = new StarSocialPojo(fans);
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
        return "{" + id + "," + name + "," + role + "," + impactIndex + "}";
    }

    @Override
    public int compareTo(StarPojo o) {
        if (this.role > o.role) {
            return 1;
        } else if (this.role < o.role) {
            return -1;
        } else {
            return 0;
//            if (this.impactIndex > o.impactIndex) {
//                return -1;
//            } else if (this.impactIndex < o.impactIndex) {
//                return 1;
//            } else {
//                return 0;
//            }
        }
    }

}
