package frc.robot.subsystems.shooter;

import static edu.wpi.first.units.Units.RadiansPerSecond;
import static edu.wpi.first.units.Units.RotationsPerSecond;

import java.util.logging.Logger;

import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Shooter extends SubsystemBase{
    private final ShooterIO io;
    private final ShooterIOInputsAutoLogged inputs = new ShooterIOInputsAutoLogged();

    private AngularVelocity shooterVelocitySetpoint = RadiansPerSecond.zero();

    public Shooter(ShooterIO io){
        this.io = io;
    }

    @Override
    public void periodic(){
        Logger.processInputs("Shooter", inputs)
    }

    public void setShooterSpeed(AngularVelocity Speed){
        shooterVelocitySetpoint = Speed;
    }
    public void setShooterSpeed(Double Speed){
        shooterVelocitySetpoint = RotationsPerSecond.of(Speed);
    }

    public Command runShooterAtSpeed(){
        return Commands.runOnce(() -> setShooterSpeed(60.0));
    }
    public Command stopShooter(){
        return Commands.runOnce(() -> setShooterSpeed(0.0));
    }

    public AngularVelocity getShooterVelocitySetpoint(){
        return shooterVelocitySetpoint;
    }
}
