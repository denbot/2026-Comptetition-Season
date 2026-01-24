package frc.robot.subsystems.auto;

import static edu.wpi.first.units.Units.Degree;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform2d;

public class onTheFlyOffsets {
    public enum trenchOffsets{
        LEFT(0, 0, 0),
        RIGHT(0, 0, 0);

        public final Transform2d transform;

        trenchOffsets(double x, double y, double angle){
            this.transform = new Transform2d(x, y, new Rotation2d(Degree.of(angle)));
        }
    }

    public enum neutralZoneOffsets{
        CENTER(0),
        CLOSE(0),
        FAR(0),
        TOP(0),
        MID(0),
        BOTTOM(0);

        public final double value;
        neutralZoneOffsets(double value){
            this.value = value;
        }
    }

}
