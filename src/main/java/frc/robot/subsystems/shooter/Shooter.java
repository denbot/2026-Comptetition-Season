package frc.robot.subsystems.shooter;

import static edu.wpi.first.units.Units.RadiansPerSecond;
import static edu.wpi.first.units.Units.RotationsPerSecond;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Current;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Shooter extends SubsystemBase{
    private final ShooterIO io;
    private final ShooterIOInputsAutoLogged inputs = new ShooterIOInputsAutoLogged();

    private AngularVelocity spinnerVelocitySetpoint = RadiansPerSecond.zero();
    private AngularVelocity kickerVelocitySetpoint = RadiansPerSecond.zero();

    public Shooter(ShooterIO io){
        this.io = io;
    }

    @Override
    public void periodic(){
        io.updateInputs(inputs);
        Logger.processInputs("Shooter", inputs);
    }

    public void setSpinnerSpeed(AngularVelocity Speed){
        spinnerVelocitySetpoint = Speed;
    }
    public void setSpinnerSpeed(Double Speed){
        spinnerVelocitySetpoint = RotationsPerSecond.of(Speed);
    }
    
    public void setKickerSpeed(AngularVelocity Speed){
        kickerVelocitySetpoint = Speed;
    }
    public void setKickerSpeed(Double Speed){
        kickerVelocitySetpoint = RotationsPerSecond.of(Speed);
    }

    public Command runSpinnerAtSpeed(){
        return Commands.runOnce(() -> setSpinnerSpeed(60.0));
    }
    public Command stopSpinner(){
        return Commands.runOnce(() -> setSpinnerSpeed(0.0));
    }

    public Command runKickerAtSpeed(){
        return Commands.runOnce(() -> setSpinnerSpeed(60.0));
    }
    public Command stopKicker(){
        return Commands.runOnce(() -> setSpinnerSpeed(0.0));
    }

    public AngularVelocity getSpinnerVelocitySetpoint(){
        return spinnerVelocitySetpoint;
    }
    public AngularVelocity getKickerVelocitySetpoint(){
        return kickerVelocitySetpoint;
    }

    public boolean getSpinnerConnected(){
        return inputs.spinnerMotorConnected;
    }
    public boolean getKickerConnected(){
        return inputs.kickerMotorConnected;
    }

    public AngularVelocity getSpinnerVelocity(){
        return inputs.spinnerRotationSpeed;
    }
    public AngularVelocity getkickerVelocity(){
        return inputs.kickerRotationSpeed;
    }

    public Angle getSpinnerPosition(){
        return inputs.spinnerPositionRots;
    }
    public Angle getkickerPosition(){
        return inputs.kickerPositionRots;
    }

    public Double getSpinnerClosedLoopError(){
        return inputs.spinnerClosedLoopError;
    }
    public Double getKickerClosedLoopError(){
        return inputs.kickerClosedLoopError;
    }

    public Current getSpinnerCurrent(){
        return inputs.spinnerCurrentAmps;
    }
    public Current getKickerCurrent(){
        return inputs.kickerCurrentAmps;
    }
}   
