package com.kando.commands;

import java.util.List;
import java.util.stream.Collectors;

import com.kando.storage.DataStore;
import com.kando.storage.Project;
import com.kando.storage.Task;
import com.kando.utils.ConsoleColors;

import picocli.CommandLine.Command;

@Command(name = "show", description = "Show the active project's Kanban board")
public class ShowBoardCommand implements Runnable {

    // ANSI color codes
    private static final String YELLOW = ConsoleColors.YELLOW;
    private static final String GREEN = ConsoleColors.GREEN;
    private static final String MAGENTA = ConsoleColors.MAGENTA;
    private static final String RESET = ConsoleColors.RESET;

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

        // Group tasks by column
        List<Task> todo = getTasksByColumn(activeProject, "todo");
        List<Task> inProgress = getTasksByColumn(activeProject, "in-progress");
        List<Task> done = getTasksByColumn(activeProject, "done");

        // Print the kanban board
        printKanbanBoard(activeProject.name, todo, inProgress, done);
    }

    private List<Task> getTasksByColumn(Project project, String column) {
        return project.tasks.stream()
                .filter(t -> column.equalsIgnoreCase(t.column))
                .collect(Collectors.toList());
    }

    private void printKanbanBoard(String projectName, List<Task> todo, List<Task> inProgress, List<Task> done) {
        System.out.println();

        // Calculate column width dynamically based on content
        int columnWidth = calculateColumnWidth(todo, inProgress, done);

        // Print column headers with proper spacing and colors
        String todoHeader = YELLOW + "todo" + RESET;
        String progressHeader = GREEN + "in-progress" + RESET;
        String doneHeader = MAGENTA + "done" + RESET;

        System.out.printf("%-" + columnWidth + "s %-" + columnWidth + "s %-" + columnWidth + "s%n",
                todoHeader, progressHeader, doneHeader);

        // Print separator line
        String separator = "─".repeat(columnWidth * 3 + 2);
        System.out.println(separator);

        // Print tasks in columns
        int maxRows = Math.max(todo.size(), Math.max(inProgress.size(), done.size()));

        // Ensure we show at least one row even if empty
        if (maxRows == 0) {
            maxRows = 1;
        }

        for (int i = 0; i < maxRows; i++) {
            String col1 = i < todo.size() ? formatTask(todo.get(i)) : "";
            String col2 = i < inProgress.size() ? formatTask(inProgress.get(i)) : "";
            String col3 = i < done.size() ? formatTask(done.get(i)) : "";

            System.out.printf("%-" + columnWidth + "s %-" + columnWidth + "s %-" + columnWidth + "s%n",
                    col1, col2, col3);
        }

        System.out.println();
        System.out.printf("Active Project: %s%n", projectName);
    }

    private int calculateColumnWidth(List<Task> todo, List<Task> inProgress, List<Task> done) {
        int maxWidth = 15; // Minimum width

        // Check all tasks to find the longest one
        for (Task task : todo) {
            maxWidth = Math.max(maxWidth, formatTask(task).length());
        }
        for (Task task : inProgress) {
            maxWidth = Math.max(maxWidth, formatTask(task).length());
        }
        for (Task task : done) {
            maxWidth = Math.max(maxWidth, formatTask(task).length());
        }

        // Add some padding
        return Math.min(maxWidth + 3, 25); // Cap at 25 characters
    }

    private String formatTask(Task task) {
        return String.format("[%d] %s", task.id, task.title);
    }
}