package frc.robot.subsystems.auto;

import static edu.wpi.first.units.Units.Degree;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform2d;

public enum onTheFlyOffsets {
    TRENCH_LEFT(0, 0, 0),
    TRENCH_RIGHT(0, 0, 0),

    CLOSE_TOP(neutralOffsets.CLOSE.value, neutralOffsets.TOP.value, 0),
    CLOSE_MID(neutralOffsets.CLOSE.value, neutralOffsets.MID.value, 0),
    CLOSE_BOTTOM(neutralOffsets.CLOSE.value, neutralOffsets.BOTTOM.value, 0),
    CENTER_TOP(neutralOffsets.CENTER.value, neutralOffsets.TOP.value, 0),
    CENTER_MID(neutralOffsets.CENTER.value, neutralOffsets.MID.value, 0),
    CENTER_BOTTOM(neutralOffsets.CENTER.value, neutralOffsets.BOTTOM.value, 0),
    FAR_TOP(neutralOffsets.FAR.value, neutralOffsets.TOP.value, 180),
    FAR_MID(neutralOffsets.FAR.value, neutralOffsets.MID.value, 180),
    FAR_BOTTOM(neutralOffsets.FAR.value, neutralOffsets.BOTTOM.value, 180);

    private enum neutralOffsets{
        CLOSE(0),
        CENTER(0),
        FAR(0),
        TOP(0),
        MID(0),
        BOTTOM(0);

        public final double value;
        neutralOffsets(double value){
            this.value = value;
        }
    }

    public final Transform2d transform;

    onTheFlyOffsets(double x, double y, double angle){
        this.transform = new Transform2d(x, y, new Rotation2d(Degree.of(angle)));
    }
}
