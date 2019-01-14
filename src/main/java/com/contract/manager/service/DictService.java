package com.contract.manager.service;

import com.contract.manager.mapper.DictMapper;
import com.contract.manager.model.Dict;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service( "dictService" )
public class DictService {

    @Autowired
    DictMapper dictMapper;

    public boolean add( Dict dict ) {
        return dictMapper.add( dict );
    }

    public boolean edit( Dict dict ) {
        return dictMapper.edit( dict );
    }

    public List<Dict> fetchList( Dict dict ) {
        return dictMapper.fetchList( dict );
    }

    public boolean remove( Dict dict ) {
        return dictMapper.remove( dict );
    }
}
