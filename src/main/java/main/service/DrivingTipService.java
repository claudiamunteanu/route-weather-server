package main.service;

import main.domain.DrivingTip;
import main.domain.User;
import main.persistence.DrivingTipRepository;
import main.persistence.UserRepository;
import main.undo.UndoController;
import main.undo.operations.AddDrivingTipOperation;
import main.undo.operations.DeleteDrivingTipOperation;
import main.undo.operations.UpdateDrivingTipOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class DrivingTipService {
    @Autowired
    DrivingTipRepository drivingTipRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UndoController undoController;

    public Long getLastId() {
        return drivingTipRepository.getLastId();
    }

    public List<DrivingTip> getAllByUsername(String username) {
        return drivingTipRepository.getAllByUsername(username);
    }


    public DrivingTip addDrivingTip(DrivingTip drivingTip, String username) {
        User user = userRepository.findByUsername(username);
        drivingTip.setUser(user);
        Long id = getLastId() + 1;
        drivingTip.setId(id);
        DrivingTip savedDrivingTip = drivingTipRepository.save(drivingTip);
        undoController.recordOperation(username, new AddDrivingTipOperation(savedDrivingTip, drivingTipRepository));
        return savedDrivingTip;
    }

    @Transactional
    public int deleteDrivingTip(Long id) {
        Optional<DrivingTip> optionalDrivingTip = drivingTipRepository.findById(id);
        if(optionalDrivingTip.isPresent()) {
            int numberOfRows = drivingTipRepository.deleteDrivingTipById(id);
            DrivingTip drivingTip = optionalDrivingTip.get();
            undoController.recordOperation(drivingTip.getUser().getUsername(), new DeleteDrivingTipOperation(drivingTip, drivingTipRepository));
            return numberOfRows;
        }
        return 0;
    }

    public DrivingTip updateDrivingTip(DrivingTip newDrivingTip) {
        Optional<DrivingTip> optionalDrivingTip = drivingTipRepository.findById(newDrivingTip.getId());
        if(optionalDrivingTip.isPresent()){
            DrivingTip oldDrivingTip = new DrivingTip(optionalDrivingTip.get().getId(), optionalDrivingTip.get().getText(), new HashSet<>(optionalDrivingTip.get().getCategories()), optionalDrivingTip.get().getUser());
            drivingTipRepository.save(newDrivingTip);
            undoController.recordOperation(oldDrivingTip.getUser().getUsername(), new UpdateDrivingTipOperation(oldDrivingTip, newDrivingTip, drivingTipRepository));
            return newDrivingTip;
        }
        return null;
    }

    public boolean undo(String username){
        return undoController.undo(username);
    }

    public boolean redo(String username){
        return undoController.redo(username);
    }
}
