package com.contract.manager.controller;

import com.contract.manager.model.Activity;
import com.contract.manager.model.Contract;
import com.contract.manager.model.Msg;
import com.contract.manager.service.ActivityService;
import com.contract.manager.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping( "/activity" )
public class ActivityController {

    @Autowired
    ActivityService activityService;

	/**
	 * 获取待办任务
	 * @return
	 */
    @RequestMapping( "tasks" )
    public @ResponseBody Msg fetch(@RequestHeader HttpHeaders headers) {
        String userName = JwtTokenUtil.getAuthenticationUser(headers);

        List<Activity> payload = activityService.fetch();

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

        return msg;
    }
}