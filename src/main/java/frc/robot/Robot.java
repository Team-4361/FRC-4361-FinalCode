package frc.robot;
/*----------------------------------------------------------------------------*/

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.libraries.Autonomous.AutonomousMethods;
import frc.libraries.Chassis.TankDrive;
import frc.libraries.Controllers.Drive;
import frc.libraries.Util.Counter;

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
  CANSparkMax shooterSpark1;
  CANSparkMax shooterSpark2;
  CANSparkMax controlPanelSpark;
  
  CANEncoder DriveSparkEnc1;
  CANEncoder DriveSparkEnc2;
  CANEncoder DriveSparkEnc3;
  CANEncoder DriveSparkEnc4;
  CANEncoder shooterSparkEnc1;
  CANEncoder shooterSparkEnc2;
  
  Drive driveTrainL;
  Drive driveTrainR;
  TankDrive theTank;

  Joystick lStick, rStick;
  
  XboxController cont1;

  AutonomousMethods autoMethods;
  Autonomous auto;

  Counter RunNum;

  Shuffleboard board;

  
  TalonSRX intakeTalon1;
  TalonSRX intakeTalon2;
  TalonSRX intakeTalon3;
  TalonSRX conveyerTalon1;
  TalonSRX conveyerTalon2;
  TalonSRX gripperTalon;
  
  Shooter theShooter;
  Intake theIntake;
  ControlPanel theControlPanel;
  
  ColorSensorV3 colorSens;
  I2C.Port i2cPort;
  
  Timer AutoTimer;
  Conveyer theConveyer;

  ShuffleboardLayout autoChooser;
  SendableChooser <String> autoSendable;
  
  DigitalInput intakeLim1, intakeLim2;

  boolean red, green, blue, yellow;

  boolean shooterTest, climberTest, intakeTest, driveTest, conveyerTest, controlTest, fullUse;

  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  @Override
  public void robotInit()
  {
    shooterTest = false;
    climberTest = false;
    intakeTest = false;
    driveTest = false;
    conveyerTest = false;
    controlTest = false;
    fullUse = false;

    //DRIVE TRAIN
    if(driveTest || fullUse)
    {
      ctrlmode = false;
      speedDivider = 1;
      DriveSpark1 = new CANSparkMax(1, MotorType.kBrushless);
      DriveSpark2 = new CANSparkMax(2, MotorType.kBrushless);
      DriveSpark3 = new CANSparkMax(3, MotorType.kBrushless);
      DriveSpark4 = new CANSparkMax(4, MotorType.kBrushless);
      DriveSparkEnc1 = new CANEncoder(DriveSpark1);
      DriveSparkEnc2 = new CANEncoder(DriveSpark2);
      DriveSparkEnc3 = new CANEncoder(DriveSpark3);
      DriveSparkEnc4 = new CANEncoder(DriveSpark4);
      CANSparkMax[] lDriveMotors = {DriveSpark1,DriveSpark2};
      CANSparkMax[] rDriveMotors = {DriveSpark3,DriveSpark4};
      driveTrainL = new Drive(lDriveMotors);
      driveTrainR = new Drive(rDriveMotors);
      theTank = new TankDrive(driveTrainL, driveTrainR, DriveSparkEnc1, DriveSparkEnc2, 6);
    }
    //CONTROL PANEL
    if(controlTest || fullUse)
    {
      controlPanelSpark = new CANSparkMax(5, MotorType.kBrushless);
      i2cPort = I2C.Port.kOnboard;
      colorSens = new ColorSensorV3(i2cPort);
      theControlPanel = new ControlPanel(controlPanelSpark, colorSens);
    }
    //SHOOTER
    if(shooterTest || fullUse)
    {
      shooterSpark1 = new CANSparkMax(6, MotorType.kBrushless);
      shooterSpark2 = new CANSparkMax(7, MotorType.kBrushless);
      shooterSparkEnc1 = new CANEncoder(shooterSpark1);
      shooterSparkEnc2 = new CANEncoder(shooterSpark2);  
      theShooter = new Shooter(shooterSpark1, shooterSpark2);
    }
    //CLIMBER
    if(climberTest || fullUse)
    {
      ClimberSpark1 = new CANSparkMax(11, MotorType.kBrushless);
      ClimberSpark2 = new CANSparkMax(12, MotorType.kBrushless);
      gripperTalon = new TalonSRX(13);
    }
    //INTAKE
    if(intakeTest || fullUse)
    {
      intakeTalon1 = new TalonSRX(8);
      intakeTalon2 = new TalonSRX(14);
      intakeLim1 = new DigitalInput(0);
      intakeLim2 = new DigitalInput(1);
      theIntake = new Intake(intakeTalon1, intakeTalon2, intakeLim1, intakeLim2);
    }
    //CONVEYER
    if(conveyerTest || fullUse)
    {
      conveyerTalon1 = new TalonSRX(9);
      conveyerTalon2 = new TalonSRX(10);
      theConveyer = new Conveyer(conveyerTalon1, conveyerTalon2);
    }
    //Sticks
    lStick = new Joystick(1);
    rStick = new Joystick(2);
    cont1 = new XboxController(0);

    RunNum = new Counter();
    
    
    auto = new Autonomous(theTank, theIntake, theShooter, theConveyer);


    autoSendable = new SendableChooser<>();
    autoSendable.addOption("Don't Move", "Don't Move");
    autoSendable.addOption("Edge of Opposing Trench", "Start 1");
    autoSendable.addOption("Loading Zone", "Start 2");
    autoSendable.addOption("Middle of Field", "Start 3");
    autoSendable.addOption("Edge of Shield Gen", "Start 4");
    autoSendable.addOption("Middle of Power Port", "Start 5");
    autoSendable.addOption("Middle of Friendly Trench", "Start 6");

    SmartDashboard.putData("Autonomous Chooser", autoSendable);

  }

  @Override
  public void autonomousInit()
  {
    
  }

  @Override
  public void autonomousPeriodic()
  {
    if(autoSendable.getSelected() == "Don't Move" || autoSendable.getSelected() ==  "Don't Move")
    {
      auto.runAuto("Don't Move");
    }
    else if(autoSendable.getSelected() == "Edge of Opposing Trench" || autoSendable.getSelected() ==  "Start 1")
    {
      auto.runAuto("Start 1");
    }
    else if(autoSendable.getSelected() == "Loading Zone" || autoSendable.getSelected() ==  "Start 2")
    {
      auto.runAuto("Start 2");
    }
    else if(autoSendable.getSelected() == "Middle of Field" || autoSendable.getSelected() ==  "Start 3")
    {
      auto.runAuto("Start 3");
    }
    else if(autoSendable.getSelected() == "Edge of Shield Gen" || autoSendable.getSelected() ==  "Start 4")
    {
      auto.runAuto("Start 4");
    }
    else if(autoSendable.getSelected() == "Middle of Power Port" || autoSendable.getSelected() ==  "Start 5")
    {
      auto.runAuto("Start 5");
    }
    else if(autoSendable.getSelected() == "Middle of Friendly Trench" || autoSendable.getSelected() ==  "Start 6")
    {
      auto.runAuto("Start 6");
    }
    else
    {
      auto.runAuto("");
    }
  }

  @Override
  public void teleopInit()
  {
    
  }

  @Override
  public void teleopPeriodic()
  {
    if(controlTest || fullUse)
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

      if(theControlPanel.matchColor() == "Blue")
      {
        red = false;
        blue = true;
        green = false;
        yellow = false;
      }
      if(theControlPanel.matchColor() == "Red")
      {
        red = true;
        blue = false;
        green = false;
        yellow = false;
      }
      if(theControlPanel.matchColor() == "Green")
      {
        red = false;
        blue = false;
        green = true;
        yellow = false;
      }
      if(theControlPanel.matchColor() == "Yellow")
      {
        red = false;
        blue = false;
        green = false;
        yellow = true;
      }
  
      SmartDashboard.putBoolean("Color Sensor Blue", blue);
      SmartDashboard.putBoolean("Color Sensor Red", red);
      SmartDashboard.putBoolean("Color Sensor Green", green);
      SmartDashboard.putBoolean("Color Sensor Yellow", yellow);
    }

    if(shooterTest || fullUse)
    {
      //Shooter Code
      if(cont1.getBButton())
      {
        theShooter.Shoot(1);
        //theConveyer.runBothConveyers(1);
      }
      else
      { 
        theShooter.StopShooting();
        //theConveyer.stopConveyer();
      }
    }

    if(conveyerTest || fullUse)
    {
      //Conveyer Code
      boolean conveyerState = false;
      if(cont1.getYButtonPressed())
      {
        conveyerState = !conveyerState;
      }
      if(conveyerState)
      {
        theConveyer.runLowerConveyer(1);
      }
      else if(!conveyerState)
      {
        theConveyer.stopConveyer();
      }
    }

    if(intakeTest || fullUse)
    {
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
    }

    if(driveTest || fullUse)
    {
      if(cont1.getRawButtonPressed(7))
      {
        ctrlmode = !ctrlmode;
      }
      
      if(cont1.getRawButtonPressed(5))
      {
        if(speedDivider > .25)
        {
          speedDivider = speedDivider - .25;
        }
      }
      else if(cont1.getRawButtonPressed(6))
      {
        if(speedDivider < 1)
        {
          speedDivider = speedDivider + .25;
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
