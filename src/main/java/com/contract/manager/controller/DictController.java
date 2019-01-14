package com.contract.manager.controller;

import com.contract.manager.model.Dict;
import com.contract.manager.model.Msg;
import com.contract.manager.service.DictService;
import com.contract.manager.util.CommonUtil;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping( "/dict" )
public class DictController {

    @Autowired
    DictService dictService;

    @RequestMapping( "/add" )
    public @ResponseBody Msg add( @RequestBody Dict dict) {

        // 1. 动态生成一个dictId
        if( dict != null ) {
            dict.setDictId( CommonUtil.randomUUID() );
        }

        // 2. 保存数据到数据库
        boolean added = dictService.add( dict );

        // 3. 返回JSON数据
        if( added ) {
            return new Msg( 1, "新增字典成功", dict );
        } else {
            return new Msg( 0, "新增字典失败", dict );
        }
    }

    @RequestMapping( "/edit" )
    public @ResponseBody Msg edit( @RequestBody Dict dict) {
        // 1. 保存数据到数据库
        boolean updated = dictService.edit( dict );

        // 2. 返回JSON数据
        if( updated ) {
            return new Msg( 1, "编辑字典成功", dict );
        } else {
            return new Msg( 0, "编辑字典失败", dict );
        }
    }

    @RequestMapping( "/fetch" )
    public @ResponseBody Msg fetch( @RequestBody Dict dict ) {
        PageHelper.startPage( 1, 5 );
        List<Dict> payload = dictService.fetchList( dict );
        //TODO: 增加分页操作
        return new Msg( 1, "字典列表",payload);
    }

    @RequestMapping( "/remove" )
    public @ResponseBody Msg remove( @RequestBody Dict dict ) {
        boolean removed = dictService.remove( dict );
        if( removed ) {
            return new Msg( 1, "删除成功", dict );
        } else {
            return new Msg( 0, "删除失败", dict );
        }
    }
}