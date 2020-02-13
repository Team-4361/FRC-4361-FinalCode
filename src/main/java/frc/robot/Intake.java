package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;

public class Intake
{
    private TalonSRX motor1;
    private TalonSRX actuationMotor1;
    private DigitalInput topLim;
    private DigitalInput botLim;

    public Intake(TalonSRX intakeMotor, TalonSRX actuationMotor1, DigitalInput topLim,
            DigitalInput botLim)
    {
        this.motor1 = intakeMotor;
        this.actuationMotor1 = actuationMotor1;
        this.topLim = topLim;
        this.botLim = botLim;
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
        if(!topLim.get())
        {
            actuationMotor1.set(ControlMode.PercentOutput, 1);
        }
        else if(topLim.get())
        {
            actuationMotor1.set(ControlMode.PercentOutput, 0);
        }
    }
    public void intakeActuateDown()
    {
        if(!botLim.get())
        {
            actuationMotor1.set(ControlMode.PercentOutput, -1);
        }
        else if(botLim.get())
        {
            actuationMotor1.set(ControlMode.PercentOutput, 0);
        }
    }
}
