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
public class TrailerView {

    private int views;
    private int willing;
    private int positive;
    private int negtive;
    
    public TrailerView() {
        views = -1;
        willing = -1;
        positive = -1;
        negtive = -1;
    }

    public TrailerView(int views, int willing, int positive, int negtive) {
        this.views = views;
        this.willing = willing;
        this.positive = positive;
        this.negtive = negtive;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public int getWilling() {
        return willing;
    }

    public void setWilling(int willing) {
        this.willing = willing;
    }

    public int getPositive() {
        return positive;
    }

    public void setPositive(int positive) {
        this.positive = positive;
    }

    public int getNegtive() {
        return negtive;
    }

    public void setNegtive(int negtive) {
        this.negtive = negtive;
    }
}
