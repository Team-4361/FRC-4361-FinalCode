package frc.libraries.Controllers;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANSparkMax;

public class Drive {
	WPI_TalonSRX[] CAN;
	CANSparkMax[] sparks;
	
	static WPI_TalonSRX[] FullCAN;
	static CANSparkMax[] FULLsparks;

	String controllerType;
	
	public Drive(final WPI_TalonSRX[] CAN) {
		this.CAN = CAN;
		controllerType = "TalonSRX";
	}

	public Drive(final CANSparkMax[] CAN) {
		this.sparks = CAN;
		controllerType = "SparkMax";
	}

	public Drive(final int[] nums) {
		if(controllerType == "TalonSRX")
		{
			CAN = new WPI_TalonSRX[nums.length];
			for (int i = 0; i < nums.length; i++) {
				CAN[i] = FullCAN[nums[i]];
			}
		}
		if(controllerType == "SparkMax")
		{	
			sparks = new CANSparkMax[nums.length];
			for (int i = 0; i < nums.length; i++) {
				sparks[i] = FULLsparks[nums[i]];
			}
		}
	}
	

	public void drive(final double val)
	{
		if(controllerType == "TalonSRX")
		{
			for (final WPI_TalonSRX tal : CAN) {
				tal.set(val);
			}
		}
		else if(controllerType == "SparkMax")
		{
			for(final CANSparkMax spark : sparks)
			{
				spark.set(val);
			}
		}
	}

	public double GetSpeed() {
		if (controllerType == "TalonSRX" && CAN != null && CAN[0] != null)
			return CAN[0].get();
		else if(controllerType == "SparkMAX" && sparks != null && sparks[0] != null)
			return sparks[0].get();
		return 0;
	}

	public static void SetFullCAN(final WPI_TalonSRX[] CAN)
	{
		FullCAN = CAN;
	}

	public static void SetFullSparks(final CANSparkMax[] sparks)
	{
		FULLsparks = sparks;
	}
	
	public WPI_TalonSRX[] GetTalons()
	{
		return CAN;
	}
	public CANSparkMax[] GetSparks()
	{
		return sparks;
	}
}