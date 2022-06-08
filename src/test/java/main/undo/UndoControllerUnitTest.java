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
        assertEquals(0, undoController.getOperations().size());
        undoController.recordOperation(new AddDrivingTipOperation());
        undoController.recordOperation(new DeleteDrivingTipOperation());
        undoController.recordOperation(new UpdateDrivingTipOperation());
        assertEquals(3, undoController.getOperations().size());
        assertEquals(2, undoController.getIndex());
    }

    @Test
    public void undoTest(){
        User user = new User(1L, "test", "test", "test", "test");
        DrivingTip drivingTip = new DrivingTip(0L, "text", new HashSet<>(List.of(WeatherType.CLOUDS)), user);
        DrivingTipRepository repository = new DrivingTipRepositoryInMemory();
        undoController.recordOperation(new AddDrivingTipOperation(drivingTip, repository));
        undoController.recordOperation(new DeleteDrivingTipOperation(drivingTip, repository));

        boolean result = undoController.undo();
        assertTrue(result);
        assertEquals(0, undoController.getIndex());
        assertEquals(1, repository.count());

        result = undoController.undo();
        assertTrue(result);
        assertEquals(-1, undoController.getIndex());
        assertEquals(0, repository.count());

        result = undoController.undo();
        assertFalse(result);
    }

    @Test
    public void redoTest(){
        User user = new User(1L, "test", "test", "test", "test");
        DrivingTip drivingTip = new DrivingTip(0L, "text", new HashSet<>(List.of(WeatherType.CLOUDS)), user);
        DrivingTipRepository repository = new DrivingTipRepositoryInMemory();
        undoController.recordOperation(new AddDrivingTipOperation(drivingTip, repository));
        undoController.recordOperation(new DeleteDrivingTipOperation(drivingTip, repository));

        boolean result = undoController.redo();
        assertFalse(result);

        undoController.undo();
        undoController.undo();

        result = undoController.redo();
        assertTrue(result);
        assertEquals(0, undoController.getIndex());
        assertEquals(1, repository.count());

        result = undoController.redo();
        assertTrue(result);
        assertEquals(1, undoController.getIndex());
        assertEquals(0, repository.count());
    }
}
