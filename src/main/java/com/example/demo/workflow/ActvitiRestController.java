package com.example.demo.workflow;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ActvitiRestController {
	
	 RepositoryService repositoryService;
	 
	 RuntimeService runtimeService;
	 
	 ProcessInstance processInstance;
	 
	 TaskService taskService;
	 
	 ProcessEngineConfiguration cfg;
	 
	 ProcessEngine processEngine;
	 
	 
	 WorkflowConfiguration workflowConfiguration;

	
    @RequestMapping(value="/workflow/deployprocess", method= RequestMethod.GET)
    public String deployProcessInstance() throws Exception
    {
    	
    	  workflowConfiguration = new WorkflowConfiguration("DemoProcess.bpmn20.xml");
    	  
    	  processEngine = workflowConfiguration.getProcessEngine();
                   
          return "Deployment of Process is Done";

    }
    
    @RequestMapping(value="/workflow/startprocess", method= RequestMethod.GET)
    public String startProcessInstance() throws Exception
    {
        
        Map<String, Object> nextDelegateVariables =  new HashMap<>();
        
        nextDelegateVariables.put("keyWord", "workflow");
        nextDelegateVariables.put("language", "java");
        
        
        WorkflowManipulator workflowManipulator = new WorkflowManipulator("demoProcess", workflowConfiguration.getProcessEngine(),
        																	nextDelegateVariables);
        ProcessInstance processInstance = workflowManipulator.startProcess();
        
        //Starts an instance of the 'myProcess' process.
        runtimeService = processEngine.getRuntimeService();
             
    	taskService = processEngine.getTaskService();

    	//Pass on these variables during start of the process
    	return "Started The Process";
    	 
    }
    
    
    @RequestMapping(value="/workflow/queryexecutionprocess", method= RequestMethod.GET)
    public String queryExecutingProcessInstance() throws Exception
    {
    	/**
         * Query running instance
   */
       String response="Queried The Execution Process";
       
       List<Execution> executionList = runtimeService.createExecutionQuery() //Create the process query object being executed
                                           .processDefinitionKey("demoProcess") //Key query defined by process
                                           .orderByProcessInstanceId() //order by process instance id
                                           .desc() //reverse order
                                           .list(); //Query out the collection
      for(Execution execution: executionList){
    	  
    	  response =   "Exec:The id of the process object being executed: "+execution.getId() +
    			       "Exec:The id of the process object being executed: "+execution.getId() + 
                       "Exec:id of the process instance belongs to:"+execution.getProcessInstanceId() + 
                       "Exec:id of the active node: "+execution.getActivityId() + 
                       "Exec:Name of the active node: "+execution.getName();
    	  System.out.println(response);
          
      }
      
    	return response;
    }
    
    @RequestMapping(value="/workflow/querycompletionprocess", method= RequestMethod.GET)
    public String queryCompletedProcessInstance() throws Exception
    {
    	/**
         * Query completed instance
         */
     String response="Queried The Completed Process";

      HistoryService historyService = processEngine.getHistoryService();
      
      List<HistoricProcessInstance> historicProcessInstances =
    		  historyService.createHistoricProcessInstanceQuery().finished().orderByProcessDefinitionId().desc().listPage(0, 10);;
      
      for(HistoricProcessInstance history: historicProcessInstances)
      {
    	  response = "History:  name of the process object being executed: "+ history.getProcessDefinitionName();
    	  System.out.println(response);
      }
      
    	
    	return response;
    }
    
    
    
    

}
