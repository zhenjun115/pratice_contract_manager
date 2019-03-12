package com.contract.manager.service;

import com.contract.manager.mapper.WorkflowMapper;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service( "workFlowService" )
public class WorkFlowService {
	
	@Autowired
    ProcessEngine processEngine;

    @Autowired
    WorkflowMapper workflowMapper;

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    IdentityService identityService;

	/**
	 * 启动流程
	 * @param workFlowKey
	 */
	public String startProcess( String workFlowKey ) {
		// TODO: 默认管理员
        identityService.setAuthenticatedUserId("admin");
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey( workFlowKey );
		return processInstance.getProcessInstanceId();
	}
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

    /**
     * 根据合同ID获取流程信息
     * @param contractId
     * @return 获取流程
     */
    public String queryProcessIdByContractId( String contractId ) {
        return workflowMapper.queryProcessIdByContractId( contractId );
    }

    /**
     * 根据流程ID获取基本信息
     * @param processInstanceId
     * @return 流程基本信息
     */
    public ProcessInstance queryInstanceByProcessInstanceId( String processInstanceId ) {
        ProcessInstance instance = runtimeService.createProcessInstanceQuery().processInstanceId( processInstanceId ).singleResult();
        return instance;
    }
}
