package org.firstinspires.ftc.teamcode.components;

import android.content.pm.PackageInfo;

import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

public class Outtake {
    private DcMotorEx slide1;
    private DcMotorEx slide2;
    public static double defaultPower = 0.3;
    private Servo arm;
    private Servo arm2;
    private double armPos;
    private Servo wrist;
    private double wristPos;
    private Servo claw;
    private double clawPos;
    private boolean isClawOpen;
    private Servo linkage;
    private double linkagePos;


    PIDController slideControl;
    public boolean positionMode;
    public static double P=0.004, I=0.00008, D=0;
    private int targetPosition;
    private int currentPosition;
    private int error;
    private int ERROR = 100;
    private double pidPower;


    public void init(HardwareMap hwMap) {
        targetPosition = 0;
        slideControl = new PIDController(P, I, D);
        slideControl.setIntegrationBounds(-10000000, 10000000);
        slide1 = hwMap.get(DcMotorEx.class, "slide1");
        slide2 = hwMap.get(DcMotorEx.class, "slide2");
        arm = hwMap.get(Servo.class, "arm");
        arm2 = hwMap.get(Servo.class, "arm2");
        wrist = hwMap.get(Servo.class, "wrist");
        claw = hwMap.get(Servo.class, "claw");
        linkage = hwMap.get(Servo.class, "linkage");
        slide1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slide1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        slide2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        arm.setPosition(ArmPos.RETRACT.getPos());
        wrist.setPosition(WristPos.TRANSFER.getPos());
        claw.setPosition(ClawPos.CLOSE.getPos());
    }

    public enum SlidePosition {
        RESET(-80),
        RETRACTED(0),
        TRANSFER(300),
        PREPARE_TRANSFER(730),
        BUCKET(3000),
        SPECIMEN(1600),
        HANG(1800);
        final int pos;
        SlidePosition(int pos) {this.pos = pos;}
        public int getPos() {
            return pos;
        }
    }

    public enum ArmPos {
        TRANSFER(0.9152),
        RETRACT(0.675),
        SPEC_DEPOSIT(0),
        SPEC_GRAB(0.76),
        BUCKET(0.159);
        final double pos;
        ArmPos(double pos) {this.pos = pos;}
        public double getPos() {
            return pos;
        }
    }

    public enum WristPos {
        BUCKET(0.778),
        SPEC_DEPOSIT(0.8712),
        SPEC_GRAB(0.6696),
        TRANSFER(0.4932);

        final double pos;

        WristPos(double pos) {this.pos = pos;}
        public double getPos() {return pos;}
    }

    public enum ClawPos {
        CLOSE(0),
        OPEN(0.38);
        final double pos;
        ClawPos(double pos) {this.pos = pos;}
        public double getPos() {
            return pos;
        }
    }

    public enum LinkagePos {
        RETRACT(0),
        EXTEND(0.6);
        final double pos;
        LinkagePos(double pos) {this.pos = pos;}
        public double getPos() {
            return pos;
        }
    }




    public void goUp() {
        positionMode = false;
        slide1.setPower(-defaultPower);
        slide2.setPower(defaultPower);
    }

    public void goDown() {
        positionMode = false;
        slide1.setPower(defaultPower);
        slide2.setPower(-defaultPower);
    }

    public void stop() {
        slide1.setPower(0);
        slide2.setPower(0);
    }

    public void setTargetPosition(int pos) {
        targetPosition = pos;
        positionMode = true;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public int getTargetPosition() {
        return targetPosition;
    }

    public void update() {
        currentPosition = slide1.getCurrentPosition();
        if (!positionMode)
            return;
        error = targetPosition - currentPosition;
        pidPower = Range.clip(slideControl.calculate(0, error), -1, 1);
        slide1.setPower(-pidPower);
        slide2.setPower(pidPower);
        if (currentPosition <= SlidePosition.RETRACTED.getPos() && targetPosition==SlidePosition.RESET.getPos()) {
            slide1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            setTargetPosition(SlidePosition.RETRACTED.getPos());
        }
    }

    public boolean isFinished() {
        return Math.abs(error) <= ERROR;
    }

    public void setPID(double P, double I, double D) {
        Intake.P = P;
        Intake.I = I;
        Intake.D = D;
        slideControl.setPID(P, I, D);
    }




    // Arm, Wrist, and Claw

    public void setArm(double pos) {
        arm2.setPosition(1-pos);
        arm.setPosition(pos);
        armPos = pos;
    }

    public double getArmPos() {
        return armPos;
    }

    public void setWrist(double pos) {
        wrist.setPosition(pos);
        wristPos = pos;
    }
    public double getWristPos() {return wristPos;}

    public void open() {
        isClawOpen = true;
        clawPos = ClawPos.OPEN.getPos();
        claw.setPosition(clawPos);
    }

    public void close() {
        isClawOpen = false;
        clawPos = ClawPos.CLOSE.getPos();
        claw.setPosition(clawPos);
    }

    public void setClaw(double pos) {
        claw.setPosition(pos);
        clawPos = pos;
    }

    public boolean isClawOpen() {
        return isClawOpen;
    }

    public boolean readyForIntake() {
        return (targetPosition == SlidePosition.PREPARE_TRANSFER.pos) && (isFinished()) &&
                (armPos == ArmPos.RETRACT.getPos()) && (wristPos == WristPos.TRANSFER.getPos());
    }

    public void setLinkage(double pos) {
        linkagePos = pos;
        linkage.setPosition(pos);
    }

    public void extendLinkage() {
        linkagePos = LinkagePos.EXTEND.getPos();
        linkage.setPosition(LinkagePos.EXTEND.getPos());
    }

    public void retractLinkage() {
        linkagePos = LinkagePos.RETRACT.getPos();
        linkage.setPosition(LinkagePos.RETRACT.getPos());
    }

    public void resetSlideEncoder() {
        slide1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }


    public String getTelemetry() {
        return "Slides" +
                "\nPosition: " + currentPosition +
                "\nTarget: " + targetPosition +
                "\nPower: " + pidPower +
                "\nPositionMode: " + positionMode +
                "\n\nArm: " + armPos +
                "\nWrist: " +wristPos +
                "\nClaw: " + clawPos +
                "\nLinkage: " + linkagePos;
    }


}
