package com.contract.manager.service;

import com.contract.manager.mapper.TemplateParamMapper;
import com.contract.manager.model.TemplateParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service( "templateParamService" )
public class TemplateParamService {

    @Autowired
    TemplateParamMapper templateParamMapper;

    public List<TemplateParam> fetchByFilePath( String filePath ) {
        return templateParamMapper.fetchByFilePath( filePath );
    }

    public boolean addParam(Map<String,Object> params) {
        return templateParamMapper.add( params );
    }
}
