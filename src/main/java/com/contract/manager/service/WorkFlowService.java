package com.contract.manager.service;

//import org.activiti.bpmn.model.BpmnModel;
//import org.activiti.bpmn.model.GraphicInfo;
//import org.activiti.bpmn.model.Process;
//import org.activiti.engine.ProcessEngine;
//import org.activiti.engine.RepositoryService;
//import org.activiti.engine.impl.util.ProcessDefinitionUtil;
//import org.activiti.engine.repository.ProcessDefinition;
//import org.activiti.engine.runtime.ProcessInstance;
//import org.activiti.engine.task.Task;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service( "workFlowService" )
public class WorkFlowService {
	
//	@Autowired
//	ProcessEngine processEngine;
//
//	@Autowired
//	RepositoryService repositoryService;
//
//	/**
//	 * 启动流程
//	 * @param workFlowKey
//	 */
//	public void startProcess(String workFlowKey, String userName ) {
//		Map<String,Object> variables = new HashMap<String,Object>();
//		variables.put( "user", userName );
//		ProcessInstance processInstance = processEngine.getRuntimeService().startProcessInstanceByKey( workFlowKey, variables );
//		// processInstance.get
//	}
//
//	/**
//	 * 根据任务编号完成任务
//	 * @param taskId
//	 */
//	public void completeTask( String taskId, String userId ) {
//		processEngine.getTaskService().complete( taskId );
//	}
//
//	/**
//	 * 获取任务列表
//	 */
//	public List<Task> fetchTasks( String assignee ) {
//		List<Task> tasks = processEngine.getTaskService()
//				.createTaskQuery()
//				.taskAssignee(assignee)
//				.list();
//
//		return tasks;
//	}
//
//	/**
//	 * 获取合同任务
//	 */
//	public List<ProcessInstance> fetchTask( String contractId, String assignee ) {
//		return null;
//	}
//
//	/**
//	 * 获取合同流程
//	 */
//	public Map<String,GraphicInfo> fetchProcess(String contractId, String assignee ) {
//		List<String> deployment = processEngine.getRepositoryService().getDeploymentResourceNames( "5001" );
//		ProcessDefinition processDefinition = processEngine.getRepositoryService().getProcessDefinition( "contract_create_flow:1:2504" );
//		String name  = processDefinition.getName();
//		deployment.add( name );
//		BpmnModel bpmnModel = processEngine.getRepositoryService().getBpmnModel( "contract_create_flow:1:2504" );
//		Map<String, GraphicInfo> bpmnModelLocationMap = bpmnModel.getLocationMap();
//		// ProcessDefinitionUtil.getBpmnModel( "" ).getUserTaskFormTypes();
//		// Process process = ProcessDefinitionUtil.getProcess( "contract_create_flow:1:4" );
//		// process.getFlowElements();
//		return bpmnModelLocationMap;
//	}
}
