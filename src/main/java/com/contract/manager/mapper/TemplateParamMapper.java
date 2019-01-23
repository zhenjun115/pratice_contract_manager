package com.contract.manager.mapper;

import com.contract.manager.model.TemplateParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface TemplateParamMapper {
  List<TemplateParam> fetchByFilePath( String filePath );
  boolean add(Map<String,Object> params);
}