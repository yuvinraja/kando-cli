package com.kando;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "kando", // the CLI command name
        mixinStandardHelpOptions = true, // adds --help and --version
        version = "kando 1.0", description = "A simple CLI tool example")
public class App implements Runnable {

    @Option(names = { "-n", "--name" }, description = "Your name")
    private String name;

    @Override
    public void run() {
        if (name == null || name.trim().isEmpty()) {
            System.out.println("Hello! Please provide your name with --name.");
        } else {
            System.out.println("Hello, " + name + "!");
        }
    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new App()).execute(args);
        System.exit(exitCode);
    }
}
