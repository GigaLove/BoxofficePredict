/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.weiresearch.film.service;

import com.weiresearch.film.entity.Star;
import com.weiresearch.film.entity.StarWork;
import com.weiresearch.film.facade.impl.EnStarFacade;
import com.weiresearch.film.facade.impl.EnStarWorkFacade;
import com.weiresearch.film.model.StarImpactModel;
import com.weiresearch.film.pojo.StarYearRoleBoxPojo;
import com.weiresearch.film.util.MovieConst;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.DefaultValue;
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
@Path("/star")
public class StarRest {

    @EJB
    private EnStarWorkFacade _starWorkFacade;
    @EJB
    private EnStarFacade _starFacade;

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Star find(@PathParam("id") Integer id) {
        return _starFacade.find(id);
    }

    @GET
    @Path("/index/{id}")
    @Produces({MediaType.TEXT_PLAIN})
    public double getStarImpactIndex(@PathParam("id") Integer starId) {
        Star starInfo = _starFacade.find(starId);
        if (starInfo == null) {
            return -1;
        } else {
            Double impactIndex = starInfo.getImpactIndex();
            if (impactIndex == null) {
                int maxYear = Calendar.getInstance().get(Calendar.YEAR) - 1;
                int minYear = maxYear - MovieConst.YEAR_SPAN + 1;

                List<Object[]> starWorks = _starWorkFacade.getStarWorkByReleaseYear(starId, minYear, maxYear);
                List<StarYearRoleBoxPojo> yearRoleBoxs = new ArrayList<>();
                for (Object[] attrs : starWorks) {
                    int role = (int) attrs[1];
                    int releaseYear = (int) attrs[2];
                    int workCount = (int) (long) attrs[3];
                    int boxoffice = ((BigDecimal) attrs[4]).intValue();
                    yearRoleBoxs.add(new StarYearRoleBoxPojo(starId, role, releaseYear, workCount, boxoffice));
                }
                impactIndex = StarImpactModel.computeIndexByWorkMatrix(yearRoleBoxs, minYear);
                starInfo.setImpactIndex(impactIndex);
                _starFacade.edit(starInfo);
            }
            return impactIndex;
        }
    }

    @GET
    @Path("/work")
    @Produces({MediaType.APPLICATION_JSON})
    public List<StarWork> getStarWork(@QueryParam("id") Integer starId, @DefaultValue("0") @QueryParam("role") Integer role,
            @DefaultValue("0") @QueryParam("minYear") Integer minYear, @DefaultValue("2016") @QueryParam("maxYear") Integer maxYear) {
        List<StarWork> starWorks;
        if (role == 0) {
            starWorks = this._starWorkFacade.getStarWorkByYear(starId, minYear, maxYear);
        } else {
            starWorks = this._starWorkFacade.getStarWorkByRoleAndYear(starId, role, minYear, maxYear);
        }
        return starWorks;
    }
    
}
