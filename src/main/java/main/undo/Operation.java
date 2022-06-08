package main.undo;

public interface Operation {
    void execute();
    void undo();
}
