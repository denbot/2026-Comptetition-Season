package frc.robot.subsystems.auto;

import static edu.wpi.first.units.Units.Degree;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform2d;
import frc.robot.subsystems.auto.onTheFlyOffsets.neutralZoneOffsets;
import frc.robot.subsystems.auto.onTheFlyOffsets.trenchOffsets;

public class onTheFlySetpoints {
    public static final AprilTagFieldLayout fieldLayout = AprilTagFieldLayout.loadField(AprilTagFields.k2026RebuiltAndymark);

    // Trench Locations
    public enum trenchSetpoints{
        TOP_LEFT(7, 17, trenchOffsets.LEFT),
        TOP_RIGHT(6, 28, trenchOffsets.RIGHT),
        BOT_LEFT(12, 22, trenchOffsets.LEFT),
        BOT_RIGHT(1, 23, trenchOffsets.RIGHT);

        public final int redAprilTag;
        public final int blueAprilTag;
        public final trenchOffsets offset;
        public final Pose2d redAlignment;
        public final Pose2d blueAlignment;

        trenchSetpoints(int redAprilTag, int blueAprilTag, trenchOffsets offset){
            this.redAprilTag = redAprilTag;
            this.blueAprilTag = blueAprilTag;
            this.offset = offset;
            this.redAlignment = getAlignmentPose(redAprilTag, this.offset.transform);
            this.blueAlignment = getAlignmentPose(blueAprilTag, this.offset.transform);
        }
    }
     
    // Neutral Zone Locations
    public enum neutralSetpoints{
        CLOSE_TOP(neutralZoneOffsets.CLOSE, neutralZoneOffsets.TOP, 6, 17),
        CLOSE_MID(neutralZoneOffsets.CLOSE, neutralZoneOffsets.MID, 4, 20),
        CLOSE_BOTTOM(neutralZoneOffsets.CLOSE, neutralZoneOffsets.BOTTOM, 1, 22),
        CENTER_TOP(neutralZoneOffsets.CENTER, neutralZoneOffsets.TOP, 6, 17),
        CENTER_MID(neutralZoneOffsets.CENTER, neutralZoneOffsets.MID, 4, 20),
        CENTER_BOTTOM(neutralZoneOffsets.CENTER, neutralZoneOffsets.BOTTOM, 1, 22),
        FAR_TOP(neutralZoneOffsets.FAR, neutralZoneOffsets.TOP, 17, 6),
        FAR_MID(neutralZoneOffsets.FAR, neutralZoneOffsets.MID, 20, 4),
        FAR_BOTTOM(neutralZoneOffsets.FAR, neutralZoneOffsets.BOTTOM, 22, 1);

        public final int redAprilTag;
        public final int blueAprilTag;
        public final Transform2d offset;
        public final Pose2d redAlignment;
        public final Pose2d blueAlignment;

        neutralSetpoints(neutralZoneOffsets xOffset, neutralZoneOffsets yOffset, int redAprilTag, int blueAprilTag){
            this.redAprilTag = redAprilTag;
            this.blueAprilTag = blueAprilTag;
            this.offset = new Transform2d(xOffset.value, yOffset.value, new Rotation2d(Degree.of(0)));
            this.redAlignment = getAlignmentPose(redAprilTag, this.offset);
            this.blueAlignment = getAlignmentPose(blueAprilTag, this.offset);
        }
    }
    
    private static Pose2d getAlignmentPose(int apriltag, Transform2d offset){
        return fieldLayout.getTagPose(apriltag).get().toPose2d().transformBy(offset);
    }
}
