package com.contract.manager.service;

import com.contract.manager.mapper.ActivityMapper;
import com.contract.manager.model.Activity;
import org.activiti.engine.HistoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("activityService")
public class ActivityService {
    @Autowired
    ActivityMapper activityMapper;

    @Autowired
    TaskService taskService;

    @Autowired
    HistoryService historyService;

    public List<Task> fetch() {
        TaskQuery taskQuery = taskService.createTaskQuery();
        taskQuery = taskQuery.taskAssignee( "admin" );
        return taskQuery.list();
    }

    public List<Activity> fetch( List<String> types ) {
        return activityMapper.fetchByType( types );
    }

    /**
     * 根据合同id获取当前流程历史流程信息、任务流程信息
     * @param contractId
     *      合同编号
     * @return 返回流程信息
     */
    public Map<String,Object> queryProcessByContractId(String contractId ) {
        // 查询合同id关联的流程信息
        String actId = activityMapper.queryProcessIdByContractId( contractId );
        // 查询已经完成的任务信息
        List<HistoricActivityInstance> hisActivityList = historyService.createHistoricActivityInstanceQuery().processInstanceId(actId).finished().list();

        // 查询正在执行的任务信息
        List<Task> taskList = taskService.createTaskQuery().processInstanceId( actId ).list();

        Map<String,Object> resultMap = new HashMap<String,Object>();
        resultMap.put( "hisActivityList", hisActivityList );
        resultMap.put( "taskList", taskList );

        return resultMap;
    }
}
