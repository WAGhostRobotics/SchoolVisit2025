package org.firstinspires.ftc.teamcode.testingOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

public class ServoTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Servo s1 = hardwareMap.get(Servo.class, "s1");
        double pos = 0;
        s1.setPosition(pos);
        waitForStart();
        while(opModeIsActive()) {
            if (gamepad1.a && pos<=1) {
                pos+=0.001;
            }
            else if (gamepad1.b && pos>=0) {
                pos-=0.001;
            }

            s1.setPosition(pos);
            telemetry.addData("Pos: ", pos);
            telemetry.update();
        }
    }
}
