// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package first.robot.constants;

import static org.wpilib.units.Units.Volts;

import org.wpilib.units.measure.Voltage;

import com.ctre.phoenix6.CANBus;

public class RollerConstants {

    public static final CANBus network = new CANBus();
    
    public static final int leftMotorId = 7;
    public static final int rightMotorId = 8;

    public static final Voltage voltage = Volts.zero();

}
