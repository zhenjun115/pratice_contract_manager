package com.contract.manager.model;

import lombok.Data;

@Data
public class Page {
    private int pageSize = 5;
    private int pageIndex = 1;
}