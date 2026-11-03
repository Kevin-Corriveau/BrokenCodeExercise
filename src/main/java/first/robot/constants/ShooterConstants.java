// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package first.robot.constants;

import static org.wpilib.units.Units.Degrees;
import static org.wpilib.units.Units.Meters;
import static org.wpilib.units.Units.Rotations;
import static org.wpilib.units.Units.RotationsPerSecond;

import org.wpilib.math.interpolation.InterpolatingDoubleTreeMap;
import org.wpilib.units.measure.Angle;
import org.wpilib.units.measure.AngularVelocity;
import org.wpilib.units.measure.Distance;

public class ShooterConstants {

    public static final InterpolatingDoubleTreeMap flywheelInterpolation = new InterpolatingDoubleTreeMap();
    public static final InterpolatingDoubleTreeMap hoodInterpolation = new InterpolatingDoubleTreeMap();

    private static void addInterpolationPoint(Distance distance, AngularVelocity flywheelVelocity, Angle angle) {
        flywheelInterpolation.put(distance.in(Meters), flywheelVelocity.in(RotationsPerSecond));
        flywheelInterpolation.put(distance.in(Meters), angle.in(Rotations));
    }

    static {
        addInterpolationPoint(Meters.of(1), RotationsPerSecond.of(30), Degrees.of(25));
        addInterpolationPoint(Meters.of(2), RotationsPerSecond.of(40), Degrees.of(30));
        addInterpolationPoint(Meters.of(3), RotationsPerSecond.of(35), Degrees.of(45));
    }

}
