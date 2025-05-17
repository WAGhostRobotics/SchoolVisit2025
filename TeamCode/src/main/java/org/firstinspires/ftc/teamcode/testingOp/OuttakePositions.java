package org.firstinspires.ftc.teamcode.testingOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.David;
import org.firstinspires.ftc.teamcode.components.Outtake;

@TeleOp
public class OuttakePositions extends LinearOpMode {
    public double armPos = 0;
    public double wristPos = 0;
    public double clawPos = 0;
    public double chamberPos = 0;
    @Override
    public void runOpMode() throws InterruptedException {

        David.init(hardwareMap);
        waitForStart();
        while (opModeIsActive()) {
            if (gamepad1.dpad_up)
                armPos += 0.01;
            else if (gamepad1.dpad_down)
                armPos -= 0.01;


            if (gamepad1.dpad_right)
                wristPos += 0.01;
            else if (gamepad1.dpad_left)
                wristPos -= 0.01;

            if (gamepad1.a)
                clawPos += 0.01;
            else if (gamepad1.b)
                clawPos -= 0.01;

            if (gamepad1.x)
                chamberPos += 0.01;
            else if (gamepad1.y)
                chamberPos -= 0.01;

            David.outtake.setArm(armPos);
            David.outtake.setWrist(wristPos);
            David.outtake.setClaw(clawPos);
            David.intake.setChamber(chamberPos);


            telemetry.addData("", David.intake.getTelemetry());
            telemetry.addData("", David.outtake.getTelemetry());
            telemetry.update();
        }
    }
}
