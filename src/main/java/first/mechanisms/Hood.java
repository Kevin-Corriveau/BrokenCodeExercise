// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package first.mechanisms;

import static org.wpilib.units.Units.Rotations;

import java.util.function.Supplier;

import org.wpilib.command3.Command;
import org.wpilib.command3.Mechanism;
import org.wpilib.units.measure.Angle;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.FeedbackSensorSourceValue;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import first.constants.HoodConstants;

public class Hood implements Mechanism {

    private final TalonFX motor = new TalonFX(HoodConstants.motorId, HoodConstants.network);
    private final CANcoder encoder = new CANcoder(HoodConstants.encoderId, HoodConstants.network);

    private final PositionVoltage positionControl = new PositionVoltage(0);

    public Hood() {
        TalonFXConfiguration config = new TalonFXConfiguration();

        config.MotorOutput.Inverted = InvertedValue.CounterClockwise_Positive;
        config.MotorOutput.NeutralMode = NeutralModeValue.Brake;

        config.Feedback.SensorToMechanismRatio = HoodConstants.sensorToMechanism;
        config.Feedback.RotorToSensorRatio = HoodConstants.rotorToSensor;

        config.Feedback.FeedbackRemoteSensorID = encoder.getDeviceID();
        config.Feedback.FeedbackSensorSource = FeedbackSensorSourceValue.FusedCANcoder;

        motor.setPosition(HoodConstants.min);

        setDefaultCommand(goTo(HoodConstants.loweredPosition));
    }

    public Angle getSetpoint() {
        return Rotations.of(positionControl.Position);
    }

    public boolean isAtSetpoint() {
        return getSetpoint().minus(motor.getPosition().getValue()).abs(Rotations) < HoodConstants.tolerance.in(Rotations);
    }

    private Angle checkBounds(Angle angle) {
        if(angle.gt(HoodConstants.max))
            angle = HoodConstants.max;

        if(angle.lt(HoodConstants.min))
            angle = HoodConstants.max;

        return angle;
    }

    public void stop() {
        motor.stopMotor();
    }

    public void setAngle(Angle angle) {
        motor.setControl(positionControl.withPosition(checkBounds(angle)));
    }

    public Command stopCmd() {
        return run(coroutine -> stop()).named(getName() + "Stop");
    }

    public Command goTo(Angle angle) {
        return run(coroutine -> {
            setAngle(angle);
            coroutine.yield();
        }).named(getName() + "goTo");
    }

    public Command track(Supplier<Angle> angle) {
        return runRepeatedly(() -> setAngle(angle.get())).named(getName() + "Track");
    }

}
