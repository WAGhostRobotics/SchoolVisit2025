package org.firstinspires.ftc.teamcode.CommandBase;

import org.firstinspires.ftc.teamcode.CommandSystem.ParallelCommand;
import org.firstinspires.ftc.teamcode.CommandSystem.RunCommand;
import org.firstinspires.ftc.teamcode.CommandSystem.SequentialCommand;
import org.firstinspires.ftc.teamcode.David;
import org.firstinspires.ftc.teamcode.components.Intake;
import org.firstinspires.ftc.teamcode.components.Outtake;

public class Transfer extends SequentialCommand {
    public Transfer() {
        super(
                new RunCommand(() -> David.outtake.open()),
                new ParallelCommand(
                        new RunCommand(() -> David.intake.intakeStop()),
                        new RunCommand(() -> David.outtake.setArm(Outtake.ArmPos.PREPARE_TRANSFER.getPos())),
                        new RunCommand(() -> David.outtake.setWrist(Outtake.WristPos.PREPARE_TRANSFER.getPos()))
                ),
                new ParallelCommand(
                        new IntakeMove(Intake.ExtensionPosition.TRANSFER.getPosition()),
                        new OuttakeMove(Outtake.SlidePosition.RESET.getPos())
                ),
                new Wait(500),
                new RunCommand(() -> David.outtake.setWrist(Outtake.WristPos.TRANSFER.getPos())),
                new Wait(100),
                new RunCommand(() -> David.outtake.setArm(Outtake.ArmPos.TRANSFER.getPos())),
                new RunCommand(() -> David.outtake.close()),
                new Wait(500),
                new RunCommand(() -> David.intake.setChamber(Intake.ChamberPosition.INTAKE.getPosition()))
        );
    }
}
