package org.firstinspires.ftc.teamcode.testingOp;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.David;
import org.firstinspires.ftc.teamcode.components.Outtake;

@Config
@TeleOp
public class OuttakePID extends LinearOpMode {
    public static double P = Outtake.P, I= Outtake.I, D= Outtake.D;
    public static int targetPosition = 0;
    @Override
    public void runOpMode() throws InterruptedException {

        David.init(hardwareMap);
        waitForStart();
        while (opModeIsActive()) {
            David.outtake.setArm(Outtake.ArmPos.SPEC_DEPOSIT.getPos());
            David.outtake.setWrist(Outtake.WristPos.SPEC_DEPOSIT.getPos());

            David.outtake.setTargetPosition(targetPosition);
            David.outtake.setPID(P, I, D);
            David.outtake.update();
            telemetry.addData("", David.outtake.getTelemetry());
            telemetry.update();
        }
    }
}
