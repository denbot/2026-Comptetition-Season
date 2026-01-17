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
        public boolean flywheelMotorConnected = false;
        public AngularVelocity velocityRotPerSec = RevolutionsPerSecond.zero();
        public double closedLoopErrorRot = 0.0;
        public Current currentAmps = Amp.zero();
        public Angle positionRots = Degree.zero();
    }

    //sets shooter velocity in RevolutionsPerSec
      public default void updateInputs(ShooterIOInputs inputs) {}
      public default void setShooterVelocity(AngularVelocity velocity) {}
}