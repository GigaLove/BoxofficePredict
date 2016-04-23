/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.weiresearch.film.service;

import com.weiresearch.film.entity.Star;
import com.weiresearch.film.facade.impl.EnStarFacade;
import com.weiresearch.film.facade.impl.EnStarWorkFacade;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;

/**
 *
 * @author GigaLiu
 */
@Stateless
@Path("starIndex")
public class StarIndexRest {

    @EJB
    private EnStarWorkFacade starWorkFacade;
    @EJB
    private EnStarFacade starFacade;
    
    @GET
    @Path("{id}")
    @Produces({"application/json"})
    public Star find(@PathParam("id") Integer id) {
        return starFacade.find(id);
    }

}
