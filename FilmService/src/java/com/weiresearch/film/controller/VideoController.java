/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.weiresearch.film.controller;

import com.weiresearch.film.pojo.WeireMoviePojo;
import com.weiresearch.film.util.DateUtil;
import java.sql.Date;
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
public class VideoController extends JDBCController {

    public VideoController() {

    }

    public int addVideo(WeireMoviePojo movie) {
        PreparedStatement preStmt = null;
        int videoId = 0;
        try {
            preStmt = _conn.prepareStatement("insert into video (name,name_en,type,format,release_time,"
                    + "runtime,country) values (?,?,?,?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);
            preStmt.setString(1, movie.getName());
            preStmt.setString(2, movie.getNameEn());
            preStmt.setString(3, movie.getType());
            preStmt.setString(4, movie.getFormat());
            java.util.Date releaseDate = DateUtil.movieReleaseTime2Date(movie.getReleaseTime());
            preStmt.setDate(5, new java.sql.Date(releaseDate.getTime()));
            preStmt.setString(6, movie.getRuntime());
            preStmt.setString(7, movie.getCountry());
            preStmt.execute();
            ResultSet rs = preStmt.getGeneratedKeys();
            rs.next();
            videoId = rs.getInt(1);
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
        return videoId;
    }
}
