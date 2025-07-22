package org.firstinspires.ftc.teamcode.components;

import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class Intake {
    private DcMotorEx extension;
    private TouchSensor touchSensor;
    private double DISTANCE_THRESHOLD = 18;
    private DistanceSensor distanceSensor;
    private Servo chamber;
    private double chamberPos;
    private DcMotorEx roller;
    private final double intakePower = 0.7;
    private final double slowIntakePower = 0.3;
    private final double outtakePower = 0.5;
    private final double maxExtensionPower = 1;
    private final int ERROR = 30;
    PIDController extensionController;
    private boolean positionMode;
    public static double P = 0.006, I = 0, D = 0;
    private double defaultPower = 0.6;

    int targetPosition;
    int error;
    int currentPosition;
    double pidPower;

    public enum ExtensionPosition {
        RETRACTED(0),
        TRANSFER(-200),
        BASE_EXTEND(600);
        final int position;

        ExtensionPosition(int position) {this.position = position;}

        public int getPosition() {
            return position;
        }
    }

    public enum ChamberPosition {
        INTAKE(0.585),
        RETRACT(0.0688),
        BASE(0.27),
        OUTTAKE(0.15);
        final double position;

        ChamberPosition(double position) {this.position = position;}

        public double getPosition() {
            return position;
        }
    }

    public void init(HardwareMap hwMap)
    {
        distanceSensor = hwMap.get(DistanceSensor.class, "dist");
        extensionController = new PIDController(P, I, D);
        extensionController.setIntegrationBounds(-10000000, 10000000);
        extension = hwMap.get(DcMotorEx.class, "extension");
        chamber = hwMap.get(Servo.class, "chamber");
        roller = hwMap.get(DcMotorEx.class, "roller");
        touchSensor = hwMap.get(TouchSensor.class, "intakeTouch");
        extension.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        roller.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        roller.setDirection(DcMotorSimple.Direction.REVERSE);
        extension.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        chamber.setPosition(ChamberPosition.RETRACT.getPosition());
    }

    public void setDefaultPower(double pw)
    {
        defaultPower = pw;
    }

    public void extend()
    {
        positionMode = false;
        extension.setPower(-0.95);
    }

    public void retract()
    {
        positionMode = false;
        extension.setPower(0.95);
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
        if (!positionMode) {
            return;
        }

        if (targetPosition == ExtensionPosition.TRANSFER.position && extension.getCurrent(CurrentUnit.AMPS) >= 5) {
            extension.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            extension.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            targetPosition = ExtensionPosition.RETRACTED.position;
        }
        error = targetPosition - currentPosition;
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
        double lowIntakePower = 0.4;
        double dist = distanceSensor.getDistance(DistanceUnit.MM);
        setChamber(ChamberPosition.INTAKE.getPosition());
        if (dist <= DISTANCE_THRESHOLD) {
            roller.setPower(lowIntakePower);
        }
        else {
            roller.setPower(intakePower);
        }
    }

    public void intakeOut() {
        setChamber(ChamberPosition.OUTTAKE.getPosition());
        roller.setPower(-outtakePower);
    }

    public void intakeStop() {
        setChamber(ChamberPosition.BASE.getPosition());
        roller.setPower(0);
    }

    public void setChamber(double pos) {
        chamberPos = pos;
        chamber.setPosition(pos);
    }

    public void resetSlideEncoder() {
        extension.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public String getTelemetry() {
        return  "TargetPos: " + getTargetPosition() +
                "\nCurrentPos: " + currentPosition +
                "\nError:" + error +
                "\nPID Power:" + pidPower +
                "\nSlides Finished: " + isFinished() +
                "\nPositionMode: " + positionMode +
                "\nChamberPos: " + chamberPos +
                "\nPressed: " + touchSensor.isPressed() +
                "\nDistance: " + distanceSensor.getDistance(DistanceUnit.MM) +
                "\nAmps: " + extension.getCurrent(CurrentUnit.AMPS) +
                "\nPower: " + extension.getPower();
    }
}
