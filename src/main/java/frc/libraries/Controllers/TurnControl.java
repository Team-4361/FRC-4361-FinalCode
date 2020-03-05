package frc.libraries.Controllers;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.SPI;
import frc.libraries.Util.*;

public class TurnControl //implements PIDOutput
{
	
	private double rotateToAngleRate;
	private AHRS navx;
	private PIDController turnController;
	
	double kP = 0.00;
	double kI = 0.00;
	double kD = 0.00;
	double kF = 0.00;
	double kToleranceDegrees = 0;
	double kSpeed = 0;

	double angle;
	
	public TurnControl()
	{
		Constants cons = new Constants();
		cons.LoadConstants();
		
		kP = cons.GetDouble("kP");
		kI = cons.GetDouble("kI");
		kD = cons.GetDouble("kD");
		kF = cons.GetDouble("kF");
		kToleranceDegrees = cons.GetDouble("kToleranceDegrees");
		
		
		try 
		{
			navx = new AHRS(SPI.Port.kMXP);
		}
		catch (RuntimeException ex )
		{
			DriverStation.reportError("Error instantiating navX-MXP:  " + ex.getMessage(), true);
		}
		
		turnController = new PIDController(kP, kI, kD);
		//turnController.setInputRange(-180.0f,  180.0f);
		//turnController.setOutputRange(-1.0, 1.0);
		turnController.setTolerance(kToleranceDegrees);
		//Enable();
	}
	public TurnControl(double kP, double kI, double kD, double kF, Float kToleranceDegrees)
	{
		this.kP = kP;
		this.kI = kI;
		this.kD = kD;
		this.kF = kF;
		this.kToleranceDegrees = kToleranceDegrees;
		
		
		try 
		{
			navx = new AHRS(SPI.Port.kMXP);
		}
		catch (RuntimeException ex )
		{
			DriverStation.reportError("Error instantiating navX-MXP:  " + ex.getMessage(), true);
		}
		
		turnController = new PIDController(kP, kI, kD);
		turnController.enableContinuousInput(-180.0f,  180.0f);
		turnController.setTolerance(kToleranceDegrees);
		//Enable();
	}
	
	public void ResetNavx()
	{
		navx.reset();
	}
	
	public void SetFromPosition(double value)
	{
		ResetNavx();
		SetAngle(value);
	}
	
	public void SetAngle(double value)
	{
		angle = value;
		turnController.setSetpoint(value);
	}
	
	public double GetAngle()
	{
		return navx.getAngle();
	}
	
	public AHRS GetNavx()
	{
		return navx;
	}
	
	public PIDController GetPIDController()
	{
		return turnController;
	}
	
	public void SetSpeed(double speed)
	{
		this.kSpeed = speed;
	}
	
	public double GetRotateRate()
	{
		rotateToAngleRate = turnController.calculate(navx.getYaw(), angle);
		System.out.println("Calculate: " + rotateToAngleRate);
		return kSpeed * rotateToAngleRate;
	}
	
	public boolean onTarget()
	{
		return turnController.atSetpoint();
	}
	
	/*
	public void Enable()
	{
		turnController.enable();
	}
	
	public void Disable()
	{
		turnController.disable();
	}

	public void pidWrite(double output)
	{
		rotateToAngleRate = output;
	}
	*/

	
}
