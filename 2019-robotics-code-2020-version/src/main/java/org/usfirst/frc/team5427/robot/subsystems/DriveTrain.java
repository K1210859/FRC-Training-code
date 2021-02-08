package org.usfirst.frc.team5427.robot.subsystems;



import org.usfirst.frc.team5427.robot.commands.DriveWithJoystick;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class DriveTrain extends Subsystem {

	public static boolean lowG = false;

	public DifferentialDrive d;
	public SpeedControllerGroup dL;
	public SpeedControllerGroup dR;

	// public SpeedController testMotor;
	public DriveTrain(SpeedControllerGroup drive_Left, SpeedControllerGroup drive_Right, DifferentialDrive drive) {
		this.d = drive;
		this.dL = drive_Left;
		this.dR = drive_Right;
	}

	@Override

	protected void initDefaultCommand() {
		setDefaultCommand(new DriveWithJoystick());
	}

	public void takeJoystickInputs(Joystick joy) {
		
		if(!lowG)
			d.arcadeDrive(joy.getY(), -joy.getZ() * .75);
		else
			d.arcadeDrive(joy.getY() * 0.70, -joy.getZ() * 0.75 * 0.70);
	}

	public static void flipLowLowGear() {
		lowG = !lowG;
	}

	public void tankDrive(double leftSpeed,double rightSpeed)
	{
		dR.set(rightSpeed);
		dL.set(leftSpeed);
	}

	public void stop() {
		d.stopMotor();
	}
}