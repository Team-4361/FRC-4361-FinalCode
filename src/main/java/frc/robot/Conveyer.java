package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Timer;

public class Conveyer
{

    private DigitalInput conveyerBottomLim;
    private DigitalInput conveyerStartLim;
    private DigitalInput conveyerEndLim;
    private Timer delayTimer;
    boolean hasSeen;
    private TalonSRX firstTalon;

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
        if(automatic)
        {
            //If there are no balls at the end of the conveyer.
            if(conveyerEndLim.get())
            {
                //If there is a ball at the intake and still no ball at the end of the conveyer. Mode MUST be automatic.
                if(!conveyerBottomLim.get() && conveyerEndLim.get() && automatic)
                {
                    firstTalon.set(ControlMode.PercentOutput, Math.abs(power));
                }
                //If the ball is at the base of the conveyer.
                if(!conveyerStartLim.get())
                {
                    hasSeen = true;
                }
                //If the ball is past the base of the conveyer.
                //This stops the conveyer to keep the ball at the correct positon.
                if(hasSeen && conveyerStartLim.get())
                {
                    stopConveyer();
                    hasSeen = false;
                }
            }
            //If a ball is at the end of the conveyer.
            else if(!conveyerEndLim.get())
            {
                stopConveyer();
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





