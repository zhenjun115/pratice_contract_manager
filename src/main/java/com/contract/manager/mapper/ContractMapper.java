package com.contract.manager.mapper;

import java.util.List;

import com.contract.manager.model.Contract;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ContractMapper {

  //@Select( "select * from contract" )
  List<Contract> selectAll();

  Contract fetch( Contract contract );

  long createDraft( Contract contract );

  Contract fetchDraft( Contract contract );

  long saveDraft( Contract contract );
}