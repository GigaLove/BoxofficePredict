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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author GigaLiu
 */
@Entity
@Table(name = "video_star_rel")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VideoStarRel.findAll", query = "SELECT v FROM VideoStarRel v"),
    @NamedQuery(name = "VideoStarRel.findById", query = "SELECT v FROM VideoStarRel v WHERE v.id = :id"),
    @NamedQuery(name = "VideoStarRel.findByMovieId", query = "SELECT v FROM VideoStarRel v WHERE v.movieId = :movieId"),
    @NamedQuery(name = "VideoStarRel.findByStarId", query = "SELECT v FROM VideoStarRel v WHERE v.starId = :starId"),
    @NamedQuery(name = "VideoStarRel.findByRole", query = "SELECT v FROM VideoStarRel v WHERE v.role = :role"),
    @NamedQuery(name = "VideoStarRel.findByRank", query = "SELECT v FROM VideoStarRel v WHERE v.rank = :rank")})
public class VideoStarRel implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "movie_id")
    private int movieId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "star_id")
    private int starId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "role")
    private int role;
    @Basic(optional = false)
    @NotNull
    @Column(name = "rank")
    private int rank;

    public VideoStarRel() {
    }

    public VideoStarRel(Integer id) {
        this.id = id;
    }

    public VideoStarRel(Integer id, int movieId, int starId, int role, int rank) {
        this.id = id;
        this.movieId = movieId;
        this.starId = starId;
        this.role = role;
        this.rank = rank;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
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

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
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
        if (!(object instanceof VideoStarRel)) {
            return false;
        }
        VideoStarRel other = (VideoStarRel) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.weiresearch.film.entity.VideoStarRel[ id=" + id + " ]";
    }
    
}
