package frc.libraries.Autonomous;

import frc.libraries.Util.*;
import frc.libraries.Chassis.*;
import frc.libraries.Controllers.*;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.Encoder;


public class AutonomousMethods
{
	//Autonomous Variables
	int RunNum;
	boolean hasRun;
	double circumference, robotWidth;
	Timer timer;
	
	//Controls
	Encoder lEnc, rEnc;
	Chassis chassis;
	
	boolean isEnc = false;
	public boolean isReversed = false, isNavx = true;
	
	public TurnControl turnControl;
	
	public AutonomousMethods(int RunNum,  double circumference,  boolean isNavx,  Chassis chassis)
	{
		this.RunNum = RunNum;
		hasRun = false;
		this.circumference = circumference;
		timer = new Timer();
		
		this.chassis = chassis;
		
		this.isNavx = isNavx;
		
		if(isNavx)
		{
			turnControl = new TurnControl();
			turnControl.GetNavx().reset();
		}
	}
	
	public AutonomousMethods(int RunNum,  double circumference,  boolean isNavx,  Chassis chassis,  Encoder lEnc,  Encoder rEnc)
	{
		this(RunNum, circumference, isNavx, chassis);
		
		this.lEnc = lEnc;
		this.rEnc = rEnc;
		isEnc = true;
	}
	
	//Autonomous Commands
	public void goDistance(double dist,  double speed)
	{
		 double timeWarm = .5;
		double timeNeeded = timeWarm + ((dist / circumference) / ((speed * 5310) / (60 * 12.75)));
		
		if(!hasRun)
		{
			chassis.Forward(speed);
			timer.start();
		}

		chassis.Straight(speed);
		
		if(isEnc)
		{
			if(!hasRun)
			{
				lEnc.reset();
				rEnc.reset();
				hasRun = true;
			}
			
			 double large = Math.max(Math.abs(lEnc.getDistance()), Math.abs(rEnc.getDistance())) / 256;
			
			chassis.Straight(speed);
			
			if(large * circumference > dist || timer.get() - 7 > timeNeeded)
			{
				System.out.println("Stop");
				
				chassis.Stop();
				
				hasRun = false;
				
				RunNum++;
			}
		}
		
		//For when the encoders break
		else if(!isEnc)
		{
			 int dist2 = 0;
			
			if(!hasRun)
			{
				timer.start();
				hasRun = true;
			}
			
			if(timer.get()>timeNeeded)
			{
				chassis.Stop();
				
				hasRun = false;
				RunNum++;
				timeNeeded = 0;
				
				timer.stop();
				timer.reset();
			}
		}
	}

	public void turn(double angle,  double speed)
	{
		if(isReversed) angle = -angle;
		
		if(isNavx)
			turnNavx(angle, speed);
		else
			turnEncoder(angle, speed);
	}
	
	public void turnEncoder( double angle,  double speed)
	{
		 double percent = Math.abs(angle)/360;
		if(!hasRun)
		{
			lEnc.reset();
			rEnc.reset();
		}
		if(!hasRun&&angle<0)
		{
			chassis.Turn(speed);
			hasRun = true;
		}
		else if(!hasRun&&angle>0)
		{
			chassis.Turn(-speed);
			hasRun = true;
		}
		else if(!hasRun&&angle==0)
			hasRun=true;
		
		 double large = Math.max(Math.abs(lEnc.get()), Math.abs(rEnc.get())) * 255;
		
		if(large*circumference >= (19.5*Math.PI)*percent)
		{
			chassis.Stop();
			
			hasRun = false;
			RunNum++;
		}
	}

	public void turnNavx( double angle,  double MaxSpeed)
	{
		if(!hasRun)
		{
			turnControl.SetSpeed(MaxSpeed);
			turnControl.SetFromPosition(angle);

			hasRun = true;
		}
	  
		 double RotateRate = turnControl.GetRotateRate();
		chassis.Turn(RotateRate);
		
		/*if(turnControl.onTarget())
		{
			if(timer.get() == 0)
			{
				timer.reset();
				timer.start();
			}
			if(timer.get() > .25)
			{
				chassis.Stop();
				
				timer.stop();
				timer.reset();
				
				hasRun = false;
				RunNum++;
			}
		}
		else
		{
			timer.stop();
			timer.reset();
		}*/
	}
	
	public void wait( double time)
	{
		if(!hasRun)
		{
			timer.start();
			hasRun = true;
		}
		if(timer.get() >= time && hasRun)
		{
			timer.stop();
			timer.reset();
			hasRun = false;
			RunNum++;
		}
	}
	
}
