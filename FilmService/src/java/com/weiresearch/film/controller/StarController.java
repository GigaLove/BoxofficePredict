/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.weiresearch.film.controller;

import com.weiresearch.film.pojo.StarPojo;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author GigaLiu
 */
public class StarController extends JDBCController {

    public StarController() {

    }

    public int find(StarPojo star) {
        Statement stmt = null;
        int starId = 0;
        try {
            stmt = _conn.createStatement();
            String query = String.format("select * from star where name = \"%s\" or name_en = \"%s\"",
                    star.getName(), star.getNameEn());
            ResultSet ret = stmt.executeQuery(query);
            if (ret.next()) {
                starId = ret.getInt("id");
            }
        } catch (SQLException ex) {
            Logger.getLogger(JDBCController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(JDBCController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return starId;
    }

    public int merge(StarPojo star) {
        Statement stmt = null;
        PreparedStatement preStmt = null;
        int starId = 0;
        try {
            stmt = _conn.createStatement();
            String query = String.format("select * from star where name = \"%s\" or name_en = \"%s\"",
                    star.getName(), star.getNameEn());
            ResultSet ret = stmt.executeQuery(query);
            if (ret.next()) {
                starId = ret.getInt("id");
            } else {
                preStmt = _conn.prepareStatement("insert into star (name,name_en) values (?,?)", Statement.RETURN_GENERATED_KEYS);
                preStmt.setString(1, star.getName());
                preStmt.setString(2, star.getNameEn());
                preStmt.execute();
                ResultSet rs = preStmt.getGeneratedKeys();
                rs.next();
                starId = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(JDBCController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (preStmt != null) {
                    preStmt.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(JDBCController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return starId;
    }
}
