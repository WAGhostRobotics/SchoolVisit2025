package org.firstinspires.ftc.teamcode.testingOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.David;
import org.firstinspires.ftc.teamcode.components.Outtake;

@TeleOp
public class OuttakePID extends LinearOpMode {
    public static double P = Outtake.P, I= Outtake.I, D= Outtake.D;
    public static int targetPosition = 0;
    @Override
    public void runOpMode() throws InterruptedException {

        David.init(hardwareMap);
        waitForStart();
        while (opModeIsActive()) {
            David.outtake.setTargetPosition(targetPosition);
            David.outtake.setPID(P, I, D);
            David.outtake.update();
            telemetry.addData("", David.outtake.getTelemetry());
            telemetry.update();
        }
    }
}
