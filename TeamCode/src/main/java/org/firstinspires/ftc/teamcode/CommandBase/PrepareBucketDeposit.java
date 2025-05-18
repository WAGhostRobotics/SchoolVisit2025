package org.firstinspires.ftc.teamcode.CommandBase;

import org.firstinspires.ftc.teamcode.CommandSystem.ParallelCommand;
import org.firstinspires.ftc.teamcode.CommandSystem.RunCommand;
import org.firstinspires.ftc.teamcode.CommandSystem.SequentialCommand;
import org.firstinspires.ftc.teamcode.David;
import org.firstinspires.ftc.teamcode.components.Intake;
import org.firstinspires.ftc.teamcode.components.Outtake;

public class PrepareBucketDeposit extends SequentialCommand {
    public PrepareBucketDeposit() {
        super(
            new Transfer(),
            new OuttakeMove(Outtake.SlidePosition.BUCKET.getPos()),
            new ParallelCommand(
                    new RunCommand(() -> David.outtake.setArm(Outtake.ArmPos.BUCKET.getPos())),
                    new RunCommand(() -> David.outtake.setWrist(Outtake.WristPos.BUCKET.getPos()))
            )
        );
    }

}
