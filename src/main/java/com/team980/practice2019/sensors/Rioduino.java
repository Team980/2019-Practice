package com.team980.practice2019.sensors;

import edu.wpi.first.wpilibj.I2C;

import java.nio.ByteBuffer;

/**
 * Communicates with a Rioduino connected to the roboRIO via MXP.
 * Shares the received data with very easy to use getter methods,
 * and provides a id-based command interface to set its properties.
 */
public final class Rioduino {

    private static final int DEVICE_ADDRESS = 10;
    private static final int BUFFER_SIZE = 4;

    private I2C i2C;

    private short distanceBetweenTargets;
    private short targetCenterCoord;

    public Rioduino() {
        i2C = new I2C(I2C.Port.kMXP, DEVICE_ADDRESS);
    }

    public void updateData() {
        ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);

        i2C.readOnly(buffer, BUFFER_SIZE);

        buffer.position(0);
        distanceBetweenTargets = buffer.getShort();
        targetCenterCoord = buffer.getShort();
    }

    public void sendCommand(Command command) {
        ByteBuffer buffer = ByteBuffer.allocate(1);
        buffer.put(command.id);
        i2C.writeBulk(buffer, 1);
    }

    /**
     * The distance between the two vision targets, in pixels
     * -1 if no targets are detected
     */
    public float getDistanceBetweenTargets() {
        return distanceBetweenTargets;
    }

    /**
     * The center x-coordinate of the detected vision targets
     * Ranges from zero to 319
     * -1 if no targets are detected
     */
    public float getTargetCenterCoord() {
        return targetCenterCoord;
    }

    public float getTargetCenterOffsetCoord() {
        return targetCenterCoord - 160 - 5; //off center
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
