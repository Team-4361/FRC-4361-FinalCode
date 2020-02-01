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
import frc.libraries.Chassis.*;
import edu.wpi.first.wpilibj.I2C;

import frc.libraries.Controllers.TurnControl;
import frc.libraries.Controllers.PneumaticsControl;

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
  
  CANEncoder DriveSparkEnc1;
  CANEncoder DriveSparkEnc2;
  CANEncoder DriveSparkEnc3;
  CANEncoder DriveSparkEnc4;
  
  Drive driveTrainL;
  Drive driveTrainR;
  TankDrive theTank;

  Joystick lStick, rStick;
  
  XboxController cont1;
  
  /*
  TalonSRX controlPanelTalon;
  TalonSRX shooterTalon1;
  TalonSRX shooterTalon2;
  TalonSRX intakeTalon;
  TalonSRX conveyerTalon1;
  TalonSRX conveyerTalon2;
  
  PneumaticsControl pneumCont;
  DoubleSolenoid intakeSol;
  
  Shooter theShooter;
  Intake theIntake;
  ControlPanel theControlPanel;
  
  ColorSensorV3 colorSens;
  I2C.Port i2cPort;
  
  Timer AutoTimer;
  Conveyer theConveyer;
  */

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

    /*
    //Talons
    controlPanelTalon = new TalonSRX(5);
    shooterTalon1 = new TalonSRX(6);
    shooterTalon2 = new TalonSRX(7);
    intakeTalon = new TalonSRX(8);
    conveyerTalon1 = new TalonSRX(9);
    conveyerTalon2 = new TalonSRX(10);


    //Pneumatics
    pneumCont = new PneumaticsControl(1, 2, 4.547368);
    intakeSol = new DoubleSolenoid(1, 2);

    //Color Sensor
    i2cPort = I2C.Port.kOnboard;
    colorSens = new ColorSensorV3(i2cPort);
    
    
    //Mechanism Final
    theShooter = new Shooter(shooterTalon1, shooterTalon2);
    theIntake = new Intake(intakeTalon, intakeSol);
    theControlPanel = new ControlPanel(controlPanelTalon, colorSens);
    theConveyer = new Conveyer(conveyerTalon1, conveyerTalon2);
    */

  }

  @Override
  public void autonomousInit()
  {
    /*
    AutoTimer = new Timer();
    AutoTimer.reset();
    */
  }

  @Override
  public void autonomousPeriodic()
  {
    
    AutoTimer.start();
    while(AutoTimer.get() < javax.management.timer.Timer.ONE_SECOND*10)
    {
      theConveyer.runConveyer(1);
      theShooter.Shoot(1);
    }
    while(AutoTimer.get() < javax.management.timer.Timer.ONE_SECOND*12)
    {
      theTank.Turn(.5);
    }
    while(AutoTimer.get() < javax.management.timer.Timer.ONE_SECOND*13)
    {
      theTank.StraightFourEnc(16.125*42);
    }
    while(AutoTimer.get() < javax.management.timer.Timer.ONE_SECOND*14)
    {
      theTank.Turn(-.5);
    }
    while(AutTimer.get() < javax.management.timer.Timer.ONE_SECOND*15)
    {
      theIntake.runIntake(1);
      theTank.StraightFourEnc(4*42);
    }
    
  }

  @Override
  public void teleopInit()
  {
  }

  @Override
  public void teleopPeriodic()
  {
    /*
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
      theIntake.intakePistonMovement(DoubleSolenoid.Value.kForward);
    }
    else if(!intakeState)
    {
      theIntake.intakePistonMovement(DoubleSolenoid.Value.kReverse);
    }
    */

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
