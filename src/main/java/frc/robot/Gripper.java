package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Gripper
{
    private TalonSRX motor1;

    public void MoveLeft(double amount)
    {
        motor1.set(ControlMode.PercentOutput, amount);
    }

    public void MoveRight(double amount)
    {
        motor1.set(ControlMode.PercentOutput, -(amount));
    }

    public void stopGripper()
    {
        motor1.set(ControlMode.PercentOutput, 0);
    }
}