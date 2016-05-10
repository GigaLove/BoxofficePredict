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
public class WeireMoviePojo {

    private int id;
    private String name;
    private String nameEn;
    private String format;
    private String type;
    private String releaseTime;
    private String runtime;
    private String country;
    private List<StarPojo> directorList;
    private List<StarPojo> starList;
    private TrailerViewPojo trailerView;
    private double boxoffice;

    public WeireMoviePojo() {

    }

    public WeireMoviePojo(int id) {
        this.id = id;
        this.starList = new ArrayList<>();
    }

    public WeireMoviePojo(int id, String name, String type, String country, String releaseTime) {
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

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(String releaseTime) {
        this.releaseTime = releaseTime;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
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

    public TrailerViewPojo getTrailerView() {
        return trailerView;
    }

    public void setTrailerView(TrailerViewPojo trailerView) {
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
        return "{" + name + "," + type + "," + releaseTime + "," + country
                + "," + directorList.toString() + "," + starList.toString()
                + "," + boxoffice;
    }

}
