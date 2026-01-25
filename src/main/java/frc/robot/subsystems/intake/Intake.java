package frc.robot.subsystems.intake;

import static edu.wpi.first.units.Units.Inches;
import static edu.wpi.first.units.Units.RotationsPerSecond;

import org.littletonrobotics.junction.Logger;

import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Distance;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;



public class Intake extends SubsystemBase {
  private final IntakeIO io;
    private final IntakeIOInputsAutoLogged inputs = new IntakeIOInputsAutoLogged();

    private AngularVelocity intakeVelocitySetpoint = RotationsPerSecond.of(60);
    private Distance intakeExtensionSetpoint = Inches.of(0);

    public Intake(IntakeIO io){
        this.io = io;
    }

public void periodic(){
        // Log key variables
        io.updateInputs(inputs);
        Logger.processInputs("Intake", inputs);
        Logger.recordOutput("Intake Velocity Setpoint", intakeVelocitySetpoint);
        Logger.recordOutput("Rack Velocity Setpoint", intakeExtensionSetpoint);
    }

public void setIntakeVelocitySetpoint(AngularVelocity speed){
        intakeVelocitySetpoint = speed;
    }
public void setIntakeExtensionSetpoint(Distance length){
        intakeExtensionSetpoint = length;
    }
public Command runIntake(){
        return Commands.runEnd(() -> this.io.setIntakeVelocity(intakeVelocitySetpoint), () -> this.io.stopIntake());
    }
public Command stopIntake(){
        return Commands.runOnce(() -> this.io.stopIntake());
    }
public Command runIntakeExtension(){
        return Commands.runEnd(() -> this.io.setKicke(intakeExtensionSetpoint), () -> this.io.stopIntakeExtension());
    }
public Command stopIntakeExtension(){
        return Commands.runOnce(() -> this.io.stopIntakeExtension());
    }
}
