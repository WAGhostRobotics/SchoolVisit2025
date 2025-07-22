package org.firstinspires.ftc.teamcode.testingOp;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.Util.PIDWrapper;

@TeleOp
@Config
public class PIDTesting extends LinearOpMode {

    public static double P, I, D;
    public static int target = 0;
    PIDWrapper pidController = new PIDWrapper();
    DcMotorEx arm;
    double power;
    @Override
    public void runOpMode() throws InterruptedException {
        arm = hardwareMap.get(DcMotorEx.class, "arm");
        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        waitForStart();
        while (opModeIsActive()) {
//            pidController.setCurrentPosition(arm.getCurrentPosition());
//            pidController.setTarget(target);
//
//            power = pidController.calculatePower();
//            pidController.setPID(P, I, D);
//            arm.setPower(power);

            telemetry.addData("Position", arm.getCurrentPosition());
            telemetry.addData("Power: ", power);
            telemetry.update();
        }
    }
}
