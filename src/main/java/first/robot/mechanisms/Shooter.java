// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package first.robot.mechanisms;

import static org.wpilib.units.Units.Rotations;
import static org.wpilib.units.Units.RotationsPerSecond;

import org.wpilib.command3.Command;

import first.robot.constants.ShooterConstants;

public class Shooter {

    public final Flywheel flywheel = new Flywheel();
    public final Hood hood = new Hood();
    public final Feeder feeder = new Feeder();

    private double getDistance() {
        return 2; // ! no localisation
    }

    private Command spinFlywheelAndHood() {
        return Command.noRequirements(coroutine -> {
            flywheel.setVelocity(RotationsPerSecond.of(ShooterConstants.flywheelInterpolation.get(getDistance())));
            coroutine.await(hood.track(() -> Rotations.of(ShooterConstants.hoodInterpolation.get(getDistance()))));
        }).named("ShooterShoot");
    }

    public Command shoot() {
        return Command.noRequirements(coroutine -> {
            coroutine.await(spinFlywheelAndHood());

            while(!flywheel.isAtSetpoint() || !hood.isAtSetpoint())
                coroutine.yield();

            coroutine.await(feeder.spin());
        }).named("ShooterShoot");
    }

}
