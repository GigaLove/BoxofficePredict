/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.weiresearch.film.facade.impl;

import com.weiresearch.film.entity.Video;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
    
    
}
