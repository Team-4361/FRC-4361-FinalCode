package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Timer;

public class Conveyer
{
    private TalonSRX firstTalon;
    private DigitalInput conveyerBottomLim;
    private DigitalInput conveyerStartLim;
    private DigitalInput conveyerEndLim;
    private Timer delayTimer;
    boolean hasSeen;

    public Conveyer(TalonSRX firstTalon, DigitalInput conveyerBottomLim, DigitalInput conveyerStartLim, DigitalInput conveyerEndLim)
    {
        this.firstTalon = firstTalon;
        this.conveyerBottomLim = conveyerBottomLim;
        this.conveyerStartLim = conveyerStartLim;
        this.conveyerEndLim = conveyerEndLim;
        delayTimer = new Timer();
        delayTimer.reset();
    }
    public Conveyer(TalonSRX firstTalon)
    {
        this.firstTalon = firstTalon;
        delayTimer = new Timer();
        delayTimer.reset();
    }

	public void runConveyer(double power, boolean automatic)
    {
        if(conveyerEndLim.get())
        {
            if(!conveyerBottomLim.get() && conveyerEndLim.get() && automatic)
            {
                firstTalon.set(ControlMode.PercentOutput, power);
            }
            if(!conveyerStartLim.get())
            {
                hasSeen = true;
            }
            if(hasSeen && conveyerStartLim.get())
            {
                    stopConveyer();
                    hasSeen = false;
            }
        }
        else if(!conveyerEndLim.get())
        {
            stopConveyer();
        }
        if(!automatic)
        {
            firstTalon.set(ControlMode.PercentOutput, power);
        }
    }

    public void reverseConveyer(double power)
    {
        firstTalon.set(ControlMode.PercentOutput, -power);
    }
    
    public void stopConveyer()
    {
        firstTalon.set(ControlMode.PercentOutput, 0);
    }
}





