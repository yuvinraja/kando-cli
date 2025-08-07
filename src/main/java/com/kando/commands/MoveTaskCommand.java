package com.kando.commands;

import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.Option;
import com.kando.storage.DataStore;
import com.kando.storage.Task;

@Command(name = "move", description = "Move a task to another column")
public class MoveTaskCommand implements Runnable {

  @Parameters(index = "0", description = "Task ID")
  private int id;

  @Option(names = "--col", required = true, description = "Target column name")
  private String column;

  @Override
  public void run() {
    DataStore ds = DataStore.getInstance();
    Task task = ds.findTaskInActiveProjectById(id);
    if (task == null) {
      System.out.printf("Task with ID [%d] not found in active project.%n", id);
      return;
    }
    task.column = column;
    ds.save();
    System.out.printf("Moved task [%d] \"%s\" to column: %s%n", task.id, task.title, task.column);
  }
}
