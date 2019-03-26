package com.team980.practice2019.autonomous;

import com.team980.practice2019.sensors.Rioduino;
import com.team980.practice2019.subsystems.DriveSystem;
import com.team980.practice2019.subsystems.EndEffector;
import com.team980.practice2019.subsystems.RobotArm;
import edu.wpi.first.wpilibj.command.CommandGroup;

public interface Autonomous { //TODO refactor?

    enum Side {
        RIGHT(1),
        LEFT(-1);

        public double invert;

        Side(double invert) {
            this.invert = invert;
        }
    }

    public static final class Builder {

        private DriveSystem driveSystem;
        private RobotArm robotArm;
        private EndEffector endEffector;
        private double[] ypr;
        private Rioduino rioduino;

        public Builder(DriveSystem driveSystem, RobotArm robotArm, EndEffector endEffector, double[] ypr, Rioduino rioduino) {
            this.driveSystem = driveSystem;
            this.robotArm = robotArm;
            this.endEffector = endEffector;
            this.ypr = ypr;
            this.rioduino = rioduino;
        }

        public CommandGroup buildTwoHatch(Autonomous.Side side) {
            return new TwoHatchAutonomous(driveSystem, robotArm, ypr, rioduino, side);
        }

        public CommandGroup buildCargoShip(Autonomous.Side side) {
            return new CargoShipAutonomous(driveSystem, robotArm, endEffector, ypr, rioduino, side);
        }
    }
}
