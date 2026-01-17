package frc.robot.subsystems.shooter;

import static edu.wpi.first.units.Units.Amp;
import static edu.wpi.first.units.Units.Degree;
import static edu.wpi.first.units.Units.RevolutionsPerSecond;

import org.littletonrobotics.junction.AutoLog;

import edu.wpi.first.units.measure.Current;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Angle;


public interface ShooterIO {
    @AutoLog
    public static class ShooterIOInputs {
        public boolean spinnerMotorConnected = false;
        public boolean kickerMotorConnected = false;
        public AngularVelocity spinnerRotationSpeed = RevolutionsPerSecond.zero();
        public AngularVelocity kickerRotationSpeed = RevolutionsPerSecond.zero();
        public double spinnerClosedLoopError = 0.0;
        public double kickerClosedLoopError = 0.0;
        public Current spinnerCurrentAmps = Amp.zero();
        public Current kickerCurrentAmps = Amp.zero();
        public Angle spinnerPositionRots = Degree.zero();
        public Angle kickerPositionRots = Degree.zero();
    }

    //sets shooter velocity in RevolutionsPerSec
      public default void updateInputs(ShooterIOInputs inputs) {}
      public default void setSpinnerVelocity(AngularVelocity velocity) {}
      public default void setKickerVelocity(AngularVelocity velocity) {}
}