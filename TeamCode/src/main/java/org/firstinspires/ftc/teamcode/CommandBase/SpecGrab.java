package org.firstinspires.ftc.teamcode.CommandBase;

import org.firstinspires.ftc.teamcode.CommandSystem.ParallelCommand;
import org.firstinspires.ftc.teamcode.CommandSystem.RunCommand;
import org.firstinspires.ftc.teamcode.CommandSystem.SequentialCommand;
import org.firstinspires.ftc.teamcode.David;
import org.firstinspires.ftc.teamcode.components.Intake;
import org.firstinspires.ftc.teamcode.components.Outtake;

public class SpecGrab extends SequentialCommand {
    public SpecGrab() {
        super(
                new IntakeMove(Intake.ExtensionPosition.RETRACTED.getPosition()),
                new RunCommand(() -> David.outtake.open()),
                new ParallelCommand(
                        new RunCommand(() -> David.outtake.setArm(Outtake.ArmPos.SPEC_GRAB.getPos())),
                        new RunCommand(() -> David.outtake.setWrist(Outtake.WristPos.SPEC_GRAB.getPos())),
                        new OuttakeMove(Outtake.SlidePosition.RETRACTED.getPos())
                )
        );
    }

}
