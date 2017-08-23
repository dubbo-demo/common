package com.way.common.rom.annotation;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * 功能描述：分页
 *
 * @ClassName Pagination
 * @Author：xinpei.xu
 * @Date：2017/08/14 20:40
 */
@Page(page = "pageIndex", rows = "pageSize", total = "total")
public class Pagination<T> implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 8937855897217091699L;

    // 返回的结果.
    private List<T> result = Collections.emptyList();

    // 返回的记录总条数.
    private long total = 0;

    // 返回的总页数.
    private int pageCount = 0;

    // 页面索引.
    private int pageIndex;

    // 页面记录条数.
    private int pageSize;

    private String sortOrder;
    private String sortField;

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
    public Pagination() {
    }

    public Pagination(BasePage page) {
        this(page.getPageIndex(), page.getPageSize(), page.getSortField(), page.getSortOrder());
        //genPagingParams(entityAmount);
    }

    public Pagination(int pageIndex, int pageSize) {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
    }

    /**
     * @param pageIndex
     * @param pageSize
     * @param sortField
     * @param sortOrder @description 详细说明
     */
    public Pagination(int pageIndex, int pageSize, String sortField, String sortOrder) {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.sortField = sortField;
        this.sortOrder = sortOrder;
    }

    /**
     * @param pageIndex
     * @param pageSize
     * @param totel
     * @description 详细说明
     */
    public Pagination(int pageIndex, int pageSize, int totel) {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        setTotal(totel);
    }

    /**
     * @return the total
     */
    public final long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;

        pageCount = (int) ((total % pageSize == 0) ? total / pageSize : total
                / pageSize + 1);

        if (pageIndex < 1)
            pageIndex = 1;
        //if (pageIndex > pageCount)
        //pageIndex = pageCount;

    }

    public int getPageCount() {
        return pageCount;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * @return the rows
     */
    public final List<T> getResult() {
        return result;
    }

    /**
     * @param result the rows to set
     */
    public final void setResult(List<T> result) {
        this.result = result;
    }


    public boolean hasResult() {
        return null != result && result.size() > 0;
    }

    @Override
    public String toString() {
        return "Pagination [result=" + result + ", total=" + total + ", pageCount=" + pageCount + ", pageIndex="
                + pageIndex + ", pageSize=" + pageSize + "]";
    }

}