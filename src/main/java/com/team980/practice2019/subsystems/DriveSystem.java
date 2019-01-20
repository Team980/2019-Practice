package com.team980.practice2019.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.CounterBase;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedControllerGroup;

import static com.team980.practice2019.Parameters.*;

/**
 * Implements velocity control for our West Coast drive system.
 */
public class DriveSystem implements Subsystem {

    private SpeedControllerGroup leftDrive;
    private SpeedControllerGroup rightDrive;

    private Encoder leftEncoder;
    private Encoder rightEncoder;

    private double leftSetpoint = 0;
    private double rightSetpoint = 0;

    public DriveSystem() {
        WPI_TalonSRX leftTopMotor = new WPI_TalonSRX(LEFT_TOP_DRIVE_CONTROLLER_CAN_ID);
        leftTopMotor.setInverted(true);
        leftDrive = new SpeedControllerGroup(leftTopMotor, new WPI_TalonSRX(LEFT_BACK_DRIVE_CONTROLLER_CAN_ID));
        leftDrive.setName("Drive System", "Left Speed Controllers");

        WPI_TalonSRX rightTopMotor = new WPI_TalonSRX(RIGHT_TOP_DRIVE_CONTROLLER_CAN_ID);
        rightTopMotor.setInverted(true);
        rightDrive = new SpeedControllerGroup(rightTopMotor, new WPI_TalonSRX(RIGHT_BACK_DRIVE_CONTROLLER_CAN_ID));
        leftDrive.setName("Drive System", "Right Speed Controllers");

        leftEncoder = new Encoder(LEFT_DRIVE_ENCODER_CHANNEL_A, LEFT_DRIVE_ENCODER_CHANNEL_B, INVERT_LEFT_DRIVE_ENCODER, CounterBase.EncodingType.k4X);
        leftEncoder.setDistancePerPulse((TAU * (WHEEL_RADIUS / 12)) / DRIVE_ENCODER_PULSES_PER_REVOLUTION);
        leftEncoder.setName("Drive System", "Left Encoder");

        rightEncoder = new Encoder(RIGHT_DRIVE_ENCODER_CHANNEL_A, RIGHT_DRIVE_ENCODER_CHANNEL_B, INVERT_RIGHT_DRIVE_ENCODER, CounterBase.EncodingType.k4X);
        rightEncoder.setDistancePerPulse((TAU * (WHEEL_RADIUS / 12)) / DRIVE_ENCODER_PULSES_PER_REVOLUTION);
        rightEncoder.setName("Drive System", "Right Encoder");
    }

    public Encoder getLeftEncoder() {
        return leftEncoder;
    }

    public Encoder getRightEncoder() {
        return rightEncoder;
    }

    /**
     *
     * @param left Requested left speed, from -1 to 1
     * @param right Requested right speed, from -1 to 1
     */
    public void tankDrive(double left, double right) {
        double leftSpeed = left * MAX_SPEED;
        double rightSpeed = right * MAX_SPEED;

        double leftError = (leftSpeed - leftEncoder.getRate()) / MAX_SPEED;
        double rightError = (rightSpeed - rightEncoder.getRate()) / MAX_SPEED;

        leftSetpoint += leftError;
        rightSetpoint += rightError;

        leftDrive.set(leftSetpoint);
        rightDrive.set(rightSetpoint);
    }

    public void arcadeDrive(double move, double turn) {
        //TODO convert to tank drive
        tankDrive(move, move);
    }

    @Override
    public void disable() {
        leftDrive.stopMotor();
        rightDrive.stopMotor();
    }
}
