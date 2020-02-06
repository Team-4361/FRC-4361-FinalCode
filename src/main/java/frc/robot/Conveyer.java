package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Conveyer
{

    private TalonSRX firstTalon;
    private TalonSRX secondTalon;

    public Conveyer(TalonSRX firstTalon, TalonSRX secondTalon)
    {
        this.firstTalon = firstTalon;
        this.secondTalon = secondTalon;
    }

    public void runLowerConveyer(double power)
    {
        firstTalon.set(ControlMode.PercentOutput, power);
    }

    public void reverseLowerConveyer(double power)
    {
        firstTalon.set(ControlMode.PercentOutput, -power);
    }

    public void runBothConveyers(double power)
    {
        firstTalon.set(ControlMode.PercentOutput, power);
        secondTalon.set(ControlMode.PercentOutput, power);
    }

    public void stopConveyer()
    {
        firstTalon.set(ControlMode.PercentOutput, 0);
        secondTalon.set(ControlMode.PercentOutput, 0);
    }
}





