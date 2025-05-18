package org.firstinspires.ftc.teamcode.TeleOp;

import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.gamepad.ToggleButtonReader;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.CommandBase.PrepareBucketDeposit;
import org.firstinspires.ftc.teamcode.CommandBase.PrepareIntake;
import org.firstinspires.ftc.teamcode.CommandBase.PrepareSpecDeposit;
import org.firstinspires.ftc.teamcode.CommandBase.SpecGrab;
import org.firstinspires.ftc.teamcode.David;

@TeleOp
public class Main extends LinearOpMode {

    // Commands
    PrepareIntake prepareIntake = new PrepareIntake();
    PrepareBucketDeposit bucketDeposit = new PrepareBucketDeposit();

    PrepareSpecDeposit specDeposit = new PrepareSpecDeposit();
    SpecGrab specGrab = new SpecGrab();


    // Buttons
    boolean intakeButton;
    boolean intakeOutButton;
    boolean extendButton;
    boolean retractButton;
    ToggleButtonReader outtakeButton;
    ToggleButtonReader clawButton;
    ToggleButtonReader specGrabButton;
    ToggleButtonReader modeButton;
    ToggleButtonReader resetSlideButton;
    boolean bucketMode = true;



    @Override
    public void runOpMode() throws InterruptedException {
        GamepadEx driverOp = new GamepadEx(gamepad1);
        GamepadEx driverOp2 = new GamepadEx(gamepad2);

        outtakeButton = new ToggleButtonReader(
                driverOp, GamepadKeys.Button.DPAD_UP
        );
        clawButton = new ToggleButtonReader(
                driverOp, GamepadKeys.Button.X
        );
        specGrabButton = new ToggleButtonReader(
                driverOp, GamepadKeys.Button.A
        );
        resetSlideButton = new ToggleButtonReader(
                driverOp2, GamepadKeys.Button.X
        );


        modeButton = new ToggleButtonReader(
                driverOp, GamepadKeys.Button.B
        );



        David.init(hardwareMap);
        waitForStart();
        while (opModeIsActive()) {
            if (modeButton.wasJustReleased())
                bucketMode = !bucketMode;


            // Button Inputs
            intakeButton = gamepad1.dpad_left;
            intakeOutButton = gamepad1.dpad_right;
            extendButton = gamepad1.right_bumper;
            retractButton = gamepad1.left_bumper;
            double driveTurn = Math.pow(-gamepad1.right_stick_x, 1);
            double driveY = Math.pow(-gamepad1.left_stick_x, 1);
            double driveX = Math.pow(-gamepad1.left_stick_y, 1);



            // Movement
            double magnitude = Math.hypot(driveX, driveY);
            double theta = Math.toDegrees(Math.atan2(driveY, driveX));
            David.drivetrain.drive(magnitude, theta, driveTurn, 0.7);



            // Outtake
            if (outtakeButton.wasJustReleased()) {
                if (bucketMode)
                    bucketDeposit.init();
                else
                    specDeposit.init();
            }

            if (clawButton.wasJustReleased()) {
                if (David.outtake.isClawOpen())
                    David.outtake.close();
                else
                    David.outtake.open();
            }



            // Intake
            if (intakeButton) {
                if (!David.outtake.readyForIntake()) {
                    bucketDeposit.stop();
                    specGrab.stop();
                    prepareIntake.init();
                }
                else {
                    David.intake.intakeIn();
                }
            }
            else if (intakeOutButton) {
                if (!David.outtake.readyForIntake()) {
                    prepareIntake.init();
                }
                else {
                    David.intake.intakeOut();
                }
            }
            else {
                David.intake.intakeStop();
            }


            if (extendButton) {
                David.intake.extend();
            }
            else if (retractButton) {
                David.intake.retract();
            }
            else {
                David.intake.stop();
            }


            if (specGrabButton.wasJustReleased()) {
                specGrab.init();
            }



            if (resetSlideButton.wasJustReleased()) {
                David.outtake.resetSlideEncoder();
            }

            David.intake.update();
            David.outtake.update();

            clawButton.readValue();
            resetSlideButton.readValue();
            prepareIntake.update();
            bucketDeposit.update();
            outtakeButton.readValue();
            telemetry.addData("", David.drivetrain.getTelemetry());
            telemetry.update();
        }
    }

}
