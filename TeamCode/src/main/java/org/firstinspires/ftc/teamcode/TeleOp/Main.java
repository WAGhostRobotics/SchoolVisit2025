package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "TeleOp")
public class Main extends LinearOpMode {
    DcMotorEx leftWheel;
    DcMotorEx rightWheel;
    @Override
    public void runOpMode() throws InterruptedException {

        leftWheel = hardwareMap.get(DcMotorEx.class, "left");
        rightWheel = hardwareMap.get(DcMotorEx.class, "right");

        rightWheel.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();
        while (opModeIsActive()) {
//            leftWheel.setPower(gamepad1.left_stick_y);
//            rightWheel.setPower(gamepad1.right_stick_y);

            leftWheel.setPower(gamepad1.left_stick_y - gamepad1.right_stick_x);
            rightWheel.setPower(gamepad1.left_stick_y + gamepad1.right_stick_x);

//            leftWheel.setPower(gamepad1.left_stick_y + gamepad1.left_stick_x);
//            rightWheel.setPower(gamepad1.left_stick_y - gamepad1.left_stick_x);
        }
    }
}
