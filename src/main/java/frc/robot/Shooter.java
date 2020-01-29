package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Shooter
{
    private TalonSRX motor1;
    private TalonSRX motor2;

    public Shooter(TalonSRX forwardMotor, TalonSRX backwardMotor)
    {
        this.motor1 = forwardMotor;
        this.motor2 = backwardMotor;
    }

    public void Shoot(int power)
    {
        motor1.set(ControlMode.PercentOutput, power);
        motor2.set(ControlMode.PercentOutput, -power);
    }
    public void StopShooting()
    {
        motor1.set(ControlMode.PercentOutput, 0);
        motor2.set(ControlMode.PercentOutput, 0);
    }

}