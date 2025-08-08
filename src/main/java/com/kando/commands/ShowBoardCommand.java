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
    
    // Box drawing characters for table borders
    private static final String TOP_LEFT = "┌";
    private static final String TOP_RIGHT = "┐";
    private static final String BOTTOM_LEFT = "└";
    private static final String BOTTOM_RIGHT = "┘";
    private static final String HORIZONTAL = "─";
    private static final String VERTICAL = "│";
    private static final String TOP_TEE = "┬";
    private static final String BOTTOM_TEE = "┴";
    private static final String CROSS = "┼";
    private static final String LEFT_TEE = "├";
    private static final String RIGHT_TEE = "┤";

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

        // Calculate column width dynamically based on content
        int columnWidth = calculateColumnWidth(todo, inProgress, done);

        // Print the kanban board
        printKanbanBoard(activeProject.name, todo, inProgress, done, columnWidth);
    }

    private List<Task> getTasksByColumn(Project project, String column) {
        return project.tasks.stream()
                .filter(t -> column.equalsIgnoreCase(t.column))
                .collect(Collectors.toList());
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
        
        // Also consider header lengths (without color codes)
        maxWidth = Math.max(maxWidth, "todo".length());
        maxWidth = Math.max(maxWidth, "in-progress".length());
        maxWidth = Math.max(maxWidth, "done".length());

        // Add some padding
        return Math.min(maxWidth + 4, 25); // Cap at 25 characters
    }

    private void printKanbanBoard(String projectName, List<Task> todo, List<Task> inProgress, List<Task> done,
            int columnWidth) {
        System.out.println();
        
        // Print top border
        printTopBorder(columnWidth);
        
        // Print column headers
        printHeaders(columnWidth);
        
        // Print middle border (separator between headers and content)
        printMiddleBorder(columnWidth);

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

            System.out.printf("%s %-" + (columnWidth - 1) + "s %s %-" + (columnWidth - 1) + "s %s %-" + (columnWidth - 1) + "s %s%n",
                    VERTICAL, col1, VERTICAL, col2, VERTICAL, col3, VERTICAL);
        }
        
        // Print bottom border
        printBottomBorder(columnWidth);

        System.out.println();
        System.out.printf("Active Project: %s%n", projectName);
    }
    
    private void printTopBorder(int columnWidth) {
        System.out.print(TOP_LEFT);
        System.out.print(HORIZONTAL.repeat(columnWidth));
        System.out.print(TOP_TEE);
        System.out.print(HORIZONTAL.repeat(columnWidth));
        System.out.print(TOP_TEE);
        System.out.print(HORIZONTAL.repeat(columnWidth));
        System.out.println(TOP_RIGHT);
    }
    
    private void printMiddleBorder(int columnWidth) {
        System.out.print(LEFT_TEE);
        System.out.print(HORIZONTAL.repeat(columnWidth));
        System.out.print(CROSS);
        System.out.print(HORIZONTAL.repeat(columnWidth));
        System.out.print(CROSS);
        System.out.print(HORIZONTAL.repeat(columnWidth));
        System.out.println(RIGHT_TEE);
    }
    
    private void printBottomBorder(int columnWidth) {
        System.out.print(BOTTOM_LEFT);
        System.out.print(HORIZONTAL.repeat(columnWidth));
        System.out.print(BOTTOM_TEE);
        System.out.print(HORIZONTAL.repeat(columnWidth));
        System.out.print(BOTTOM_TEE);
        System.out.print(HORIZONTAL.repeat(columnWidth));
        System.out.println(BOTTOM_RIGHT);
    }
    
    private void printHeaders(int columnWidth) {
        // Create padded headers without color codes first
        String todoText = centerText("todo", columnWidth - 1);
        String progressText = centerText("in-progress", columnWidth - 1);
        String doneText = centerText("done", columnWidth - 1);
        
        // Apply colors to the centered text
        String coloredTodo = YELLOW + todoText + RESET;
        String coloredProgress = GREEN + progressText + RESET;
        String coloredDone = MAGENTA + doneText + RESET;
        
        // Print with proper spacing (accounting for the fact that colors don't affect display width)
        System.out.printf("%s%s %s%s %s%s %s%n",
                VERTICAL, coloredTodo, VERTICAL, coloredProgress, VERTICAL, coloredDone, VERTICAL);
    }
    
    private String centerText(String text, int width) {
        if (text.length() >= width) {
            return text.substring(0, width);
        }
        
        int padding = width - text.length();
        int leftPadding = padding / 2;
        int rightPadding = padding - leftPadding;
        
        return " ".repeat(leftPadding) + text + " ".repeat(rightPadding);
    }

    private String formatTask(Task task) {
        return String.format("[%d] %s", task.id, task.title);
    }
}