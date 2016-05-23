/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.weiresearch.film.service;

import com.google.gson.Gson;
import com.weiresearch.film.entity.Video;
import com.weiresearch.film.facade.impl.EnVideoFacade;
import com.weiresearch.film.model.MovieModel;
import com.weiresearch.film.model.SingletonPredictModel;
import com.weiresearch.film.pojo.EnMoviePojo;
import com.weiresearch.film.pojo.PredictResPojo;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
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

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Video find(@PathParam("id") Integer id) {
        return _videoFacade.find(id);
    }

    @GET
    @Path("/predict")
    @Produces({MediaType.APPLICATION_JSON})
    public String getPredictBoxoffice(@QueryParam("name") String videoName) {
        PredictResPojo resPojo = new PredictResPojo();

        Video video = this._videoFacade.findByName(videoName);
        if (video == null) {
            resPojo.setErrorCode(1);
            resPojo.setMsg("Invalid video name");
        } else {
            List<Object[]> predictInfo = this._videoFacade.findVideoPredictInfo(videoName);
            EnMoviePojo enMoviePojo;
            if (predictInfo != null && !predictInfo.isEmpty()
                    && (enMoviePojo = MovieModel.convertData(predictInfo)) != null) {
                SingletonPredictModel.getInstance().predict(enMoviePojo, resPojo);
                resPojo.setPredictInfo(enMoviePojo);
            } else {
                resPojo.setErrorCode(2);
                resPojo.setMsg("Can't find correspond star info");
            }
        }
        return new Gson().toJson(resPojo);
    }
    
    @GET
    @Path("/predict/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public String getPredictBoxofficeById(@PathParam("id") Integer videoId) {
        PredictResPojo resPojo = new PredictResPojo();

        Video video = this._videoFacade.find(videoId);
        if (video == null) {
            resPojo.setErrorCode(1);
            resPojo.setMsg("Invalid video id");
        } else {
            List<Object[]> predictInfo = this._videoFacade.findVideoPredictInfo(videoId);
            EnMoviePojo enMoviePojo;
            if (predictInfo != null && !predictInfo.isEmpty()
                    && (enMoviePojo = MovieModel.convertData(predictInfo)) != null) {
                SingletonPredictModel.getInstance().predict(enMoviePojo, resPojo);
                resPojo.setPredictInfo(enMoviePojo);
            } else {
                resPojo.setErrorCode(2);
                resPojo.setMsg("Can't find correspond star info");
            }
        }
        return new Gson().toJson(resPojo);
    }
}
