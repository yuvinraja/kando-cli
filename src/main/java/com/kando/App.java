package com.kando;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import com.kando.commands.NewProjectCommand;
import com.kando.commands.ListProjectsCommand;

@Command(name = "kando", mixinStandardHelpOptions = true, version = "kando 1.0", description = "Kando CLI - local kanban for projects", subcommands = {
        NewProjectCommand.class,
        ListProjectsCommand.class
        // we'll add more subcommands later (add, move, show, etc.)
})
public class App implements Runnable {
    @Override
    public void run() {
        System.out.println("Kando CLI — a tiny local kanban. Use --help for commands.");
    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new App()).execute(args);
        System.exit(exitCode);
    }
}
