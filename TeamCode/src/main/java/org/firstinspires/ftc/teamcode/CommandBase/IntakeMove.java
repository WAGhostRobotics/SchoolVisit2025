package org.firstinspires.ftc.teamcode.CommandBase;

import org.firstinspires.ftc.teamcode.CommandSystem.Command;
import org.firstinspires.ftc.teamcode.David;

public class IntakeMove extends Command {
    int ticks;

    public IntakeMove(int ticks) {
        this.ticks = ticks;
    }

    @Override
    public void init() {
        David.intake.setTargetPosition(ticks);
    }


    @Override
    public void update() {
        David.intake.update();
    }
    // Might have to change this implementation to have the intake slide position hold constantly

    @Override
    public boolean isFinished() {
        return David.intake.isFinished();
    }

    @Override
    public void stop() {}
}