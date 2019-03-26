package com.team980.practice2019.autonomous.subcommands;

import com.team980.practice2019.subsystems.DriveSystem;
import com.team980.practice2019.vision.VisionDataProvider;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.shuffleboard.EventImportance;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;

import static com.team980.practice2019.Parameters.*;

public final class VisionTrack extends Command {

    private DriveSystem driveSystem;

    private VisionDataProvider provider;

    private final double followSpeed;
    private final double targetRange;

    private boolean isFinished = false;

    public VisionTrack(DriveSystem driveSystem, VisionDataProvider provider, double followSpeed, double targetRange) {
        this.driveSystem = driveSystem;
        this.provider = provider;

        this.followSpeed = followSpeed;
        this.targetRange = targetRange;
    }

    @Override
    protected void initialize() {
        Shuffleboard.addEventMarker("VisionTrack: provider " + provider.getSource(), EventImportance.kTrivial);
        System.out.println("VisionTrack: provider " + provider.getSource());
    }

    @Override
    protected void execute() {
        if (provider.getTargetWidth() < targetRange) { //this works???

            //TODO adjust speed based on range?
            //if (Math.abs(followSpeed) < AUTO_MIN_SPEED) followSpeed = Math.copySign(AUTO_MIN_SPEED, followSpeed);
            //if (Math.abs(followSpeed) > AUTO_MAX_SPEED) followSpeed = Math.copySign(AUTO_MAX_SPEED, followSpeed);

            var targetCenterOffset = provider.getTargetCenterCoord() - 160 - 25; // Normalize coordinates, account for off center
            var turnSpeed = targetCenterOffset / AUTO_VISION_CORRECTION_DIVISOR;

            if (provider.getTargetCenterCoord() == -1) turnSpeed = 0; // No targets detected

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
