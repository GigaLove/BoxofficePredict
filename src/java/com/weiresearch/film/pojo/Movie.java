package com.weiresearch.film.pojo;

import java.util.ArrayList;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author GigaLiu
 */
public class Movie {
    private int id;
    private String name;
    private String type;
    private String releaseTime;
    private String country;
    private List<StarPojo> directorList;
    private List<StarPojo> starList;
    private TrailerView trailerView;
    private double boxoffice;
    
    public Movie() {
        
    }
    
    public Movie(int id, String name, String type, String country, String releaseTime) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.releaseTime = releaseTime;
        this.country = country;
        this.directorList = new ArrayList<>();
        this.starList = new ArrayList<>();
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(String releaseTime) {
        this.releaseTime = releaseTime;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<StarPojo> getDirectorList() {
        return directorList;
    }

    public void setDirectorList(List<StarPojo> directorList) {
        this.directorList = directorList;
    }

    public List<StarPojo> getStarList() {
        return starList;
    }

    public void setStarList(List<StarPojo> starList) {
        this.starList = starList;
    }

    public double getBoxoffice() {
        return boxoffice;
    }

    public void setBoxoffice(double boxoffice) {
        this.boxoffice = boxoffice;
    }

    public TrailerView getTrailerView() {
        return trailerView;
    }

    public void setTrailerView(TrailerView trailerView) {
        this.trailerView = trailerView;
    }
    
    
    public void addDirector(StarPojo director) {
        this.directorList.add(director);
    }
    
    public void addStar(StarPojo star) {
        this.starList.add(star);
    }
    
    @Override
    public String toString() {
        return "{" +  name + "," + type + "," + releaseTime + "," + country 
                + "," + directorList.toString() + "," + starList.toString() 
                + "," + boxoffice;
    }
    
}
