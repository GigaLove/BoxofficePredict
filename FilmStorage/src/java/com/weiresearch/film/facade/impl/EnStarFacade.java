/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.weiresearch.film.facade.impl;

import com.weiresearch.film.entity.Star;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
}
