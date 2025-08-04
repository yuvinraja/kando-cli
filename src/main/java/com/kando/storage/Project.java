package com.kando.storage;

import java.util.ArrayList;
import java.util.List;

public class Project {
  public String name;
  public List<Task> tasks = new ArrayList<>();

  public Project() {
  }

  public Project(String name) {
    this.name = name;
  }
}
