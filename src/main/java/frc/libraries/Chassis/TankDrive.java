package frc.libraries.Chassis;

import com.revrobotics.CANEncoder;

import edu.wpi.first.wpilibj.Encoder;
import frc.libraries.Controllers.*;
import frc.libraries.Util.Constant;

public class TankDrive implements Chassis {
	Drive Left, Right;
	Encoder lEnc, rEnc;
	CANEncoder lFrontEnc, rFrontEnc, lBackEnc, rBackEnc;

	public TankDrive(Drive Left, Drive Right) {
		this.Left = Left;
		this.Right = Right;
	}

	public TankDrive(Drive Left, Drive Right, Encoder lEnc, Encoder rEnc) {
		this(Left, Right);

		this.lEnc = lEnc;
		this.rEnc = rEnc;
	}

	public TankDrive(Drive Left, Drive Right, CANEncoder lFrontEnc, CANEncoder rFrontEnc, CANEncoder lBackEnc,
			CANEncoder rBackEnc)
	{
		this(Left, Right);
		lFrontEnc = lFrontEnc;
		rFrontEnc = rFrontEnc;
		lBackEnc = lBackEnc;
		rBackEnc = rBackEnc;
		
	}
	
	public void Forward(double value)
	{
		drive(value, -value);
	}
	
	public void Straight(double value)
	{
		if(lEnc == null || rEnc == null)
			Forward(value);
		
		double SpeedChange = .1 * value;
		
		if(Math.abs(lFrontEnc.getPosition()) > Math.abs(rFrontEnc.getPosition()))
			drive(value - SpeedChange, -value);
		else if(Math.abs(lFrontEnc.getPosition()) < Math.abs(rFrontEnc.getPosition()))
			drive(value, -(value - SpeedChange));
		else
			Forward(value);
	}

	public void StraightFourEnc(double value)
	{
		if(lFrontEnc == null || rFrontEnc == null || lBackEnc == null || rBackEnc == null)
			Forward(value);
		
		double SpeedChange = .1 * value;
		
		if(Math.abs(lFrontEnc.getPosition()) > Math.abs(rFrontEnc.getPosition()))
			drive(value - SpeedChange, -value);
		else if(Math.abs(lFrontEnc.getPosition()) < Math.abs(rFrontEnc.getPosition()))
			drive(value, -(value - SpeedChange));
		else
			Forward(value);
	}
	
	public void Turn(double value)
	{
		Left.drive(value);
		Right.drive(-value);
	}
	
	public double GetDistance()
	{
		return (Math.max(Math.abs(lEnc.getDistance()), Math.abs(rEnc.getDistance())) / 256) * Constant.AllConstant.GetDouble("WheelDiameter")*Math.PI;
	}
	
	public void Stop()
	{
		Left.drive(0);
		Right.drive(0);
	}
	
	public void drive(double lVal, double rVal)
	{
		Left.drive(lVal);
		Right.drive(rVal);
	}
	
	public boolean HasEncoder()
	{
		return !(lEnc == null || rEnc == null);
	}
	
	public void ResetEncoders()
	{
		lEnc.reset();
		rEnc.reset();
	}
}
