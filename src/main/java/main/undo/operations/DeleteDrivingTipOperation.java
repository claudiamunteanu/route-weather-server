package main.undo.operations;

import main.domain.DrivingTip;
import main.persistence.DrivingTipRepository;
import main.undo.Operation;

import javax.transaction.Transactional;

public class DeleteDrivingTipOperation implements Operation {
    private DrivingTip drivingTip;
    private DrivingTipRepository drivingTipRepository;

    public DeleteDrivingTipOperation(DrivingTip drivingTip, DrivingTipRepository drivingTipRepository) {
        this.drivingTip = drivingTip;
        this.drivingTipRepository = drivingTipRepository;
    }

    public DeleteDrivingTipOperation() {
    }

    @Override
    @Transactional
    public void execute() {
        drivingTipRepository.deleteDrivingTipById(drivingTip.getId());
    }

    @Override
    public void undo() {
        drivingTipRepository.save(drivingTip);
    }
}
