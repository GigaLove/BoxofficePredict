/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.weiresearch.film.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author xiazy
 */
public class PropertiesLoader {

    private Properties prop = new Properties();
    private static PropertiesLoader _instance = null;

    protected PropertiesLoader() {
        this._load();
    }

    public static PropertiesLoader getInstance() {
        if (_instance == null) {
            _instance = new PropertiesLoader();
        }
        return _instance;
    }

    private void _load() {
        InputStream input = null;
        try {
            input = PropertiesLoader.class.getClassLoader().getResourceAsStream("conf.properties");
            prop.load(input);
        } catch (IOException ex) {
            Logger.getLogger(PropertiesLoader.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException ex) {
                    Logger.getLogger(PropertiesLoader.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public Properties getProp() {
        return prop;
    }

    public void setProp(Properties prop) {
        this.prop = prop;
    }

}
