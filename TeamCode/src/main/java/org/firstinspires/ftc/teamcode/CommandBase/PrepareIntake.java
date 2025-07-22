package org.firstinspires.ftc.teamcode.CommandBase;

import org.firstinspires.ftc.teamcode.CommandSystem.ParallelCommand;
import org.firstinspires.ftc.teamcode.CommandSystem.RunCommand;
import org.firstinspires.ftc.teamcode.CommandSystem.SequentialCommand;
import org.firstinspires.ftc.teamcode.David;
import org.firstinspires.ftc.teamcode.components.Intake;
import org.firstinspires.ftc.teamcode.components.Outtake;

public class PrepareIntake extends SequentialCommand {
    public PrepareIntake() {
        super(
                new RunCommand(() -> David.outtake.open()),
                new RunCommand(() -> David.outtake.setArm(Outtake.ArmPos.RETRACT.getPos())),
                new Wait(300),
                new ParallelCommand(
                        new OuttakeMove(Outtake.SlidePosition.PREPARE_TRANSFER.getPos()),
                        new RunCommand(() -> David.outtake.setWrist(Outtake.WristPos.TRANSFER.getPos()))
                ),
                new IntakeMove(Intake.ExtensionPosition.BASE_EXTEND.getPosition())
        );
    }
}
