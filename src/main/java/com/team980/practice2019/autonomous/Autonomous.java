package com.team980.practice2019.autonomous;

import com.team980.practice2019.autonomous.subcommands.EncoderMove;
import com.team980.practice2019.subsystems.DriveSystem;
import edu.wpi.first.wpilibj.command.CommandGroup;

public final class Autonomous extends CommandGroup {

    private Autonomous(DriveSystem driveSystem, Side side) {
        super("Autonomous");

        // 1. Drive forward (time) until on slope of platform

        // 2. Drive forward until IMU stabilizes

        // 3. Turn to overshot angle

        // 4. Drive forward determined length
        addSequential(new EncoderMove(driveSystem, 8.0));

        // 5. Use Pixy to drive to target (and score)

        // 6. Back up short length

        // 7. Turn to zero

        // 8. Drive to loading station

        // 9. Inch forward

        // 10. Turn to slight angle

        // 11. Drive to center of field

        // 12. Turn to far side of rocket

        // 13. Drive into rocket, score

    }

    public enum Side {
        RIGHT,
        LEFT
    }

    public static final class Builder {

        private DriveSystem driveSystem;

        public Builder(DriveSystem driveSystem) {
            this.driveSystem = driveSystem;
        }

        public Autonomous build(Side side) {
            return new Autonomous(driveSystem, side);
        }
    }
}
