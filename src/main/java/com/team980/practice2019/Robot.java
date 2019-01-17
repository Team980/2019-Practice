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

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

import static com.team980.practice2019.Parameters.*;

/**
 * Base robot class for FRC Robot programming.
 * Periodic methods are called on a 20ms timer.
 */
public class Robot extends TimedRobot {

    private NetworkTable table;

    private DifferentialDrive robotDrive;

    private Joystick driveStick;
    private Joystick driveWheel;
    private XboxController xboxController;

    private Encoder leftDriveEncoder;
    private Encoder rightDriveEncoder;

    /*private PigeonIMU imu;
    private double[] ypr;*/ //Stores yaw/pitch/roll from IMU

    private Solenoid shifterSolenoid;

    /**
     * Robot-wide initialization code goes here.
     * Called ONCE when the robot is powered on.
     */
    @Override
    public void robotInit() {
        WPI_TalonSRX leftTopMotor = new WPI_TalonSRX(LEFT_TOP_DRIVE_CONTROLLER_CAN_ID);
        leftTopMotor.setInverted(true);
        SpeedControllerGroup leftDrive = new SpeedControllerGroup(leftTopMotor, new WPI_TalonSRX(LEFT_BACK_DRIVE_CONTROLLER_CAN_ID));

        WPI_TalonSRX rightTopMotor = new WPI_TalonSRX(RIGHT_TOP_DRIVE_CONTROLLER_CAN_ID);
        rightTopMotor.setInverted(true);
        SpeedControllerGroup rightDrive = new SpeedControllerGroup(rightTopMotor, new WPI_TalonSRX(RIGHT_BACK_DRIVE_CONTROLLER_CAN_ID));

        robotDrive = new DifferentialDrive(leftDrive, rightDrive);
        robotDrive.setName("Robot Drive");

        driveStick = new Joystick(DRIVE_STICK_ID);
        driveWheel = new Joystick(DRIVE_WHEEL_ID);
        xboxController = new XboxController(XBOX_CONTROLLER_ID);

        leftDriveEncoder = new Encoder(LEFT_DRIVE_ENCODER_CHANNEL_A, LEFT_DRIVE_ENCODER_CHANNEL_B, INVERT_LEFT_DRIVE_ENCODER, CounterBase.EncodingType.k4X);
        leftDriveEncoder.setDistancePerPulse((TAU * (WHEEL_RADIUS / 12)) / DRIVE_ENCODER_PULSES_PER_REVOLUTION);
        leftDriveEncoder.setName("Drive Encoders", "Left");

        rightDriveEncoder = new Encoder(RIGHT_DRIVE_ENCODER_CHANNEL_A, RIGHT_DRIVE_ENCODER_CHANNEL_B, INVERT_RIGHT_DRIVE_ENCODER, CounterBase.EncodingType.k4X);
        rightDriveEncoder.setDistancePerPulse((TAU * (WHEEL_RADIUS / 12)) / DRIVE_ENCODER_PULSES_PER_REVOLUTION);
        rightDriveEncoder.setName("Drive Encoders", "Right");

        /*imu = new PigeonIMU(IMU_CAN_ID);
        ypr = new double[3];*/

        shifterSolenoid = new Solenoid(PCM_CAN_ID, SHIFTER_SOLENOID_PCM_CHANNEL);
        shifterSolenoid.setName("Pneumatics", "Shifter Solenoid");
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
        shifterSolenoid.set(true); //low

        leftDriveEncoder.reset();
        rightDriveEncoder.reset();

        //imu.setYaw(0, 0);
    }

    /**
     * Runs periodically in the autonomous period.
     */
    @Override
    public void autonomousPeriodic() {
        /*if (leftDriveEncoder.getDistance() > 5.0 || rightDriveEncoder.getDistance() > 5.0) {
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
        shifterSolenoid.set(true); //low
    }

    /**
     * Runs periodically in the teleoperated period.
     */
    @Override
    public void teleopPeriodic() {
        robotDrive.arcadeDrive(-driveStick.getY(), driveWheel.getX());

        if (xboxController.getAButtonPressed()) {
            shifterSolenoid.set(true); //low
        }

        if (xboxController.getBButtonPressed()) {
            shifterSolenoid.set(false); //high
        }
    }

    /**
     * Called once when the robot is disabled.
     * NOTE that this will be called in between the autonomous and teleoperated periods!
     */
    @Override
    public void disabledInit() {
        robotDrive.stopMotor();
    }
}