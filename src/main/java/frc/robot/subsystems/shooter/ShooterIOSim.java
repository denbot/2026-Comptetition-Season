package frc.robot.subsystems.shooter;

import static edu.wpi.first.units.Units.Amp;
import static edu.wpi.first.units.Units.RotationsPerSecond;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.system.plant.LinearSystemId;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.wpilibj.simulation.DCMotorSim;

public class ShooterIOSim implements ShooterIO{
    private static final DCMotor shooterMotor = DCMotor.getKrakenX60Foc(1);

    private DCMotorSim shooterMotorSim;

    private PIDController shooterController = new PIDController(0, 0, 0);

    private double appliedVolts = 0.0;

    public ShooterIOSim(){
        shooterMotorSim = 
        new DCMotorSim(
            LinearSystemId.createDCMotorSystem(shooterMotor, 0.1, 0.1), 
            shooterMotor);   
    }

    public void updateInputs(ShooterIOInputs inputs){
        appliedVolts = shooterController.calculate(shooterMotorSim.getAngularVelocity().in(RotationsPerSecond));

        shooterMotorSim.setInputVoltage(appliedVolts);
        shooterMotorSim.update(0.02);

        inputs.flywheelMotorConnected = true;
        
        inputs.velocityRotPerSec = RotationsPerSecond.of(shooterMotorSim.getAngularVelocityRPM() / 60);
        inputs.currentAmps = Amp.of(shooterMotorSim.getCurrentDrawAmps());

        inputs.positionRots = shooterMotorSim.getAngularPosition();
        inputs.closedLoopErrorRot = shooterController.getError();
    }

    public void setShooterVelocity(AngularVelocity velocity){
        shooterController.setSetpoint(velocity.in(RotationsPerSecond));
    }
}
