package com.contract.manager.mapper;

import com.contract.manager.model.Dict;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DictMapper {
    boolean add(Dict dict);
    boolean edit( Dict dict );
    List<Dict> fetchList(Dict dict);
    boolean remove(Dict dict);
}
