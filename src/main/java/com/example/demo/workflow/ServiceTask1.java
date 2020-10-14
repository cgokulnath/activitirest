package com.example.demo.workflow;

import java.util.Date;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

public class ServiceTask1 implements JavaDelegate {

  @Override
  public void execute(DelegateExecution execution) {
    Date now = new Date();
    
    //Get the variables from process
    
   String var = (String) execution.getVariable("keyWord");
   
   execution.setVariable("Task1Variable", "T1");
    
   System.out.println("Task 1 Started with : Var1:" + var );
    
   try {
	Thread.sleep(1000);
} catch (InterruptedException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
   
    System.out.println("Task 1 completed with ::"+var);
  }

}