package frc.robot;

import com.revrobotics.CANEncoder;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.libraries.Autonomous.AutonomousMethods;
import frc.libraries.Controllers.Drive;
import frc.libraries.Controllers.TurnControl;
import frc.libraries.Chassis.TankDrive;
import frc.libraries.Util.*;

public class Autonomous {
    Counter RunNum;

    TankDrive chassis;
    Intake intake;
    Shooter shooter;
    Conveyer conveyer;

    AutonomousMethods methods;
    Timer timer;

    double wheelCirc;
    TurnControl turnControl;

    //PART 1
    public static final int left2ToTrench = 0;
    public static final int left1ToTrench = 0;
    public static final int midToTrench = 0;
    public static final int right1ToTrench = 0;
    public static final int right2ToTrench = 0;
    public static final int toBackTrench = 0;


    public Autonomous(TankDrive chassis, Intake intake, Shooter shooter, Conveyer conveyer, AutonomousMethods methods) {
        RunNum = new Counter();
        this.chassis = chassis;
        this.intake = intake;
        this.shooter = shooter;
        wheelCirc = 6 * Math.PI;
        this.methods = methods;
        turnControl = new TurnControl();
    }

    public void runAuto(String fromPos) {
        timer.stop();
        timer.reset();
        turnControl.ResetNavx();
        if (fromPos == "Left 2") {
            shooter.Shoot(1);
            methods.wait(2.0);
            shooter.StopShooting();
            methods.turnNavx(90, .5);
            methods.wait(1.5);
            methods.goDistance(left2ToTrench, 1);
            methods.wait(1.5);
            methods.turnNavx(-90, .5);
            methods.wait(1.5);
            intake.startIntake(1);
            methods.goDistance(-(toBackTrench), .5);
            methods.wait(1.5);
            intake.stopIntake();
            methods.goDistance(toBackTrench, .5);
            methods.wait(1.5);
            methods.turnNavx(-90, .5);
            methods.wait(1.5);
            methods.goDistance(-(left2ToTrench), 1);
            methods.turnNavx(90, .5);
            methods.wait(1.5);
        } else if (fromPos == "Left 1") {
            shooter.Shoot(1);
            methods.wait(2.0);
            shooter.StopShooting();
            methods.turnNavx(90, .5);
            methods.wait(1.5);
            methods.goDistance(left1ToTrench, 1);
            methods.wait(1.5);
            methods.turnNavx(-90, .5);
            methods.wait(1.5);
            intake.startIntake(1);
            methods.goDistance(-(toBackTrench), .5);
            methods.wait(1.5);
            intake.stopIntake();
            methods.goDistance(toBackTrench, .5);
            methods.wait(1.5);
            methods.turnNavx(-90, .5);
            methods.wait(1.5);
            methods.goDistance(-(left1ToTrench), 1);
            methods.turnNavx(90, .5);
            methods.wait(1.5);
        } else if (fromPos == "Mid") {
            shooter.Shoot(1);
            methods.wait(2.0);
            shooter.StopShooting();
            methods.turnNavx(90, .5);
            methods.wait(1.5);
            methods.goDistance(midToTrench, 1);
            methods.wait(1.5);
            methods.turnNavx(-90, .5);
            methods.wait(1.5);
            intake.startIntake(1);
            methods.goDistance(-(toBackTrench), .5);
            methods.wait(1.5);
            intake.stopIntake();
            methods.goDistance(toBackTrench, .5);
            methods.wait(1.5);
            methods.turnNavx(-90, .5);
            methods.wait(1.5);
            methods.goDistance(-(midToTrench), 1);
            methods.turnNavx(90, .5);
            methods.wait(1.5);
        } else if (fromPos == "Right 1") {
            shooter.Shoot(1);
            methods.wait(2.0);
            shooter.StopShooting();
            methods.turnNavx(90, .5);
            methods.wait(1.5);
            methods.goDistance(right1ToTrench, 1);
            methods.wait(1.5);
            methods.turnNavx(-90, .5);
            methods.wait(1.5);
            intake.startIntake(1);
            methods.goDistance(-(toBackTrench), .5);
            methods.wait(1.5);
            intake.stopIntake();
            methods.goDistance(toBackTrench, .5);
            methods.wait(1.5);
            methods.turnNavx(-90, .5);
            methods.wait(1.5);
            methods.goDistance(-(right1ToTrench), 1);
            methods.turnNavx(90, .5);
            methods.wait(1.5);
        } else if (fromPos == "Right 2") {
            shooter.Shoot(1);
            methods.wait(2.0);
            shooter.StopShooting();
            methods.turnNavx(90, .5);
            methods.wait(1.5);
            methods.goDistance(right2ToTrench, 1);
            methods.wait(1.5);
            methods.turnNavx(-90, .5);
            methods.wait(1.5);
            intake.startIntake(1);
            methods.goDistance(-(toBackTrench), .5);
            methods.wait(1.5);
            intake.stopIntake();
            methods.goDistance(toBackTrench, .5);
            methods.wait(1.5);
            methods.turnNavx(-90, .5);
            methods.wait(1.5);
            methods.goDistance(-(right2ToTrench), 1);
            methods.turnNavx(90, .5);
            methods.wait(1.5);
        }
    }
    
}