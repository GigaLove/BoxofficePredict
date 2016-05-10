/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.weiresearch.film.service;

import com.weiresearch.film.model.SingletonPredictModel;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 *
 * @author GigaLiu
 */
public class ModelListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        SingletonPredictModel.getInstance();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }

}
