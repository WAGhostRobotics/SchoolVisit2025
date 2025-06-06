package org.firstinspires.ftc.teamcode.CommandSystem;

/**
 * @author Snakuwul Joshi
 * **/

public class SequentialCommand extends Command {

    Command[] commandList;

    private int index;

    private boolean finished = true;

    public SequentialCommand(Command... commandList){
        this.commandList = commandList;
        index = 0;
    }

    @Override
    public void init() {
        index = 0;
        commandList[0].init();
        finished = false;
    }

    @Override
    public void update() {

        if(!finished) {

            if (commandList[index].isFinished()) {
                if (index < commandList.length - 1) {
                    index++;
                    commandList[index].init();

                } else {
                    finished = true;
                }
            }

            commandList[index].update();
        }
    }

    public void stop(){
        finished = true;
    }

    @Override
    public boolean isFinished() {
        return finished;
    }

    public int getIndex(){
        return index;
    }

    public int getSize(){
        return commandList.length;
    }

    public void initIndex(){
        commandList[index].init();
    }

}