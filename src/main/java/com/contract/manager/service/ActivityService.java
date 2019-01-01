package com.contract.manager.service;

import com.contract.manager.mapper.ActivityMapper;
import com.contract.manager.model.Activity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("activityService")
public class ActivityService {
    @Autowired
    ActivityMapper activityMapper;

    public List<Activity> fetch() {
        return activityMapper.fetch();
    }

    public List<Activity> fetch( List<String> types ) {
        return activityMapper.fetchByType( types );
    }
}
