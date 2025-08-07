package com.kando.commands;

import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import com.kando.storage.DataStore;
import com.kando.storage.Task;

@Command(name = "rename", description = "Rename a task")
public class RenameTaskCommand implements Runnable {

  @Parameters(index = "0", description = "Task ID")
  private int id;

  @Parameters(index = "1", description = "New task title")
  private String newTitle;

  @Override
  public void run() {
    DataStore ds = DataStore.getInstance();
    Task task = ds.findTaskInActiveProjectById(id);
    if (task == null) {
      System.out.printf("Task with ID [%d] not found in active project.%n", id);
      return;
    }
    task.title = newTitle;
    ds.save();
    System.out.printf("Renamed task [%d] to \"%s\"%n", task.id, task.title);
  }
}
