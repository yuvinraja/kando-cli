package com.kando.commands;

import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import com.kando.storage.DataStore;
import com.kando.storage.Project;

@Command(name = "new", description = "Create a new project and make it active")
public class NewProjectCommand implements Runnable {

  @Parameters(index = "0", description = "Project name")
  private String name;

  @Override
  public void run() {
    DataStore ds = DataStore.getInstance();
    Project existing = ds.findProjectByName(name);
    if (existing != null) {
      ds.getData().activeProject = name;
      ds.save();
      System.out.printf("Project already exists. Switched to project: %s%n", name);
      return;
    }
    ds.createProject(name);
    System.out.printf("Created new project: %s%nSwitched to project: %s%n", name, name);
  }
}
