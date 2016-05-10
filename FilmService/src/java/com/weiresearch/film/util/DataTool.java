/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.weiresearch.film.util;

import com.weiresearch.film.controller.StarController;
import com.weiresearch.film.controller.VideoController;
import com.weiresearch.film.controller.VideoStarRelController;
import com.weiresearch.film.pojo.WeireMoviePojo;
import com.weiresearch.film.pojo.MovieInfo;
import com.weiresearch.film.pojo.MovieTrailerPojo;
import com.weiresearch.film.pojo.StarPojo;
import com.weiresearch.film.pojo.StarImpactPojo;
import com.weiresearch.film.pojo.TrailerViewPojo;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author GigaLiu
 */
public class DataTool {

    private Map<String, WeireMoviePojo> movieMap;
    private Map<String, StarImpactPojo> starImpactMap;
    private Map<String, StarImpactPojo> directorImpactMap;
    private Map<Integer, MovieTrailerPojo> trailerMap;
    private final VideoController videoController = new VideoController();
    private final StarController starController = new StarController();
    private final VideoStarRelController videoStarRelController = new VideoStarRelController();

    public Map<String, WeireMoviePojo> getMovieMap() {
        return movieMap;
    }

    public Map<String, StarImpactPojo> getStarImpactMap() {
        return starImpactMap;
    }

