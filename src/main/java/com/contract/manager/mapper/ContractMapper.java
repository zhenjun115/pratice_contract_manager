package com.contract.manager.mapper;

import java.util.List;
// import org.apache.ibatis.annotations.Select;
// import org.apache.ibatis.annotations.Repository;
import com.contract.manager.model.*;

import org.apache.ibatis.annotations.Mapper;

// @Repository
@Mapper
public interface ContractMapper {

  //@Select( "select * from contract" )
  List<ContractBean> selectAll();
}