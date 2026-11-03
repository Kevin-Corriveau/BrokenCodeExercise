// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package first.constants;

import static org.wpilib.units.Units.Degrees;

import org.wpilib.units.measure.Angle;

import com.ctre.phoenix6.CANBus;

public class HoodConstants {

    public static final CANBus network = new CANBus();

    public static final int motorId = -1;

    public static final double ratio = 1 / 2;

    public static final Angle min = Degrees.of(0);
    public static final Angle max = Degrees.of(90);

    public static final Angle loweredPosition = Degrees.of(0);

    public static final Angle tolerance = Degrees.of(1);

}
