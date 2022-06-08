package main.undo;

import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Component
public class UndoController {
    private List<Operation> operations;
    private int index;
    private boolean duringCallback;

    public UndoController() {
        this.operations = new ArrayList<>();
        this.index = -1;
        this.duringCallback = false;
    }

    public void recordOperation(Operation operation) {
        if (duringCallback)
            return;
        // No redos after a new operation
        operations = operations.subList(0, index + 1);
        // Add the new operation
        operations.add(operation);
        index += 1;
    }

    @Transactional
    public boolean undo() {
        if (index < 0)
            return false;
        duringCallback = true;
        operations.get(index).undo();
        duringCallback = false;
        index -= 1;
        return true;
    }

    @Transactional
    public boolean redo() {
        if (index+1 >= operations.size())
            return false;
        duringCallback = true;
        index += 1;
        operations.get(index).execute();
        duringCallback = false;
        return true;
    }

    public List<Operation> getOperations() {
        return operations;
    }

    public int getIndex() {
        return index;
    }
}
