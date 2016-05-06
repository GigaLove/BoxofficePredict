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
@Table(name = "star")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Star.findAll", query = "SELECT s FROM Star s"),
    @NamedQuery(name = "Star.findById", query = "SELECT s FROM Star s WHERE s.id = :id"),
    @NamedQuery(name = "Star.findByName", query = "SELECT s FROM Star s WHERE s.name = :name"),
    @NamedQuery(name = "Star.findByNameEn", query = "SELECT s FROM Star s WHERE s.nameEn = :nameEn"),
    @NamedQuery(name = "Star.findByCountry", query = "SELECT s FROM Star s WHERE s.country = :country"),
    @NamedQuery(name = "Star.findByCompany", query = "SELECT s FROM Star s WHERE s.company = :company"),
    @NamedQuery(name = "Star.findByWorkCount", query = "SELECT s FROM Star s WHERE s.workCount = :workCount"),
    @NamedQuery(name = "Star.findByImpactIndex", query = "SELECT s FROM Star s WHERE s.impactIndex = :impactIndex"),
    @NamedQuery(name = "Star.findBySocialIndex", query = "SELECT s FROM Star s WHERE s.socialIndex = :socialIndex"),
    @NamedQuery(name = "Star.findByUpdateOn", query = "SELECT s FROM Star s WHERE s.updateOn = :updateOn")})
public class Star implements Serializable {
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
    @Column(name = "country")
    private String country;
    @Lob
    @Size(max = 65535)
    @Column(name = "description")
    private String description;
    @Size(max = 256)
    @Column(name = "company")
    private String company;
    @Column(name = "work_count")
    private Integer workCount;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "impact_index")
    private Double impactIndex;
    @Column(name = "social_index")
    private Double socialIndex;
    @Basic(optional = false)
    @NotNull
    @Column(name = "updateOn")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateOn;

    public Star() {
    }

    public Star(Integer id) {
        this.id = id;
    }

    public Star(Integer id, Date updateOn) {
        this.id = id;
        this.updateOn = updateOn;
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Integer getWorkCount() {
        return workCount;
    }

    public void setWorkCount(Integer workCount) {
        this.workCount = workCount;
    }

    public Double getImpactIndex() {
        return impactIndex;
    }

    public void setImpactIndex(Double impactIndex) {
        this.impactIndex = impactIndex;
    }

    public Double getSocialIndex() {
        return socialIndex;
    }

    public void setSocialIndex(Double socialIndex) {
        this.socialIndex = socialIndex;
    }

    public Date getUpdateOn() {
        return updateOn;
    }

    public void setUpdateOn(Date updateOn) {
        this.updateOn = updateOn;
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
        if (!(object instanceof Star)) {
            return false;
        }
        Star other = (Star) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.weiresearch.film.entity.Star[ id=" + id + " ]";
    }
    
}
