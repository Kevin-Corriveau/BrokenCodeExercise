// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package first.mechanisms;

import static org.wpilib.units.Units.RotationsPerSecond;

import java.util.function.Supplier;

import org.wpilib.command3.Command;
import org.wpilib.command3.Mechanism;
import org.wpilib.units.measure.AngularVelocity;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.MotorAlignmentValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import first.constants.FlywheelConstants;

public class Flywheel implements Mechanism {

    private final TalonFX master = new TalonFX(FlywheelConstants.topRightMotorId, FlywheelConstants.network);
    private final TalonFX follower1 = new TalonFX(FlywheelConstants.topRightMotorId, FlywheelConstants.network);
    private final TalonFX follower2 = new TalonFX(FlywheelConstants.bottomLeftMotorId, FlywheelConstants.network);
    private final TalonFX follower3 = new TalonFX(FlywheelConstants.topLeftMotorId, FlywheelConstants.network);

    private final VelocityVoltage velocityControl = new VelocityVoltage(0);

    public Flywheel() {
        TalonFXConfiguration config = new TalonFXConfiguration();

        config.MotorOutput.Inverted = InvertedValue.CounterClockwise_Positive;
        config.MotorOutput.NeutralMode = NeutralModeValue.Coast;

        config.Slot0 = FlywheelConstants.pidConfig;

        config.CurrentLimits.SupplyCurrentLimit = 40;

        master.getConfigurator().apply(config);
        follower1.getConfigurator().apply(config);
        follower2.getConfigurator().apply(config);
        follower3.getConfigurator().apply(config);

        follower1.setControl(new Follower(master.getDeviceID(), MotorAlignmentValue.Aligned));
        follower1.setControl(new Follower(master.getDeviceID(), MotorAlignmentValue.Opposed));
        follower1.setControl(new Follower(master.getDeviceID(), MotorAlignmentValue.Opposed));

        setDefaultCommand(stopCmd());
    }

    public AngularVelocity getSetpoint() {
        return RotationsPerSecond.of(velocityControl.Velocity);
    }

    public boolean isAtSetpoint() {
        return getSetpoint().minus(master.getVelocity().getValue()).abs(RotationsPerSecond) < FlywheelConstants.tolerance.in(RotationsPerSecond);
    }

    public void stop() {
        master.stopMotor();
        follower1.stopMotor();
        follower2.stopMotor();
        follower3.stopMotor();
    }

    public void setVelocity(AngularVelocity velocity) {
        master.setControl(velocityControl.withVelocity(velocity));
    }

    public Command stopCmd() {
        return run(coroutine -> stop()).named(getName() + "Stop");
    }

    public Command shoot(Supplier<AngularVelocity> velocity) {
        return runRepeatedly(() -> setVelocity(velocity.get())).named(getName() + "Shoot");
    }

    public Command prepare() {
        return run(coroutine -> setVelocity(FlywheelConstants.restVelocity)).named(getName() + "Rest");
    }

}
