/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.weiresearch.film.facade.impl;

import com.weiresearch.film.entity.Star;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author GigaLiu
 */
@Stateless
public class EnStarFacade extends AbstractFacade<Star> {

    @PersistenceContext(unitName = "FilmPredictPU")
    private EntityManager em;

    public EnStarFacade() {
        super(Star.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public List<Star> getRankStar() {
        Query query = this.getEntityManager().createQuery("select s from Star s "
                + "where s.impactIndex is not null order by s.impactIndex desc");
        query.setMaxResults(100);
        query.setHint("eclipselink.refresh", "True");
        try {
            List<Star> rankStars = (List<Star>) query.getResultList();
            if (!rankStars.isEmpty()) {
                return rankStars;
            }
        } catch (Exception e) {
            Logger.getLogger(EnStarWorkFacade.class.getName()).log(Level.SEVERE, null, e);
        }
        return new ArrayList<>();
    }
    
    public List<Star> getRankStarByCountry(String country) {
        Query query = this.getEntityManager().createQuery("select s from Star s "
                + "where s.impactIndex is not null and s.country = :country order by s.impactIndex desc");
        query.setParameter("country", country);
        query.setMaxResults(100);
        query.setHint("eclipselink.refresh", "True");
        try {
            List<Star> rankStars = (List<Star>) query.getResultList();
            if (!rankStars.isEmpty()) {
                return rankStars;
            }
        } catch (Exception e) {
            Logger.getLogger(EnStarWorkFacade.class.getName()).log(Level.SEVERE, null, e);
        }
        return new ArrayList<>();
    }
    
    public List<Star> getRankStarByRole(int role) {
        Query query = this.getEntityManager().createQuery("select distinct(s) from Star s join StarWork sw on s.id = sw.starId"
                + " where s.impactIndex is not null and sw.role = :role order by s.impactIndex desc");
        query.setMaxResults(100);
        query.setParameter("role", role);
        query.setHint("eclipselink.refresh", "True");
        try {
            List<Star> rankStars = query.getResultList();
            if (!rankStars.isEmpty()) {
                return rankStars;
            }
        } catch (Exception e) {
            Logger.getLogger(EnStarWorkFacade.class.getName()).log(Level.SEVERE, null, e);
        }
        return new ArrayList<>();
    }
    
    public List<Star> getRankStarByRoleAndCountry(int role, String country) {
        Query query = this.getEntityManager().createQuery("select distinct(s) from Star s join StarWork sw on s.id = sw.starId"
                + " where s.impactIndex is not null and s.country = :country and sw.role = :role order by s.impactIndex desc");
        query.setMaxResults(100);
        query.setParameter("country", country);
        query.setParameter("role", role);
        query.setHint("eclipselink.refresh", "True");
        try {
            List<Star> rankStars = query.getResultList();
            if (!rankStars.isEmpty()) {
                return rankStars;
            }
        } catch (Exception e) {
            Logger.getLogger(EnStarWorkFacade.class.getName()).log(Level.SEVERE, null, e);
        }
        return new ArrayList<>();
    }
}
