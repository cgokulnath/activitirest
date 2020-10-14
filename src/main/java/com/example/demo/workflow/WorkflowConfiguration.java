package com.example.demo.workflow;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.impl.asyncexecutor.AsyncExecutor;
import org.activiti.engine.impl.asyncexecutor.ManagedAsyncJobExecutor;
import org.activiti.engine.impl.cfg.StandaloneInMemProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.StandaloneProcessEngineConfiguration;

public class WorkflowConfiguration {
  final ProcessEngine processEngine;

  public WorkflowConfiguration(final String workFlowName) {
    processEngine = setUpProcessEngine(workFlowName);
  }

  public ProcessEngine getProcessEngine() {
    return processEngine;
  }

  private ProcessEngine setUpProcessEngine(String workFlowName) {
    ProcessEngineConfiguration cfg = null;
    AsyncExecutor asyncExecutor = new ManagedAsyncJobExecutor();
    
    cfg = new StandaloneInMemProcessEngineConfiguration()
        .setJdbcUrl("jdbc:h2:mem:activiti;DB_CLOSE_DELAY=1000")
        .setJdbcUsername("sa")
        .setJdbcPassword("")
        .setJdbcDriver("org.h2.Driver")
     .setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE)
    .setAsyncExecutor(asyncExecutor)
    .setAsyncExecutorActivate(true);
    
    final ProcessEngine processEngine = cfg.buildProcessEngine();
    RepositoryService repositoryService = processEngine.getRepositoryService();
    repositoryService.createDeployment().addClasspathResource(workFlowName)
        .deploy();
    return processEngine;
  }

}