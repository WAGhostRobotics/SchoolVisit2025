package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.components.Intake;
import org.firstinspires.ftc.teamcode.components.MecanumDrive;
import org.firstinspires.ftc.teamcode.components.Outtake;

public class David {
    public static HardwareMap hardwareMap;
    public static MecanumDrive drivetrain;
    public static double movementPower;
    public static Intake intake;
    public static Outtake outtake;

    public static void init(HardwareMap hwMap) {
        hardwareMap = hwMap;
        drivetrain = new MecanumDrive(hwMap);

        outtake = new Outtake();
        outtake.init(hwMap);
        intake = new Intake();
        intake.init(hwMap);
    }

}
