/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.weiresearch.model;

import com.weiresearch.data.MovieConst;
import com.weiresearch.entry.EnMovie;
import com.weiresearch.tool.DataTool;
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
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author GigaLiu
 */
public class MovieModel {

    private Map<Long, EnMovie> movieMap;
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

    private Map<Long, EnMovie> loadMovieInfo(String starPath) {
        BufferedReader br;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(
                    starPath), "GBK"));
            movieMap = new HashMap<>();
            String lineStr;
            String[] values;
            EnMovie movie;
            long movieId;
            while ((lineStr = br.readLine()) != null) {
                if (!lineStr.startsWith("id")) {
                    values = lineStr.split(",");
                    movieId = Long.parseLong(values[0]);
                    if (!movieMap.containsKey(movieId)) {
                        movie = new EnMovie(movieId, values[1]);
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

    public void writeMovieInfo(String outputPath) {
        FileOutputStream fos;
        PrintWriter pw;
        try {
            if (movieMap != null) {
                fos = new FileOutputStream(new File(outputPath));
                pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputPath), "utf-8")));
                EnMovie mi;
                pw.println("id,type,country,releaseYear,period,"
                        + "is3D,isIMAX,dirBoxIndex,starOneBoxIndex,starTwoBoxIndex,boxClass");
                for (Map.Entry<Long, EnMovie> entry : movieMap.entrySet()) {
                    mi = entry.getValue();
                    pw.println(mi);
                }
                pw.close();
                fos.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(DataTool.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public double computeStarIndex(long starOneId, long starTwoId) {
        return (starModel.getImpacIndex(starOneId) + 
                starModel.getImpacIndex(starTwoId)) / 2;
    }

    public static int filterReleaseYear(String releaseYear) {
        int year = Integer.parseInt(releaseYear);
        return year - 2011;
    }

    /**
     * 0：爱情 1：动作 2：喜剧 3：剧情 4：科幻 5：魔幻 6：动画 7：惊悚 8：战争 9：纪实 10：歌舞
     *
     * @param type
     * @return
     */
    public static int filterType(String type) {
        type = type.substring(0, 2);

        for (int i = 0; i < MovieConst.MOVIE_TYPE.length; i++) {
            if (type.equals(MovieConst.MOVIE_TYPE[i])) {
                return i;
            }
        }
        return 0;
    }

    public static void filterFormat(String format, EnMovie movie) {
        if (format.contains("3D")) {
            movie.setIs3D(1);
        } else if (format.contains("IMAX")) {
            movie.setIsIMAX(1);
        }
    }

    public static int filterCountry(String country) {
        if (country.equals("中国")) {
            return 0;
        } else if (country.contains("美国")) {
            return 1;
        } else if (country.contains("香港") || country.contains("台湾")) {
            return 2;
        } else {
            return 3;
        }
    }

    public static int filterPeriod(String releaseTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String year = releaseTime.substring(0, 4);
        try {
            Date date = sdf.parse(releaseTime);
            Date sumBegin = sdf.parse(year + "/07/15");
            Date sumEnd = sdf.parse(year + "/08/31");
            Date winBegin = sdf.parse(year + "/01/01");
            Date winEnd = sdf.parse(year + "/02/28");
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            if (date.after(sumBegin) && date.before(sumEnd)) {
                return 1;
            } else if (date.after(winBegin) && date.before(winEnd)) {
                return 2;
            } else {
                return 0;
            }
        } catch (ParseException ex) {
            Logger.getLogger(MovieModel.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
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
