// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package first.constants;

import static org.wpilib.units.Units.RotationsPerSecond;

import org.wpilib.units.measure.AngularVelocity;

import com.ctre.phoenix6.CANBus;
import com.ctre.phoenix6.configs.Slot0Configs;

public class FlywheelConstants {

    public static final CANBus network = new CANBus();

    public static final int topLeftMotorId = 0;
    public static final int bottomLeftMotorId = 1;
    public static final int topRightMotorId = 2;
    public static final int bottomRightMotorId = 3;
    
    public static final Slot0Configs pidConfig = new Slot0Configs()
        .withKS(0.1)
        .withKV(10)
        .withKP(5)
        .withKD(0.5);

    public static final AngularVelocity restVelocity = RotationsPerSecond.of(15);

}
