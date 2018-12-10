package com.contract.manager.model;

public class Page {
    private long pageSize;
    private long pageIndex;
    
    public void setPageSize( long pageSize ) {
        this.pageSize = pageSize;
    }

    public long getPageSize() {
        return this.pageSize;
    }

    public void setPageIndex( long pageIndex ) {
      this.pageIndex = pageIndex;
    }

    public long getPageIndex() {
      return this.pageIndex;
    }
}