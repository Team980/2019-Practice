package com.team980.practice2019.autonomous;

import com.team980.practice2019.autonomous.subcommands.EncoderMove;
import com.team980.practice2019.subsystems.DriveSystem;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class Autonomous extends CommandGroup {

    private Autonomous(DriveSystem driveSystem, Side side) {

        addSequential(new EncoderMove(driveSystem, 4.0));
    }

    public enum Side {
        RIGHT,
        LEFT
    }

    public static class Builder {

        private DriveSystem driveSystem;

        public Builder(DriveSystem driveSystem) {
            this.driveSystem = driveSystem;
        }

        public Autonomous build(Side side) {
            return new Autonomous(driveSystem, side);
        }
    }
}
