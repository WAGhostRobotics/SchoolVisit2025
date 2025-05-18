package org.firstinspires.ftc.teamcode.testingOp;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DistanceSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@Config
@TeleOp
public class MotorTest extends LinearOpMode {
    public static double power = 0.7;
    public static double distanceThreshold = 5;
    DistanceSensor distanceSensor;
    @Override
    public void runOpMode() throws InterruptedException {
        DcMotorEx motor = hardwareMap.get(DcMotorEx.class, "roller");
        distanceSensor = hardwareMap.get(DistanceSensor.class, "dist");
        waitForStart();
        while (opModeIsActive()) {
            double dist= distanceSensor.getDistance(DistanceUnit.MM);
            if (gamepad1.a) {
                motor.setPower(0.3);
            }
            else if(gamepad1.b) {
                if (dist <= distanceThreshold) {
                    motor.setPower(-0.2);
                }
                else {
                    motor.setPower(-power);
                }
            }
            else {
                motor.setPower(0);
            }

            telemetry.addData("Distance: ", dist);
            telemetry.addData("Power: ", motor.getPower());
            telemetry.update();
        }
    }
}
