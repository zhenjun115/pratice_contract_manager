package com.contract.manager.mapper;

import java.util.List;
import java.util.Map;

import com.contract.manager.model.Contract;

import com.contract.manager.model.request.ContractQueryRequest;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ContractMapper {

  //@Select( "select * from contract" )
  List<Contract> selectAll();

  /**
   * 查询合同列表
   * @param contract
   * @return
   */
  List<Contract> queryAll(Contract contract );

  List<Contract> queryContract(ContractQueryRequest request);

  Contract fetch( Contract contract );

  long createDraft( Contract contract );

  Contract fetchDraft( Contract contract );

  long saveDraft( Contract contract );

  long save( Map<String,Object> contact );

  boolean saveInfo( Contract contract );
}