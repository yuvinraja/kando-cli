package com.kando.storage;

public class Task {
    public int id;
    public String title;
    public String column; // "todo", "in-progress", "done"

    // No-arg constructor needed by Gson
    public Task() {
    }

    public Task(int id, String title, String column) {
        this.id = id;
        this.title = title;
        this.column = column;
    }
}
