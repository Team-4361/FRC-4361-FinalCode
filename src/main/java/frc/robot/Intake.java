package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Timer;

public class Intake
{
    private TalonSRX motor1;
    private TalonSRX actuationMotor1;
    private TalonSRX actuationMotor2;

    public Intake(TalonSRX intakeMotor, TalonSRX actuationMotor1, TalonSRX actuationMotor2)
    {
        this.motor1 = intakeMotor;
        this.actuationMotor1 = actuationMotor1;
        this.actuationMotor2 = actuationMotor2;
    }

    public void startIntake(double power)
    {
        motor1.set(ControlMode.PercentOutput, power);
    }
    public void stopIntake()
    {
        motor1.set(ControlMode.PercentOutput, 0);
    }

    public void intakeActuateUp()
    {
        Timer intakeTimer = new Timer();
        intakeTimer.start();
        while(intakeTimer.get() < .5)
        {
            actuationMotor1.set(ControlMode.PercentOutput, -1);
            actuationMotor2.set(ControlMode.PercentOutput, -1);
        }
        actuationMotor1.set(ControlMode.PercentOutput, 0);
        actuationMotor2.set(ControlMode.PercentOutput, 0);
        intakeTimer.stop();
        intakeTimer.reset();
    }
    public void intakeActuateDown()
    {
        Timer intakeTimer = new Timer();
        intakeTimer.start();
        while(intakeTimer.get() < .5)
        {
            actuationMotor1.set(ControlMode.PercentOutput, -1);
            actuationMotor2.set(ControlMode.PercentOutput, -1);
        }
        actuationMotor1.set(ControlMode.PercentOutput, 0);
        actuationMotor2.set(ControlMode.PercentOutput, 0);
        intakeTimer.stop();
        intakeTimer.reset();
    }
}
