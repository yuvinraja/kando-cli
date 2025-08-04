package com.kando.storage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.List;

public class DataStore {
  private static final String DIR = System.getProperty("user.home") + File.separator + ".kando";
  private static final String DATA_FILE = DIR + File.separator + "data.json";

  private static DataStore instance;
  private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
  private KandoData data;

  private DataStore() {
    load();
  }

  public static synchronized DataStore getInstance() {
    if (instance == null)
      instance = new DataStore();
    return instance;
  }

  private void load() {
    try {
      File dir = new File(DIR);
      if (!dir.exists())
        dir.mkdirs();

      File file = new File(DATA_FILE);
      if (!file.exists()) {
        data = new KandoData();
        save(); // create initial file
        return;
      }

      try (Reader reader = new FileReader(file)) {
        data = gson.fromJson(reader, KandoData.class);
        if (data == null)
          data = new KandoData();
        if (data.projects == null)
          data.projects = new java.util.ArrayList<>();
      }
    } catch (Exception e) {
      System.err.println("Failed to load data.json, starting with an empty store.");
      e.printStackTrace();
      data = new KandoData();
    }
  }

  public synchronized void save() {
    try (Writer writer = new FileWriter(DATA_FILE)) {
      gson.toJson(data, writer);
    } catch (IOException e) {
      System.err.println("Failed to save data.json:");
      e.printStackTrace();
    }
  }

  public KandoData getData() {
    return data;
  }

  public synchronized Project createProject(String name) {
    Project p = new Project(name);
    data.projects.add(p);
    data.activeProject = name;
    save();
    return p;
  }

  public synchronized Project findProjectByName(String name) {
    for (Project p : data.projects) {
      if (p != null && p.name != null && p.name.equals(name))
        return p;
    }
    return null;
  }

  public synchronized List<Project> listProjects() {
    return data.projects;
  }

  public synchronized Project getActiveProject() {
    if (data.activeProject == null)
      return null;
    return findProjectByName(data.activeProject);
  }

  public synchronized int nextGlobalId() {
    int max = 0;
    for (Project p : data.projects) {
      for (Task t : p.tasks) {
        if (t != null && t.id > max)
          max = t.id;
      }
    }
    return max + 1;
  }

  public synchronized Task addTaskToActiveProject(String title, String column) {
    Project p = getActiveProject();
    if (p == null)
      return null;
    int id = nextGlobalId();
    Task t = new Task(id, title, column);
    p.tasks.add(t);
    save();
    return t;
  }

  // Additional helpers will be added as we implement more commands.
}
