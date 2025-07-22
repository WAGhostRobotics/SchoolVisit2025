package org.firstinspires.ftc.teamcode.Util;

import com.arcrobotics.ftclib.controller.PIDController;

public class PIDWrapper {

    public double P, I, D;
    double target;
    double currentPos;
    PIDController pidController;

    public PIDWrapper() {
        pidController.setIntegrationBounds(-10000000, 10000000);
    }

    public void setTarget(double target) {
        this.target = target;
    }

    public void setP(int p) {
        P = p;
    }

    public void setI(int i) {
        I = i;
    }

    public void setD(int d) {
        D = d;
    }

    public void setCurrentPosition(double currentPosition) {
        currentPos = currentPosition;
    }

    public void calculatePower() {

    }
}
