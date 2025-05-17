package org.firstinspires.ftc.teamcode.CommandBase;

import org.firstinspires.ftc.teamcode.CommandSystem.Command;
import org.firstinspires.ftc.teamcode.David;

public class OuttakeMove extends Command {
    int ticks;

    public OuttakeMove(int ticks) {
        this.ticks = ticks;
    }

    @Override
    public void init() {
        David.outtake.setTargetPosition(ticks);
    }


    @Override
    public void update() {
        David.outtake.setTargetPosition(ticks);
    }

    @Override
    public boolean isFinished() {
        return David.outtake.isFinished();
    }

    @Override
    public void stop() {}
}