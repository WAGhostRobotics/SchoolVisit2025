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
import org.firstinspires.ftc.teamcode.components.Intake;
import org.firstinspires.ftc.teamcode.components.Outtake;

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
    ToggleButtonReader hangRetractButton;
    ToggleButtonReader hangPrepButton;
    ToggleButtonReader modeButton;
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
        hangRetractButton = new ToggleButtonReader(
                driverOp, GamepadKeys.Button.A
        );
        hangPrepButton = new ToggleButtonReader(
                driverOp, GamepadKeys.Button.Y
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
            double driveTurn = Math.pow(-gamepad2.right_stick_x, 1);
            double driveY = Math.pow(-gamepad2.left_stick_x, 1);
            double driveX = Math.pow(-gamepad2.left_stick_y, 1);



            // Movement
            double magnitude = Math.hypot(driveX, driveY);
            double theta = Math.toDegrees(Math.atan2(driveY, driveX));
            David.drivetrain.drive(magnitude, theta, driveTurn, 0.9);



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
                if (!bucketMode) {
                    specDeposit.stop();
                    bucketDeposit.stop();
                    specGrab.init();
                }
                else {
                    if (!David.outtake.readyForIntake()) {
                        bucketDeposit.stop();
                        specGrab.stop();
                        prepareIntake.init();
                    } else {
                        David.intake.intakeIn();
                    }
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

            if (hangRetractButton.wasJustReleased()) {
                prepareIntake.stop();
                bucketDeposit.stop();

                David.outtake.setTargetPosition(Outtake.SlidePosition.RETRACTED.getPos());
            }
            else if (hangPrepButton.wasJustReleased()) {
                prepareIntake.stop();
                bucketDeposit.stop();
                David.intake.setTargetPosition(Intake.ExtensionPosition.TRANSFER.getPosition());
                David.outtake.setTargetPosition(Outtake.SlidePosition.HANG.getPos());
            }



            David.intake.update();
            David.outtake.update();

            hangPrepButton.readValue();
            hangRetractButton.readValue();
            clawButton.readValue();
            prepareIntake.update();
            bucketDeposit.update();
            specDeposit.update();
            specGrab.update();
            modeButton.readValue();
            outtakeButton.readValue();
            telemetry.addData("", David.drivetrain.getTelemetry());
            telemetry.addData("", David.outtake.getTelemetry());
            telemetry.addData("", David.intake.getTelemetry());
            telemetry.addData("Bucket: ", bucketMode);
            telemetry.update();
        }
    }

}
