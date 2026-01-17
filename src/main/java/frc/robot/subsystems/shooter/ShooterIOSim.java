package frc.robot.subsystems.shooter;

import static edu.wpi.first.units.Units.Amp;
import static edu.wpi.first.units.Units.RotationsPerSecond;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.system.plant.LinearSystemId;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.wpilibj.simulation.DCMotorSim;

public class ShooterIOSim implements ShooterIO{
    private static final DCMotor spinnerMotor = DCMotor.getKrakenX60Foc(1);
    private static final DCMotor kickerMotor = DCMotor.getKrakenX60Foc(1);

    private DCMotorSim spinnerMotorSim;
    private DCMotorSim kickerMotorSim;

    private PIDController spinnerController = new PIDController(0, 0, 0);
    private PIDController kickerController = new PIDController(0, 0, 0);

    private double spinnerAppliedVolts = 0.0;
    private double kickerAppliedVolts = 0.0;

    public ShooterIOSim(){
        spinnerMotorSim = 
        new DCMotorSim(
            LinearSystemId.createDCMotorSystem(spinnerMotor, 0.1, 0.1), 
            spinnerMotor);   
        
        kickerMotorSim = 
        new DCMotorSim(
            LinearSystemId.createDCMotorSystem(kickerMotor, 0.1, 0.1), 
            kickerMotor);   
    }

    @Override
    public void updateInputs(ShooterIOInputs inputs){
        spinnerAppliedVolts = spinnerController.calculate(spinnerMotorSim.getAngularVelocity().in(RotationsPerSecond));
        kickerAppliedVolts = kickerController.calculate(kickerMotorSim.getAngularVelocity().in(RotationsPerSecond));

        spinnerMotorSim.setInputVoltage(spinnerAppliedVolts);
        kickerMotorSim.setInputVoltage(kickerAppliedVolts);

        spinnerMotorSim.update(0.02);
        kickerMotorSim.update(0.02);

        inputs.spinnerMotorConnected = true;
        inputs.kickerMotorConnected = true;
        
        inputs.spinnerRotationSpeed = RotationsPerSecond.of(spinnerMotorSim.getAngularVelocityRPM() / 60);
        inputs.kickerRotationSpeed = RotationsPerSecond.of(kickerMotorSim.getAngularVelocityRPM() / 60);
        
        inputs.spinnerCurrentAmps = Amp.of(spinnerMotorSim.getCurrentDrawAmps());
        inputs.kickerCurrentAmps = Amp.of(kickerMotorSim.getCurrentDrawAmps());

        inputs.spinnerPositionRots = spinnerMotorSim.getAngularPosition();
        inputs.kickerPositionRots = kickerMotorSim.getAngularPosition();
        
        inputs.spinnerClosedLoopError = spinnerController.getError();
        inputs.kickerClosedLoopError = kickerController.getError();
    }

    public void setSpinnerVelocity(AngularVelocity velocity){
        spinnerController.setSetpoint(velocity.in(RotationsPerSecond));
    }
    public void stopSpinner(){
        spinnerController.setSetpoint(0);
    }
    public void setKickerVelocity(AngularVelocity velocity){
        kickerController.setSetpoint(velocity.in(RotationsPerSecond));
    }
    public void stopKicker(){
        kickerController.setSetpoint(0);
    }
}
