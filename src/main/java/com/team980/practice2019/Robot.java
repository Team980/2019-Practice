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

import com.ctre.phoenix.sensors.PigeonIMU;
import com.team980.practice2019.autonomous.Autonomous;
import com.team980.practice2019.sensors.Rioduino;
import com.team980.practice2019.subsystems.DriveSystem;
import com.team980.practice2019.vision.BackCameraProcessor;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.command.Scheduler;

import static com.team980.practice2019.Parameters.*;

/**
 * Base robot class for FRC Robot programming.
 * Periodic methods are called on a 20ms timer.
 */
public final class Robot extends TimedRobot {

    @Deprecated
    private NetworkTable table;

    private Joystick driveStick;
    private Joystick driveWheel;
    private XboxController xboxController;

    private PigeonIMU imu;
    private double[] ypr; //Stores yaw/pitch/roll from IMU

    private Rioduino rioduino;

    private BackCameraProcessor cameraProcessor;

    private DriveSystem driveSystem;
    //TODO implement RobotArm

    private Autonomous.Builder autonomous;

    /**
     * Robot-wide initialization code goes here.
     * Called ONCE when the robot is powered on.
     */
    @Override
    public void robotInit() {
        table = NetworkTableInstance.getDefault().getTable("Data"); //TODO figure out how they want us to do this

        driveStick = new Joystick(DRIVE_STICK_ID);
        driveWheel = new Joystick(DRIVE_WHEEL_ID);
        xboxController = new XboxController(XBOX_CONTROLLER_ID);

        imu = new PigeonIMU(IMU_CAN_ID);
        ypr = new double[3];

        rioduino = new Rioduino();

        cameraProcessor = new BackCameraProcessor();

        driveSystem = new DriveSystem();

        autonomous = new Autonomous.Builder(driveSystem, ypr, rioduino, cameraProcessor);
    }

    /**
     * Runs periodically in all robot modes.
     */
    @Override
    public void robotPeriodic() {
        imu.getYawPitchRoll(ypr);

        rioduino.updateData();

        cameraProcessor.updateData();

        //TODO determine the formal way to do this
        table.getSubTable("Sensors").getSubTable("IMU").getEntry("Yaw").setNumber(ypr[0]);
        table.getSubTable("Sensors").getSubTable("IMU").getEntry("Pitch").setNumber(ypr[1]);
        table.getSubTable("Sensors").getSubTable("IMU").getEntry("Roll").setNumber(ypr[2]);

        table.getSubTable("Vision").getSubTable("Front Camera").getEntry("Target Center Coord").setNumber(rioduino.getTargetCenterCoord());
        table.getSubTable("Vision").getSubTable("Front Camera").getEntry("Target Width").setNumber(rioduino.getTargetWidth());

        table.getSubTable("Vision").getSubTable("Back Camera").getEntry("Target Center Coord").setNumber(cameraProcessor.getTargetCenterCoord());
        table.getSubTable("Vision").getSubTable("Back Camera").getEntry("Target Width").setNumber(cameraProcessor.getTargetWidth());
    }

    /**
     * Called once at the beginning of the autonomous period.
     */
    @Override
    public void autonomousInit() {
        imu.setYaw(0, 0);

        driveSystem.setGear(DriveSystem.Gear.LOW);
        driveSystem.setPIDEnabled(true);
        driveSystem.setAutoShiftEnabled(false);

        driveSystem.resetEncoders();

        autonomous.build(Autonomous.Side.RIGHT).start();
    }

    /**
     * Runs periodically in the autonomous period.
     */
    @Override
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    /**
     * Called once at the beginning of the teleoperated period.
     */
    @Override
    public void teleopInit() {
        driveSystem.setGear(DriveSystem.Gear.LOW);
        driveSystem.setPIDEnabled(true);
        driveSystem.setAutoShiftEnabled(true);

        driveSystem.resetEncoders();
    }

    /**
     * Runs periodically in the teleoperated period.
     */
    @Override
    public void teleopPeriodic() {
        driveSystem.arcadeDrive(-driveStick.getY(), driveWheel.getX());
        //driveSystem.arcadeDrive(-xboxController.getY(GenericHID.Hand.kLeft), xboxController.getX(GenericHID.Hand.kRight));
    }

    /**
     * Called once when the robot is disabled.
     * NOTE that this will be called in between the autonomous and teleoperated periods!
     */
    @Override
    public void disabledInit() {
        driveSystem.disable();

        Scheduler.getInstance().removeAll();
    }
}