package com.team980.practice2019.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.team980.practice2019.sensors.Rioduino;

import static com.team980.practice2019.Parameters.*;

public class RobotArm {

    /**
     * Used to access absolute encoder readings
     */
    private Rioduino rioduino;

    private WPI_TalonSRX shoulderMotor;
    private WPI_TalonSRX elbowMotor;
    private WPI_TalonSRX wristMotor;

    private State shoulderState;
    private State elbowState;
    private State wristState;

    private Pose pose;

    public RobotArm(Rioduino rioduino) {
        this.rioduino = rioduino;

        shoulderMotor = new WPI_TalonSRX(SHOULDER_CONTROLLER_CAN_ID);
        elbowMotor = new WPI_TalonSRX(ELBOW_CONTROLLER_CAN_ID);
        wristMotor = new WPI_TalonSRX(WRIST_CONTROLLER_CAN_ID);
    }

    public void initialize() {
        shoulderState = State.STOPPED;
        elbowState = State.STOPPED;
        wristState = State.STOPPED;

        pose = Pose.STOWED;
    }

    public void setPose(Pose pose) {
        shoulderState = State.MOVING_TO_POSITION;
        elbowState = State.MOVING_TO_POSITION;
        wristState = State.MOVING_TO_POSITION;

        this.pose = pose;
    }

    public void execute() {
        if (shoulderState == State.MOVING_TO_POSITION) {
            var differential = rioduino.getShoulderAngle() - pose.shoulderAngle;

            if (Math.abs(differential) < SHOULDER_DEADBAND) {
                shoulderMotor.set(Math.copySign(SHOULDER_MOTOR_SPEED, differential));
            } else {
                shoulderMotor.set(0);
                shoulderState = State.STOPPED;
            }
        } else {
            shoulderMotor.set(0);
        }

        if (elbowState == State.MOVING_TO_POSITION) {
            var differential = rioduino.getElbowAngle() - pose.elbowAngle;

            if (Math.abs(rioduino.getElbowAngle() - pose.elbowAngle) < ELBOW_DEADBAND) {
                elbowMotor.set(Math.copySign(ELBOW_MOTOR_SPEED, differential));
            } else {
                elbowMotor.set(0);
                elbowState = State.STOPPED;
            }
        } else {
            elbowMotor.set(0);
        }

        if (wristState == State.MOVING_TO_POSITION) {
            var differential = rioduino.getWristAngle() - pose.wristAngle;

            if (Math.abs(rioduino.getWristAngle() - pose.wristAngle) < WRIST_DEADBAND) {
                wristMotor.set(Math.copySign(WRIST_MOTOR_SPEED, differential));
            } else {
                wristMotor.set(0);
                wristState = State.STOPPED;
            }
        } else {
            wristMotor.set(0);
        }
    }

    public void disable() {
        shoulderState = State.STOPPED;
        elbowState = State.STOPPED;
        wristState = State.STOPPED;

        shoulderMotor.stopMotor();
        elbowMotor.stopMotor();
        wristMotor.stopMotor();
    }

    public enum State {
        MOVING_TO_POSITION,
        STOPPED
    }

    public enum Pose {
        STOWED(0, 0, 0);

        double shoulderAngle;
        double elbowAngle;
        double wristAngle;

        Pose(double shoulderAngle, double elbowAngle, double wristAngle) {
            this.shoulderAngle = shoulderAngle;
            this.elbowAngle = elbowAngle;
            this.wristAngle = wristAngle;
        }
    }
}
