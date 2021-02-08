
package org.usfirst.frc.team5427.robot.subsystems;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Intake extends Subsystem {

	private SpeedController topF;
	

    private SpeedController bottomF;
    

	public Intake(SpeedController topFlywheel, SpeedController bottomFlywheel) {
		this.topF = topFlywheel;
		this.bottomF = bottomFlywheel;
	}


	public void setSpeed(double speed) {
		topF.set(-speed);
		bottomF.set(speed);
	}


	@Override
	public void initDefaultCommand() {
	}


	public void stop() {
		setSpeed(0);
	}

}
