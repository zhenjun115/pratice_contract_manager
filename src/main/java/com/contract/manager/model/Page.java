package com.contract.manager.model;

public class Page {
    private int pageSize = 5;
    private int pageIndex = 1;
    
    public void setPageSize( int pageSize ) {
        this.pageSize = pageSize;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageIndex( int pageIndex ) {
      this.pageIndex = pageIndex;
    }

    public int getPageIndex() {
      return this.pageIndex;
    }
}