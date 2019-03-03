package com.contract.manager.controller;

import com.contract.manager.model.Activity;
import com.contract.manager.model.Contract;
import com.contract.manager.model.Msg;
import com.contract.manager.service.ActivityService;
import com.contract.manager.util.JwtTokenUtil;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping( "/activity" )
public class ActivityController {

    @Autowired
    ActivityService activityService;

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    IdentityService identityService;

	/**
	 * 获取待办任务
	 * @return
	 */
    @RequestMapping( "task/fetch" )
    public @ResponseBody Msg fetch(@RequestHeader HttpHeaders headers) {
        // String userName = JwtTokenUtil.getAuthenticationUser(headers);

        List<Task> tasks = activityService.fetch();

        List<Activity> activities = new ArrayList<Activity>();

        for( Task task : tasks ) {
            Activity activity = new Activity();
            activity.setTitle( task.getName() );
            activity.setContent( task.getDescription() );
            activities.add( activity );
        }

        Msg msg = new Msg();
        msg.setCode( 1 );
        msg.setContent( "获取成功" );
        msg.setPayload( activities );

        return msg;
    }

    /**
     * 启动流程
     */
    @RequestMapping( "process/start/{processDefKey}")
    public @ResponseBody Msg startProcess( @PathVariable String processDefKey ) {
        Msg msg = new Msg();
        msg.setCode( 1 );
        // msg.setContent( "启动流程:" + processDefKey );
        identityService.setAuthenticatedUserId("admin");
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey( processDefKey );
        msg.setContent( processInstance.getName() );
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

        return msg;
    }
}