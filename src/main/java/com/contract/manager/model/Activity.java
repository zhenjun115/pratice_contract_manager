package com.contract.manager.model;

import lombok.Data;

@Data
public class Activity {
    private String id;
    private String type;
    private String title;
    private String content;
    private String url;
    private String owner;
}
