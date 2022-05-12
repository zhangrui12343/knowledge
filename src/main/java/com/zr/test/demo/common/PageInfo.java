package com.zr.test.demo.common;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.List;

/**
 *
 * 处理分页信息
 *
 * 把分页的属性，设置到通用的返回值上面去
 *
 * @author yangliangliang
 * @author kjhuang
 */
@SuppressWarnings({"rawtypes", "unchecked"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PageInfo<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 当前页
     */
    private Integer page;

    /** 总的数 */
    private Integer pages;

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(int rows) {
        this.pageSize = rows;
    }

    /**
     * 每页数量
     */
    private Integer pageSize;
    /**
     * 总记录数
     */
    private Long total;
    /**
     * 分页列表数据
     */
    private List<T> list;

    public PageInfo() {
    }
 

    public Integer getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }


    /**
     * 判定页面边界
     */
    private void judgePageBoudary() {
    }
 
    public Long getTotal() {
        return total;
    }
 
    public void setTotal(long total) {
        this.total = total;
    }
 
    public List<T> getList() {
        return list;
    }
 
    public void setList(List<T> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "PageInfo{" +
                "page=" + page +
                ", pageSize=" + pageSize +
                ", total=" + total +
                ", pages=" + pages +
                ", list=" + list +
                '}';
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }
}