package com.contract.manager.controller;

import com.contract.manager.model.Contract;
import com.contract.manager.model.Msg;
import com.contract.manager.model.Page;
import com.contract.manager.model.request.ContractPageRequest;
import com.contract.manager.service.ContractService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping( "/contract" )
public class ContractController {
    @Autowired
    private ContractService contractService;

    /**
     * 获取所有合同
     * @param request
     * @return
     */
    @RequestMapping( "/fetch" )
    public @ResponseBody Msg fetch(@RequestBody ContractPageRequest request) {
        Page page = request.getPage();
        if( page == null ) {
            page = new Page();
            request.setPage( page );
        }

        PageHelper.startPage(page.getPageIndex(), page.getPageSize() );
        List<Contract> contractList = contractService.query( request );

        Map<String,Object> payload = new HashMap<String,Object>();
        payload.put( "contractList", contractList );
        payload.put( "page", page );

        Msg msg = new Msg();
        msg.setCode( 1 );
        msg.setContent( "获取成功" );
        msg.setPayload( payload );

        return msg;
    }
}