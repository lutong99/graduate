package com.master.graduate.utils;

import java.util.List;

/**
 * 分页工具类
 *
 * @author Master_Joe lutong99
 * @since 2/22/2020 5:57 PM
 */

public class PageUtil<T> {
    /**
     * 当前页码
     */
    private int pageNo = 1;

    /**
     * 页面大小
     */
    private int pageSize = 5;

    /**
     * 总数
     */
    private int totalCount = 0;

    /**
     * 总页数
     */
    private int totalPage = 1;

    /**
     * 每页上显示的产品个数
     */
    private List<T> lists;

    public PageUtil() {
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getStartNum() {
        return (pageNo - 1) * pageSize;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalPage() {
        totalPage = totalCount / pageSize;
        if (totalCount == 0 || totalCount % pageSize != 0) {
            totalPage++;
        }
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<T> getLists() {
        return lists;
    }

    public void setLists(List<T> lists) {
        this.lists = lists;
    }
}