    public Map<String, StarImpactPojo> getDirectorImpactMap() {
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
            WeireMoviePojo movie;
            StarPojo star;

            while ((lineStr = br.readLine()) != null) {
                values = lineStr.split(",");
                starType = Integer.parseInt(values[7]);
                if (starType == 1 || starType == 4) {
                    star = new StarPojo(values[6]);
                    star.setRole(starType);
                    star.setWeiboUrl(values[8].equals("\\N") ? null : values[8]);
                    star.setFans(values[11].equals("\\N") ? 0 : Integer.parseInt(values[10]));

                    movieName = values[1];
                    if (!movieMap.containsKey(movieName)) {
                        movie = new WeireMoviePojo(Integer.parseInt(values[0]), movieName,
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
            WeireMoviePojo movie;

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

//            for (Map.Entry<String, WeireMoviePojo> entry : movieMap.entrySet()) {
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
            StarImpactPojo si;

            while ((lineStr = br.readLine()) != null) {
                values = lineStr.split(",");
                impact = Double.parseDouble(values[2]);
                if (impact != 0) {
                    impact *= 10000;
                    starName = values[1];
                    si = new StarImpactPojo(starName, Integer.parseInt(values[0]), impact, 2,
                            Integer.parseInt(values[3]), Integer.parseInt(values[4]),
                            Integer.parseInt(values[5]), Integer.parseInt(values[6]));
                    if (!starImpactMap.containsKey(starName)) {
                        starImpactMap.put(starName, si);
                    }
                }
            }
            br.close();

            List<StarPojo> starList;
            for (Map.Entry<String, WeireMoviePojo> entry : movieMap.entrySet()) {
                starList = entry.getValue().getStarList();
                for (StarPojo star : starList) {
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
     * 加载导演影响力信息
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
            StarImpactPojo si;

            while ((lineStr = br.readLine()) != null) {
                values = lineStr.split(",");
                impact = Double.parseDouble(values[2]);
                if (impact != 0) {
                    impact *= 10000;
                    directorName = values[1];
                    si = new StarImpactPojo(directorName, Integer.parseInt(values[0]), impact, 1,
                            (int) (Long.parseLong(values[3]) / 10000), Integer.parseInt(values[7]),
                            (int) (Long.parseLong(values[4]) / 10000), (int) (Long.parseLong(values[5]) / 10000));
                    if (!directorImpactMap.containsKey(directorName)) {
                        directorImpactMap.put(directorName, si);
                    }
                }
            }
            br.close();

            List<StarPojo> directorList;
            for (Map.Entry<String, WeireMoviePojo> entry : movieMap.entrySet()) {
                directorList = entry.getValue().getDirectorList();
                for (StarPojo director : directorList) {
                    if ((si = directorImpactMap.get(director.getName())) != null) {
                        director.setImpactIndex(si.getImpactIndex());
                    }
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(DataTool.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * 加载预告片观看量等信息
     *
     * @param inputPath
     * @return
     */
    public Map<Integer, MovieTrailerPojo> loadTrailerInfo(String inputPath) {
        BufferedReader br = null;

        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(
                    inputPath), "utf-8"));
            String lineStr;
            String[] lineValues;
            trailerMap = new HashMap<>();
            MovieTrailerPojo movieTrailer;
            int mid;
            Pattern pattern = Pattern.compile("http[s]?://.*?\",\"(.*)\"$");
            Matcher matcher;
            String statisticStr;
            JSONArray trailerArray;
            JSONObject trailerObject;

            while ((lineStr = br.readLine()) != null) {
                lineValues = lineStr.split(",");
                mid = Integer.parseInt(lineValues[0]);
                if (!trailerMap.containsKey(mid)) {
                    movieTrailer = new MovieTrailerPojo(mid,
                            lineValues[1].replace("\"", ""));
                    trailerMap.put(mid, movieTrailer);
                }
                movieTrailer = trailerMap.get(mid);

                matcher = pattern.matcher(lineStr);
                if (matcher.find()) {
                    statisticStr = matcher.group(1).replace("\\", "");
                    try {
                        List<TrailerViewPojo> trailerList = new ArrayList<>();
                        if (statisticStr.charAt(0) == '[') {
                            trailerArray = new JSONArray(statisticStr);
                            for (int i = 0; i < trailerArray.length(); i++) {
                                trailerObject = trailerArray.getJSONObject(i);
                                trailerList.add(new TrailerViewPojo(trailerObject.getInt("views"),
                                        trailerObject.getInt("willing"), trailerObject.getInt("positive"),
                                        trailerObject.getInt("negetive")));
                            }
                        } else {
                            trailerObject = new JSONObject(statisticStr);
                            trailerList.add(new TrailerViewPojo(trailerObject.getInt("views"),
                                    trailerObject.getInt("willing"), trailerObject.getInt("positive"),
                                    trailerObject.getInt("negetive")));
                        }
                        movieTrailer.addTrailerList(trailerList);
                    } catch (JSONException ex) {
                        Logger.getLogger(DataTool.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    System.out.println(lineStr);
                }
            }

            for (Map.Entry<Integer, MovieTrailerPojo> entry : trailerMap.entrySet()) {
                entry.getValue().computeAvgTrailerInfo();
                entry.getValue().computeMaxTrailerInfo();
                System.out.println(entry.getValue());
            }

//            for (Map.Entry<String, WeireMoviePojo> entry : movieMap.entrySet()) {
//                mid = entry.getValue().getId();
//                movieTrailer = trailerMap.get(mid);
//                if (movieTrailer != null) {
//                    movieTrailer.computeAvgTrailerInfo();
//                    entry.getValue().setTrailerView(movieTrailer.getAvgTrailerInfo());
//                } else {
//                    entry.getValue().setTrailerView(new TrailerViewPojo());
//                }
//            }
        } catch (IOException ex) {
            Logger.getLogger(DataTool.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException ex) {
                    Logger.getLogger(DataTool.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return trailerMap;
    }

    public void loadComingMovie(String inputPath, String coding) {
        BufferedReader br = null;

        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(
                    inputPath), coding));
            Map<Integer, WeireMoviePojo> movieComingMap = new HashMap<>();

            String lineStr;
            String[] lineValues;
            WeireMoviePojo weireMovie;
            StarPojo star;
            while ((lineStr = br.readLine()) != null) {
                lineValues = lineStr.split(",");
                if (lineValues.length == 12) {
                    int mId = Integer.parseInt(lineValues[0]);
                    if (!movieComingMap.containsKey(mId)) {
                        weireMovie = new WeireMoviePojo(mId);
                        weireMovie.setName(lineValues[1]);
                        weireMovie.setNameEn(lineValues[2]);
                        weireMovie.setType(lineValues[3].replace("|", "/"));
                        weireMovie.setFormat(lineValues[4]);
                        weireMovie.setReleaseTime(lineValues[5]);
                        weireMovie.setRuntime(lineValues[6]);
                        weireMovie.setCountry(lineValues[7]);
                        movieComingMap.put(mId, weireMovie);
                    }
                    weireMovie = movieComingMap.get(mId);
                    star = new StarPojo(Long.parseLong(lineValues[9]),
                            lineValues[10], lineValues[11], Integer.parseInt(lineValues[8]));
                    weireMovie.addStar(star);
                }
            }

            for (Map.Entry<Integer, WeireMoviePojo> entry : movieComingMap.entrySet()) {
                weireMovie = entry.getValue();
                int videoId = this.videoController.addVideo(weireMovie);
                List<StarPojo> starList = weireMovie.getStarList();
                Collections.sort(starList);

                int[] rank = {1, 1, 1, 1, 1};
                for (StarPojo starPojo : starList) {
                    starPojo.setRole(filterRole(starPojo.getRole()));
                    int starId = this.starController.merge(starPojo);
                    if (starId > 0) {
                        this.videoStarRelController.add(videoId, starId,
                                starPojo.getRole(), rank[starPojo.getRole()]);
                        rank[starPojo.getRole()]++;
                    }
                }
            }

        } catch (IOException ex) {
            Logger.getLogger(DataTool.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException ex) {
                    Logger.getLogger(DataTool.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public int filterRole(int role) {
        switch (role) {
            case 1:
            case 2:
                return 2;
            case 4:
                return 1;
            case 8:
                return 3;
            default:
                return 0;
        }
    }

    /**
     * 将整理好的影片信息写入csv文件
     *
     * @param outputPath
     */
    public void writeMovieInfo(String outputPath) {
        FileOutputStream fos;
        PrintWriter pw;
        try {
            if (movieMap != null) {
                fos = new FileOutputStream(new File(outputPath));
                pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputPath), "utf-8")));
                MovieInfo mi;
                WeireMoviePojo movie;
//                pw.println("name,type,country,releaseTime,dirBoxImpactIndex,"
//                        + "starBoxImpactIndex,dirSocialImpactIndex,starSocialImpactIndex,series,boxClass");
                pw.println("name,type,country,releaseTime,dirBoxImpactIndex,"
                        + "starBoxImpactIndex,trailerViews,trailerPos,trailerNeg,boxClass");
                for (Map.Entry<String, WeireMoviePojo> entry : movieMap.entrySet()) {
                    movie = entry.getValue();
                    if (movie.getTrailerView() == null) {
                        continue;
                    }
                    mi = new MovieInfo(movie.getName(), movie.getType());
                    mi.setCountry(filterCountry(movie.getCountry()));
                    mi.setReleaseTime(filterReleaseTime(movie.getReleaseTime()));
                    mi.setDirBoxImpactIndex(getStarBoxIndex(movie.getDirectorList()));
                    mi.setStarBoxImpactIndex(getStarBoxIndex(movie.getStarList()));
//                    mi.setDirSocialImpactIndex(getStarSocialIndex(movie.getDirectorList()));
//                    mi.setStarSocialImpactIndex(getStarSocialIndex(movie.getStarList()));
                    mi.setTrailerViews(movie.getTrailerView().getViews());
                    mi.setTrailerPos(movie.getTrailerView().getPositive());
                    mi.setTrailerNeg(movie.getTrailerView().getNegtive());
                    mi.setBoxClass(filterBoxoffice2(movie.getBoxoffice()));
                    if (mi.getDirBoxImpactIndex() != 0 && mi.getStarBoxImpactIndex() != 0) {
                        pw.println(mi);
                    }
                }
                pw.close();
                fos.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(DataTool.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private int filterCountry(String country) {
        if (country.equals("中国")) {
            return 0;
        } else if (country.equals("美国")) {
            return 1;
        } else if (country.contains("香港") || country.contains("台湾")) {
            return 2;
        } else {
            return 3;
        }
    }

    private int filterReleaseTime(String releaseTime) {
        int year = Integer.parseInt(releaseTime.substring(0, 4));
        switch (year) {
            case 2013:
                return 0;
            case 2014:
                return 1;
            case 2015:
                return 2;
            case 2016:
                return 3;
            default:
                return 0;
        }
    }

    private double getStarBoxIndex(List<StarPojo> starList) {
        double index = 0;
        int count = 0;
        for (StarPojo star : starList) {
            if (star.getImpactIndex() > 0) {
                index += star.getImpactIndex();
                count++;
            }
        }
        if (count > 0) {
            index /= count;
        }
        DecimalFormat df = new DecimalFormat("#.00");
        return Double.parseDouble(df.format(index));
    }

    private int getStarSocialIndex(List<StarPojo> starList) {
        int fansCount = 0;
        int count = 0;
        for (StarPojo star : starList) {
            fansCount += star.getFans();
            if (star.getFans() > 0) {
                count++;
            }
        }

        int index = -1;
        if (count > 0) {
            int avgFans = fansCount / count;
            if (avgFans > 10000000) {
                index = 2;
            } else if (avgFans > 1000000) {
                index = 1;
            } else {
                index = 0;
            }
        }

        return index;
    }

    private int filterBoxoffice(double boxoffice) {
        if (boxoffice > 100000) {
            return 5;
        } else if (boxoffice > 50000) {
            return 4;
        } else if (boxoffice > 10000) {
            return 3;
        } else if (boxoffice > 5000) {
            return 2;
        } else if (boxoffice > 1000) {
            return 1;
        } else {
            return 0;
        }
    }

    private int filterBoxoffice2(double boxoffice) {
        if (boxoffice > 100000) {
            return 3;
        } else if (boxoffice > 10000) {
            return 2;
        } else if (boxoffice > 1000) {
            return 1;
        } else {
            return 0;
        }
    }

    public static void main(String[] args) {
        DataTool tool = new DataTool();
//        tool.loadMovieInfo("C:\\Users\\GigaLiu\\Desktop\\影视数据全\\movie_info4.csv");
//        tool.loadTrailerInfo("C:\\Users\\GigaLiu\\Desktop\\影视数据全\\movie_trailer_20160508.csv");
//        tool.writeMovieInfo("C:\\Users\\GigaLiu\\Desktop\\影视数据全\\movie_ins.csv");
        tool.loadComingMovie("C:/Users/GigaLiu/Desktop/影视数据全/movie_coming_20160510.csv", "GBK");
    }
}
