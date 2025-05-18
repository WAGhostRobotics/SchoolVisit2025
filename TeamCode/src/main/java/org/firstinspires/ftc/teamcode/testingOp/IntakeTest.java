package org.firstinspires.ftc.teamcode.testingOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.David;

@TeleOp
public class IntakeTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        David.init(hardwareMap);
        waitForStart();
        while (opModeIsActive())
        {
            if (gamepad1.a) {
                David.intake.intakeIn();
            }
            else if (gamepad1.b) {
                David.intake.intakeOut();
            }
            else {
                David.intake.intakeStop();
            }

            if (gamepad1.dpad_left) {
                David.intake.extend();
            }
            else if (gamepad1.dpad_right) {
                David.intake.retract();
            }
            else {
                David.intake.stop();
            }
        }
    }
}
