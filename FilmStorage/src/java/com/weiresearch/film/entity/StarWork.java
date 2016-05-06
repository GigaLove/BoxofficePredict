/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.weiresearch.film.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author GigaLiu
 */
@Entity
@Table(name = "star_work")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "StarWork.findAll", query = "SELECT s FROM StarWork s"),
    @NamedQuery(name = "StarWork.findById", query = "SELECT s FROM StarWork s WHERE s.id = :id"),
    @NamedQuery(name = "StarWork.findByStarId", query = "SELECT s FROM StarWork s WHERE s.starId = :starId"),
    @NamedQuery(name = "StarWork.findByStarName", query = "SELECT s FROM StarWork s WHERE s.starName = :starName"),
    @NamedQuery(name = "StarWork.findByVideoId", query = "SELECT s FROM StarWork s WHERE s.videoId = :videoId"),
    @NamedQuery(name = "StarWork.findByVideoName", query = "SELECT s FROM StarWork s WHERE s.videoName = :videoName"),
    @NamedQuery(name = "StarWork.findByRole", query = "SELECT s FROM StarWork s WHERE s.role = :role"),
    @NamedQuery(name = "StarWork.findByReleaseYear", query = "SELECT s FROM StarWork s WHERE s.releaseYear = :releaseYear"),
    @NamedQuery(name = "StarWork.findByBoxoffice", query = "SELECT s FROM StarWork s WHERE s.boxoffice = :boxoffice")})
public class StarWork implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "star_id")
    private int starId;
    @Size(max = 256)
    @Column(name = "star_name")
    private String starName;
    @Basic(optional = false)
    @NotNull
    @Column(name = "video_id")
    private int videoId;
    @Size(max = 256)
    @Column(name = "video_name")
    private String videoName;
    @Basic(optional = false)
    @NotNull
    @Column(name = "role")
    private Integer role;
    @Column(name = "release_year")
    private Integer releaseYear;
    @Basic(optional = false)
    @NotNull
    @Column(name = "boxoffice")
    private int boxoffice;

    public StarWork() {
    }

    public StarWork(Integer id) {
        this.id = id;
    }

    public StarWork(Integer id, int starId, int videoId, int role, int boxoffice) {
        this.id = id;
        this.starId = starId;
        this.videoId = videoId;
        this.role = role;
        this.boxoffice = boxoffice;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

    public int getBoxoffice() {
        return boxoffice;
    }

    public void setBoxoffice(int boxoffice) {
        this.boxoffice = boxoffice;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StarWork)) {
            return false;
        }
        StarWork other = (StarWork) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.weiresearch.film.entity.StarWork[ id=" + id + " ]";
    }
    
}
