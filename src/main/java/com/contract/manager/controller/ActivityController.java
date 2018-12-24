package com.contract.manager.controller;

import com.contract.manager.model.Contract;
import com.contract.manager.model.Msg;
import com.contract.manager.service.WorkFlowService;
import com.contract.manager.util.JwtTokenUtil;
//import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping( "/workflow/activity" )
public class ActivityController {

    @Autowired
    WorkFlowService workFlowService;

	/**
	 * 获取待办任务
	 * @return
	 */
    @RequestMapping( "task" )
    public @ResponseBody Msg fetch(@RequestHeader HttpHeaders headers) {
        String userName = JwtTokenUtil.getAuthenticationUser(headers);
//        List<Task> tasks = workFlowService.fetchTasks( userName );
        HashMap<String,Object> payload = new HashMap<String,Object>();

//        for( Task task : tasks ) {
//            payload.put( "name", task.getName() );
//            payload.put( "createTime", task.getCreateTime() );
//        }

        // System.out.println( "tasks: " + tasks.size() );

        Msg msg = new Msg();
        msg.setCode( 1 );
        msg.setContent( "获取成功" );
        msg.setPayload( payload );

        return msg;
    }

    /**
     * 获取流程
     * @param contract
     * @return
     */
    @RequestMapping( "process" )
    public @ResponseBody Msg fetchProcess( @RequestBody Contract contract ) {
        Msg msg = new Msg();
        msg.setCode( 1 );
        msg.setContent( "" );
//        msg.setPayload( workFlowService.fetchProcess( "2505", "user" ) );

        return msg;
    }
}