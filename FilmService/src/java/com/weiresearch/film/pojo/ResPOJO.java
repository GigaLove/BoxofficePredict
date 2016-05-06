/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.weiresearch.film.pojo;

import java.io.Serializable;

/**
 *
 * @author whuan
 */
public class ResPOJO implements Serializable {

    private Integer code;
    private String msg;
    private Integer totalCount;
    private Integer pageNum;
    private Integer pageSize;
    private Object data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
