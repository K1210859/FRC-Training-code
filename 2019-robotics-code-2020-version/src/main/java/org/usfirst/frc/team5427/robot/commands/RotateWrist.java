package org.usfirst.frc.team5427.robot.commands;

import org.usfirst.frc.team5427.robot.Robot;
import org.usfirst.frc.team5427.util.Config;

import edu.wpi.first.wpilibj.command.Command;

public class RotateWrist extends Command
{
    public double s;

    public RotateWrist(double speed)
    {
        requires(Robot.wrist);
        this.s = speed;
    }

    @Override
    protected void initialize()
    {
        this.setInterruptible(true);
    }

    @Override
    protected void execute()
    {
        Robot.wrist.moveWrist(s);
    }

    @Override
    protected boolean isFinished()
    {
        if (s > 0)
            return !Robot.oi.getJoy().getRawButton(Config.BUTTON_WRIST_UP);
        else if (s < 0)
            return !Robot.oi.getJoy().getRawButton(Config.BUTTON_WRIST_DOWN);
        return false;
    }

    @Override
    protected void end()
    {
        Robot.wrist.stop();
    }
}