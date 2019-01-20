package com.team980.practice2019.subsystems;

import edu.wpi.first.wpilibj.Solenoid;

import static com.team980.practice2019.Parameters.PCM_CAN_ID;
import static com.team980.practice2019.Parameters.SHIFTER_SOLENOID_PCM_CHANNEL;

public class Pneumatics implements Subsystem {

    private Solenoid shifterSolenoid;

    public Pneumatics() {
        shifterSolenoid = new Solenoid(PCM_CAN_ID, SHIFTER_SOLENOID_PCM_CHANNEL);
        shifterSolenoid.setName("Pneumatics", "Shifter Solenoid");
    }

    public Solenoid getShifterSolenoid() {
        return shifterSolenoid;
    }

    @Override
    public void disable() {

    }
}
