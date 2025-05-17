package org.firstinspires.ftc.teamcode.testingOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.David;
import org.firstinspires.ftc.teamcode.components.Intake;
import org.firstinspires.ftc.teamcode.components.Outtake;

@TeleOp
public class DriveTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        David.init(hardwareMap);
        waitForStart();
        while (opModeIsActive()) {
            double driveTurn = Math.pow(-gamepad1.right_stick_x, 1);
            double driveY = Math.pow(-gamepad1.left_stick_x, 1);
            double driveX = Math.pow(-gamepad1.left_stick_y, 1);

            double magnitude = Math.hypot(driveX, driveY);
            double theta = Math.toDegrees(Math.atan2(driveY, driveX));
            David.drivetrain.drive(magnitude, theta, driveTurn, 0.7);


            telemetry.addData("", David.drivetrain.getTelemetry());
            telemetry.update();
        }
    }
}
