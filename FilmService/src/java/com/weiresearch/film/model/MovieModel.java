/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.weiresearch.film.model;

import com.weiresearch.film.pojo.EnMoviePojo;
import com.weiresearch.film.util.DataTool;
import com.weiresearch.film.util.MovieConst;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author GigaLiu
 */
public class MovieModel {

    private Map<Long, EnMoviePojo> movieMap;
    private StarImpactModel starModel;

    public void compute(String moviePath, String starPath) {
        loadStarImpactModel(starPath);
        loadMovieInfo(moviePath);
    }

    private void loadStarImpactModel(String starPath) {
        starModel = new StarImpactModel();
        starModel.loadStarInfo(starPath);
        starModel.compute();
    }

    /**
     * version 0.1 加载影视影视信息
     *
     * @param moviePath
     * @return
     */
    private Map<Long, EnMoviePojo> loadMovieInfo(String moviePath) {
        BufferedReader br;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(
                    moviePath), "GBK"));
            movieMap = new HashMap<>();
            String lineStr;
            String[] values;
            EnMoviePojo movie;
            long movieId;
            while ((lineStr = br.readLine()) != null) {
                if (!lineStr.startsWith("id")) {
                    values = lineStr.split(",");
                    movieId = Long.parseLong(values[0]);
                    if (!movieMap.containsKey(movieId)) {
                        movie = new EnMoviePojo(movieId, values[1]);
                        movie.setReleaseYear(Integer.parseInt(values[3]));
                        movie.setPeriod(filterPeriod(values[4]));
                        movie.setType(filterType(values[5]));
                        filterFormat(values[6], movie);
                        movie.setCountry(filterCountry(values[7]));
                        movie.setDirId(Long.parseLong(values[8]));
                        movie.setDirBoxIndex(this.starModel.getImpacIndex(Long.parseLong(values[8])));
                        movie.setStarOneId(Long.parseLong(values[10]));
                        movie.setStarOneBoxIndex(this.starModel.getImpacIndex(Long.parseLong(values[10])));
//                        movie.setStarOneBoxIndex(computeStarIndex(Long.parseLong(values[10]), 
//                                Long.parseLong(values[12])));
                        movie.setStarTwoId(Long.parseLong(values[12]));
                        movie.setStarTwoBoxIndex(this.starModel.getImpacIndex(Long.parseLong(values[12])));
                        movie.setBoxClass(filterBoxoffice2(values[14]));
                        movieMap.put(movieId, movie);
                        System.out.println(movie);
                    }
                }
            }
            br.close();

        } catch (IOException ex) {
            Logger.getLogger(DataTool.class.getName()).log(Level.SEVERE, null, ex);
        }
        return movieMap;
    }

    /**
     * version 0.2加载影视信息
     *
     * @param inputPath
     */
    public void loadEnMovieInfo(String inputPath) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(
                    inputPath), "utf-8"));
            movieMap = new HashMap<>();
            String lineStr;
            String[] values;
            EnMoviePojo movie;
            while ((lineStr = br.readLine()) != null) {
                if (!lineStr.startsWith("id")) {
                    try {
                        values = lineStr.split(",");
                        int rank = Integer.parseInt(values[12]);
                        if (rank < 3) {
                            long movieId = Long.parseLong(values[0]);
                            if (!movieMap.containsKey(movieId)) {
                                movie = new EnMoviePojo(movieId, values[1]);
                                movie.setType(filterType(values[2]));
                                filterFormat(values[3], movie);
                                movie.setReleaseYear(Integer.parseInt(values[4].substring(0, 4)));
                                movie.setPeriod(filterPeriod(values[4]));
                                movie.setCountry(filterCountry(values[5]));
                                movie.setIsSeries(Integer.parseInt(values[6]));
                                movie.setIsIp(Integer.parseInt(values[7]));
                                movie.setMarketCount(Integer.parseInt(values[8]));
                                movie.setBoxClass(filterBoxoffice2(values[9]));
                                movieMap.put(movieId, movie);
                            }
                            movie = movieMap.get(movieId);
                            movie.addChiefIndex(this.optDouble(values[14]));
                        }
                    } catch (NumberFormatException ex) {
                        Logger.getLogger(DataTool.class.getName()).log(Level.INFO, null, ex);
                    }
                }
            }

            for (Map.Entry<Long, EnMoviePojo> entry : movieMap.entrySet()) {
                List<Double> impactIndexs = entry.getValue().getChiefIndexs();
                int count = 0;
                double impactValue = 0;
                for (Double val : impactIndexs) {
                    if (val != null && val != 0) {
                        impactValue += val;
                        count++;
                    }
                }
                entry.getValue().setVideoChiefIndex(count > 0 ? (impactValue / count) : 0);
            }

        } catch (IOException ex) {
            Logger.getLogger(DataTool.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException ex) {
                    Logger.getLogger(MovieModel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    /**
     * 将处理过的影视信息重新保存下来
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
//                pw.println("id,type,country,releaseYear,period,"
//                        + "is3D,isIMAX,dirBoxIndex,starOneBoxIndex,starTwoBoxIndex,boxClass");
                pw.println("id,type,country,releaseYear,period,"
                        + "is3D,isIMAX,isIp,isSeries,marketCount,chiefIndex,boxClass");
                for (Map.Entry<Long, EnMoviePojo> entry : movieMap.entrySet()) {
                    pw.println(entry.getValue());
                }
                pw.flush();
                pw.close();
                fos.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(DataTool.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private double optDouble(String val) {
        try {
            double value = Double.parseDouble(val);
            return value;
        } catch (NumberFormatException ex) {
            return 0;
        }
    }

    public double computeStarIndex(long starOneId, long starTwoId) {
        return (starModel.getImpacIndex(starOneId)
                + starModel.getImpacIndex(starTwoId)) / 2;
    }

    public static int filterReleaseYear(String releaseYear) {
        int year = Integer.parseInt(releaseYear);
        return year - 2011;
    }

    /**
     * 0：爱情 1：动作 2：喜剧 3：剧情 4：科幻 5：魔幻 6：动画 7：惊悚 8：战争 9：纪实 10：歌舞 11：儿童 12：纪录片
     *
     * @param typeStr
     * @return
     */
    public static int filterType(String typeStr) {
        if (typeStr != null && !typeStr.trim().isEmpty()) {
            String[] types = typeStr.trim().split("/");

            for (String type : types) {
                for (int i = 0; i < MovieConst.MOVIE_TYPE.length; i++) {
                    for (String str : MovieConst.MOVIE_TYPE[i]) {
                        if (type.equals(str)) {
                            return i;
                        }
                    }
                }
            }
        }
        return 3;
    }

    public static void filterFormat(String format, EnMoviePojo movie) {
        if (format == null || movie == null) {
            return;
        }

        if (format.contains("3D")) {
            movie.setIs3D(MovieConst.FORMAT_3D);
        }
        if (format.contains("IMAX")) {
            movie.setIsIMAX(MovieConst.FORMAT_IMAX);
        }
    }

    /**
     * 影视作品国家过滤器
     *
     * @param countryStr
     * @return
     */
    public static int filterCountry(String countryStr) {
        if (countryStr != null && !countryStr.trim().isEmpty()) {
            String[] countrys = countryStr.trim().split("/");
            for (String country : countrys) {
                if (country.equals("中国")) {
                    return 0;
                } else if (country.equals("美国")) {
                    return 1;
                } else if (country.equals("中国香港")) {
                    return 2;
                } else if (countryStr.equals("中国台湾")) {
                    return 3;
                } else {
                    return 4;
                }
            }
        }
        return 0;
    }

    public static int filterPeriod(String releaseTime) {
        if (releaseTime != null && !releaseTime.trim().isEmpty()) {
            releaseTime = releaseTime.trim();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            String year = releaseTime.substring(0, 4);
            try {
                Date date = sdf.parse(releaseTime);
                Date sumBegin = sdf.parse(year + "/07/15");
                Date sumEnd = sdf.parse(year + "/08/31");
                Date chrisBegin = sdf.parse(year + "/12/20");
                Date chrisEnd = sdf.parse(year + "/12/31");
                Date winBegin = sdf.parse(year + "/01/01");
                Date winEnd = sdf.parse(year + "/02/28");
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                if (date.after(sumBegin) && date.before(sumEnd) || date.equals(sumBegin) || date.equals(sumEnd)) {
                    return 1;
                } else if (date.after(winBegin) && date.before(winEnd) || date.equals(winBegin) || date.equals(winEnd)) {
                    return 2;
                } else if (date.after(chrisBegin) && date.before(chrisEnd) || date.equals(chrisBegin) || date.equals(chrisEnd)) {
                    return 3;
                } else {
                    return 0;
                }
            } catch (ParseException ex) {
                Logger.getLogger(MovieModel.class.getName()).log(Level.SEVERE, null, ex);
                return 0;
            }
        }
        return 0;
    }

    public static int filterBoxoffice(String box) {
        double boxoffice = Double.parseDouble(box);
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

    public static int filterBoxoffice2(String box) {
        double boxoffice = Double.parseDouble(box);
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
        MovieModel model = new MovieModel();
        model.compute("data/2011-2016-boxoffice.csv",
                "data/star_works_2.txt");
        model.writeMovieInfo("data/train_data_4_2016.csv");
//        model.compute("data/EN2016票房.csv",
//                "data/star_works_2.txt");
//        model.writeMovieInfo("data/test_data_4.csv");
    }

}
