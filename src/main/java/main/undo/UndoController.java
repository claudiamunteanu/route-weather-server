package main.undo;

import main.domain.User;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UndoController {
    private Map<String, List<Operation>> operations;
    private Map<String, Integer> indexes;
    private boolean duringCallback;

    public UndoController() {
        this.operations = new HashMap<>();
        this.indexes = new HashMap<>();
        this.duringCallback = false;
    }

    public void recordOperation(String username, Operation operation) {
        if (duringCallback)
            return;
        // No redos after a new operation
        if(!operations.containsKey(username)){
            operations.put(username, new ArrayList<>());
            indexes.put(username, -1);
        }
        int index = indexes.get(username);
        operations.put(username, operations.get(username).subList(0, index+1));
        // Add the new operation
        operations.get(username).add(operation);
        indexes.put(username, index+1);
    }

    @Transactional
    public boolean undo(String username) {
        int index = indexes.get(username);
        if (index < 0)
            return false;
        operations.get(username).get(index).undo();
        duringCallback = false;
        indexes.put(username, index-1);
        return true;
    }

    @Transactional
    public boolean redo(String username) {
        int index = indexes.get(username);
        if (index+1 >= operations.get(username).size())
            return false;
        duringCallback = true;

        operations.get(username).get(index+1).execute();
        indexes.put(username, index + 1);
        duringCallback = false;
        return true;
    }

    public List<Operation> getUserOperations(String username) {
        return operations.get(username);
    }

    public int getOperationsSize() {
        return operations.size();
    }

    public int getIndex(String username) {
        return indexes.get(username);
    }
}
