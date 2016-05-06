/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.weiresearch.film.util;

/**
 *
 * @author GigaLiu
 */
public class MovieConst {

    public static final int YEAR_SPAN = 5;
    public static final int ROLE_COUNT = 4;
    public static final int FORMAT_3D = 1;
    public static final int FORMAT_IMAX = 1;

    public static final String[][] MOVIE_TYPE = {
        {"爱情"},
        {"动作"},
        {"喜剧"},
        {"剧情"},
        {"科幻"},
        {"魔幻", "奇幻"},
        {"动画", "儿童"},
        {"惊悚", "恐怖"},
        {"战争"},
        {"纪录片", "纪实", "传记", "革命"},
        {"歌舞", "音乐"},
        {"灾难"}
    };
    public static final int MOVIE_TYPE_OTHER = MOVIE_TYPE.length;

    public static final int AREA_CHINA = 0;
    public static final int AREA_USA = 1;
    public static final int AREA_HANGKONG = 2;
    public static final int AREA_TAIWAN = 3;
    public static final int AREA_OTHER = 4;
}
