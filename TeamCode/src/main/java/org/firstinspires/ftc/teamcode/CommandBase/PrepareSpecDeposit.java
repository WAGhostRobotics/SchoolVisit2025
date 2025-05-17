package org.firstinspires.ftc.teamcode.CommandBase;

import org.firstinspires.ftc.teamcode.CommandSystem.ParallelCommand;
import org.firstinspires.ftc.teamcode.CommandSystem.RunCommand;
import org.firstinspires.ftc.teamcode.CommandSystem.SequentialCommand;
import org.firstinspires.ftc.teamcode.David;
import org.firstinspires.ftc.teamcode.components.Intake;
import org.firstinspires.ftc.teamcode.components.Outtake;

public class PrepareSpecDeposit extends SequentialCommand {
    public PrepareSpecDeposit() {
        super(
                new IntakeMove(Intake.ExtensionPosition.RETRACTED.getPosition()),
                new RunCommand(() -> David.outtake.close()),
                new ParallelCommand(
                        new RunCommand(() -> David.outtake.setArm(Outtake.ArmPos.SPEC_DEPOSIT.getPos())),
                        new RunCommand(() -> David.outtake.setWrist(Outtake.WristPos.SPEC_DEPOSIT.getPos())),
                        new OuttakeMove(Outtake.SlidePosition.SPECIMEN.getPos())
                )
        );
    }

}
