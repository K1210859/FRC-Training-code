package org.usfirst.frc.team5427.robot.subsystems;

import org.usfirst.frc.team5427.robot.Robot;
import org.usfirst.frc.team5427.util.Config;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Wrist extends Subsystem
{
    private SpeedController wM;
    public double wA;
    
    public Wrist(SpeedController wristMotor)
    {
        this.wM = wristMotor;
    }
    
    public void moveWrist(double speed)
    {

        
        if((speed > 0 && Robot.wristPot.get() >= Config.WRIST_LIMIT_TOP) || (speed < 0 && Robot.wristPot.get() <= Config.WRIST_LIMIT_BOTTOM) )
            wM.set(speed);
        else
            wM.set(0);
    }

    public void moveWristNoLimits(double speed)
    {
        wM.set(speed);
    }

    
    @Override
    protected void initDefaultCommand()
    {
        
    }

    public void stop() {
        wM.set(0);
    }
    
    public void setWristAngle(double angle)
    {
        this.wA = angle;
    }
    
    public double getWristAngle()
    {
        return wA;
    }
}