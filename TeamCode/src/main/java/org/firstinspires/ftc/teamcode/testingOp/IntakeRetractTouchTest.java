package org.firstinspires.ftc.teamcode.testingOp;

import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.gamepad.ToggleButtonReader;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.David;
import org.firstinspires.ftc.teamcode.components.Intake;

@TeleOp
public class IntakeRetractTouchTest extends LinearOpMode {
    public void runOpMode() throws InterruptedException {
        David.init(hardwareMap);
        boolean extend;
        boolean retract;
        ToggleButtonReader retractButton = new ToggleButtonReader(
                new GamepadEx(gamepad1), GamepadKeys.Button.A
        );
        waitForStart();
        while (opModeIsActive()) {
            extend = gamepad1.dpad_left;
            retract = gamepad1.dpad_right;
            retractButton.readValue();

            if (extend) {
                David.intake.extend();
            }
            else if (retract) {
                David.intake.retract();
            }
            else {
                David.intake.stop();
            }

            if (retractButton.wasJustReleased()) {
                David.intake.setTargetPosition(Intake.ExtensionPosition.TRANSFER.getPosition());
            }

            David.intake.update();

            telemetry.addData("", David.intake.getTelemetry());
            telemetry.update();

        }
    }
}
