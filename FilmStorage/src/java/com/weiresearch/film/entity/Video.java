/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.weiresearch.film.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author GigaLiu
 */
@Entity
@Table(name = "video")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Video.findAll", query = "SELECT v FROM Video v"),
    @NamedQuery(name = "Video.findById", query = "SELECT v FROM Video v WHERE v.id = :id"),
    @NamedQuery(name = "Video.findByName", query = "SELECT v FROM Video v WHERE v.name = :name"),
    @NamedQuery(name = "Video.findByNameEn", query = "SELECT v FROM Video v WHERE v.nameEn = :nameEn"),
    @NamedQuery(name = "Video.findByType", query = "SELECT v FROM Video v WHERE v.type = :type"),
    @NamedQuery(name = "Video.findByFormat", query = "SELECT v FROM Video v WHERE v.format = :format"),
    @NamedQuery(name = "Video.findByReleaseTime", query = "SELECT v FROM Video v WHERE v.releaseTime = :releaseTime"),
    @NamedQuery(name = "Video.findByRuntime", query = "SELECT v FROM Video v WHERE v.runtime = :runtime"),
    @NamedQuery(name = "Video.findByCountry", query = "SELECT v FROM Video v WHERE v.country = :country"),
    @NamedQuery(name = "Video.findBySeries", query = "SELECT v FROM Video v WHERE v.series = :series"),
    @NamedQuery(name = "Video.findByIp", query = "SELECT v FROM Video v WHERE v.ip = :ip"),
    @NamedQuery(name = "Video.findByMarketCount", query = "SELECT v FROM Video v WHERE v.marketCount = :marketCount"),
    @NamedQuery(name = "Video.findByBoxoffice", query = "SELECT v FROM Video v WHERE v.boxoffice = :boxoffice"),
    @NamedQuery(name = "Video.findByAvgTrailerView", query = "SELECT v FROM Video v WHERE v.avgTrailerView = :avgTrailerView"),
    @NamedQuery(name = "Video.findByMaxTrailerView", query = "SELECT v FROM Video v WHERE v.maxTrailerView = :maxTrailerView"),
    @NamedQuery(name = "Video.findByUpdateTime", query = "SELECT v FROM Video v WHERE v.updateTime = :updateTime")})
public class Video implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Size(max = 256)
    @Column(name = "name")
    private String name;
    @Size(max = 256)
    @Column(name = "name_en")
    private String nameEn;
    @Size(max = 256)
    @Column(name = "type")
    private String type;
    @Size(max = 100)
    @Column(name = "format")
    private String format;
    @Column(name = "release_time")
    @Temporal(TemporalType.DATE)
    private Date releaseTime;
    @Size(max = 100)
    @Column(name = "runtime")
    private String runtime;
    @Size(max = 256)
    @Column(name = "country")
    private String country;
    @Basic(optional = false)
    @NotNull
    @Column(name = "series")
    private int series;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ip")
    private int ip;
    @Lob
    @Size(max = 65535)
    @Column(name = "market_event")
    private String marketEvent;
    @Column(name = "market_count")
    private Integer marketCount;
    @Column(name = "boxoffice")
    private Integer boxoffice;
    @Column(name = "avg_trailer_view")
    private Integer avgTrailerView;
    @Column(name = "max_trailer_view")
    private Integer maxTrailerView;
    @Column(name = "update_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    public Video() {
    }

    public Video(Integer id) {
        this.id = id;
    }

    public Video(Integer id, int series, int ip) {
        this.id = id;
        this.series = series;
        this.ip = ip;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Date getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(Date releaseTime) {
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

    public int getSeries() {
        return series;
    }

    public void setSeries(int series) {
        this.series = series;
    }

    public int getIp() {
        return ip;
    }

    public void setIp(int ip) {
        this.ip = ip;
    }

    public String getMarketEvent() {
        return marketEvent;
    }

    public void setMarketEvent(String marketEvent) {
        this.marketEvent = marketEvent;
    }

    public Integer getMarketCount() {
        return marketCount;
    }

    public void setMarketCount(Integer marketCount) {
        this.marketCount = marketCount;
    }

    public Integer getBoxoffice() {
        return boxoffice;
    }

    public void setBoxoffice(Integer boxoffice) {
        this.boxoffice = boxoffice;
    }

    public Integer getAvgTrailerView() {
        return avgTrailerView;
    }

    public void setAvgTrailerView(Integer avgTrailerView) {
        this.avgTrailerView = avgTrailerView;
    }

    public Integer getMaxTrailerView() {
        return maxTrailerView;
    }

    public void setMaxTrailerView(Integer maxTrailerView) {
        this.maxTrailerView = maxTrailerView;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
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
        if (!(object instanceof Video)) {
            return false;
        }
        Video other = (Video) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.weiresearch.film.entity.Video[ id=" + id + " ]";
    }
    
}
