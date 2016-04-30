/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.weiresearch.film.service;

import com.weiresearch.film.entity.Video;
import com.weiresearch.film.facade.impl.EnStarFacade;
import com.weiresearch.film.facade.impl.EnVideoFacade;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author GigaLiu
 */
@Stateless
@Path("/video")
public class VideoRest {

    @EJB
    private EnVideoFacade _videoFacade;
    @EJB
    private EnStarFacade _starFacade;

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Video find(@PathParam("id") Integer id) {
        return _videoFacade.find(id);
    }

    @GET
    @Path("/predict/{id}")
    @Produces({MediaType.TEXT_PLAIN})
    public double getPredictBoxoffice(@PathParam("id") Integer videoId) {
        Video videoInfo = _videoFacade.find(videoId);
        if (videoInfo != null) {

        }
        return 0;
    }
}
