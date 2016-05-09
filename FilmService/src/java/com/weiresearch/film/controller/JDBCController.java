/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.weiresearch.film.controller;

import com.weiresearch.film.service.PropertiesLoader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author xiazy
 */
public class JDBCController {

    protected Connection _conn = null;
    private Statement _stmt = null;
    protected static final int OPERATOR_LARGER = 1;
    protected static final int OPERATOR_SMALLER = 2;

    public JDBCController() {
        try {
            PropertiesLoader.getInstance().getProp().getProperty("mysql.pwd");
            _conn = DriverManager.getConnection(PropertiesLoader.getInstance().getProp().getProperty("mysql.url"),
                    PropertiesLoader.getInstance().getProp().getProperty("mysql.user"),
                    PropertiesLoader.getInstance().getProp().getProperty("mysql.pwd"));
        } catch (SQLException ex) {
            Logger.getLogger(JDBCController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ResultSet query(String sql) {
        Statement stmt = null;
        ResultSet ret = null;
        try {
            stmt = _conn.createStatement();
            ret = stmt.executeQuery(sql);
        } catch (SQLException ex) {
            Logger.getLogger(JDBCController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    Logger.getLogger(JDBCController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
        return ret;
    }

    public Object edit(String sql) {
        Object result = null;
        Statement stmt = null;
        try {
            stmt = _conn.createStatement();
            stmt.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(JDBCController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    Logger.getLogger(JDBCController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
        return result;
    }

    protected Object create(String sql) {
        Object result = null;
        Statement stmt = null;
        try {
            stmt = _conn.createStatement();
            stmt.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = stmt.getGeneratedKeys();
            rs.next();
            result = rs.getInt(1);
        } catch (SQLException ex) {
            Logger.getLogger(JDBCController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    Logger.getLogger(JDBCController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
        return result;
    }

    public void commit() {
        if (this._stmt != null) {
            try {
                this._stmt.executeBatch();
            } catch (SQLException ex) {
                Logger.getLogger(JDBCController.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    this._stmt.close();
                    this._stmt = null;
                } catch (SQLException ex) {
                    Logger.getLogger(JDBCController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void close() {
        try {
            if (this._conn != null) {
                this._conn.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(JDBCController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
