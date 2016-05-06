/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.weiresearch.film.pojo;

import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author GigaLiu
 */
@XmlRootElement
public class StarWorkPojo {

    private int starId;
    private String starName;
    private List<WorkInfo> works;

    public int getStarId() {
        return starId;
    }

    public void setStarId(int starId) {
        this.starId = starId;
    }

    public String getStarName() {
        return starName;
    }

    public void setStarName(String starName) {
        this.starName = starName;
    }

    public List<WorkInfo> getWorks() {
        return works;
    }

    public void setWorks(List<WorkInfo> works) {
        this.works = works;
    }

    @XmlRootElement
    public class WorkInfo {

        private int videoId;
        private String videoName;
        private int role;
        private int releaseYear;

        public int getVideoId() {
            return videoId;
        }

        public void setVideoId(int videoId) {
            this.videoId = videoId;
        }

        public String getVideoName() {
            return videoName;
        }

        public void setVideoName(String videoName) {
            this.videoName = videoName;
        }

        public int getRole() {
            return role;
        }

        public void setRole(int role) {
            this.role = role;
        }

        public int getReleaseYear() {
            return releaseYear;
        }

        public void setReleaseYear(int releaseYear) {
            this.releaseYear = releaseYear;
        }
    }
}
