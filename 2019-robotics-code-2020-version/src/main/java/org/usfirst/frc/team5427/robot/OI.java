/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package org.usfirst.frc.team5427.robot;

import org.usfirst.frc.team5427.robot.commands.*;
import org.usfirst.frc.team5427.util.Config;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.buttons.POVButton;

public class OI
{

	private Joystick j1;


	private Button solenoidActivate;


	private Button solenoidHatchActivate;


	private Button intakeOut;


	private Button intakeIn;
	private Button armDown;


	private Button climberArmDown;

	private Button armUp;

	private Button climberArmUp;

	private Button wristUp;


	private Button wristDown;

	private Button lowlowgear;

	private Button solenoidClimb;

	private POVButton climberForward;

	private POVButton climberBackward;

	public OI()
	{
		//initializes joystick. 
		j1 = new Joystick(Config.JOYSTICK_PORT);

		//initializes buttons. 
		intakeOut = new JoystickButton(j1, Config.BUTTON_INTAKE_OUT);
		intakeIn = new JoystickButton(j1, Config.BUTTON_INTAKE_IN);
		armDown = new JoystickButton(j1, Config.BUTTON_ARM_DOWN);
		climberArmDown = new JoystickButton(j1, Config.BUTTON_CLIMBER_ARM_DOWN);
		armUp = new JoystickButton(j1, Config.BUTTON_ARM_UP);
		climberArmUp = new JoystickButton(j1, Config.BUTTON_CLIMBER_ARM_UP);
		wristUp = new JoystickButton(j1, Config.BUTTON_WRIST_UP);
		wristDown = new JoystickButton(j1, Config.BUTTON_WRIST_DOWN);
		solenoidActivate = new JoystickButton(j1, Config.PCM_JOYSTICK_PORT);
		lowlowgear = new JoystickButton(j1, Config.BUTTON_LOWLOWGEAR);
		solenoidHatchActivate = new JoystickButton(j1,Config.BUTTON_PNEUMATIC_ACTIVATE);
		solenoidClimb = new JoystickButton(j1, Config.SOLENOID_CLIMB_BUTTON);
		climberForward = new POVButton(j1, 0);
		climberBackward = new POVButton(j1, 180);

		//binds buttons to commands. 
		intakeOut.whileHeld(new MoveIntake(Config.INTAKE_SPEED_OUT));
		intakeIn.whileHeld(new MoveIntake(Config.INTAKE_SPEED_IN));
		climberArmDown.whenPressed(new MoveClimberArm(Config.CLIMBER_ARM_SPEED_DOWN));
		climberArmUp.whenPressed(new MoveClimberArm(Config.CLIMBER_ARM_SPEED_UP));
		armDown.whileHeld(new RotateArm(Config.ARM_SPEED_DOWN));
		armUp.whileHeld(new RotateArm(Config.ARM_SPEED_UP));
		lowlowgear.whenPressed(new LowLowGear());
		wristDown.whenPressed(new RotateWrist(Config.WRIST_SPEED_DOWN));
		wristUp.whenPressed(new RotateWrist(Config.WRIST_SPEED_UP));
		climberForward.whenPressed(new MoveClimberWheels(Config.CLIMBER_WHEEL_SPEED_FORWARD));
		climberBackward.whenPressed(new MoveClimberWheels(Config.CLIMBER_WHEEL_SPEED_BACKWARD));
		solenoidActivate.whenPressed(new ActivateSolenoid());
		solenoidHatchActivate.whenPressed(new ActivateSolenoidHatch());
		solenoidClimb.whenPressed(new ActivateSolenoidClimb());
	}

	/**
	 * Accessor for the joystick we use.
	 *
	 * @return the current joystick.
	 */
	public Joystick getJoy()
	{
		return j1;
	}
}