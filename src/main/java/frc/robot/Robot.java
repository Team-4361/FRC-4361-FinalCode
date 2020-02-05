package frc.robot;
/*----------------------------------------------------------------------------*/

/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

import edu.wpi.first.hal.HAL;
import edu.wpi.first.wpilibj.PWMSpeedController;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Joystick;
import frc.libraries.Controllers.Drive;

import com.ctre.phoenix.*;
import com.ctre.phoenix.sensors.*;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.*;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ColorSensorV3;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import frc.libraries.Autonomous.AutonomousMethods;
import frc.libraries.Chassis.*;
import edu.wpi.first.wpilibj.I2C;

import frc.libraries.Controllers.TurnControl;
import frc.libraries.Controllers.PneumaticsControl;
import frc.libraries.Util.*;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot
{
  boolean ctrlmode;
  double speedDivider;

  CANSparkMax DriveSpark1;
  CANSparkMax DriveSpark2;
  CANSparkMax DriveSpark3;
  CANSparkMax DriveSpark4;
  CANSparkMax ClimberSpark1;
  CANSparkMax ClimberSpark2;
  CANSparkMax shooterTalon1;
  CANSparkMax shooterTalon2;
  
  CANEncoder DriveSparkEnc1;
  CANEncoder DriveSparkEnc2;
  CANEncoder DriveSparkEnc3;
  CANEncoder DriveSparkEnc4;
  
  Drive driveTrainL;
  Drive driveTrainR;
  TankDrive theTank;

  Joystick lStick, rStick;
  
  XboxController cont1;

  AutonomousMethods autoMethods;
  Autonomous auto;

  Counter RunNum;

  Shuffleboard board;

  
  TalonSRX controlPanelTalon;
  TalonSRX intakeTalon1;
  TalonSRX intakeTalon2;
  TalonSRX intakeTalon3;
  TalonSRX conveyerTalon1;
  TalonSRX conveyerTalon2;
  TalonSRX gripperTalon;

  
  PneumaticsControl pneumCont;
  DoubleSolenoid intakeSol;
  
  Shooter theShooter;
  Intake theIntake;
  ControlPanel theControlPanel;
  
  ColorSensorV3 colorSens;
  I2C.Port i2cPort;
  
  Timer AutoTimer;
  Conveyer theConveyer;
  

  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  @Override
  public void robotInit()
  {

    ctrlmode = false;
    speedDivider = 1;
    //Sparks
    DriveSpark1 = new CANSparkMax(1, MotorType.kBrushless);
    DriveSpark2 = new CANSparkMax(2, MotorType.kBrushless);
    DriveSpark3 = new CANSparkMax(3, MotorType.kBrushless);
    DriveSpark4 = new CANSparkMax(4, MotorType.kBrushless);
    ClimberSpark1 = new CANSparkMax(11, MotorType.kBrushless);
    ClimberSpark2 = new CANSparkMax(12, MotorType.kBrushless);
    shooterTalon1 = new CANSparkMax(6, MotorType.kBrushless);
    shooterTalon2 = new CANSparkMax(7, MotorType.kBrushless);

    //Spark Encoders
    DriveSparkEnc1 = new CANEncoder(DriveSpark1);
    DriveSparkEnc2 = new CANEncoder(DriveSpark2);
    DriveSparkEnc3 = new CANEncoder(DriveSpark3);
    DriveSparkEnc4 = new CANEncoder(DriveSpark4);

    //Drive Train Information
    CANSparkMax[] lDriveMotors = {DriveSpark1,DriveSpark2};
    CANSparkMax[] rDriveMotors = {DriveSpark3,DriveSpark4};
    driveTrainL = new Drive(lDriveMotors);
    driveTrainR = new Drive(rDriveMotors);
    theTank = new TankDrive(driveTrainL, driveTrainR, DriveSparkEnc1, DriveSparkEnc2, DriveSparkEnc3, DriveSparkEnc4);
    
    //Sticks
    lStick = new Joystick(1);
    rStick = new Joystick(2);
    cont1 = new XboxController(0);

    //Auto Stuff
    RunNum = new Counter();

    
    //Talons
    controlPanelTalon = new TalonSRX(5);
    intakeTalon1 = new TalonSRX(8);
    intakeTalon1 = new TalonSRX(14);
    intakeTalon1 = new TalonSRX(15);
    conveyerTalon1 = new TalonSRX(9);
    conveyerTalon2 = new TalonSRX(10);
    gripperTalon = new TalonSRX(13);



    //Pneumatics
    pneumCont = new PneumaticsControl(1, 2, 4.547368);
    intakeSol = new DoubleSolenoid(1, 2);

    //Color Sensor
    i2cPort = I2C.Port.kOnboard;
    colorSens = new ColorSensorV3(i2cPort);
    
    
    //Mechanism Final
    theShooter = new Shooter(shooterTalon1, shooterTalon2);
    theIntake = new Intake(intakeTalon1, intakeTalon2, intakeTalon3);
    theControlPanel = new ControlPanel(controlPanelTalon, colorSens);
    theConveyer = new Conveyer(conveyerTalon1, conveyerTalon2);
    

    //TODO
    autoMethods = new AutonomousMethods(RunNum, 6*Math.PI,  true, theTank);
    auto = new Autonomous(theTank, theIntake, theShooter, theConveyer, autoMethods);
    

  }

  @Override
  public void autonomousInit()
  {
    
  }

  @Override
  public void autonomousPeriodic()
  {
    
  }

  @Override
  public void teleopInit()
  {
  }

  @Override
  public void teleopPeriodic()
  {
    
    //Control Panel Code
    if(cont1.getAButton())
    {
      theControlPanel.Spin(1);
    }
    else
    {
      theControlPanel.StopSpin();
    }

    //Shooter Code
    if(cont1.getBButton())
    {
      theShooter.Shoot(1);
    }
    else
    { 
      theShooter.StopShooting();
    }

    //Conveyer Code
    boolean conveyerState = false;
    if(cont1.getYButtonPressed())
    {
      conveyerState = !conveyerState;
    }
    if(conveyerState)
    {
      theConveyer.runConveyer(1);
    }
    else if(!conveyerState)
    {
      theConveyer.stopConveyer();
    }


    //Intake Code
    if(cont1.getBumper(Hand.kRight))
    {
      theIntake.startIntake(1);
    }
    else
    {
      theIntake.stopIntake();
    }
    boolean intakeState = false;
    if(cont1.getXButtonPressed())
    {
      intakeState = !intakeState;
    }
    if(intakeState)
    {
      
      theIntake.intakeActuateUp();

    }
    else if(!intakeState)
    {
      theIntake.intakeActuateDown();
    }
    

    if(cont1.getRawButtonPressed(7))
    {
      ctrlmode = !ctrlmode;
      System.out.println("ctrlMode Attempt Change");
    }


    
    if(cont1.getRawButtonPressed(5))
    {
      if(speedDivider > .25)
      {
        speedDivider = speedDivider - .25;
        System.out.println("Speed 25% lower Successfully");
      }
    }
    else if(cont1.getRawButtonPressed(6))
    {
      if(speedDivider < 1)
      {
        speedDivider = speedDivider + .25;
        System.out.println("Speed 25% higher Successfully");
      }
    }

    if(ctrlmode)
    {
      theTank.drive(-(cont1.getY(Hand.kLeft))*speedDivider, (cont1.getY(Hand.kRight))*speedDivider);
      //System.out.println("ctrlMode Change Success to Sticks");
    }
    if(!ctrlmode)
    {
      theTank.drive(-(lStick.getY())*speedDivider, (rStick.getY())*speedDivider);
      //System.out.println("ctrlMode Change Success to Controller");
    }

  }

  @Override
  public void testInit()
  {
  }

  @Override
  public void testPeriodic()
  {
  }

}
