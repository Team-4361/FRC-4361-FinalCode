package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class Intake
{
    private TalonSRX motor1;
    private DoubleSolenoid sol1;

    public Intake(TalonSRX intakeMotor, DoubleSolenoid intakeSolenoid)
    {
        this.motor1 = intakeMotor;
        this.sol1 = intakeSolenoid;
    }

    public void startIntake(int power)
    {
        motor1.set(ControlMode.PercentOutput, power);
    }
    public void stopIntake()
    {
        motor1.set(ControlMode.PercentOutput, 0);
    }

    public void intakePistonMovement(DoubleSolenoid.Value val)
    {
        sol1.set(val);
    }
}
