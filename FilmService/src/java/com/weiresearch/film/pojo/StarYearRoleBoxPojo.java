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
public class StarYearRoleBoxPojo {
    private int starId;
    private int role;
    private int year;
    private int workCount;
    private double boxoffice;

    public StarYearRoleBoxPojo(int starId, int role, int year, int workCount, double boxoffice) {
        this.starId = starId;
        this.role = role;
        this.year = year;
        this.workCount = workCount;
        this.boxoffice = boxoffice;
    }

    public int getStarId() {
        return starId;
    }

    public void setStarId(int starId) {
        this.starId = starId;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getWorkCount() {
        return workCount;
    }

    public void setWorkCount(int workCount) {
        this.workCount = workCount;
    }

    public double getBoxoffice() {
        return boxoffice;
    }

    public void setBoxoffice(double boxoffice) {
        this.boxoffice = boxoffice;
    }
    
    
}
