package com.contract.manager.service;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service( "workFlowService" )
public class WorkFlowService {
	
	@Autowired
	ProcessEngine processEngine;
	
	/**
	 * 启动流程
	 * @param workFlowKey
	 */
	public void startProcess(String workFlowKey, String userName ) {
		Map<String,Object> variables = new HashMap<String,Object>();
		variables.put( "user", userName );
		ProcessInstance processInstance = processEngine.getRuntimeService().startProcessInstanceByKey( workFlowKey, variables );
		// processInstance.get
	}
	
	/**
	 * 根据任务编号完成任务
	 * @param taskId
	 */
	public void completeTask( String taskId, String userId ) {
		processEngine.getTaskService().complete( taskId );
	}

	/**
	 * 获取任务列表
	 */
	public List<Task> fetchTasks( String assignee ) {
		List<Task> tasks = processEngine.getTaskService()
				.createTaskQuery()
				.taskAssignee(assignee)
				.list();

		return tasks;
	}

	/**
	 * 获取合同任务
	 */
	public List<ProcessInstance> fetchTask( String contractId, String assignee ) {
		return null;
	}
}
