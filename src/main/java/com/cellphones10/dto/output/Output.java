package com.cellphones10.dto.output;

import java.util.List;

public class Output<T> {
    private Long totalPage;
    private  Integer page;
    private List<T> listResult;
    private Integer remainingproduct;
    private Long totalItems;
    public Output(Long totalPage, Integer page, List<T> listResult, Integer remainingproduct) {
        this.totalPage = totalPage;
        this.page = page;
        this.listResult = listResult;
        this.remainingproduct = remainingproduct;
    }
    public Output() {

    }

    public Long getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(Long totalItems) {
        this.totalItems = totalItems;
    }

    public Long getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Long totalPage) {
        this.totalPage = totalPage;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public List<T> getListResult() {
        return listResult;
    }

    public Integer getRemainingproduct() {
        return remainingproduct;
    }

    public void setRemainingproduct(Integer remainingproduct) {
        this.remainingproduct = remainingproduct;
    }

    public void setListResult(List<T> listResult) {
        this.listResult = listResult;
    }
}
