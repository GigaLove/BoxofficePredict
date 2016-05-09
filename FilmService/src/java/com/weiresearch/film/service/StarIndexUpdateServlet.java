/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.weiresearch.film.service;

import com.google.gson.Gson;
import com.weiresearch.film.entity.Star;
import com.weiresearch.film.facade.impl.EnStarFacade;
import com.weiresearch.film.facade.impl.EnStarWorkFacade;
import com.weiresearch.film.model.StarImpactModel;
import com.weiresearch.film.pojo.ResPOJO;
import com.weiresearch.film.pojo.StarYearRoleBoxPojo;
import com.weiresearch.film.util.MovieConst;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author GigaLiu
 */
public class StarIndexUpdateServlet extends HttpServlet {

    @EJB
    private EnStarWorkFacade starWorkFacade;
    @EJB
    private EnStarFacade starFacade;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        ResPOJO res = new ResPOJO();

        
        
        

        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            Gson gson = new Gson();
            out.write(gson.toJson(res));
            out.flush();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    class StarIndexThread extends Thread {

        private final EnStarWorkFacade starWorkFacade;
        private final EnStarFacade starFacade;
        private final int method;

        public StarIndexThread(EnStarWorkFacade starWorkFacade, EnStarFacade starFacade, int method) {
            this.starWorkFacade = starWorkFacade;
            this.starFacade = starFacade;
            this.method = method;
        }

        @Override
        public void run() {
            // query all star works between minYear and maxYear
            Logger.getLogger(StarIndexUpdateServlet.class.getName()).log(Level.INFO,
                    "Start update all star impact index");
            if (this.method == 0) {
                computeIndexByRecentYears();
            } else if (this.method == 1) {
                computeIndexByYear();
            } else {
            }
            Logger.getLogger(StarIndexUpdateServlet.class.getName()).log(Level.INFO,
                    "All star impact index recompute completely");
        }

        /**
         * 基于近5年的影视作品信息计算影人的主创指数
         */
        private void computeIndexByRecentYears() {
            int maxYear = Calendar.getInstance().get(Calendar.YEAR) - 1;
            int minYear = Calendar.getInstance().get(Calendar.YEAR) - MovieConst.YEAR_SPAN;

            List<Object[]> starWorks = starWorkFacade.getAllStarWorkByReleaseYear(minYear, maxYear);
            Map<Integer, List<StarYearRoleBoxPojo>> yearRoleMap = new HashMap<>();
            List<StarYearRoleBoxPojo> yearRoleBoxs;
            int starId;
            for (Object[] attrs : starWorks) {
                starId = (int) attrs[0];
                int role = (int) attrs[1];
                int releaseYear = (int) attrs[2];
                int workCount = (int) (long) attrs[3];
                int boxoffice = ((BigDecimal) attrs[4]).intValue();
                if (!yearRoleMap.containsKey(starId)) {
                    yearRoleBoxs = new ArrayList<>();
                    yearRoleMap.put(starId, yearRoleBoxs);
                }
                yearRoleBoxs = yearRoleMap.get(starId);
                yearRoleBoxs.add(new StarYearRoleBoxPojo(starId, role, releaseYear, workCount, boxoffice));
            }

            double impactIndex;
            Star star;
            for (Map.Entry<Integer, List<StarYearRoleBoxPojo>> entry : yearRoleMap.entrySet()) {
                starId = entry.getKey();
                yearRoleBoxs = entry.getValue();
                impactIndex = StarImpactModel.computeIndexByWorkMatrix(yearRoleBoxs, minYear);
                star = starFacade.find(starId);
                if (star != null) {
                    star.setImpactIndex(impactIndex);
                    starFacade.edit(star);
                }
            }

        }

        /**
         * 基于近8年的影视作品信息计算影人的主创指数
         */
        private void computeIndexByYear() {
            int maxYear = Calendar.getInstance().get(Calendar.YEAR) - 1;
            int minYear = 2000;

            List<Object[]> starWorks = starWorkFacade.getAllStarWorkAvgInfoByReleaseYear(minYear, maxYear);
            Map<Integer, List<StarYearRoleBoxPojo>> yearRoleMap = new HashMap<>();
            List<StarYearRoleBoxPojo> yearRoleBoxs;
            int starId;
            for (Object[] attrs : starWorks) {
                starId = (int) attrs[0];
                int role = (int) attrs[1];
                int releaseYear = (int) attrs[2];
                int workCount = (int) (long) attrs[3];
                double boxoffice = ((BigDecimal) attrs[4]).doubleValue();
                if (!yearRoleMap.containsKey(starId)) {
                    yearRoleBoxs = new ArrayList<>();
                    yearRoleMap.put(starId, yearRoleBoxs);
                }
                yearRoleBoxs = yearRoleMap.get(starId);
                yearRoleBoxs.add(new StarYearRoleBoxPojo(starId, role, releaseYear, workCount, boxoffice));
            }

            double impactIndex;
            Star star;
            for (Map.Entry<Integer, List<StarYearRoleBoxPojo>> entry : yearRoleMap.entrySet()) {
                starId = entry.getKey();
                yearRoleBoxs = entry.getValue();
                impactIndex = StarImpactModel.computeIndexByWorkMatrix2(yearRoleBoxs, minYear);
                star = starFacade.find(starId);
                if (star != null) {
                    star.setImpactIndex(impactIndex);
                    starFacade.edit(star);
                }
            }
        }

    }

}
