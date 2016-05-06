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
public class MovieTrailer {

    private int mid;
    private String movieName;
    private final List<List<TrailerView>> viewList;
    private TrailerView avgTrailerInfo;

    public MovieTrailer(int mid, String movieName) {
        this.mid = mid;
        this.movieName = movieName;
        this.viewList = new ArrayList<>();
    }

    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public TrailerView getAvgTrailerInfo() {
        return avgTrailerInfo;
    }

    public void addTrailerView(int views, int willing, int positive, int negtive, boolean flag) {
        List<TrailerView> trailerList;
        if (flag) {
            trailerList = new ArrayList<>();
            trailerList.add(new TrailerView(views, willing, positive, negtive));
            viewList.add(trailerList);
        } else {
            trailerList = viewList.get(viewList.size() - 1);
            trailerList.add(new TrailerView(views, willing, positive, negtive));
        }
    }
    
    public void addTrailerList(List<TrailerView> trailerList) {
        viewList.add(trailerList);
    }

    public void computeAvgTrailerInfo() {
        int views = 0;
        int willings = 0;
        int pos = 0;
        int neg = 0;

        for (List<TrailerView> trailerList : viewList) {
            for (TrailerView view : trailerList) {
                views += view.getViews();
                willings += view.getWilling();
                pos += view.getPositive();
                neg += view.getNegtive();
            }
        }
        views /= viewList.size();
        willings /= viewList.size();
        pos /= viewList.size();
        neg /= viewList.size();
        avgTrailerInfo = new TrailerView(views, willings, pos, neg);
    }
}
