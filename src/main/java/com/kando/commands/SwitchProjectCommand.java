package com.kando.commands;

import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import com.kando.storage.DataStore;
import com.kando.storage.Project;

@Command(name = "switch", description = "Switches to an existing project")
public class SwitchProjectCommand implements Runnable {

    @Parameters(index="0", description = "Name of the project to switch to")
    private String name;
  
    @Override
    public void run() {
        DataStore ds = DataStore.getInstance();
        Project existing = ds.findProjectByName(name);

        if (existing == null) {
            System.err.printf("Error: Project '%s' does not exist.%n", name);
            return;
        }

        ds.getData().activeProject = name;
        ds.save();
        System.err.printf("Switched to project: %s%n", name);
    }
}
