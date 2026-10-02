package task;

import java.time.LocalTime;

public class Task {
    private final int id;
    private final String name;
    private final LocalTime start;
    private final LocalTime end;

    public Task(int id, String name, LocalTime start, LocalTime end) {
        this.id = id;
        this.name = name;
        this.start = start;
        this.end = end;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public LocalTime getStart() { return start; }
    public LocalTime getEnd() { return end; }

    @Override
    public String toString() {
        return id + " " + name + " " + start + " " + end;
    }
}
