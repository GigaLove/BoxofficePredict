/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.weiresearch.film.controller;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author GigaLiu
 */
public class VideoStarRelController extends JDBCController {

    public VideoStarRelController() {

    }
    
    public void add(int videoId, int starId, int role, int rank) {
        PreparedStatement preStmt = null;
        try {
            preStmt = _conn.prepareStatement("insert into video_star_rel (movie_id,star_id,role,rank)"
                    + " values (?,?,?,?)");
            preStmt.setInt(1, videoId);
            preStmt.setInt(2, starId);
            preStmt.setInt(3, role);
            preStmt.setInt(4, rank);
            preStmt.execute();
        } catch (SQLException ex) {
            Logger.getLogger(JDBCController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (preStmt != null) {
                    preStmt.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(JDBCController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
