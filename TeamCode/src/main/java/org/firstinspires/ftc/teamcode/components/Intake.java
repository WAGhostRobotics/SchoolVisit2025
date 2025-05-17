package org.firstinspires.ftc.teamcode.components;

import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

public class Intake {
    private DcMotorEx extension;
    private Servo chamber;
    private double chamberPos;
    private DcMotorEx roller;
    private final double intakePower = 0.7;
    private final double outtakePower = 0.5;
    private final double maxExtensionPower = 1;
    private final int ERROR = 500;
    PIDController extensionController;
    private boolean positionMode;
    public static double P = 0, I = 0, D = 0;
    private double defaultPower = 0.6;

    int targetPosition;
    int error;
    int currentPosition;
    double pidPower;

    public enum ExtensionPosition {
        RETRACTED(0),
        TRANSFER(800),
        BASE_EXTEND(5000);
        final int position;

        ExtensionPosition(int position) {this.position = position;}

        public int getPosition() {
            return position;
        }
    }

    public enum ChamberPosition {
        INTAKE(0),
        TRANSFER(0.5),
        OUTTAKE(0.8);
        final double position;

        ChamberPosition(double position) {this.position = position;}

        public double getPosition() {
            return position;
        }
    }

    public void init(HardwareMap hwMap)
    {
        extensionController = new PIDController(P, I, D);
        extensionController.setIntegrationBounds(-10000000, 10000000);
        extension = hwMap.get(DcMotorEx.class, "extension");
        chamber = hwMap.get(Servo.class, "chamber");
        roller = hwMap.get(DcMotorEx.class, "roller");
        roller.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        extension.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        extension.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public void setDefaultPower(double pw)
    {
        defaultPower = pw;
    }

    public void extend()
    {
        extension.setPower(defaultPower);
    }

    public void retract()
    {
        extension.setPower(-defaultPower);
    }

    public void stop()
    {
        extension.setPower(0);
    }


    public void setTargetPosition(int targetPos)
    {
        targetPosition = targetPos;
        positionMode = true;
    }

    public void update()
    {
        currentPosition = extension.getCurrentPosition();
        if (!positionMode)
            return;
        error = targetPosition - currentPosition;
        if (Math.abs(error) < ERROR) {
            stop();
            return;
        }
        pidPower = Range.clip(extensionController.calculate(0, error), -maxExtensionPower, maxExtensionPower);
        extension.setPower(pidPower);

    }

    public int getTargetPosition()
    {
        return targetPosition;
    }

    public boolean isFinished(){
        return Math.abs(currentPosition - targetPosition)<=ERROR;
    }

    public void setPID(double P, double I, double D) {
        Intake.P = P;
        Intake.I = I;
        Intake.D = D;
        extensionController.setPID(P, I, D);
    }


    public void intakeIn() {

        setChamber(ChamberPosition.INTAKE.getPosition());
        roller.setPower(intakePower);
    }

    public void intakeOut() {
        setChamber(ChamberPosition.OUTTAKE.getPosition());
        roller.setPower(-outtakePower);
    }

    public void intakeStop() {
        setChamber(ChamberPosition.TRANSFER.getPosition());
        roller.setPower(0);
    }

    public void setChamber(double pos) {
        chamberPos = pos;
        chamber.setPosition(pos);
    }

    public String getTelemetry() {
        return  "TargetPos: " + getTargetPosition() +
                "\nCurrentPos: " + currentPosition +
                "\nError:" + error +
                "\nPID Power:" + pidPower +
                "\nSlides Finished: " + isFinished() +
                "\nPositionMode: " + positionMode +
                "\nChamberPos: " + chamberPos;
    }
}
