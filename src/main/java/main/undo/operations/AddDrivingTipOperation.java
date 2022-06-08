package main.undo.operations;

import main.domain.DrivingTip;
import main.persistence.DrivingTipRepository;
import main.undo.Operation;

import javax.transaction.Transactional;

public class AddDrivingTipOperation implements Operation {
    private DrivingTip drivingTip;
    private DrivingTipRepository drivingTipRepository;

    public AddDrivingTipOperation(DrivingTip drivingTip, DrivingTipRepository drivingTipRepository) {
        this.drivingTip = drivingTip;
        this.drivingTipRepository = drivingTipRepository;
    }

    public AddDrivingTipOperation() {
    }

    @Override
    public void execute() {
        drivingTipRepository.save(drivingTip);
    }

    @Override
    @Transactional
    public void undo() {
        drivingTipRepository.deleteDrivingTipById(drivingTip.getId());
    }
}
