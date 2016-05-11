/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.weiresearch.film.filter;

import java.io.IOException;
import java.io.OutputStream;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author GigaLiu
 */
//@WebFilter("/filmapi/*")
public class JsonpFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        String jsonCallback = ((HttpServletRequest) req).getParameter("jsonpCallback");
        if (jsonCallback != null) {
            HttpServletResponse response = (HttpServletResponse) res;
            response.setBufferSize(2048);
            OutputStream os = response.getOutputStream();
            os.write((jsonCallback + "(").getBytes());
            chain.doFilter(req, res);
            os.write(")".getBytes());
//            os.flush();
//            os.close();
        } else {
            chain.doFilter(req, res);
        }
    }

    @Override
    public void destroy() {
    }

}
