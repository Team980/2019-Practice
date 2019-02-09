package com.team980.practice2019.autonomous;

import com.team980.practice2019.autonomous.subcommands.EncoderMove;
import com.team980.practice2019.autonomous.subcommands.IMUTurn;
import com.team980.practice2019.subsystems.DriveSystem;
import edu.wpi.first.wpilibj.command.CommandGroup;

public final class Autonomous extends CommandGroup {

    private Autonomous(DriveSystem driveSystem, double[] ypr, Side side) {
        super("Autonomous");

        addSequential(new EncoderMove(driveSystem, 6.0));

        addSequential(new IMUTurn(driveSystem, ypr, -45 * side.invert));

        addSequential(new EncoderMove(driveSystem, 2.0));

        addSequential(new EncoderMove(driveSystem, -5.0));

        addSequential(new IMUTurn(driveSystem, ypr, 45 * side.invert));

        addSequential(new EncoderMove(driveSystem, -3.0));

        // 1. Drive forward (time) until on slope of platform

        // 2. Drive forward until IMU stabilizes

        // 3. Turn to overshot angle

        // 4. Drive forward determined length

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
        RIGHT(1),
        LEFT(-1);

        public double invert;

        Side(double invert) {
            this.invert = invert;
        }
    }

    public static final class Builder {

        private DriveSystem driveSystem;
        private double[] ypr;

        public Builder(DriveSystem driveSystem, double[] ypr) {
            this.driveSystem = driveSystem;
            this.ypr = ypr;
        }

        public Autonomous build(Side side) {
            return new Autonomous(driveSystem, ypr, side);
        }
    }
}
