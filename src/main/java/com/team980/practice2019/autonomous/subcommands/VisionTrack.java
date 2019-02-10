package com.team980.practice2019.autonomous.subcommands;

import com.team980.practice2019.sensors.Rioduino;
import com.team980.practice2019.subsystems.DriveSystem;
import edu.wpi.first.wpilibj.command.Command;

import static com.team980.practice2019.Parameters.*;

public final class VisionTrack extends Command {

    private DriveSystem driveSystem;

    private Rioduino rioduino;

    private boolean isFinished = false;

    public VisionTrack(DriveSystem driveSystem, Rioduino rioduino) {
        super("VisionTrack");

        this.driveSystem = driveSystem;
        this.rioduino = rioduino;
    }

    @Override
    protected void initialize() {
        //unused
    }

    @Override
    protected void execute() {
        if (rioduino.getTargetWidth() < AUTO_TARGET_SCORING_WIDTH) { //this works???

            var followSpeed = 2.0; //TODO ranging?

            if (Math.abs(followSpeed) < AUTO_MIN_SPEED) followSpeed = Math.copySign(AUTO_MIN_SPEED, followSpeed);
            if (Math.abs(followSpeed) > AUTO_MAX_SPEED) followSpeed = Math.copySign(AUTO_MAX_SPEED, followSpeed);

            var targetCenterOffset = rioduino.getTargetCenterCoord() - 160 - 35; // Normalize coordinates, account for off center
            var turnSpeed = targetCenterOffset / AUTO_VISION_CORRECTION_DIVISOR;

            if (rioduino.getTargetCenterCoord() == -1) turnSpeed = 0; // No targets detected

            driveSystem.setSetpoints(followSpeed + turnSpeed, followSpeed - turnSpeed);
        } else {
            isFinished = true;
        }
    }

    @Override
    protected boolean isFinished() {
        return isFinished;
    }

    @Override
    protected void end() {
        driveSystem.setSetpoints(0, 0);
    }
}
