package com.way.common.rom.annotation;

import java.io.Serializable;

/**
 * 功能描述：分页基础类
 *
 * @ClassName BasePage
 * @Author：xinpei.xu
 * @Date：2017/07/23 20:14
 */

public class BasePage implements Serializable {
    // 分页的页码索引.(从1开始计数)
    protected int pageIndex = 1;

    //开始的记录编号  从0开始
    protected Long startIndex = 0l;

    // 每页要求的记录条数.
    protected int pageSize = 10;

    protected String sortOrder;
    protected String sortField;

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    /**
     * @description 详细说明
     */
    public BasePage(int pageIndex, int pageSize) {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
    }

    public BasePage(int pageIndex, int pageSize, String sortField, String sortOrder) {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.sortOrder = sortOrder;
        this.sortField = sortField;
    }

    public BasePage(int pageSize) {
        this.pageSize = pageSize;
    }

    public BasePage() {
    }

    public Long getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(Long startIndex) {
        this.startIndex = startIndex;
    }

    /**
     * @return the pageIndex
     */
    public final int getPageIndex() {
        return pageIndex;
    }

    /**
     * @param pageIndex the pageIndex to set
     */
    public final void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    /**
     * @return the pageSize
     */
    public final int getPageSize() {
        return pageSize;
    }

    /**
     * @param pageSize the pageSize to set
     */
    public final void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }


}

