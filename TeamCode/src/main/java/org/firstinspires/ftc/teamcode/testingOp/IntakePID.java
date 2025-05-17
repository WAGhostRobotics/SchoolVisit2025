package org.firstinspires.ftc.teamcode.testingOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.David;
import org.firstinspires.ftc.teamcode.components.Intake;
import org.firstinspires.ftc.teamcode.components.Outtake;

@TeleOp
public class IntakePID extends LinearOpMode {
    public static double P = Intake.P, I= Intake.I, D= Intake.D;
    public static int targetPosition = 0;
    @Override
    public void runOpMode() throws InterruptedException {

        David.init(hardwareMap);
        waitForStart();
        while (opModeIsActive()) {
            David.intake.setTargetPosition(targetPosition);
            David.intake.setPID(P, I, D);
            David.intake.update();
            telemetry.addData("", David.intake.getTelemetry());
            telemetry.update();
        }
    }
}
