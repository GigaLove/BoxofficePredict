/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.weiresearch.film.facade.impl;

import com.weiresearch.film.entity.StarWork;
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
public class EnStarWorkFacade extends AbstractFacade<StarWork> {

    @PersistenceContext(unitName = "FilmPredictPU")
    private EntityManager em;

    public EnStarWorkFacade() {
        super(StarWork.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public List<Object[]> getAllStarWorkByReleaseYear(int minYear, int maxYear) {
        String queryStr = String.format("select star_id, role, release_year, count(*) as work_count,"
                + " sum(boxoffice) as total_boxoffice from star_work where boxoffice > 0 and release_year BETWEEN %s "
                + "and %s GROUP BY star_id, release_year, role order by star_id, release_year, role", minYear, maxYear);
        Query query = this.getEntityManager().createNativeQuery(queryStr);
        try {
            return (List<Object[]>) query.getResultList();
        } catch (Exception e) {
            Logger.getLogger(EnStarWorkFacade.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
    }

    public List<Object[]> getAllStarWorkAvgInfoByReleaseYear(int minYear, int maxYear) {
        String queryStr = String.format("select star_id, role, release_year, count(*) as work_count,"
                + " avg(boxoffice) as avg_boxoffice from star_work where boxoffice > 0 and release_year BETWEEN %s "
                + "and %s GROUP BY star_id, release_year, role order by star_id, release_year, role", minYear, maxYear);
        Query query = this.getEntityManager().createNativeQuery(queryStr);
        try {
            return (List<Object[]>) query.getResultList();
        } catch (Exception e) {
            Logger.getLogger(EnStarWorkFacade.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
    }

    /**
     * 获取特定演员一定时间里的影视作品统计信息
     *
     * @param starId
     * @param minYear
     * @param maxYear
     * @return
     */
    public List<Object[]> getStarWorkByReleaseYear(int starId, int minYear, int maxYear) {
        String queryStr = String.format("select star_id, role, release_year, count(*) as work_count,"
                + " sum(boxoffice) as total_boxoffice from star_work where star_id = %s and boxoffice > 0 and release_year BETWEEN %s "
                + "and %s GROUP BY release_year, role", starId, minYear, maxYear);
        Query query = this.getEntityManager().createNativeQuery(queryStr);
        try {
            return (List<Object[]>) query.getResultList();
        } catch (Exception e) {
            Logger.getLogger(EnStarWorkFacade.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
    }

    /**
     * 获取特定演员一定时间里的影视作品信息
     *
     * @param starId
     * @param minYear
     * @param maxYear
     * @return
     */
    public List<StarWork> getStarWorkByYear(int starId, int minYear, int maxYear) {
        Query query = this.getEntityManager().createQuery("select sw from StarWork "
                + "sw where sw.starId=:starId and sw.releaseYear between :minYear "
                + "and :maxYear order by sw.role asc, sw.releaseYear desc");
        query.setParameter("starId", starId);
        query.setParameter("minYear", minYear);
        query.setParameter("maxYear", maxYear);
        query.setHint("eclipselink.refresh", "True");
        try {
            return (List<StarWork>) query.getResultList();
        } catch (Exception e) {
            Logger.getLogger(EnStarWorkFacade.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
    }

    public List<StarWork> getStarWorkByRoleAndYear(int starId, int role, int minYear, int maxYear) {
        Query query = this.getEntityManager().createQuery("select sw from StarWork "
                + "sw where sw.starId=:starId and sw.role=:role and sw.releaseYear "
                + "between :minYear and :maxYear order by sw.releaseYear desc");
        query.setParameter("starId", starId);
        query.setParameter("role", role);
        query.setParameter("minYear", minYear);
        query.setParameter("maxYear", maxYear);
        query.setHint("eclipselink.refresh", "True");
        try {
            return (List<StarWork>) query.getResultList();
        } catch (Exception e) {
            Logger.getLogger(EnStarWorkFacade.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
    }
}
