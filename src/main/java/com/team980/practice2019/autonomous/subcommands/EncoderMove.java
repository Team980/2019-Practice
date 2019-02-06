package com.team980.practice2019.autonomous.subcommands;

import com.team980.practice2019.subsystems.DriveSystem;
import edu.wpi.first.wpilibj.command.Command;

public class EncoderMove extends Command {

    private DriveSystem driveSystem;

    private double distance;

    private boolean isFinished = false;

    public EncoderMove(DriveSystem driveSystem, double distance) {
        this.driveSystem = driveSystem;

        this.distance = distance;
    }

    @Override
    protected void initialize() {
        driveSystem.getLeftEncoder().reset();
        driveSystem.getRightEncoder().reset();
    }

    @Override
    protected void execute() {
        if (Math.abs(driveSystem.getLeftEncoder().getDistance()) > Math.abs(distance)
                || Math.abs(driveSystem.getRightEncoder().getDistance()) > Math.abs(distance)) {

            driveSystem.setSetpoints(4.0, 4.0);
        } else {
            isFinished = true;
        }
    }

    @Override
    protected boolean isFinished() {
        return isFinished;
    }
}
