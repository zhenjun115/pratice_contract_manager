package com.contract.manager.mapper;

import com.contract.manager.model.Contract;
import com.contract.manager.model.ContractFile;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ContractFileMapper {
    boolean addFile(ContractFile contractFile);
    boolean delFile(ContractFile contractFile);
    List<ContractFile> fetchFiles(Contract contract);
}
