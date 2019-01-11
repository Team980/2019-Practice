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

/**
 * Robot-wide parameters are stored here.
 */
@SuppressWarnings("WeakerAccess")
public final class Parameters {

    // DRIVE SYSTEM
    public static final int LEFT_TOP_DRIVE_CONTROLLER_CAN_ID = 0;
    public static final int LEFT_BACK_DRIVE_CONTROLLER_CAN_ID = 1;
    public static final int RIGHT_TOP_DRIVE_CONTROLLER_CAN_ID = 2;
    public static final int RIGHT_BACK_DRIVE_CONTROLLER_CAN_ID = 3;

    // ENCODERS
    public static final int LEFT_DRIVE_ENCODER_CHANNEL_A = 1;
    public static final int LEFT_DRIVE_ENCODER_CHANNEL_B = 2;
    public static final boolean INVERT_LEFT_DRIVE_ENCODER = false;

    public static final int RIGHT_DRIVE_ENCODER_CHANNEL_A = 3;
    public static final int RIGHT_DRIVE_ENCODER_CHANNEL_B = 4;
    public static final boolean INVERT_RIGHT_DRIVE_ENCODER = false;

    // IMU
    public static final int IMU_CAN_ID = 0;

    // PNEUMATICS
    public static final int PCM_CAN_ID = 0;
    public static final int SHIFTER_SOLENOID_PCM_CHANNEL = 0;

    // JOYSTICKS
    public static final int DRIVE_STICK_ID = 0;
    public static final int DRIVE_WHEEL_ID = 1;
    public static final int XBOX_CONTROLLER_ID = 2;
}
