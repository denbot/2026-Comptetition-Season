package frc.robot.subsystems.shooter;

import static frc.robot.util.PhoenixUtil.*;

import com.ctre.phoenix6.BaseStatusSignal;
import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.FeedbackConfigs;
import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.VelocityTorqueCurrentFOC;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.FeedbackSensorSourceValue;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

//import frc.robot.Constants.OperatorConstants;
import edu.wpi.first.math.filter.Debouncer;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Current;

public class ShooterIOTalonFX implements ShooterIO {
    private final TalonFX spinnerMotor =
        new TalonFX(ShooterConstants.SPINNER_MOTOR_ID);
    
    private final TalonFX kickerMotor =
        new TalonFX(ShooterConstants.KICKER_MOTOR_ID);

    private final Debouncer spinnerMotorDebounce = new Debouncer(0.5);
    private final Debouncer kickerMotorDebounce = new Debouncer(0.5);

    private static final VelocityTorqueCurrentFOC spinnerFOC =
        new VelocityTorqueCurrentFOC(0).withAcceleration(ShooterConstants.spinnerAcceleration);
    private static final VelocityTorqueCurrentFOC kickerFOC =
        new VelocityTorqueCurrentFOC(0).withAcceleration(ShooterConstants.kickerAcceleration);

    private final StatusSignal<AngularVelocity> spinnerVelocity = spinnerMotor.getVelocity();
    private final StatusSignal<Current> spinnerCurrentAmps = spinnerMotor.getSupplyCurrent();

    private final StatusSignal<AngularVelocity> kickerVelocity = spinnerMotor.getVelocity();
    private final StatusSignal<Current> kickerCurrentAmps = spinnerMotor.getSupplyCurrent();

    private final StatusSignal<Angle> spinnerPositionRot = spinnerMotor.getPosition();
    private final StatusSignal<Double> spinnerClosedLoopError = spinnerMotor.getClosedLoopError();

    private final StatusSignal<Angle> kickerPositionRot = kickerMotor.getPosition();
    private final StatusSignal<Double> kickerClosedLoopError = kickerMotor.getClosedLoopError();

    public ShooterIOTalonFX() {
        var spinnerMotorConfig =
        new TalonFXConfiguration()
            .withMotorOutput(new MotorOutputConfigs().withInverted(InvertedValue.Clockwise_Positive))
            .withCurrentLimits(
                new CurrentLimitsConfigs()
                    .withStatorCurrentLimitEnable(true)
                    .withStatorCurrentLimit(70))
            .withFeedback(
                new FeedbackConfigs()
                    .withFeedbackSensorSource(FeedbackSensorSourceValue.RotorSensor))
            .withSlot0(
                new Slot0Configs()
                    .withKP(45)
                    .withKD(0)
                    .withKG(0.2));

        var kickerMotorConfig =
        new TalonFXConfiguration()
            .withMotorOutput(new MotorOutputConfigs().withInverted(InvertedValue.Clockwise_Positive))
            .withCurrentLimits(
                new CurrentLimitsConfigs()
                    .withStatorCurrentLimitEnable(true)
                    .withStatorCurrentLimit(70))
            .withFeedback(
                new FeedbackConfigs()
                    .withFeedbackSensorSource(FeedbackSensorSourceValue.RotorSensor))
            .withSlot0(
                new Slot0Configs()
                    .withKP(45)
                    .withKD(0)
                    .withKG(0.2));


        spinnerMotor.setNeutralMode(NeutralModeValue.Coast);
        kickerMotor.setNeutralMode(NeutralModeValue.Coast);
        tryUntilOk(5, () -> spinnerMotor.getConfigurator().apply(spinnerMotorConfig, 0.25));
        tryUntilOk(5, () -> kickerMotor.getConfigurator().apply(kickerMotorConfig, 0.25));

        BaseStatusSignal.setUpdateFrequencyForAll(
            spinnerMotor.getIsProLicensed().getValue() ? 200 : 50, 
            spinnerVelocity, 
            spinnerCurrentAmps);
            
        BaseStatusSignal.setUpdateFrequencyForAll(
            kickerMotor.getIsProLicensed().getValue() ? 200 : 50, 
            kickerVelocity, 
            kickerCurrentAmps);
    }

    @Override
    public void updateInputs(ShooterIOInputs inputs){
        var spinnerMotorStatus = BaseStatusSignal.refreshAll(spinnerVelocity, spinnerCurrentAmps);
        var kickerMotorStatus = BaseStatusSignal.refreshAll(kickerVelocity, kickerCurrentAmps);

        inputs.spinnerMotorConnected = spinnerMotorDebounce.calculate(spinnerMotorStatus.isOK());
        inputs.kickerMotorConnected = kickerMotorDebounce.calculate(kickerMotorStatus.isOK());

        inputs.spinnerRotationSpeed = spinnerVelocity.getValue();
        inputs.kickerRotationSpeed = kickerVelocity.getValue();

        inputs.spinnerPositionRots = spinnerPositionRot.getValue();
        inputs.kickerPositionRots = kickerPositionRot.getValue();

        inputs.spinnerClosedLoopError = spinnerClosedLoopError.getValueAsDouble();
        inputs.kickerClosedLoopError = kickerClosedLoopError.getValueAsDouble();

        inputs.spinnerCurrentAmps = spinnerCurrentAmps.getValue();
        inputs.kickerCurrentAmps = kickerCurrentAmps.getValue();
    }

    public void setSpinnerVelocity(AngularVelocity velocity){
        spinnerMotor.setControl(new VelocityVoltage(velocity));
    }
    
    public void setKickerVelocity(AngularVelocity velocity){
        spinnerMotor.setControl(new VelocityVoltage(velocity));
    }
}