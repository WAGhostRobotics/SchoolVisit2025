package org.firstinspires.ftc.teamcode.Util;

import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.util.Range;

public class PIDWrapper {

    public double P, I, D;
    double target;
    double currentPos;
    double error;
    PIDController pidController;
    // Pull test
    public PIDWrapper() {
        pidController.setIntegrationBounds(-10000000, 10000000);
    }

    public void setTarget(double target) {
        this.target = target;
    }

    public void setP(double p) {
        P = p;
    }

    public void setI(double i) {
        I = i;
    }

    public void setD(double d) {
        D = d;
    }

    public void setCurrentPosition(double currentPosition) {
        currentPos = currentPosition;
    }

    public double calculatePower() {
        error = target - currentPos;
        double pw = Range.clip(pidController.calculate(0, error), -1, 1);
        return pw;
    }

    public void setPID(double p, double i , double d) {
        setP(p);
        setI(i);
        setD(d);
    }
}
