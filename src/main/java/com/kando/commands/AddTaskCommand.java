package com.kando.commands;

import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import com.kando.storage.DataStore;
import com.kando.storage.Project;
import com.kando.storage.Task;

@Command(name = "add-task", description = "Add a new task to the active project")
public class AddTaskCommand implements Runnable {

  @Parameters(index = "0", description = "Task title")
  private String title;

  @Override
  public void run() {
    DataStore ds = DataStore.getInstance();
    String activeName = ds.getData().activeProject;

    if (activeName == null) {
      System.out.println("No active project. Create one with: kando new \"ProjectName\"");
      return;
    }

    // get the active project
    Project activeProject = ds.findProjectByName(activeName);
    if (activeProject == null) {
      System.out.println("Active project not found. Please create a project first.");
      return;
    }

    // generate task ID
    int newId = 1;
    for (Task t : activeProject.tasks) {
      if (t.id >= newId) {
        newId = t.id + 1;
      }
    }

    // create and add task
    Task newTask = new Task(newId, title, "todo");
    activeProject.tasks.add(newTask);

    ds.save();

    System.out.printf("Added task [%d] \"%s\" to column: todo%n", newId, title);
  }
}
