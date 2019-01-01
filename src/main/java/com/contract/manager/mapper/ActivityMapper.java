package com.contract.manager.mapper;

import com.contract.manager.model.Activity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ActivityMapper {
    List<Activity> fetch();
    List<Activity> fetchByType( List<String> types );
}
