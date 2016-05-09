/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.weiresearch.film.pojo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 *
 * @author GigaLiu
 */
public class MovieTrailerPojo {

    private int mid;
    private String movieName;
    private Date releaseTime;
    private final List<List<TrailerViewPojo>> viewList;
    private TrailerViewPojo avgTrailerInfo;
    private TrailerViewPojo maxTrailerInfo;

    public MovieTrailerPojo(int mid, String movieName) {
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

    public TrailerViewPojo getAvgTrailerInfo() {
        return avgTrailerInfo;
    }

    public TrailerViewPojo getMaxTrailerInfo() {
        return maxTrailerInfo;
    }

    public Date getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(Date releaseTime) {
        this.releaseTime = releaseTime;
    }

    public void addTrailerView(int views, int willing, int positive, int negtive, boolean flag) {
        List<TrailerViewPojo> trailerList;
        if (flag) {
            trailerList = new ArrayList<>();
            trailerList.add(new TrailerViewPojo(views, willing, positive, negtive));
            viewList.add(trailerList);
        } else {
            trailerList = viewList.get(viewList.size() - 1);
            trailerList.add(new TrailerViewPojo(views, willing, positive, negtive));
        }
    }

    public void addTrailerList(List<TrailerViewPojo> trailerList) {
        viewList.add(trailerList);
    }

    public void computeAvgTrailerInfo() {
        int views = 0;
        int willings = 0;
        int pos = 0;
        int neg = 0;

        for (List<TrailerViewPojo> trailerList : viewList) {
            Collections.sort(trailerList);
            views += trailerList.get(0).getViews();
            willings += trailerList.get(0).getWilling();
            pos += trailerList.get(0).getPositive();
            neg += trailerList.get(0).getNegtive();
        }
        views /= viewList.size();
        willings /= viewList.size();
        pos /= viewList.size();
        neg /= viewList.size();
        avgTrailerInfo = new TrailerViewPojo(views, willings, pos, neg);
    }

    public void computeMaxTrailerInfo() {
        List<TrailerViewPojo> allTrailerViews = new ArrayList<>();

        for (List<TrailerViewPojo> trailerList : viewList) {
            allTrailerViews.addAll(trailerList);
        }
        Collections.sort(allTrailerViews);
        maxTrailerInfo = allTrailerViews.get(0);
    }

    @Override
    public String toString() {
        return "[" + mid + ", " + movieName + ", " + avgTrailerInfo.getViews()
                + ", " + maxTrailerInfo.getViews() + "]";
//        return "update video set avg_trailer_view = " + avgTrailerInfo.getViews()
//                + ", max_trailer_view = " + maxTrailerInfo.getViews()
//                + " where name = '" + movieName + "' and YEAR(release_time) >= 2011;";
    }

}
