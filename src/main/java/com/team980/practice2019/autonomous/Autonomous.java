package com.team980.practice2019.autonomous;

import com.team980.practice2019.autonomous.subcommands.EncoderMove;
import com.team980.practice2019.autonomous.subcommands.IMUTurn;
import com.team980.practice2019.autonomous.subcommands.VisionTrack;
import com.team980.practice2019.sensors.Rioduino;
import com.team980.practice2019.subsystems.DriveSystem;
import edu.wpi.first.wpilibj.command.CommandGroup;

public final class Autonomous extends CommandGroup {

    private Autonomous(DriveSystem driveSystem, Rioduino rioduino, double[] ypr, Side side) {
        super("Autonomous");

        // 1. Drive forward (time) until on slope of platform

        // 2. Drive forward until IMU stabilizes

        // 3. Turn to overshot angle
        addSequential(new IMUTurn(driveSystem, ypr, -54.5 * side.invert));

        // 4. Drive forward to midpoint
        addSequential(new EncoderMove(driveSystem, ypr, 7.0));

        // 5: Turn to face rocket
        addSequential(new IMUTurn(driveSystem, ypr, -30 * side.invert));

        // 5.5. Use Pixy to drive to target (and score)
        addSequential(new VisionTrack(driveSystem, rioduino));

        // 6. Back up short length
        addSequential(new EncoderMove(driveSystem, ypr, -2.5));

        // 7. Turn to zero
        addSequential(new IMUTurn(driveSystem, ypr, 0));

        // 8. Drive to loading station
        addSequential(new EncoderMove(driveSystem, ypr, -10.0));

        // 8.5. Use Pixy to pick up from loading station

        // 9. Inch forward

        // 10. Turn to slight angle

        // 11. Drive to center of field

        // 12. Turn to far side of rocket (+30)

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
        private Rioduino rioduino;
        private double[] ypr;

        public Builder(DriveSystem driveSystem, Rioduino rioduino, double[] ypr) {
            this.driveSystem = driveSystem;
            this.rioduino = rioduino;
            this.ypr = ypr;
        }

        public Autonomous build(Side side) {
            return new Autonomous(driveSystem, rioduino, ypr, side);
        }
    }
}
