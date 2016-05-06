/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.weiresearch.film.pojo;

/**
 *
 * @author GigaLiu
 */
public class StarSocialPojo {
    private String weiboUrl;
    private int fans;

    public StarSocialPojo(String weiboUrl) {
        this.weiboUrl = weiboUrl;
    }

    public StarSocialPojo(int fans) {
        this.fans = fans;
    }
    
    public StarSocialPojo(String weiboUrl, int fans) {
        this.weiboUrl = weiboUrl;
        this.fans = fans;
    }
    
    public String getWeiboUrl() {
        return weiboUrl;
    }

    public void setWeiboUrl(String weiboUrl) {
        this.weiboUrl = weiboUrl;
    }

    public int getFans() {
        return fans;
    }

    public void setFans(int fans) {
        this.fans = fans;
    }
    
    
}
