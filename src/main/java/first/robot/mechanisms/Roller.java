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

import first.robot.constants.RollerConstants;

public class Roller implements Mechanism {

    TalonFX temp;

    private final TalonFX master = new TalonFX(RollerConstants.leftMotorId, RollerConstants.network);
    private final TalonFX follower = new TalonFX(RollerConstants.leftMotorId, RollerConstants.network);

    public Roller() {
        TalonFXConfiguration config = new TalonFXConfiguration();

        config.MotorOutput.Inverted = InvertedValue.Clockwise_Positive;
        config.MotorOutput.NeutralMode = NeutralModeValue.Coast;

        config.CurrentLimits.SupplyCurrentLimit = 30;
        config.CurrentLimits.SupplyCurrentLimitEnable = true;

        master.getConfigurator().apply(config);
        follower.getConfigurator().apply(config);
    }

    public void stop() {
        master.stopMotor();
    }

    public void setVoltage(Voltage voltage) {
        master.setVoltage(voltage);
    }

    public Command stopCmd() {
        return run(coroutine -> stop()).named(getName() + "Stop");
    }

    public Command roll() {
        return run(coroutine -> setVoltage(RollerConstants.voltage)).named(getName() + "Roll");
    }

    public Command vomit() {
        return run(coroutine -> setVoltage(RollerConstants.voltage.div(-1))).named(getName() + "Vomit");
    }

}
