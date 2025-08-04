package com.kando.commands;

import picocli.CommandLine.Command;
import com.kando.storage.DataStore;
import com.kando.storage.Project;

@Command(name = "list", description = "List all projects (active one is highlighted)")
public class ListProjectsCommand implements Runnable {

    @Override
    public void run() {
        DataStore ds = DataStore.getInstance();
        System.out.println("Projects:");
        String active = ds.getData().activeProject;
        for (Project p : ds.listProjects()) {
            if (p == null)
                continue;
            if (p.name != null && p.name.equals(active)) {
                System.out.println("* " + p.name + "  (active)");
            } else {
                System.out.println("  " + p.name);
            }
        }
    }
}
