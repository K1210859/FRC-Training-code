/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5427.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import org.usfirst.frc.team5427.robot.commands.auto.MoveClimberLegAuto;

import org.usfirst.frc.team5427.robot.commands.auto.presets.*;

import org.usfirst.frc.team5427.robot.subsystems.Arm;
import org.usfirst.frc.team5427.robot.subsystems.DriveTrain;
import org.usfirst.frc.team5427.robot.subsystems.Intake;
import org.usfirst.frc.team5427.robot.subsystems.Wrist;
import org.usfirst.frc.team5427.util.Config;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends TimedRobot
{


    public static OI oi;

    public static SpeedController driveLeftTop;

    public static SpeedController driveLeftMiddle;

    public static SpeedController driveLeftBottom;

    public static SpeedController driveRightTop;

    public static SpeedController driveRightMiddle;

    public static SpeedController driveRightBottom;

    public static SpeedControllerGroup driveLeft;

    public static SpeedControllerGroup driveRight;

    public static DriveTrain driveTrain;

    public static DifferentialDrive drive;

    public static SpeedController armMotor;

    public static SpeedController climberArmMotor;

    public static SpeedController climberArmMotor1;

    public static SpeedController climberWheelMotor;

    public static SpeedController climberLegMotor;

    public static SpeedController wristMotor;

    public static SpeedController intakeTopMotor;

    public static SpeedController intakeBottomMotor;

    public static Arm arm;

    public static ClimberArm climberArm;

    public static ClimberLeg climberLeg;

    public static ClimberWheel climberWheel;

    public static Wrist wrist;

    public static Intake intake;

    public static Solenoid solenoidGearShifter;

    public static Solenoid solenoidHatchShifter;

    public static Solenoid solenoidLight;

    public static Solenoid solenoidClimb;

    public static AnalogPotentiometer wristPot;

    public static AnalogPotentiometer armPot;

    public static Ultrasonic ultra;

    public static Encoder climb_enc;

    public static CameraServer camServer;

    public static UsbCamera cam1;

    public static UsbCamera cam2;

    private static Encoder encoder;

    @Override
    public void robotInit()
    {
        //initialization of all subsystems
        driveLeftTop = new WPI_VictorSPX(Config.LEFT_TOP_MOTOR);
        driveLeftBottom = new WPI_VictorSPX(Config.LEFT_BOTTOM_MOTOR);
        driveRightMiddle = new WPI_VictorSPX(Config.RIGHT_MIDDLE_MOTOR);
        driveRightBottom = new WPI_VictorSPX(Config.RIGHT_BOTTOM_MOTOR);
        
        driveLeft = new SpeedControllerGroup(driveLeftTop,driveLeftBottom);
        driveRight = new SpeedControllerGroup(driveRightMiddle,driveRightBottom);

        drive = new DifferentialDrive(driveLeft, driveRight);
        driveTrain = new DriveTrain(driveLeft, driveRight, drive);

        climberArmMotor = new WPI_VictorSPX(Config.CLIMBER_ARM_MOTOR_LEFT);
        climberArmMotor1 = new WPI_VictorSPX(Config.CLIMBER_ARM_MOTOR_RIGHT);
        climberArm = new ClimberArm();

        climberWheelMotor = new WPI_VictorSPX(Config.CLIMBER_WHEEL_MOTOR);
        climberWheel = new ClimberWheel();

        climberLegMotor = new WPI_VictorSPX(Config.CLIMBER_LEG_MOTOR);
        climberLeg = new ClimberLeg();

        armMotor = new WPI_VictorSPX(Config.ARM_MOTOR);
        arm = new Arm(armMotor);

        wristMotor = new WPI_VictorSPX(Config.WRIST_MOTOR);
        wrist = new Wrist(wristMotor);

        intakeTopMotor = new WPI_VictorSPX(Config.INTAKE_TOP_MOTOR);
        intakeBottomMotor = new WPI_VictorSPX(Config.INTAKE_BOTTOM_MOTOR);
        intake = new Intake(intakeTopMotor, intakeBottomMotor);

        //ultrasonic initialization
        ultra = new Ultrasonic(Config.ULTRA_PORT2, Config.ULTRA_PORT1);
        ultra.setAutomaticMode(true);
      
        //solenoid initialization
        solenoidGearShifter = new Solenoid(Config.PCM_ID, Config.SOLENOID_ONE_CHANNEL);
        solenoidLight = new Solenoid(Config.PCM_ID, Config.SOLENOID_LIGHT_CHANNEL);
        solenoidHatchShifter = new Solenoid(Config.PCM_ID, Config.SOLENOID_HATCH_CHANNEL);
        solenoidClimb = new Solenoid(Config.PCM_ID, Config.SOLENOID_CLIMB_CHANNEL);
        solenoidLight.set(true);

        //sensor initialization
        climb_enc = new Encoder(Config.ENCODER_CLIMB_PORT_1, Config.ENCODER_CLIMB_PORT_2, false, EncodingType.k4X);
        wristPot = new AnalogPotentiometer(Config.ROTATION_POTENTIOMETER_PORT_WRIST, 121);
        armPot = new AnalogPotentiometer(Config.ROTATION_POTENTIOMETER_PORT_ARM, 118);

        //camera setup
        camServer = CameraServer.getInstance();
        cam1 = camServer.startAutomaticCapture(0);
        cam1.setBrightness (35);
        cam1.setFPS(30);
        cam1.setResolution(100, 100);

        cam2 = camServer.startAutomaticCapture(1);
        cam2.setBrightness(40);
        cam2.setFPS(30);
        cam2.setResolution(100, 100);

        //dreaded shuffleboard preset buttons
        Shuffleboard.getTab("SmartDashboard").add("Hatch LV1", new HatchLevel1()).withWidget(BuiltInWidgets.kCommand);
        Shuffleboard.getTab("SmartDashboard").add("Ship Hatch LV1", new HatchLevel1()).withWidget(BuiltInWidgets.kCommand);
        
        Shuffleboard.getTab("SmartDashboard").add("Hatch LV2", new HatchLevel2()).withWidget(BuiltInWidgets.kCommand);
        Shuffleboard.getTab("SmartDashboard").add("Hatch LV3", new HatchLevel3()).withWidget(BuiltInWidgets.kCommand);

        Shuffleboard.getTab("SmartDashboard").add("Cargo LV1", new CargoLevel1()).withWidget(BuiltInWidgets.kCommand);
        Shuffleboard.getTab("SmartDashboard").add("Cargo LV2", new CargoLevel2()).withWidget(BuiltInWidgets.kCommand);
        Shuffleboard.getTab("SmartDashboard").add("Cargo LV3", new CargoLevel3()).withWidget(BuiltInWidgets.kCommand);

        Shuffleboard.getTab("SmartDashboard").add("Cargo", new IntakeCargoLevel1()).withWidget(BuiltInWidgets.kCommand);
        Shuffleboard.getTab("SmartDashboard").add("Cargo Keep", new IntakeCargoLevel1Keep()).withWidget(BuiltInWidgets.kCommand);
        Shuffleboard.getTab("SmartDashboard").add("Intake Cargo", new IntakeCargoLoadingStation()).withWidget(BuiltInWidgets.kCommand);
        
        Shuffleboard.getTab("SmartDashboard").add("Intake Hatch", new IntakeHatchLoadingStation()).withWidget(BuiltInWidgets.kCommand);
        Shuffleboard.getTab("SmartDashboard").add("Hatch", new IntakeHatchFloor()).withWidget(BuiltInWidgets.kCommand);

        Shuffleboard.getTab("SmartDashboard").add("Travel", new Travel()).withWidget(BuiltInWidgets.kCommand);
        Shuffleboard.getTab("SmartDashboard").add("Cargo Ship Cargo", new CargoShipCargo()).withWidget(BuiltInWidgets.kCommand);
        
        Shuffleboard.getTab("SmartDashboard").add("Climber Leg Level 2", new MoveClimberLegAuto(100)).withWidget(BuiltInWidgets.kCommand);
        Shuffleboard.getTab("SmartDashboard").add("Climber Leg Level 3", new MoveClimberLegAuto(200)).withWidget(BuiltInWidgets.kCommand);

        Shuffleboard.getTab("SmartDashboard").add("Load Hatch", new IntakeHatchLoadingStation()).withWidget(BuiltInWidgets.kCommand);
        Shuffleboard.getTab("SmartDashboard").add("Intake Cargo LS", new IntakeCargoLoadingStation()).withWidget(BuiltInWidgets.kCommand);
        Shuffleboard.getTab("SmartDashboard").add("Cargo Cargo", new CargoShipCargo()).withWidget(BuiltInWidgets.kCommand);
        Shuffleboard.getTab("SmartDashboard").add("Cargo Floor", new CargoFloor()).withWidget(BuiltInWidgets.kCommand);

        encoder = new Encoder(0, 1);

        //this should be initialized last in robotInit()
        oi = new OI();
    }


    @Override
    public void robotPeriodic()
    {
        //needs to be here (scheduler handles commands during robot program). 
        Scheduler.getInstance().run();

        //shuffleboard sensor value displays
        SmartDashboard.putNumber("climb encoder", climb_enc.get());
        SmartDashboard.putNumber("arm pot wpi angle", armPot.get());
        SmartDashboard.putNumber("wrist pot wpi angle", wristPot.get());

        SmartDashboard.putNumber("Ultrasonic", ultra.getRangeInches());
        SmartDashboard.putBoolean("LowLowGear", DriveTrain.lowG);
        SmartDashboard.putBoolean("Distance Hatch", ultra.getRangeInches() >= 11 && ultra.getRangeInches() <= 13);
        SmartDashboard.putBoolean("Distance Cargo Loading", ultra.getRangeInches() >= 19 && ultra.getRangeInches() <= 21);
        SmartDashboard.putBoolean("Distance Ship Shoot", ultra.getRangeInches() >= 23 && ultra.getRangeInches() <= 25);
        SmartDashboard.putNumber("Encoder distance", encoder.getDistance());
    }

    @Override
    public void autonomousInit()
    {

    }


    @Override
    public void autonomousPeriodic()
    {
        Scheduler.getInstance().run();
    }


    @Override
    public void teleopInit()
    {

    }


    @Override
    public void teleopPeriodic()
    {
        Scheduler.getInstance().run();
    }
}
