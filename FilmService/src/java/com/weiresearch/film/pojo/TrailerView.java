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
public class TrailerView implements Comparable<TrailerView> {

    private int views;
    private int willing;
    private int positive;
    private int negtive;

    public TrailerView() {
        this.views = -1;
        this.willing = -1;
        this.positive = -1;
        this.negtive = -1;
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

    @Override
    public int compareTo(TrailerView o) {
        return o.views - this.views;
    }
}
