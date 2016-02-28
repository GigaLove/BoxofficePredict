/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.weiresearch.data.tool;

import com.weiresearch.data.entry.Movie;
import com.weiresearch.data.entry.MovieIns;
import com.weiresearch.data.entry.Star;
import com.weiresearch.data.entry.StarImpact;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author GigaLiu
 */
public class DataTool {

    private Map<String, Movie> movieMap;
    private Map<String, StarImpact> starImpactMap;
    private Map<String, StarImpact> directorImpactMap;

    public Map<String, Movie> getMovieMap() {
        return movieMap;
    }

    public Map<String, StarImpact> getStarImpactMap() {
        return starImpactMap;
    }

    public Map<String, StarImpact> getDirectorImpactMap() {
        return directorImpactMap;
    }

    /**
     * 加载影片基本信息
     *
     * @param inputPath
     */
    public void loadMovieInfo(String inputPath) {
        BufferedReader br;

        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(
                    inputPath), "utf-8"));
            movieMap = new HashMap<>();
            String lineStr;
            String[] values;
            String movieName;
            int starType;
            Movie movie;
            Star star;

            while ((lineStr = br.readLine()) != null) {
                values = lineStr.split(",");
                starType = Integer.parseInt(values[7]);
                if (starType == 1 || starType == 4) {
                    star = new Star(values[6]);
                    star.setType(starType);
                    star.setWeiboUrl(values[8].equals("\\N") ? null : values[8]);
                    star.setFans(values[11].equals("\\N") ? 0 : Integer.parseInt(values[10]));

                    movieName = values[1];
                    if (!movieMap.containsKey(movieName)) {
                        movie = new Movie(Integer.parseInt(values[0]), movieName,
                                values[2], values[3], values[4]);
                        movieMap.put(movieName, movie);
                    }
                    movie = movieMap.get(movieName);
                    if (starType == 1) {
                        movie.addStar(star);
                    } else {
                        movie.addDirector(star);
                    }
                }
            }
            br.close();
            loadBoxoffice("C:\\Users\\GigaLiu\\Desktop\\影视数据全\\票房预测数据.csv");
            loadStarImpact("C:\\Users\\GigaLiu\\Desktop\\影视数据全\\演员号召力.csv");
            loadDirectorImpact("C:\\Users\\GigaLiu\\Desktop\\影视数据全\\导演号召力.csv");
        } catch (IOException ex) {
            Logger.getLogger(DataTool.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * 加载票房信息
     *
     * @param inputPath
     */
    private void loadBoxoffice(String inputPath) {
        BufferedReader br;

        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(
                    inputPath), "GBK"));
            String lineStr;
            String[] values;
            String movieName;
            Movie movie;

            while ((lineStr = br.readLine()) != null) {
                values = lineStr.split(",");
                movieName = values[1];
                movie = movieMap.get(movieName);
                if (movie != null) {
                    movie.setType(values[3]);
                    movie.setCountry(values[4]);
                    movie.setBoxoffice(Double.parseDouble(values[2]));
                }
            }
            br.close();

//            for (Map.Entry<String, Movie> entry : movieMap.entrySet()) {
//                if (entry.getValue().getStarList().size() > 3) {
//                    System.out.println(entry.getValue());
//                }
//            }
        } catch (IOException ex) {
            Logger.getLogger(DataTool.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * 加载演员影响力信息
     *
     * @param inputPath
     */
    private void loadStarImpact(String inputPath) {
        BufferedReader br;

        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(
                    inputPath), "GBK"));
            starImpactMap = new HashMap<>();
            String lineStr;
            String[] values;
            String starName;
            double impact;
            StarImpact si;

            while ((lineStr = br.readLine()) != null) {
                values = lineStr.split(",");
                impact = Double.parseDouble(values[2]);
                if (impact != 0) {
                    impact *= 10000;
                    starName = values[1];
                    si = new StarImpact(starName, Integer.parseInt(values[0]), impact, 1,
                            Integer.parseInt(values[3]), Integer.parseInt(values[4]),
                            Integer.parseInt(values[5]), Integer.parseInt(values[6]));
                    if (!starImpactMap.containsKey(starName)) {
                        starImpactMap.put(starName, si);
                    }
                }
            }
            br.close();

            List<Star> starList;
            for (Map.Entry<String, Movie> entry : movieMap.entrySet()) {
                starList = entry.getValue().getStarList();
                for (Star star : starList) {
                    if ((si = starImpactMap.get(star.getName())) != null) {
                        star.setImpactIndex(si.getImpactIndex());
                    }
                }
//                System.out.println(entry.getValue());
            }
        } catch (IOException ex) {
            Logger.getLogger(DataTool.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * 加载演员影响力信息
     *
     * @param inputPath
     */
    private void loadDirectorImpact(String inputPath) {
        BufferedReader br;

        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(
                    inputPath), "GBK"));
            directorImpactMap = new HashMap<>();
            String lineStr;
            String[] values;
            String directorName;
            double impact;
            StarImpact si;

            while ((lineStr = br.readLine()) != null) {
                values = lineStr.split(",");
                impact = Double.parseDouble(values[2]);
                if (impact != 0) {
                    impact *= 10000;
                    directorName = values[1];
                    si = new StarImpact(directorName, Integer.parseInt(values[0]), impact, 8,
                            (int) (Long.parseLong(values[3]) / 10000), Integer.parseInt(values[7]),
                            (int) (Long.parseLong(values[4]) / 10000), (int) (Long.parseLong(values[5]) / 10000));
                    if (!directorImpactMap.containsKey(directorName)) {
                        directorImpactMap.put(directorName, si);
                    }
                }
            }
            br.close();

            List<Star> directorList;
            for (Map.Entry<String, Movie> entry : movieMap.entrySet()) {
                directorList = entry.getValue().getDirectorList();
                for (Star director : directorList) {
                    if ((si = directorImpactMap.get(director.getName())) != null) {
                        director.setImpactIndex(si.getImpactIndex());
                    }
                }
                System.out.println(entry.getValue());
            }
        } catch (IOException ex) {
            Logger.getLogger(DataTool.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void writeMovieInfo(String outputPath) {
        FileOutputStream fos;
        PrintWriter pw;
        try {
            if (movieMap != null) {
                fos = new FileOutputStream(new File(outputPath));
                pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputPath), "utf-8")));
                MovieIns mi;
                Movie movie;
                for (Map.Entry<String, Movie> entry : movieMap.entrySet()) {
                    movie = entry.getValue();
                    
                }
                pw.close();
                fos.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(DataTool.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        DataTool tool = new DataTool();
        tool.loadMovieInfo("C:\\Users\\GigaLiu\\Desktop\\影视数据全\\movie_info4.csv");
    }
}
