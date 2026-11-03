// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package first.robot;

import org.wpilib.command3.button.CommandXboxController;
import org.wpilib.opmode.PeriodicOpMode;
import org.wpilib.opmode.Teleop;

import first.robot.mechanisms.Flywheel;

@Teleop(textColor = "#833636")
public class TeleopOp extends PeriodicOpMode {

    private final Robot robot;

    private final Flywheel flywheel = new Flywheel();

    private final CommandXboxController copilot = new CommandXboxController(1);

    public TeleopOp(Robot robot) {
        this.robot = robot;
    }

    @Override
    public void start() {
        copilot.rightTrigger().onTrue(robot.shooter.shoot());
        copilot.leftTrigger().and(copilot.rightTrigger()).negate().whileTrue(flywheel.prepare());

        copilot.a().onTrue(robot.roller.roll()).onFalse(robot.roller.stopCmd());
        copilot.b().onTrue(robot.roller.vomit()).onFalse(robot.roller.stopCmd());

    }

}
