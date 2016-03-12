/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.weiresearch.entry;

/**
 *
 * @author GigaLiu
 */
public class StarSocial {
    private String weiboUrl;
    private int fans;

    public StarSocial(String weiboUrl) {
        this.weiboUrl = weiboUrl;
    }

    public StarSocial(int fans) {
        this.fans = fans;
    }
    
    public StarSocial(String weiboUrl, int fans) {
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
