package com.contract.manager.service;

import com.contract.manager.mapper.ContractFileMapper;
import com.contract.manager.model.Contract;
import com.contract.manager.model.ContractFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("contractFileService")
public class ContractFileService {
    @Autowired
    ContractFileMapper contractFileMapper;

    public List<ContractFile> fetchFiles( Contract contract ) {
        return contractFileMapper.fetchFiles( contract );
    }

    public ContractFile fetch( String fileId ) {
        return contractFileMapper.fetch( fileId );
    }

    public boolean addFile( ContractFile contractFile ) {
        return contractFileMapper.addFile( contractFile );
    }

    public boolean delFile( ContractFile contractFile ) {
        return contractFileMapper.delFile( contractFile );
    }
}
