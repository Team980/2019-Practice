package com.team980.practice2019.autonomous.subcommands;

import com.team980.practice2019.subsystems.DriveSystem;
import edu.wpi.first.wpilibj.command.Command;

import static com.team980.practice2019.Parameters.*;

public final class EncoderMove extends Command {

    private DriveSystem driveSystem;

    private final double distance;

    private boolean isFinished = false;

    public EncoderMove(DriveSystem driveSystem, double distance) {
        super("EncoderMove: " + distance + " feet");

        this.driveSystem = driveSystem;

        this.distance = distance;
    }

    @Override
    protected void initialize() {
        driveSystem.resetEncoders();
    }

    @Override
    protected void execute() {
        if (Math.abs(driveSystem.getLeftEncoder().getDistance()) < Math.abs(distance)
                && Math.abs(driveSystem.getRightEncoder().getDistance()) < Math.abs(distance)) {

            var differential = distance;
            if (Math.abs(driveSystem.getLeftEncoder().getDistance()) > Math.abs(driveSystem.getRightEncoder().getDistance())) {
                differential -= driveSystem.getLeftEncoder().getDistance();
            } else {
                differential -= driveSystem.getRightEncoder().getDistance();
            }

            var speed = differential * (AUTO_MAX_SPEED / distance) * AUTO_MOVE_MULTIPLIER;

            if (Math.abs(speed) < AUTO_MIN_SPEED) speed = Math.copySign(AUTO_MIN_SPEED, speed);
            if (Math.abs(speed) > AUTO_MAX_SPEED) speed = Math.copySign(AUTO_MAX_SPEED, speed);

            //TODO IMU compensation

            driveSystem.setSetpoints(speed, speed);
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
