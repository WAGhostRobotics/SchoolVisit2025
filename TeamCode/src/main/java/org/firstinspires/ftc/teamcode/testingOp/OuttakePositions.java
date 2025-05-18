package org.firstinspires.ftc.teamcode.testingOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.David;
import org.firstinspires.ftc.teamcode.components.Intake;
import org.firstinspires.ftc.teamcode.components.Outtake;

@TeleOp
public class OuttakePositions extends LinearOpMode {
    public double armPos = 0;
    public double wristPos = 1;
    public double linkagePos = 0;
    public double clawPos = Outtake.ClawPos.OPEN.getPos();
    public double chamberPos = Intake.ChamberPosition.INTAKE.getPosition();
    @Override
    public void runOpMode() throws InterruptedException {

        David.init(hardwareMap);
        waitForStart();
        while (opModeIsActive()) {
            if (gamepad1.dpad_up && armPos <=1)
                armPos += 0.0008;
            else if (gamepad1.dpad_down && armPos>=0)
                armPos -= 0.0008;


            if (gamepad1.dpad_right && wristPos<=1)
                wristPos += 0.0007;
            else if (gamepad1.dpad_left && wristPos>=0)
                wristPos -= 0.0007;

            if (gamepad1.a && clawPos<=1)
                clawPos += 0.0008;
            else if (gamepad1.b && clawPos>=0)
                clawPos -= 0.0008;

            if (gamepad1.x && chamberPos<=1)
                chamberPos += 0.0008;
            else if (gamepad1.y && chamberPos>=0)
                chamberPos -= 0.0008;

            if (gamepad1.right_bumper)
                linkagePos+=0.0008;
            else if (gamepad1.left_bumper)
                linkagePos-=0.0008;

            David.outtake.setLinkage(linkagePos);
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
