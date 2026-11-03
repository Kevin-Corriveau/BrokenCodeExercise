// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package first.robot.mechanisms;

import org.wpilib.command3.Command;
import org.wpilib.command3.Mechanism;
import org.wpilib.units.measure.Voltage;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import first.robot.constants.FeederConstants;

public class Feeder implements Mechanism {

    private final TalonFX motor = null;

    public Feeder() {
        TalonFXConfiguration config = new TalonFXConfiguration();

        config.MotorOutput.Inverted = InvertedValue.CounterClockwise_Positive;
        config.MotorOutput.NeutralMode = NeutralModeValue.Coast;

        motor.getConfigurator().apply(config);

        setDefaultCommand(stopCmd());
    }

    public void stop() { motor.stopMotor(); }
    public void setVoltage(Voltage voltage) {}

    public Command stopCmd() {
        return run(coroutine -> stop()).named("Stop");
    }

    public Command spin() {
        return run(coroutine -> {
            setVoltage(FeederConstants.spinVoltage);
            coroutine.park();
        }).named(getName() + "Spin");
    }

}
