package main.undo;

import main.domain.DrivingTip;
import main.domain.User;
import main.domain.WeatherType;
import main.persistence.DrivingTipRepository;
import main.persistence.DrivingTipRepositoryInMemory;
import main.undo.operations.AddDrivingTipOperation;
import main.undo.operations.DeleteDrivingTipOperation;
import main.undo.operations.UpdateDrivingTipOperation;
import org.junit.jupiter.api.*;
import org.apache.log4j.BasicConfigurator;

import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UndoControllerUnitTest {
    public UndoController undoController;

    @BeforeAll
    static void init() {
        BasicConfigurator.configure();
    }

    @BeforeEach
    public void setUp() {
        undoController = new UndoController();
    }
    @Test
    public void recordOperationTest(){
        String username = "test";
        assertEquals(0, undoController.getOperationsSize());
        undoController.recordOperation(username, new AddDrivingTipOperation());
        undoController.recordOperation(username, new DeleteDrivingTipOperation());
        undoController.recordOperation(username, new UpdateDrivingTipOperation());
        assertEquals(3, undoController.getUserOperations(username).size());
        assertEquals(2, undoController.getIndex(username));
        assertEquals(1, undoController.getOperationsSize());

        String username2 = "test2";
        undoController.recordOperation(username2, new DeleteDrivingTipOperation());
        assertEquals(2, undoController.getOperationsSize());
        assertEquals(1, undoController.getUserOperations(username2).size());
        assertEquals(3, undoController.getUserOperations(username).size());
        assertEquals(2, undoController.getIndex(username));
        assertEquals(0, undoController.getIndex(username2));


    }

    @Test
    public void undoTest(){
        User user = new User(1L, "test", "test", "test", "test");
        DrivingTip drivingTip = new DrivingTip(0L, "text", new HashSet<>(List.of(WeatherType.CLOUDS)), user);
        DrivingTipRepository repository = new DrivingTipRepositoryInMemory();
        undoController.recordOperation(user.getUsername(), new AddDrivingTipOperation(drivingTip, repository));
        undoController.recordOperation(user.getUsername(), new DeleteDrivingTipOperation(drivingTip, repository));

        boolean result = undoController.undo(user.getUsername());
        assertTrue(result);
        assertEquals(0, undoController.getIndex(user.getUsername()));
        assertEquals(1, repository.count());

        result = undoController.undo(user.getUsername());
        assertTrue(result);
        assertEquals(-1, undoController.getIndex(user.getUsername()));
        assertEquals(0, repository.count());

        result = undoController.undo(user.getUsername());
        assertFalse(result);

        undoController.recordOperation(user.getUsername(), new DeleteDrivingTipOperation(drivingTip, repository));
        User user2 = new User(2L, "test2", "test2", "test2", "test2");
        DrivingTip drivingTip2 = new DrivingTip(1L, "text", new HashSet<>(List.of(WeatherType.CLOUDS)), user2);
        undoController.recordOperation(user2.getUsername(), new DeleteDrivingTipOperation(drivingTip2, repository));
        undoController.recordOperation(user2.getUsername(), new AddDrivingTipOperation(drivingTip2, repository));
        undoController.recordOperation(user2.getUsername(), new DeleteDrivingTipOperation(drivingTip2, repository));


        result = undoController.undo(user2.getUsername());
        assertTrue(result);
        assertEquals(1, undoController.getIndex(user2.getUsername()));
        assertEquals(0, undoController.getIndex(user.getUsername()));
        assertEquals(1, repository.count());

        result = undoController.undo(user.getUsername());
        assertTrue(result);
        assertEquals(1, undoController.getIndex(user2.getUsername()));
        assertEquals(-1, undoController.getIndex(user.getUsername()));
        assertEquals(2, repository.count());
    }

    @Test
    public void redoTest(){
        User user = new User(1L, "test", "test", "test", "test");
        DrivingTip drivingTip = new DrivingTip(0L, "text", new HashSet<>(List.of(WeatherType.CLOUDS)), user);
        DrivingTipRepository repository = new DrivingTipRepositoryInMemory();
        undoController.recordOperation(user.getUsername(), new AddDrivingTipOperation(drivingTip, repository));
        undoController.recordOperation(user.getUsername(), new DeleteDrivingTipOperation(drivingTip, repository));

        boolean result = undoController.redo(user.getUsername());
        assertFalse(result);

        undoController.undo(user.getUsername());
        undoController.undo(user.getUsername());

        result = undoController.redo(user.getUsername());
        assertTrue(result);
        assertEquals(0, undoController.getIndex(user.getUsername()));
        assertEquals(1, repository.count());

        result = undoController.redo(user.getUsername());
        assertTrue(result);
        assertEquals(1, undoController.getIndex(user.getUsername()));
        assertEquals(0, repository.count());

        undoController.recordOperation(user.getUsername(), new DeleteDrivingTipOperation(drivingTip, repository));
        User user2 = new User(2L, "test2", "test2", "test2", "test2");
        DrivingTip drivingTip2 = new DrivingTip(1L, "text", new HashSet<>(List.of(WeatherType.CLOUDS)), user2);
        undoController.recordOperation(user2.getUsername(), new DeleteDrivingTipOperation(drivingTip2, repository));
        undoController.recordOperation(user2.getUsername(), new AddDrivingTipOperation(drivingTip2, repository));
        undoController.recordOperation(user2.getUsername(), new DeleteDrivingTipOperation(drivingTip2, repository));

        undoController.undo(user2.getUsername());
        undoController.undo(user2.getUsername());
        undoController.undo(user2.getUsername());

        result = undoController.redo(user2.getUsername());
        assertTrue(result);
        assertEquals(2, undoController.getIndex(user.getUsername()));
        assertEquals(0, undoController.getIndex(user2.getUsername()));
        assertEquals(0, repository.count());

        result = undoController.redo(user2.getUsername());
        assertTrue(result);
        assertEquals(2, undoController.getIndex(user.getUsername()));
        assertEquals(1, undoController.getIndex(user2.getUsername()));
        assertEquals(1, repository.count());
    }
}
