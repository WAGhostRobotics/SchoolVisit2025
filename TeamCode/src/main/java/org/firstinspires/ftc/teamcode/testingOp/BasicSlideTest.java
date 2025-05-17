package org.firstinspires.ftc.teamcode.testingOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.David;

@TeleOp
public class BasicSlideTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        David.init(hardwareMap);
        waitForStart();
        while (opModeIsActive()) {
            if (gamepad1.right_bumper)
                David.outtake.goUp();
            else if (gamepad1.left_bumper)
                David.outtake.goDown();
            else
                David.outtake.stop();


            telemetry.addData("", David.outtake.getTelemetry());
            telemetry.update();

        }

    }
}
