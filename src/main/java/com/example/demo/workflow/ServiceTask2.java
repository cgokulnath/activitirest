package com.example.demo.workflow;

import java.util.Date;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

public class ServiceTask2 implements JavaDelegate {

  @Override
  public void execute(DelegateExecution execution) {
    Date now = new Date();
    
    String var1 = (String) execution.getVariable("language");
    String var2 = (String) execution.getVariable("Task1Variable");
    
    System.out.println("Task 2 Started with : Var1:" + var1 + " and Var2:"+ var2);
    
    try {
    	Thread.sleep(1000);
    } catch (InterruptedException e) {
    	// TODO Auto-generated catch block
    	e.printStackTrace();
    }
    
    
    System.out.println("Task 2 completed");
  }

}