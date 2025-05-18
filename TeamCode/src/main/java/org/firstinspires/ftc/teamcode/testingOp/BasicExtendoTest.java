package org.firstinspires.ftc.teamcode.testingOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.David;

@TeleOp
public class BasicExtendoTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        David.init(hardwareMap);
        waitForStart();
        while (opModeIsActive()) {
            if (gamepad1.a) {
                David.intake.extend();
            }
            else if (gamepad1.b) {
                David.intake.retract();
            }
            else {
                David.intake.stop();
            }


            telemetry.addData("", David.intake.getTelemetry());
            telemetry.update();

        }

    }
}
