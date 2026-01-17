package frc.robot.subsystems.shooter;

import static frc.robot.util.PhoenixUtil.*;

import com.ctre.phoenix6.BaseStatusSignal;
import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.FeedbackConfigs;
import com.ctre.phoenix6.configs.MotionMagicConfigs;
import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.SoftwareLimitSwitchConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.VelocityTorqueCurrentFOC;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.FeedbackSensorSourceValue;
import com.ctre.phoenix6.signals.GravityTypeValue;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

//import frc.robot.Constants.OperatorConstants;
import edu.wpi.first.math.filter.Debouncer;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Current;

import com.ctre.phoenix6.controls.VelocityTorqueCurrentFOC;
public class ShooterIOTalonFX implements ShooterIO {
    private final TalonFX flywheelMotor =
        new TalonFX(ShooterConstants.FLYWHEEL_MOTOR_ID);

    private final Debouncer flywheelMotorDebounce = new Debouncer(0.5);

    private static final VelocityTorqueCurrentFOC intakeSpin =
        new VelocityTorqueCurrentFOC(0).withAcceleration(ShooterConstants.shooterAcceleration);

    private final StatusSignal<AngularVelocity> leftVelocityRotPerSec = flywheelMotor.getVelocity();
    private final StatusSignal<Current> leftCurrentAmps = flywheelMotor.getSupplyCurrent();

    public ShooterIOTalonFX() {
    var flywheelMotorConfig =
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

    flywheelMotor.setNeutralMode(NeutralModeValue.Coast);
    tryUntilOk(5, () -> flywheelMotor.getConfigurator().apply(flywheelMotorConfig, 0.25));

     BaseStatusSignal.setUpdateFrequencyForAll(
        flywheelMotor.getIsProLicensed().getValue() ? 200 : 50, 
        leftVelocityRotPerSec, 
        leftCurrentAmps

    );


}
}