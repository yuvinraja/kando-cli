package com.kando.commands;

import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import com.kando.storage.DataStore;
import com.kando.storage.Task;

@Command(name = "delete", description = "Delete a task")
public class DeleteTaskCommand implements Runnable {

  @Parameters(index = "0", description = "Task ID")
  private int id;

  @Override
  public void run() {
    DataStore ds = DataStore.getInstance();
    Task task = ds.findTaskInActiveProjectById(id);
    if (task == null) {
      System.out.printf("Task with ID [%d] not found in active project.%n", id);
      return;
    }
    boolean removed = ds.removeTaskFromActiveProjectById(id);
    if (removed) {
      System.out.printf("Deleted task [%d] \"%s\"%n", task.id, task.title);
    } else {
      System.out.printf("Failed to delete task [%d].%n", id);
    }
  }
}
