package org.usfirst.frc.team5427.robot.commands;

import org.usfirst.frc.team5427.robot.Robot;


import edu.wpi.first.wpilibj.command.Command;


public class DriveWithJoystick extends Command
{

	public static boolean lowlowgear = false;


	public DriveWithJoystick()
	{
		requires(Robot.driveTrain);
	}


	@Override
	protected void initialize()
	{
	}


	@Override
	protected void execute()
	{
		Robot.driveTrain.takeJoystickInputs(Robot.oi.getJoy());
	}


	@Override
	protected boolean isFinished()
	{
		return false;
	}


	@Override
	protected void end()
	{
		Robot.driveTrain.stop();
	}


	@Override
	protected void interrupted()
	{
		end();
	}
}
