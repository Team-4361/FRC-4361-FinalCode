package frc.robot;

import com.revrobotics.SparkMax;

public class Climber
{
    private SparkMax motor1;
    private SparkMax motor2;

    public Climber(SparkMax motor1, SparkMax motor2)
    {
        this.motor1 = motor1;
        this.motor2 = motor2;
    }

    public void climberUp(double power)
    {
        motor1.set(power);
        motor2.set(-power);        
    }

    public void climberDown(double power)
    {
        motor1.set(-power);
        motor2.set(power);
    }

    public void stopClimber()
    {
        motor1.set(0);
        motor2.set(0);
    }

}