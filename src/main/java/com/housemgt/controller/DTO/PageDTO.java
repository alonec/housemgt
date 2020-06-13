package com.housemgt.controller.DTO;

import java.util.List;

/**
 * 分页DTO，用于查询分页返回结果集封装
 */
public class PageDTO {
    //总记录数
    private long totals;

    //结果集
    private List<?> list;

    public long getTotals() {
        return totals;
    }

    public void setTotals(long totals) {
        this.totals = totals;
    }

    public List<?> getList() {
        return list;
    }

    public void setList(List<?> list) {
        this.list = list;
    }
}
