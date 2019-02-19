package com.team980.practice2019.sensors;

import com.team980.practice2019.vision.VisionDataProvider;
import edu.wpi.first.wpilibj.I2C;

import java.nio.ByteBuffer;

/**
 * Communicates with a Rioduino connected to the roboRIO via MXP.
 * Shares the received data with very easy to use getter methods,
 * and provides a id-based command interface to set its properties.
 * <p>
 * Implements VisionDataProvider to provide data from the front Pixy, which was already calculated offboard.
 */
public final class Rioduino implements VisionDataProvider {

    private static final int DEVICE_ADDRESS = 10;
    private static final int BUFFER_SIZE = 16;

    private I2C i2C;

    // Absolute Encoders
    private int shoulderAngle; //TODO floating point!
    private int elbowAngle; //TODO floating point!!
    private int wristAngle; //TODO floating point!!!

    // Front Pixy
    private short targetCenterCoord;
    private short targetWidth;

    public Rioduino() {
        i2C = new I2C(I2C.Port.kMXP, DEVICE_ADDRESS);
    }

    public void updateData() {
        ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);

        i2C.readOnly(buffer, BUFFER_SIZE);

        buffer.position(0);

        shoulderAngle = buffer.getInt();
        elbowAngle = buffer.getInt();
        wristAngle = buffer.getInt();

        targetCenterCoord = buffer.getShort();
        targetWidth = buffer.getShort();
    }

    public void sendCommand(Command command) {
        ByteBuffer buffer = ByteBuffer.allocate(1);
        buffer.put(command.id);
        i2C.writeBulk(buffer, 1);
    }

    @Override
    public String getSource() {
        return "Rioduino";
    }

    /**
     * <p>The angle of the arm's shoulder joint</p>
     * <p>Ranges from zero to 360</p>
     */
    public int getShoulderAngle() {
        return shoulderAngle;
    }

    /**
     * <p>The angle of the arm's elbow joint</p>
     * <p>Ranges from zero to 360</p>
     */
    public int getElbowAngle() {
        return elbowAngle;
    }

    /**
     * <p>The angle of the arm's wrist joint</p>
     * <p>Ranges from zero to 360</p>
     */
    public int getWristAngle() {
        return wristAngle;
    }

    /**
     * <p>The center x-coordinate of the detected vision targets</p>
     * <p>Ranges from zero to 319</p>
     */
    public double getTargetCenterCoord() {
        return targetCenterCoord;
    }

    /**
     * <p>The combined width of the vision targets, in pixels</p>
     */
    public double getTargetWidth() {
        return targetWidth;
    }

    public enum Command {

        @Deprecated //unimplemented
                SET_MODE_COLOR_TRACKING((byte) 0);

        private byte id;

        Command(byte id) {
            this.id = id;
        }
    }
}
