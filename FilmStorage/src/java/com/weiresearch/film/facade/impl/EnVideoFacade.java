/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.weiresearch.film.facade.impl;

import com.weiresearch.film.entity.Video;
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
public class EnVideoFacade extends AbstractFacade<Video> {

    @PersistenceContext(unitName = "FilmPredictPU")
    private EntityManager em;

    public EnVideoFacade() {
        super(Video.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public Video findByName(String name) {
        Query query = this.getEntityManager().createNamedQuery("Video.findByName");
        query.setParameter("name", name);
        query.setHint("eclipselink.refresh", "True");
        try {
            List<Video> videos = query.getResultList();
            if (!videos.isEmpty()) {
                return videos.get(0);
            }
        } catch (Exception e) {
            Logger.getLogger(EnStarWorkFacade.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    public List<Object[]> findVideoPredictInfo(String name) {
        String queryStr = "select v.id, v.name, v.type, v.format, v.release_time, v.country, v.series, v.ip, "
                + "v.market_count, v.avg_trailer_view, v.max_trailer_view, v.boxoffice, s.id as star_id, vsr.role, vsr.rank, s.name as star_name, s.impact_index "
                + "from video v join video_star_rel vsr on v.id = vsr.movie_id join star s on vsr.star_id = s.id "
                + "where v.name = '" + name + "'and YEAR(v.release_time) >= 2016 and (v.country like '中国%' or "
                + "v.country like '美国%') and s.impact_index is not NULL order by v.id desc, vsr.role, vsr.rank";
        Query query = this.getEntityManager().createNativeQuery(queryStr);
        try {
            return (List<Object[]>) query.getResultList();
        } catch (Exception e) {
            Logger.getLogger(EnStarWorkFacade.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }
    
    public List<Object[]> findVideoPredictInfo(Integer id) {
        String queryStr = "select v.id, v.name, v.type, v.format, v.release_time, v.country, v.series, v.ip, "
                + "v.market_count, v.avg_trailer_view, v.max_trailer_view, v.boxoffice, s.id as star_id, vsr.role, vsr.rank, s.name as star_name, s.impact_index "
                + "from video v join video_star_rel vsr on v.id = vsr.movie_id join star s on vsr.star_id = s.id "
                + "where v.id = " + id + " and YEAR(v.release_time) >= 2016 and (v.country like '中国%' or "
                + "v.country like '美国%') and s.impact_index is not NULL order by v.id desc, vsr.role, vsr.rank";
        Query query = this.getEntityManager().createNativeQuery(queryStr);
        try {
            return (List<Object[]>) query.getResultList();
        } catch (Exception e) {
            Logger.getLogger(EnStarWorkFacade.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }
}
