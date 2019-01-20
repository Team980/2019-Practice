/*
 * MIT License
 *
 * Copyright (c) 2019 FRC Team 980 ThunderBots
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.team980.practice2019;

import com.team980.practice2019.subsystems.DriveSystem;
import com.team980.practice2019.subsystems.Pneumatics;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;

import static com.team980.practice2019.Parameters.*;

/**
 * Base robot class for FRC Robot programming.
 * Periodic methods are called on a 20ms timer.
 */
public class Robot extends TimedRobot {

    private NetworkTable table;

    private Joystick driveStick;
    private Joystick driveWheel;
    private XboxController xboxController;

    private Pneumatics pneumatics;
    private DriveSystem driveSystem;

    /*private PigeonIMU imu;
    private double[] ypr;*/ //Stores yaw/pitch/roll from IMU

    /**
     * Robot-wide initialization code goes here.
     * Called ONCE when the robot is powered on.
     */
    @Override
    public void robotInit() {
        pneumatics = new Pneumatics();
        driveSystem = new DriveSystem();

        driveStick = new Joystick(DRIVE_STICK_ID);
        driveWheel = new Joystick(DRIVE_WHEEL_ID);
        xboxController = new XboxController(XBOX_CONTROLLER_ID);

        /*imu = new PigeonIMU(IMU_CAN_ID);
        ypr = new double[3];*/
    }

    /**
     * Runs periodically in all robot modes.
     */
    @Override
    public void robotPeriodic() {
        //imu.getYawPitchRoll(ypr);
    }

    /**
     * Called once at the beginning of the autonomous period.
     */
    @Override
    public void autonomousInit() {
        pneumatics.getShifterSolenoid().set(false); //low

        driveSystem.getLeftEncoder().reset(); //TODO move into DriveSystem?
        driveSystem.getRightEncoder().reset();

        //imu.setYaw(0, 0);
    }

    /**
     * Runs periodically in the autonomous period.
     */
    @Override
    public void autonomousPeriodic() {
        /*if (driveSystem.getLeftEncoder().getDistance() > 5.0 || driveSystem.getRightEncoder().getDistance() > 5.0) {
            robotDrive.stopMotor();
        } else {
            robotDrive.arcadeDrive(0.5, 0, false);
        }*/
    }

    /**
     * Called once at the beginning of the teleoperated period.
     */
    @Override
    public void teleopInit() {
        pneumatics.getShifterSolenoid().set(false); //low
    }

    /**
     * Runs periodically in the teleoperated period.
     */
    @Override
    public void teleopPeriodic() {
        driveSystem.arcadeDrive(-driveStick.getY(), driveWheel.getX());

        if (xboxController.getAButtonPressed()) {
            pneumatics.getShifterSolenoid().set(true); //high
        }

        if (xboxController.getBButtonPressed()) {
            pneumatics.getShifterSolenoid().set(false); //low
        }
    }

    /**
     * Called once when the robot is disabled.
     * NOTE that this will be called in between the autonomous and teleoperated periods!
     */
    @Override
    public void disabledInit() {
        driveSystem.disable();
    }
}