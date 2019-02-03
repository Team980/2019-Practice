package com.team980.practice2019.sensors;

import edu.wpi.first.wpilibj.I2C;

import java.nio.ByteBuffer;

public class Rioduino {

    private static final int DEVICE_ADDRESS = 10;
    private static final int BUFFER_SIZE = 2;

    private I2C i2C;

    public Rioduino() {
        i2C = new I2C(I2C.Port.kMXP, DEVICE_ADDRESS);
    }

    public void updateData() {
        ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);

        i2C.readOnly(buffer, BUFFER_SIZE);

        buffer.position(0);
        short value = buffer.getShort();
        //System.out.println(value);
    }

    public void sendCommand(byte command) {
        ByteBuffer buffer = ByteBuffer.allocate(2);

        buffer.put(command);

        boolean status = i2C.writeBulk(buffer, 2);
        System.out.println(status);
    }
}
