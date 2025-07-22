package org.firstinspires.ftc.teamcode.testingOp;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@Config
@TeleOp
public class MotorTest extends LinearOpMode {
    public static double power = 0.7;
    public static double distanceThreshold = 5;

    @Override
    public void runOpMode() throws InterruptedException {
        DcMotorEx motor1 = hardwareMap.get(DcMotorEx.class, "m1");
        DcMotorEx motor2 = hardwareMap.get(DcMotorEx.class, "m2");
        DcMotorEx motor3 = hardwareMap.get(DcMotorEx.class, "m3");
        DcMotorEx motor4 = hardwareMap.get(DcMotorEx.class, "m4");
        double pos = 0;
        Servo s1 = hardwareMap.get(Servo.class, "s1");
        Servo s2 = hardwareMap.get(Servo.class, "s2");
        Servo s3 = hardwareMap.get(Servo.class, "s3");
        Servo s4 = hardwareMap.get(Servo.class, "s4");
        Servo s5 = hardwareMap.get(Servo.class, "s5");
        Servo s6 = hardwareMap.get(Servo.class, "s6");

        waitForStart();
        while (opModeIsActive()) {
            if (gamepad1.a) {
                motor1.setPower(0.95);
                motor2.setPower(0.95);
                motor3.setPower(0.95);
                motor4.setPower(0.95);
            }
            else if(gamepad1.b) {
                motor1.setPower(-0.95);
                motor2.setPower(-0.95);
                motor3.setPower(-0.95);
                motor4.setPower(-0.95);
            }
            else {
                motor1.setPower(0);
                motor2.setPower(0);
                motor3.setPower(0);
                motor4.setPower(0);
            }

            if (gamepad1.x && pos<=1) {
                pos+=0.001;
            }
            else if (gamepad1.y && pos>=0) {
                pos-=0.001;
            }

            s1.setPosition(pos);
            s2.setPosition(pos);
            s3.setPosition(pos);
            s4.setPosition(pos);
            s5.setPosition(pos);
            s6.setPosition(pos);
            telemetry.addData("Power: ", motor1.getPower());
            telemetry.update();
        }
    }
}
