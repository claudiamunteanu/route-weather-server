package main.undo.operations;

import main.domain.DrivingTip;
import main.persistence.DrivingTipRepository;
import main.undo.Operation;

public class UpdateDrivingTipOperation implements Operation {
    private DrivingTip oldDrivingTip;
    private DrivingTip newDrivingTip;
    private DrivingTipRepository drivingTipRepository;

    public UpdateDrivingTipOperation(DrivingTip oldDrivingTip, DrivingTip newDrivingTip, DrivingTipRepository drivingTipRepository) {
        this.oldDrivingTip = oldDrivingTip;
        this.newDrivingTip = newDrivingTip;
        this.drivingTipRepository = drivingTipRepository;
    }

    public UpdateDrivingTipOperation() {
    }

    @Override
    public void execute() {
        drivingTipRepository.save(newDrivingTip);
    }

    @Override
    public void undo() {
        drivingTipRepository.save(oldDrivingTip);
    }
}
