package com.example.demo.workflow;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.runtime.ProcessInstance;

import java.util.HashMap;
import java.util.Map;

public class WorkflowManipulator {
  private final Map<String, Object> nextDelegateVariables ;
  private final String wfName;
  private final ProcessEngine engine;

  public WorkflowManipulator(String wfName, ProcessEngine engine,Map<String, Object> nextDelegateVariables) {
	  
    this.nextDelegateVariables = nextDelegateVariables;
    this.wfName = wfName;
    this.engine = engine;
  }


  public ProcessInstance startProcess() {
	if ((null != nextDelegateVariables) && (nextDelegateVariables.size() > 0))
         return engine.getRuntimeService().startProcessInstanceByKey(wfName, nextDelegateVariables);
    else 
      return engine.getRuntimeService().startProcessInstanceByKey(wfName);
  }
}