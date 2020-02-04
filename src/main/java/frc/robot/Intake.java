package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Intake
{
    private TalonSRX motor1;

    public Intake(TalonSRX intakeMotor)
    {
        this.motor1 = intakeMotor;
    }

    public void startIntake(double power)
    {
        motor1.set(ControlMode.PercentOutput, power);
    }
    public void stopIntake()
    {
        motor1.set(ControlMode.PercentOutput, 0);
    }
}
