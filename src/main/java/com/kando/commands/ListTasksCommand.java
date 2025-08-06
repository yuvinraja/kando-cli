package com.kando.commands;

import picocli.CommandLine.Command;
import com.kando.storage.DataStore;
import com.kando.storage.Project;
import com.kando.storage.Task;

@Command(name = "list-tasks", description = "List all tasks in the active project, grouped by column")
public class ListTasksCommand implements Runnable {

  @Override
  public void run() {
    DataStore ds = DataStore.getInstance();
    String activeName = ds.getData().activeProject;

    if (activeName == null) {
      System.out.println("No active project. Create one with: kando new \"ProjectName\"");
      return;
    }

    Project activeProject = ds.findProjectByName(activeName);
    if (activeProject == null) {
      System.out.println("Active project not found. Please create a project first.");
      return;
    }

    if (activeProject.tasks.isEmpty()) {
      System.out.println("No tasks found in this project.");
      return;
    }

    System.out.printf("Tasks for project: %s%n", activeProject.name);

    // group tasks by column
    String[] columns = { "todo", "in-progress", "done" };
    for (String col : columns) {
      System.out.println("\n" + col.toUpperCase());
      for (Task t : activeProject.tasks) {
        if (col.equalsIgnoreCase(t.column)) {
          System.out.printf("  [%d] %s%n", t.id, t.title);
        }
      }
    }
  }
}
