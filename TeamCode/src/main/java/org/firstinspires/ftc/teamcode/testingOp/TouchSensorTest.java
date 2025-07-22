package org.firstinspires.ftc.teamcode.testingOp;

import android.text.method.Touch;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.TouchSensor;

@TeleOp
public class TouchSensorTest extends LinearOpMode {
    public void runOpMode() throws InterruptedException {
        TouchSensor touchSensor = hardwareMap.get(TouchSensor.class, "intakeTouch");
        waitForStart();
        while (opModeIsActive()) {
            telemetry.addData("Touch: ", touchSensor.isPressed());
            telemetry.update();
        }
    }

}
