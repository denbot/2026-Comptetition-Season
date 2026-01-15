package frc.robot.subsystems;
import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.Subsystem;

public class Intake implements Subsystem  {
    TalonSRX intakeMotor = new TalonSRX(5);
    
    public Command getDriveForwardCommand(){
        return Commands.runOnce(() -> intakeMotor.set(TalonSRXControlMode.PercentOutput, 0.5));
    }
    public Command getStopCommand(){
        return Commands.runOnce(() -> intakeMotor.set(TalonSRXControlMode.PercentOutput, 0));
    }
}
