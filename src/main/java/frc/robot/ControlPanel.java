package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.util.Color;

import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorMatch;

public class ControlPanel
{
    private TalonSRX spinner;
    private ColorSensorV3 colorSensor;
    private final ColorMatch colorMatcher;
    private final Color kBlueTarget;
    private final Color kGreenTarget;
    private final Color kRedTarget;
    private final Color kYellowTarget;

    public ControlPanel(TalonSRX spinner, ColorSensorV3 colorSensor)
    {
        this.spinner = spinner;
        this.colorSensor = colorSensor;
        colorMatcher = new ColorMatch();
        kBlueTarget = ColorMatch.makeColor(0.143, 0.427, 0.429);
        kGreenTarget = ColorMatch.makeColor(0.197, 0.561, 0.240);
        kRedTarget = ColorMatch.makeColor(0.561, 0.232, 0.114);
        kYellowTarget = ColorMatch.makeColor(0.361, 0.524, 0.113);
        colorMatcher.addColorMatch(kBlueTarget);
        colorMatcher.addColorMatch(kGreenTarget);
        colorMatcher.addColorMatch(kRedTarget);
        colorMatcher.addColorMatch(kYellowTarget);    

    }
    
    public String matchColor(Color detectedColor)
    {
        ColorMatchResult match = colorMatcher.matchClosestColor(detectedColor);
        if (match.color == kBlueTarget)
        {
            return "Blue";
        }
        else if (match.color == kRedTarget)
        {
            return "Red";
        }
        else if (match.color == kGreenTarget)
        {
            return "Green";
        }
        else if (match.color == kYellowTarget)
        {
            return "Yellow";
        }
        else
        {
            return "Unknown";
        }
    }

    public void Spin(double spinSpeed)
    {
        spinner.set(ControlMode.PercentOutput, spinSpeed);
    }
    
    public void StopSpin()
    {
        spinner.set(ControlMode.PercentOutput, 0);
    }
}